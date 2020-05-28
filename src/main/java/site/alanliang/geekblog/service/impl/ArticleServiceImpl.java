package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.dao.OperationLogMapper;
import site.alanliang.geekblog.dao.TagMapper;
import site.alanliang.geekblog.model.*;
import site.alanliang.geekblog.query.ArticleQuery;
import site.alanliang.geekblog.dao.ArticleMapper;
import site.alanliang.geekblog.dao.ArticleTagMapper;
import site.alanliang.geekblog.query.ArchivesQuery;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.utils.RequestHolder;
import site.alanliang.geekblog.utils.UserInfoUtil;
import site.alanliang.geekblog.vo.ArticleDateVO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

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
    public long countAll() {
        return articleMapper.selectCount(null);
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
    public void removeByIdList(List<Long> idList) {
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
    public List<Article> listNewest() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "summary", "create_time")
                .orderByDesc("create_time")
                .last("limit " + Constant.NEWEST_PAGE_SIZE);
        return articleMapper.selectList(wrapper);
    }

    @Override
    public Integer countByLastIndexViewToNow() {
        String username = UserInfoUtil.getUsername();
        if (StringUtils.isEmpty(username)) {
            return 0;
        }
        Date date = operationLogMapper.selectLastIndexViewTimeByUsername(username);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.between("create_time", date, new Date());
        return articleMapper.selectCount(wrapper);
    }

    @Override
    public Boolean reachedMaxRecommend() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("recommend", true);
        Integer count = articleMapper.selectCount(wrapper);
        return count >= Constant.MAX_RECOMMEND_ARTICLES;
    }

    @Override
    public Boolean reachedMaxTop() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("top", true);
        Integer count = articleMapper.selectCount(wrapper);
        return count >= Constant.MAX_TOP_ARTICLES;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Article article) {
        QueryWrapper<Tag> tagWrapper = new QueryWrapper<>();
        tagWrapper.select("id", "name");
        List<Tag> tagList = tagMapper.selectList(tagWrapper);
        //存在新标签则添加新标签
        List<Tag> newTagList = article.getTagList().stream().filter(t -> (t.getId() == null)).collect(Collectors.toList());
        for (Tag newTag : newTagList) {
            //添加标签
            newTag.setColor(Constant.DEFAULT_COLOR);
            newTag.setCreateTime(new Date());
            newTag.setUpdateTime(newTag.getCreateTime());
            tagMapper.insert(newTag);
        }
        if (article.getId() == null) {
            //新增
            articleMapper.insert(article);
        } else {
            //编辑
            articleMapper.updateById(article);
            QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
            wrapper.eq("article_id", article.getId());
            articleTagMapper.delete(wrapper);
        }
        List<Long> tagIdList = article.getTagList().stream().map(Tag::getId).collect(Collectors.toList());
        articleTagMapper.insertBatch(article.getId(), tagIdList);
    }

}
