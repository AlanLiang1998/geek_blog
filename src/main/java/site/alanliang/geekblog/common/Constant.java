package site.alanliang.geekblog.common;

/**
 * @author AlanLiang
 */
public interface Constant {
    String HOME_TITLE = "首页";

    String HOME_HREF = "/admin/api/home/dashboard";

    String LOGO_TITLE = "Geek Blog";

    String LOGO_IMAGE = "/static/admin/layuimini/images/logo.png";

    Integer MAX_TOP_ARTICLES = 5;

    Integer MAX_RECOMMEND_ARTICLES = 4;

    String PAGE_SIZE = "6";

    Integer FILTER_BY_DAY = 1;
    Integer FILTER_BY_MONTH = 2;
    Integer FILTER_BY_YEAR = 3;

    /**
     * 用于IP定位转换
     */
    String REGION = "内网IP|内网IP";

    /**
     * 表别名前缀
     */
    String TABLE_ALIAS_ARTICLE = "ta.";

    /**
     * 默认颜色
     */
    String DEFAULT_COLOR = "#D5F5E3";

    /**
     * 请求耗时等级
     */
    Integer LOW_REQUEST_TIME_RANK = 1;

    Integer MID_REQUEST_TIME_RANK = 2;

    Integer HIGH_REQUEST_TIME_RANK = 3;

    Integer LOW_REQUEST_TIME = 10;

    Integer HIGH_REQUEST_TIME = 1000;
}
