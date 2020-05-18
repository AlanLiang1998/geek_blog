package site.alanliang.geekblog.dao;

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
     * 分页查询所有顶级留言
     *
     * @param page 分页参数
     * @return 留言分页
     */
    Page<Comment> listRootMessageByPage(Page<Comment> page);

    /**
     * 查询所有留言
     *
     * @return 留言列表
     */
    List<Comment> listMessage();
}
