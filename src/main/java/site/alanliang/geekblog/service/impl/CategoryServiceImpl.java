package site.alanliang.geekblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alanliang.geekblog.domain.Category;
import site.alanliang.geekblog.mapper.CategoryMapper;
import site.alanliang.geekblog.service.CategoryService;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/7 19:44
 * Version 1.0
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryMapper.selectList(null);
    }
}
