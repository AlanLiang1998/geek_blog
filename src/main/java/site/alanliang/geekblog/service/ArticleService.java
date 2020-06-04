package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.dto.ArticleDocument;
import site.alanliang.geekblog.model.Article;
import site.alanliang.geekblog.query.ArchivesQuery;
import site.alanliang.geekblog.query.ArticleQuery;
import site.alanliang.geekblog.vo.ArticleDateVO;

import java.io.IOException;
import java.util.List;

/**
 * @author AlanLiang
 */
public interface ArticleService {

    /**
     * 保存或更新文章
     *
     * @param article 文章
     */
    void saveOrUpdate(Article article);

    /**
     * 分页查询文章
     *
     * @param current      当前页码
     * @param size         页面大小
     * @param articleQuery 条件
     * @return 文章列表
     */
    Page<Article> listTableByPage(Integer current, Integer size, ArticleQuery articleQuery);

    /**
     * 查询记录总数
     *
     * @return 记录总数
     */
    long countAll();

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
    void removeByIdList(List<Long> idList);

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
     * @param current       当前页
     * @param size          页面大小
     * @param archivesQuery 条件
     * @return 文章预览列表
     */
    Page<Article> listPreviewPageByDate(Integer current, Integer size, ArchivesQuery archivesQuery);

    /**
     * 根据关键词获取文章
     *
     * @return 文章列表
     * @param keyword
     */
    List<ArticleDocument> listByKeyword(String keyword) throws IOException;

    /**
     * 判断置顶文章是否达到上限
     *
     * @return 布尔值
     */
    Boolean reachedMaxTop();

    /**
     * 判断推荐文章是否达到上限
     *
     * @return 布尔值
     */
    Boolean reachedMaxRecommend();

    /**
     * 查询最近的文章
     *
     * @return 文章列表
     */
    List<Article> listNewest();

    /**
     * 统计上次访问首页至现在增加的文章数
     */
    Integer countByLastIndexViewToNow();
}
