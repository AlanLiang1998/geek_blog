package site.alanliang.geekblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.alanliang.geekblog.dto.ArticleDto;
import site.alanliang.geekblog.mapper.ArticleMapper;
import site.alanliang.geekblog.mapper.ArticleTagMapper;
import site.alanliang.geekblog.service.ArticleService;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/8 9:00
 * Version 1.0
 **/
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(ArticleDto articleDto) {
        if (articleDto.getId() == null) {
            //新增
            articleMapper.insert(articleDto);
            articleTagMapper.batchInsert(articleDto.getId(), articleDto.getTagIdList());
        } else {
            //编辑

        }
    }
}
