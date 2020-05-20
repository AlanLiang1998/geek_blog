package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.model.Comment;

import java.util.List;

public interface CommentService {
    /**
     * 保存评论
     *
     * @param comment 评论
     */
    void save(Comment comment);

    /**
     * 分页查询文章的所有评论
     *
     * @return 评论分页
     */
    Page<Comment> listByArticleId(Long articleId, Page<Comment> page);

    /**
     * 后台分页查询所有评论
     *
     * @param current 当前页码
     * @param size    页面大小
     * @return 评论列表
     */
    Page<Comment> listTableByPage(Integer current, Integer size);


    /**
     * 根据ID列表批量删除评论
     *
     * @param idList 评论ID列表
     */
    void removeByIdList(List<Long> idList);

    /**
     * 根据ID删除评论
     *
     * @param id 评论ID
     */
    void removeById(Long id);

    /**
     * 根据ID回复评论
     *
     * @param comment 评论
     */
    void reply(Comment comment);
}
