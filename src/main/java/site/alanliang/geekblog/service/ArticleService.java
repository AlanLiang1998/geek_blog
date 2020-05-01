package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.entity.Article;
import site.alanliang.geekblog.vo.ArticleVO;
import site.alanliang.geekblog.query.ArticleQuery;
import site.alanliang.geekblog.vo.ArticleDateVO;

import java.util.List;

/**
 * @author AlanLiang
 */
public interface ArticleService {

    /**
     * 保存或更新文章
     *
     * @param articleVo 文章
     */
    void saveOrUpdate(ArticleVO articleVo);

    /**
     * 分页查询文章
     *
     * @param current 当前页码
     * @param size    页面大小
     * @param wrapper 条件
     * @return 文章列表
     */
    Page<Article> listTableByPage(Integer current, Integer size, QueryWrapper<Article> wrapper);

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
    Article getById(Long id);

    /**
     * 根据ID删除文章
     *
     * @param id 文章ID
     */
    void removeById(Long id);

    /**
     * 根据ID列表批量删除文章
     *
     * @param idList 文章ID列表
     */
    void removeByIds(List<Long> idList);

    /**
     * 查询置顶文章
     *
     * @return 文章列表
     */
    List<Article> listTop();

    /**
     * 查询推荐文章
     *
     * @return 推荐文章列表
     */
    List<Article> listRecommend();

    /**
     * 分页查询所有文章
     *
     * @param current 当前页码
     * @param size    页码大小
     * @return 文章列表
     */
    Page<Article> listPreviewByPage(Integer current, Integer size);

    /**
     * 根据ID查询文章
     *
     * @param id 文章ID
     * @return 文章
     */
    Article getDetailById(Long id);

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
    Article getPrevPreviewById(Long id);

    /**
     * 获取当前文章的下一篇文章预览
     *
     * @param id 当前文章ID
     * @return 下一篇文章预览
     */
    Article getNextPreviewById(Long id);

    /**
     * 根据分类ID分页获取分类的所有文章
     *
     * @param categoryId 分类ID
     * @return 文章列表
     */
    Page<Article> listPreviewPageByCategoryId(Integer current, Integer size, Long categoryId);

    /**
     * 根据标签ID分页获取标签的所有文章
     *
     * @param tagId 标签ID
     * @return 文章列表
     */
    Page<Article> listPreviewPageByTagId(Integer current, Integer size, Long tagId);

    /**
     * 根据日期统计文章数量
     *
     * @param articleQuery
     * @return 文章日期统计
     */
    List<ArticleDateVO> countByDate(Integer articleQuery);

    /**
     * 根据日期分页获取所有文章预览
     *
     * @param current      当前页
     * @param size         页面大小
     * @param articleQuery 条件
     * @return 文章预览列表
     */
    Page<Article> listPreviewPageByDate(Integer current, Integer size, ArticleQuery articleQuery);

    /**
     * 根据关键词获取文章
     *
     * @return 文章列表
     */
    List<Article> listByKeyword();

}
