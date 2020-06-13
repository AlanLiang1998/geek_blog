package site.alanliang.geekblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import site.alanliang.geekblog.model.*;

import java.io.Serializable;
import java.util.List;

@ApiModel("后台主页数据")
@Data
public class IndexVO implements Serializable {
    @ApiModelProperty("文章数量")
    private Long articleCount;

    @ApiModelProperty("分类数量")
    private Long categoryCount;

    @ApiModelProperty("标签数量")
    private Long tagCount;

    @ApiModelProperty("评论数量")
    private Integer commentCount;

    @ApiModelProperty("用户数量")
    private Integer userCount;

    @ApiModelProperty("访客数量")
    private Integer visitorCount;

    @ApiModelProperty("浏览量")
    private Integer viewCount;

    @ApiModelProperty("留言数量")
    private Integer messageCount;

    @ApiModelProperty("最近访问日志列表")
    private List<AccessLog> accessLogs;

    @ApiModelProperty("最近操作日志列表")
    private List<OperationLog> operationLogs;

    @ApiModelProperty("最近评论列表")
    private List<Comment> comments;

    @ApiModelProperty("最近留言列表")
    private List<Message> messages;

    @ApiModelProperty("最近文章列表")
    private List<Article> articles;

    @ApiModelProperty("前台流量日期统计")
    private List<ViewDateVO> frontViews;

    @ApiModelProperty("后台流量日期统计")
    private List<ViewDateVO> backViews;

    @ApiModelProperty("距离上次访问主页新增浏览量")
    private Integer increasedViews;

    @ApiModelProperty("距离上次访问主页新增文章数量")
    private Integer increasedArticles;

    @ApiModelProperty("距离上次访问主页新增留言数量")
    private Integer increasedMessages;

    @ApiModelProperty("距离上次访问主页新增评论数量")
    private Integer increasedComments;

    @ApiModelProperty("公告列表")
    private List<Notice> notices;
}
