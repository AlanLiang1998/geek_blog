package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.domain.ArticleTag;
import site.alanliang.geekblog.dto.ArticleVo;
import site.alanliang.geekblog.mapper.ArticleMapper;
import site.alanliang.geekblog.mapper.ArticleTagMapper;
import site.alanliang.geekblog.query.ArticleQuery;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.vo.ArticleDateVo;

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
    @Transactional(rollbackFor = Exception.class)
    public void increaseLikes(Long id) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("likes").eq("id", id);
        Article article = articleMapper.selectOne(wrapper);
        article.setId(id);
        article.setLikes(article.getLikes() + 1);
        articleMapper.updateById(article);
    }

    @Override
    public Page<Article> listPageArticlePreviewByDate(Integer current, Integer size, ArticleQuery articleQuery) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listPageArticlePreviewByDate(articlePage);
    }

    @Override
    public List<Article> listArticlesByKeyword() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "content");
        return articleMapper.selectList(wrapper);
    }

    @Override
    public List<ArticleDateVo> countArticleByDate(Integer dateFilterType) {
        if (dateFilterType == null) {
            dateFilterType = Constant.FILTER_BY_DAY;
        }
        return articleMapper.countArticleByDate(dateFilterType);
    }

    @Override
    public Page<Article> listPageArticlePreviewByTagId(Integer current, Integer size, Long tagId) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listPageArticlePreviewByTagId(articlePage, tagId);
    }

    @Override
    public Page<Article> listPageArticlePreviewByCategoryId(Integer current, Integer size, Long categoryId) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listPageArticlePreviewByCategoryId(articlePage, categoryId);
    }

    @Override
    public Article getNextArticlePreview(Long id) {
        return articleMapper.selectNextArticlePreview(id);
    }

    @Override
    public Article getPrevArticlePreview(Long id) {
        return articleMapper.selectPrevArticlePreview(id);
    }

    @Override
    public Article getArticleById(Long id) {
        //浏览次数加1
        increaseViews(id);
        return articleMapper.selectArticleById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseViews(Long id) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("views").eq("id", id);
        Article article = articleMapper.selectOne(wrapper);
        article.setId(id);
        article.setViews(article.getViews() + 1);
        articleMapper.updateById(article);
    }

    @Override
    public Article findById(Long id) {
        return articleMapper.selectById(id);
    }

    @Override
    public long countAll(QueryWrapper<Article> wrapper) {
        return articleMapper.selectCount(wrapper);
    }

    @Override
    public List<Article> listTopArticles() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "summary", "cover")
                .eq("published", true)
                .eq("top", true)
                .last("limit " + Constant.MAX_TOP_ARTICLES);
        return articleMapper.selectList(wrapper);
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
    public Page<Article> listArticlesByPage(Integer current, Integer size) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listArticlesPreviewByPage(articlePage);
    }

    @Override
    public List<Article> listRecommendArticles() {
        return articleMapper.listRecommendArticles(Constant.MAX_RECOMMEND_ARTICLES);
    }

    @Override
    public List<site.alanliang.geekblog.vo.ArticleVo> listByPageForAdmin(Integer current, Integer size, QueryWrapper<Article> wrapper) {
        Page<Article> page = new Page<>(current, size);
        List<Article> articles = articleMapper.listArticlesTableByPage(page, wrapper);
        List<site.alanliang.geekblog.vo.ArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            site.alanliang.geekblog.vo.ArticleVo articleVo = new site.alanliang.geekblog.vo.ArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            articleVos.add(articleVo);
        }
        return articleVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(ArticleVo articleVo) {
        if (articleVo.getId() == null) {
            //新增
            articleMapper.insert(articleVo);
            articleTagMapper.batchInsert(articleVo.getId(), articleVo.getTagIdList());
        } else {
            //编辑
            articleMapper.updateById(articleVo);
            QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
            wrapper.eq("article_id", articleVo.getId());
            articleTagMapper.delete(wrapper);
            articleTagMapper.batchInsert(articleVo.getId(), articleVo.getTagIdList());
        }
    }

}
