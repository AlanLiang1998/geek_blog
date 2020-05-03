package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.entity.Article;
import site.alanliang.geekblog.entity.ArticleTag;
import site.alanliang.geekblog.query.ArticleQuery;
import site.alanliang.geekblog.vo.ArticleVO;
import site.alanliang.geekblog.dao.ArticleMapper;
import site.alanliang.geekblog.dao.ArticleTagMapper;
import site.alanliang.geekblog.query.ArchivesQuery;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.vo.ArticleDateVO;

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
    public Page<Article> listPreviewPageByDate(Integer current, Integer size, ArchivesQuery archivesQuery) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listPreviewPageByDate(articlePage);
    }

    @Override
    public List<Article> listByKeyword() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "content");
        return articleMapper.selectList(wrapper);
    }

    @Override
    public List<ArticleDateVO> countByDate(Integer dateFilterType) {
        if (dateFilterType == null) {
            dateFilterType = Constant.FILTER_BY_DAY;
        }
        return articleMapper.countByDate(dateFilterType);
    }

    @Override
    public Page<Article> listPreviewPageByTagId(Integer current, Integer size, Long tagId) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listPreviewPageByTagId(articlePage, tagId);
    }

    @Override
    public Page<Article> listPreviewPageByCategoryId(Integer current, Integer size, Long categoryId) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listPreviewPageByCategoryId(articlePage, categoryId);
    }

    @Override
    public Article getNextPreviewById(Long id) {
        return articleMapper.selectNextPreviewById(id);
    }

    @Override
    public Article getPrevPreviewById(Long id) {
        return articleMapper.selectPrevPreviewById(id);
    }

    @Override
    public Article getDetailById(Long id) {
        //浏览次数加1
        increaseViews(id);
        return articleMapper.selectDetailById(id);
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
    public Article getById(Long id) {
        return articleMapper.selectById(id);
    }

    @Override
    public long countAll(QueryWrapper<Article> wrapper) {
        return articleMapper.selectCount(wrapper);
    }

    @Override
    public List<Article> listTop() {
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
    public void removeById(Long id) {
        articleMapper.deleteById(id);
    }

    @Override
    public Page<Article> listPreviewByPage(Integer current, Integer size) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listPreviewByPage(articlePage);
    }

    @Override
    public List<Article> listRecommend() {
        return articleMapper.listRecommend(Constant.MAX_RECOMMEND_ARTICLES);
    }

    @Override
    public Page<Article> listTableByPage(Integer current, Integer size, ArticleQuery articleQuery) {
        Page<Article> page = new Page<>(current, size);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(articleQuery.getTitle())) {
            wrapper.like("title", articleQuery.getTitle());
        }
        if (articleQuery.getType() != null) {
            wrapper.eq("type", articleQuery.getType());
        }
        if (articleQuery.getCategoryId() != null) {
            wrapper.eq("category_id", articleQuery.getCategoryId());
        }
        if (articleQuery.getPublished() != null) {
            wrapper.eq("published", articleQuery.getPublished());
        }
        if (articleQuery.getStartDate() != null && articleQuery.getEndDate() != null) {
            wrapper.between(Constant.TABLE_ALIAS_ARTICLE + "create_time", articleQuery.getStartDate(), articleQuery.getEndDate());
        }
        return articleMapper.listTableByPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(ArticleVO articleVo) {
        if (articleVo.getId() == null) {
            //新增
            articleMapper.insert(articleVo);
            articleTagMapper.insertBatch(articleVo.getId(), articleVo.getTagIdList());
        } else {
            //编辑
            articleMapper.updateById(articleVo);
            QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
            wrapper.eq("article_id", articleVo.getId());
            articleTagMapper.delete(wrapper);
            articleTagMapper.insertBatch(articleVo.getId(), articleVo.getTagIdList());
        }
    }

}
