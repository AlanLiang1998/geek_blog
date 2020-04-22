package site.alanliang.geekblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.domain.Article;

import java.util.List;

/**
 * @author AlanLiang
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 后台分页查询所有文章
     *
     * @param page         分页参数
     * @param queryWrapper 条件
     * @return 文章列表
     */
    List<Article> listArticlesTableByPage(IPage<Article> page, @Param("ew") QueryWrapper<Article> queryWrapper);

    /**
     * 查询推荐文章
     *
     * @param limit 最大限制
     * @return 推荐文章列表
     */
    List<Article> listRecommendArticles(int limit);

    /**
     * 前台分页查询所有文章预览
     *
     * @param page         分页参数
     * @param queryWrapper 条件
     * @return 文章预览列表
     */
    Page<Article> listArticlesPreviewByPage(IPage<Article> page, @Param("ew") QueryWrapper<Article> queryWrapper);

    /**
     * 前台根据ID查询文章
     *
     * @param id 文章ID
     * @return 文章
     */
    Article selectArticleById(Long id);

    /**
     * 获取当前文章的上一篇文章预览
     *
     * @param id 当前文章ID
     * @return 上一篇文章预览
     */
    Article selectPrevArticlePreview(Long id);

    /**
     * 获取当前文章的下一篇文章预览
     *
     * @param id 当前文章ID
     * @return 下一篇文章预览
     */
    Article selectNextArticlePreview(Long id);

    /**
     * 根据分类ID分页查询分类的所有文章预览
     *
     * @param page 分页参数
     * @param categoryId  分类ID
     * @return 文章预览分页
     */
    Page<Article> listPageArticlePreviewByCategoryId(IPage<Article> page, @Param("categoryId") Long categoryId);

    /**
     * 根据标签ID分页查询标签的所有文章预览
     *
     * @param page 分页参数
     * @param tagId  标签ID
     * @return 文章预览分页
     */
    Page<Article> listPageArticlePreviewByTagId(Page<Article> page, @Param("tagId") Long tagId);
}
