package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import site.alanliang.geekblog.model.Tag;
import site.alanliang.geekblog.exception.EntityExistException;
import site.alanliang.geekblog.dao.TagMapper;
import site.alanliang.geekblog.query.TagQuery;
import site.alanliang.geekblog.service.TagService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/7 20:37
 * Version 1.0
 **/
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Page<Tag> listTableByPage(Integer current, Integer size, TagQuery tagQuery) {
        Page<Tag> tagPage = new Page<>(current, size);
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(tagQuery.getName())) {
            wrapper.like("name", tagQuery.getName());
        }
        if (tagQuery.getStartDate() != null && tagQuery.getEndDate() != null) {
            wrapper.between("create_time", tagQuery.getStartDate(), tagQuery.getEndDate());
        }
        return tagMapper.listTableByPage(tagPage, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOfUpdate(Tag tag) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("name", tag.getName());
        if (tag.getId() == null) {
            //新增
            //检查分类名称是否唯一
            if (tagMapper.selectOne(wrapper) != null) {
                throw new EntityExistException("标签名称已存在");
            }
            tagMapper.insert(tag);
        } else {
            //更新
            List<Tag> tags = tagMapper.selectList(wrapper);
            tags = tags.stream().filter(c -> !c.getId().equals(tag.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tags)) {
                throw new EntityExistException("标签名称已存在");
            }
            tagMapper.updateById(tag);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        tagMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        tagMapper.deleteBatchIds(idList);
    }

    @Override
    public Tag getById(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public List<Tag> listByArticleCount() {
        return tagMapper.listByArticleCount();
    }

    @Override
    public long countAll() {
        return tagMapper.selectCount(null);
    }

    @Override
    public List<String> listColor() {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.select("color")
                .groupBy("color");
        List<Tag> tags = tagMapper.selectList(wrapper);
        return tags.stream().map(Tag::getColor).collect(Collectors.toList());
    }

    @Override
    public List<Tag> listByArticleId(Long id) {
        return tagMapper.selectByArticleId(id);
    }

    @Override
    public List<Tag> listAll() {
        return tagMapper.selectList(null);
    }
}
