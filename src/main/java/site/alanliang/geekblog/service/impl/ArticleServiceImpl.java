package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.domain.ArticleTag;
import site.alanliang.geekblog.dto.ArticleDto;
import site.alanliang.geekblog.mapper.ArticleMapper;
import site.alanliang.geekblog.mapper.ArticleTagMapper;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.vo.ArticleVo;

import java.util.ArrayList;
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
    public Article findById(Long id) {
        return articleMapper.selectById(id);
    }

    @Override
    public long countAll(QueryWrapper<Article> wrapper) {
        return articleMapper.selectCount(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIds(List<Long> idList) {
        articleMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        articleMapper.deleteById(id);
    }

    @Override
    public List<ArticleVo> listByPage(Integer current, Integer size, QueryWrapper<Article> wrapper) {
        Page<Article> page = new Page<>(current, size);
        List<Article> articles = articleMapper.selectPageWithExtra(page, wrapper);
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            articleVos.add(articleVo);
        }
        return articleVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(ArticleDto articleDto) {
        if (articleDto.getId() == null) {
            //新增
            articleMapper.insert(articleDto);
            articleTagMapper.batchInsert(articleDto.getId(), articleDto.getTagIdList());
        } else {
            //编辑
            articleMapper.updateById(articleDto);
            QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
            wrapper.eq("article_id", articleDto.getId());
            articleTagMapper.delete(wrapper);
            articleTagMapper.batchInsert(articleDto.getId(), articleDto.getTagIdList());
        }
    }
}
