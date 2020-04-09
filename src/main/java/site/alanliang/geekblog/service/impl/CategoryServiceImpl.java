package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import site.alanliang.geekblog.domain.Category;
import site.alanliang.geekblog.exception.NameNotUniqueException;
import site.alanliang.geekblog.mapper.CategoryMapper;
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
    public Page<Category> listByPage(Integer current, Integer size, QueryWrapper<Category> wrapper) {
        Page<Category> categoryPage = new Page<>(current, size);
        return categoryMapper.selectPage(categoryPage, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        categoryMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchRemove(List<Long> idList) {
        categoryMapper.deleteBatchIds(idList);
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
                throw new NameNotUniqueException("分类名称已存在");
            }
            categoryMapper.insert(category);
        } else {
            //更新
            List<Category> categories = categoryMapper.selectList(wrapper);
            categories = categories.stream().filter(c -> !c.getId().equals(category.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(categories)) {
                throw new NameNotUniqueException("分类名称已存在");
            }
            categoryMapper.updateById(category);
        }
    }

    @Override
    public Category findById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<Category> list() {
        return categoryMapper.selectList(null);
    }
}
