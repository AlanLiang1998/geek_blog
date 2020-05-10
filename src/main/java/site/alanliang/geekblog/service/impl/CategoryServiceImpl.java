package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import site.alanliang.geekblog.model.Category;
import site.alanliang.geekblog.exception.EntityExistException;
import site.alanliang.geekblog.dao.CategoryMapper;
import site.alanliang.geekblog.query.CategoryQuery;
import site.alanliang.geekblog.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

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
    public Page<Category> listByPage(Integer current, Integer size, CategoryQuery categoryQuery) {
        Page<Category> page = new Page<>(current, size);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(categoryQuery.getName())) {
            wrapper.like("name", categoryQuery.getName());
        }
        if (categoryQuery.getDisplay() != null) {
            wrapper.eq("display", categoryQuery.getDisplay());
        }
        if (categoryQuery.getStartDate() != null && categoryQuery.getEndDate() != null) {
            wrapper.between("create_time", categoryQuery.getStartDate(), categoryQuery.getEndDate());
        }
        return categoryMapper.listTableByPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        categoryMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        categoryMapper.deleteBatchIds(idList);
    }

    @Override
    public long countAll() {
        return categoryMapper.selectCount(null);
    }

    @Override
    public List<String> listColor() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.select("color")
                .groupBy("color");
        List<Category> categories = categoryMapper.selectList(wrapper);
        return categories.stream().map(Category::getColor).collect(Collectors.toList());
    }

    @Override
    public List<Category> listByArticleCount() {
        return categoryMapper.listByArticleCount();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOfUpdate(Category category) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("name", category.getName());
        if (category.getId() == null) {
            //新增
            //检查分类名称是否唯一
            if (categoryMapper.selectOne(wrapper) != null) {
                throw new EntityExistException("分类名称已存在");
            }
            categoryMapper.insert(category);
        } else {
            //更新
            List<Category> categories = categoryMapper.selectList(wrapper);
            categories = categories.stream().filter(c -> !c.getId().equals(category.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(categories)) {
                throw new EntityExistException("分类名称已存在");
            }
            categoryMapper.updateById(category);
        }
    }

    @Override
    public Category getById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<Category> listAll() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name");
        return categoryMapper.selectList(wrapper);
    }
}
