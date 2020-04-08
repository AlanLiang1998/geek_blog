package site.alanliang.geekblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alanliang.geekblog.domain.Tag;
import site.alanliang.geekblog.mapper.TagMapper;
import site.alanliang.geekblog.service.TagService;

import java.util.List;

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
    public List<Tag> findByArticleId(Long id) {
        return tagMapper.selectByArticleId(id);
    }

    @Override
    public List<Tag> list() {
        return tagMapper.selectList(null);
    }
}
