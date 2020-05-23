package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.model.Comment;

import java.util.List;

@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 根据文章ID分页查询顶级评论
     *
     * @param articleId 文章ID
     * @param page      分页参数
     * @return 评论分页
     */
    Page<Comment> listRootPageByArticleId(IPage<Comment> page, @Param("articleId") Long articleId);

    /**
     * 根据文章ID查询所有所有评论
     *
     * @param articleId 文章ID
     * @return 评论列表
     */
    List<Comment> listByArticleId(@Param("articleId") Long articleId);

    /**
     * 后台分页查询所有评论
     *
     * @param page    分页参数
     * @param wrapper 条件
     * @return 评论分页
     */
    Page<Comment> listTableByPage(IPage<Comment> page, @Param("ew") QueryWrapper<Comment> wrapper);
}
