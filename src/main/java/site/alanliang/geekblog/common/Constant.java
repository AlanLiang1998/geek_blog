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

    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "123456";

    /**
     * 默认头像
     */
    String DEFAULT_AVATAR = "https://gravatar.loli.net/avatar/f2c02ce7474e4b228a576f7e47f00bd1?d=mp&v=1.3.10";

    /**
     * 菜单树根结点
     */
    Long MENU_TREE_ROOT = 0L;

    /**
     * 菜单树根结点名称
     */
    String MENU_TREE_ROOT_NAME = "根目录";

    /**
     * 菜单树开始结点
     */
    Long MENU_TREE_START = -1L;

    /**
     * 菜单树复选框未选中
     */
    String MENU_TREE_NOT_SELECTED = "0";

    /**
     * 菜单树复选框选中
     */
    Integer MENU_TREE_SELECTED = 1;

    /**
     * 评论链表根节点
     */
    Long COMMENT_LINKED_LIST_ROOT = 0L;


    /**
     * 访客状态
     */
    Integer VISITOR_ENABLE = 1;

    Integer VISITOR_DISABLE = 0;

    /**
     * 评论状态
     */
    Integer COMMENT_PASS = 2;

    Integer COMMENT_WAIT = 1;

    Integer COMMENT_NOT_PASS = 0;

    /**
     * 用户状态
     */
    Integer USER_ENABLE = 1;

    Integer USER_DISABLE = 0;

    /**
     * 显示
     */
    Integer DISPLAY = 1;

    /**
     * 隐藏
     */
    Integer HIDDEN = 0;


    Integer SUCCESS = 1;

    Integer FAILURE = 0;

    Integer NEWEST_PAGE_SIZE = 10;

    String USER = "user";
}
