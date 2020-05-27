package site.alanliang.geekblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.alanliang.geekblog.anntation.AccessLog;
import site.alanliang.geekblog.anntation.OperationLog;
import site.alanliang.geekblog.common.JsonResult;
import site.alanliang.geekblog.model.User;
import site.alanliang.geekblog.exception.BadRequestException;
import site.alanliang.geekblog.service.*;
import site.alanliang.geekblog.utils.MD5Util;
import site.alanliang.geekblog.vo.IndexVO;
import site.alanliang.geekblog.vo.InitInfoVO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/6 11:46
 * Version 1.0
 **/
@Controller
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private AccessLogService accessLogService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private OperationLogService operationLogService;

    @ResponseBody
    @GetMapping("/init")
    public ResponseEntity<Object> init(HttpSession session, HttpServletRequest request) {
        Long userId = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                userId = Long.valueOf(cookie.getValue());
                break;
            }
        }
        if (userId == null) {
            Object o = session.getAttribute("user");
            if (o != null) {
                User user = (User) o;
                userId = user.getId();
            }
        }
        if (userId != null) {
            InitInfoVO initInfoVO = menuService.menu(userId);
            return new ResponseEntity<>(initInfoVO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("当前用户未登录，菜单初始化失败", HttpStatus.BAD_REQUEST);
        }
    }

    @OperationLog("访问后台首页")
    @GetMapping
    public String toIndex() {
        return "admin/home/index";
    }

    @ResponseBody
    @GetMapping("/indexData")
    public ResponseEntity<Object> index() {
        IndexVO indexVO = new IndexVO();
        indexVO.setArticleCount(articleService.countAll());
        indexVO.setCategoryCount(categoryService.countAll());
        indexVO.setTagCount(tagService.countAll());
        indexVO.setCommentCount(commentService.countAll());
        indexVO.setUserCount(userService.countAll());
        indexVO.setVisitorCount(visitorService.countAll());
        indexVO.setViewCount(accessLogService.countAll());
        indexVO.setMessageCount(messageService.countAll());
        indexVO.setAccessLogs(accessLogService.listNewest());
        indexVO.setOperationLogs(operationLogService.listNewest());
        indexVO.setComments(commentService.listNewest());
        indexVO.setMessages(messageService.listNewest());
        indexVO.setArticles(articleService.listNewest());
        indexVO.setFrontViews(accessLogService.countByLast7Days());
        indexVO.setBackViews(operationLogService.countByLast7Days());
        return new ResponseEntity<>(indexVO, HttpStatus.OK);
    }

}
