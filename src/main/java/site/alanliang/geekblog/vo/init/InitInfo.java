package site.alanliang.geekblog.vo.init;

import lombok.Data;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.domain.SysMenu;
import site.alanliang.geekblog.utils.MenuTreeUtil;
import site.alanliang.geekblog.vo.MenuVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/6 10:49
 * Version 1.0
 **/
@Data
public class InitInfo {
    private List<MenuVo> menuInfo;

    private HomeInfo homeInfo;

    private LogoInfo logoInfo;


    public static InitInfo init(List<SysMenu> menuList) {
        List<MenuVo> menuInfo = new ArrayList<>();
        for (SysMenu e : menuList) {
            MenuVo menuVO = new MenuVo();
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

        InitInfo initInfo = new InitInfo();
        initInfo.setMenuInfo(MenuTreeUtil.toTree(menuInfo, 0L));
        initInfo.setHomeInfo(homeInfo);
        initInfo.setLogoInfo(logoInfo);
        return initInfo;
    }
}
