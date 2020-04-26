package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.dto.ArticleVo;
import site.alanliang.geekblog.query.ArticleQuery;
import site.alanliang.geekblog.vo.ArticleDateVo;

import java.util.List;

/**
 * @author AlanLiang
 */
public interface ArticleService {

    /**
     * 保存或编辑文章
     *
     * @param articleVo 文章DTO
     */
    void saveOrUpdate(ArticleVo articleVo);

    /**
     * 后台分页查询所有文章
     *
     * @param current 当前页码
     * @param size    页面大小
     * @param wrapper 条件
     * @return 文章列表
     */
    List<site.alanliang.geekblog.vo.ArticleVo> listByPageForAdmin(Integer current, Integer size, QueryWrapper<Article> wrapper);

    /**
     * 查询记录总数
     *
     * @param wrapper 条件
     * @return 记录总数
     */
    long countAll(QueryWrapper<Article> wrapper);

    /**
     * 根据ID查询文章
     *
     * @param id 文章ID
     * @return 文章
     */
    Article findById(Long id);

    /**
     * 根据ID删除文章
     *
     * @param id 文章ID
     */
    void remove(Long id);

    /**
     * 根据ID列表批量删除文章
     *
     * @param idList 文章ID列表
     */
    void removeByIds(List<Long> idList);

    /**
     * 根据条件查询所有文章
     *
     * @return 文章列表
     */
    List<Article> listTopArticles();

    /**
     * 查询所有推荐文章信息
     *
     * @return 推荐文章列表
     */
    List<Article> listRecommendArticles();

    /**
     * 前台分页查询所有文章
     *
     * @param current 当前页码
     * @param size    页码大小
     * @return 文章列表
     */
    Page<Article> listArticlesByPage(Integer current, Integer size);

    /**
     * 前台根据ID查询文章
     *
     * @param id 文章ID
     * @return 文章
     */
    Article getArticleById(Long id);

    /**
     * 增加文章点赞量
     *
     * @param id 文章ID
     */
    void increaseLikes(Long id);

    /**
     * 增加文章浏览次数
     *
     * @param id 文章ID
     */
    void increaseViews(Long id);

    /**
     * 获取当前文章的上一篇文章预览
     *
     * @param id 当前文章ID
     * @return 上一篇文章预览
     */
    Article getPrevArticlePreview(Long id);

    /**
     * 获取当前文章的下一篇文章预览
     *
     * @param id 当前文章ID
     * @return 下一篇文章预览
     */
    Article getNextArticlePreview(Long id);

    /**
     * 根据分类ID分页获取分类的所有文章
     *
     * @param categoryId 分类ID
     * @return 文章列表
     */
    Page<Article> listPageArticlePreviewByCategoryId(Integer current, Integer size, Long categoryId);

    /**
     * 根据标签ID分页获取标签的所有文章
     *
     * @param tagId 标签ID
     * @return 文章列表
     */
    Page<Article> listPageArticlePreviewByTagId(Integer current, Integer size, Long tagId);

    /**
     * 根据日期统计文章数量
     *
     * @param articleQuery
     * @return 文章日期统计
     */
    List<ArticleDateVo> countArticleByDate(Integer articleQuery);

    /**
     * 根据日期分页获取所有文章预览
     *
     * @param current      当前页
     * @param size         页面大小
     * @param articleQuery 条件
     * @return 文章预览列表
     */
    Page<Article> listPageArticlePreviewByDate(Integer current, Integer size, ArticleQuery articleQuery);

    /**
     * 根据关键词获取文章
     *
     * @return 文章列表
     */
    List<Article> listArticlesByKeyword();

}
