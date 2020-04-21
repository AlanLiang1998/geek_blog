package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.domain.Category;

import java.util.List;

/**
 * @author AlanLiang
 */
public interface CategoryService {
    /**
     * 查询所有分类
     *
     * @return 分类列表
     */
    List<Category> list();

    /**
     * 分页查询所有分类
     *
     * @param current 当前页码
     * @param size    页面大小
     * @param wrapper 条件
     * @return 分类列表
     */
    Page<Category> listByPage(Integer current, Integer size, QueryWrapper<Category> wrapper);

    /**
     * 新增或者更新分类
     *
     * @param category 分类
     */
    void saveOfUpdate(Category category);

    /**
     * 根据ID查询分类
     *
     * @param id 分类ID
     * @return 分类
     */
    Category findById(Long id);

    /**
     * 根据ID删除分类
     *
     * @param id 分类ID
     */
    void remove(Long id);

    /**
     * 根据ID列表批量删除分类
     *
     * @param idList 分类ID列表
     */
    void batchRemove(List<Long> idList);

    /**
     * 查询所有分类（统计文章数目）
     *
     * @return 分类列表
     */
    List<Category> listByArticleCount();

}
