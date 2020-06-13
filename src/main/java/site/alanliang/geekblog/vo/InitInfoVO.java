package site.alanliang.geekblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.model.Menu;
import site.alanliang.geekblog.utils.MenuTreeUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Descriptin 后台初始化信息
 * @Author AlanLiang
 * Date 2020/4/6 10:49
 * Version 1.0
 **/
@ApiModel("初始化数据")
@Data
public class InitInfoVO implements Serializable {
    @ApiModelProperty("菜单列表")
    private List<MenuVO> menuInfo;

    @ApiModelProperty("主页信息")
    private HomeInfo homeInfo;

    @ApiModelProperty("logo信息")
    private LogoInfo logoInfo;


    public static InitInfoVO init(List<Menu> menuList) {
        List<MenuVO> menuInfo = new ArrayList<>();
        for (Menu e : menuList) {
            MenuVO menuVO = new MenuVO();
            menuVO.setId(e.getId());
            menuVO.setPid(e.getPid());
            menuVO.setHref(e.getHref());
            menuVO.setTitle(e.getTitle());
            menuVO.setIcon(e.getIcon());
            menuVO.setTarget(e.getTarget());
            menuInfo.add(menuVO);
        }
        HomeInfo homeInfo = new HomeInfo();
        homeInfo.setHref(Constant.HOME_HREF);
        homeInfo.setTitle(Constant.HOME_TITLE);

        LogoInfo logoInfo = new LogoInfo();
        logoInfo.setTitle(Constant.LOGO_TITLE);
        logoInfo.setImage(Constant.LOGO_IMAGE);

        InitInfoVO initInfoVO = new InitInfoVO();
        initInfoVO.setMenuInfo(MenuTreeUtil.toTree(menuInfo, 0L));
        initInfoVO.setHomeInfo(homeInfo);
        initInfoVO.setLogoInfo(logoInfo);
        return initInfoVO;
    }

    @Data
    private static class HomeInfo {
        private String title;

        private String href;
    }

    @Data
    private static class LogoInfo {
        private String title;

        private String image;
    }
}
