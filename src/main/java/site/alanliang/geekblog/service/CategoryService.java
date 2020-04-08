package site.alanliang.geekblog.service;

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
}
