package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.model.Comment;

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
     * 分页查询所有留言
     *
     * @param page 分页参数
     * @return 留言分页
     */
    Page<Comment> listMessageByPage(Page<Comment> page);
}
