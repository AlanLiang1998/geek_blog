package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.dao.*;
import site.alanliang.geekblog.model.Article;
import site.alanliang.geekblog.model.Comment;
import site.alanliang.geekblog.model.User;
import site.alanliang.geekblog.model.Visitor;
import site.alanliang.geekblog.query.CommentQuery;
import site.alanliang.geekblog.service.CommentService;
import site.alanliang.geekblog.utils.LinkedListUtil;
import site.alanliang.geekblog.utils.UserInfoUtil;
import site.alanliang.geekblog.vo.AuditVO;

import java.util.Date;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/17 9:33
 * Version 1.0
 **/
@Service
@CacheConfig(cacheNames = "comment")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private VisitorMapper visitorMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void save(Comment comment) {
        commentMapper.insert(comment);
        //评论量+1
        QueryWrapper<Article> articleWrapper = new QueryWrapper<>();
        articleWrapper.select(Article.Table.COMMENTS).eq(Article.Table.ID, comment.getArticleId());
        Article article = articleMapper.selectOne(articleWrapper);
        article.setComments(article.getComments() + 1);
        article.setId(comment.getArticleId());
        articleMapper.updateById(article);
    }

    /**
     * 根据评论ID找到对应文章，文章的评论量-1
     *
     * @param id 评论ID
     */
    @Override
    public void decreaseArticleComments(Long id) {
        QueryWrapper<Comment> commentWrapper = new QueryWrapper<>();
        commentWrapper.select(Comment.Table.ARTICLE_ID).eq(Comment.Table.ID, id);
        Comment comment = commentMapper.selectOne(commentWrapper);
        QueryWrapper<Article> articleWrapper = new QueryWrapper<>();
        articleWrapper.select(Article.Table.COMMENTS).eq(Article.Table.ID, comment.getArticleId());
        Article article = articleMapper.selectOne(articleWrapper);
        article.setComments(article.getComments() - 1);
        article.setId(comment.getArticleId());
        articleMapper.updateById(article);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        for (Long id : idList) {
            decreaseArticleComments(id);
        }
        commentMapper.deleteBatchIds(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        //评论量-1
        decreaseArticleComments(id);
        commentMapper.deleteById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void reply(Comment comment) {
        if (comment.getVisitorId() != null) {
            QueryWrapper<Comment> commentWrapper = new QueryWrapper<>();
            commentWrapper.select(Comment.Table.VISITOR_ID).eq(Comment.Table.ID, comment.getPid());
            Comment parentComment = commentMapper.selectOne(commentWrapper);
            QueryWrapper<Visitor> visitorWrapper = new QueryWrapper<>();
            visitorWrapper.select(Visitor.Table.NICKNAME).eq(Visitor.Table.ID, parentComment.getVisitorId());
            Visitor visitor = visitorMapper.selectOne(visitorWrapper);
            comment.setParentNickname(visitor.getNickname());
        } else {
            QueryWrapper<User> userWrapper = new QueryWrapper<>();
            userWrapper.select(User.Table.NICKNAME).eq(User.Table.ID, comment.getUserId());
            User user = userMapper.selectOne(userWrapper);
            comment.setParentNickname(user.getNickname());
        }
        commentMapper.insert(comment);
    }

    @Override
    public Integer countByLastIndexViewToNow() {
        String username = UserInfoUtil.getUsername();
        if (StringUtils.isEmpty(username)) {
            return 0;
        }
        Date date = operationLogMapper.selectLastIndexViewTimeByUsername(username);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.between(Comment.Table.CREATE_TIME, date, new Date());
        return commentMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable
    public List<Comment> listNewest() {
        return commentMapper.listNewest(Constant.NEWEST_PAGE_SIZE);
    }

    @Override
    @Cacheable
    public Integer countAll() {
        return commentMapper.selectCount(null);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void audit(AuditVO auditVO) {
        Comment comment = new Comment();
        comment.setId(auditVO.getId());
        comment.setStatus(auditVO.getStatus());
        commentMapper.updateById(comment);
    }

    @Override
    @Cacheable
    public Page<Comment> listTableByPage(Integer current, Integer size, CommentQuery commentQuery) {
        Page<Comment> page = new Page<>(current, size);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        if (commentQuery.getStartDate() != null && commentQuery.getEndDate() != null) {
            wrapper.between(Comment.Table.CREATE_TIME, commentQuery.getStartDate(), commentQuery.getEndDate());
        }
        if (commentQuery.getStatus() != null) {
            wrapper.eq(Comment.Table.STATUS, commentQuery.getStatus());
        }
        return commentMapper.listTableByPage(page, wrapper);
    }

    @Override
    @Cacheable
    public Page<Comment> listByArticleId(Long articleId, Integer current, Integer size) {
        Page<Comment> page = new Page<>(current, size);
        Page<Comment> pageInfo = commentMapper.listRootPageByArticleId(page, articleId);
        List<Comment> comments = commentMapper.listByArticleId(articleId);
        LinkedListUtil.toCommentLinkedList(pageInfo.getRecords(), comments);
        return pageInfo;
    }
}
