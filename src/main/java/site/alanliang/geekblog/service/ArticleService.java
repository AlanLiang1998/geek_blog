package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.dto.ArticleDto;
import site.alanliang.geekblog.vo.ArticleVo;

import java.util.List;

/**
 * @author AlanLiang
 */
public interface ArticleService {

    /**
     * 保存或编辑文章
     *
     * @param articleDto 文章DTO
     */
    void saveOrUpdate(ArticleDto articleDto);

    /**
     * 后台分页查询所有文章
     *
     * @param current 当前页码
     * @param size    页面大小
     * @param wrapper 条件
     * @return 文章列表
     */
    List<ArticleVo> listByPageForAdmin(Integer current, Integer size, QueryWrapper<Article> wrapper);

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
}
