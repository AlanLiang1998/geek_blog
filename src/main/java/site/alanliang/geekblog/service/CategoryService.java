package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.model.Category;
import site.alanliang.geekblog.query.CategoryQuery;

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
    List<Category> listAll();

    /**
     * 分页查询所有分类
     *
     * @param current 当前页码
     * @param size    页面大小
     * @param categoryQuery 条件
     * @return 分类列表
     */
    Page<Category> listTableByPage(Integer current, Integer size, CategoryQuery categoryQuery);

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
    Category getById(Long id);

    /**
     * 根据ID删除分类
     *
     * @param id 分类ID
     */
    void removeById(Long id);

    /**
     * 根据ID列表批量删除分类
     *
     * @param idList 分类ID列表
     */
    void removeByIdList(List<Long> idList);

    /**
     * 查询所有分类（统计文章数目）
     *
     * @return 分类列表
     */
    List<Category> listByArticleCount();

    /**
     * 统计分类数量
     *
     * @return
     */
    long countAll();

    /**
     * 查询分类的所有颜色
     *
     * @return 颜色列表
     */
    List<String> listColor();
}
