package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.common.TableConstant;
import site.alanliang.geekblog.dao.ArticleMapper;
import site.alanliang.geekblog.dao.ArticleTagMapper;
import site.alanliang.geekblog.dao.OperationLogMapper;
import site.alanliang.geekblog.dao.TagMapper;
import site.alanliang.geekblog.dto.ArticleDocument;
import site.alanliang.geekblog.model.Article;
import site.alanliang.geekblog.model.ArticleTag;
import site.alanliang.geekblog.model.Tag;
import site.alanliang.geekblog.query.ArchivesQuery;
import site.alanliang.geekblog.query.ArticleQuery;
import site.alanliang.geekblog.service.ArticleService;
import site.alanliang.geekblog.utils.ElasticSearchUtil;
import site.alanliang.geekblog.utils.RedisUtils;
import site.alanliang.geekblog.utils.UserInfoUtil;
import site.alanliang.geekblog.vo.ArticleDateVO;
import site.alanliang.geekblog.vo.AuditVO;

import java.io.IOException;
import java.util.ArrayList;
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
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;


    @Autowired
    private ElasticSearchUtil elasticSearchUtil;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseLikes(Long id) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select(Article.Table.LIKES).eq(Article.Table.ID, id);
        Article article = articleMapper.selectOne(wrapper);
        article.setId(id);
        article.setLikes(article.getLikes() + 1);
        articleMapper.updateById(article);
    }

    @Override
    @Cacheable
    public Page<Article> listPreviewPageByDate(Integer current, Integer size, ArchivesQuery archivesQuery) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listPreviewPageByDate(articlePage);
    }

    @Override
    public List<ArticleDocument> listByKeyword(String keyword) throws IOException {
        return elasticSearchUtil.listByKeyword(keyword);
    }

    @Override
    @Cacheable
    public List<ArticleDateVO> countByDate(Integer dateFilterType) {
        if (dateFilterType == null) {
            dateFilterType = Constant.FILTER_BY_DAY;
        }
        return articleMapper.countByDate(dateFilterType);
    }

    @Override
    @Cacheable
    public Page<Article> listPreviewPageByTagId(Integer current, Integer size, Long tagId) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listPreviewPageByTagId(articlePage, tagId);
    }

    @Override
    @Cacheable
    public Page<Article> listPreviewPageByCategoryId(Integer current, Integer size, Long categoryId) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listPreviewPageByCategoryId(articlePage, categoryId);
    }

    @Override
    @Cacheable
    public Article getNextPreviewById(Long id) {
        return articleMapper.selectNextPreviewById(id);
    }

    @Override
    @Cacheable
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
        wrapper.select(Article.Table.VIEWS).eq(Article.Table.ID, id);
        Article article = articleMapper.selectOne(wrapper);
        article.setId(id);
        article.setViews(article.getViews() + 1);
        articleMapper.updateById(article);
    }

    @Override
    @Cacheable
    public Article getById(Long id) {
        return articleMapper.selectById(id);
    }

    @Override
    @Cacheable
    public long countAll() {
        return articleMapper.selectCount(null);
    }

    @Override
    @Cacheable
    public List<Article> listTop() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select(Article.Table.ID, Article.Table.TITLE, Article.Table.SUMMARY, Article.Table.COVER)
                .eq(Article.Table.TOP, true)
                .eq(Article.Table.PUBLISHED, true)
                .eq(Article.Table.STATUS, Constant.AUDIT_PASS)
                .orderByDesc(Article.Table.SORT)
                .last(TableConstant.LIMIT + Constant.MAX_TOP_ARTICLES);
        return articleMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        articleMapper.deleteBatchIds(idList);
        //从ElasticSearch中删除
        ArrayList<ArticleDocument> articleDocuments = new ArrayList<>();
        for (Long id : idList) {
            ArticleDocument articleDocument = new ArticleDocument();
            articleDocument.setId(id);
            articleDocuments.add(articleDocument);
        }
        elasticSearchUtil.deleteAll(articleDocuments);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        articleMapper.deleteById(id);
        //从ElasticSearch中删除
        elasticSearchUtil.deleteById(id);
    }

    @Override
    @Cacheable
    public Page<Article> listPreviewByPage(Integer current, Integer size) {
        Page<Article> articlePage = new Page<>(current, size);
        return articleMapper.listPreviewByPage(articlePage);
    }

    @Override
    @Cacheable
    public List<Article> listRecommend() {
        return articleMapper.listRecommend(Constant.MAX_RECOMMEND_ARTICLES);
    }

    @Override
    @Cacheable
    public Page<Article> listTableByPage(Integer current, Integer size, ArticleQuery articleQuery) {
        Page<Article> page = new Page<>(current, size);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(articleQuery.getTitle())) {
            wrapper.like(Article.Table.TITLE, articleQuery.getTitle());
        }
        if (articleQuery.getType() != null) {
            wrapper.eq(Article.Table.TYPE, articleQuery.getType());
        }
        if (articleQuery.getCategoryId() != null) {
            wrapper.eq(Article.Table.CATEGORY_ID, articleQuery.getCategoryId());
        }
        if (articleQuery.getPublished() != null) {
            wrapper.eq(Article.Table.PUBLISHED, articleQuery.getPublished());
        }
        if (articleQuery.getStatus() != null) {
            wrapper.eq(Article.Table.STATUS, articleQuery.getStatus());
        }
        if (articleQuery.getTop() != null) {
            wrapper.eq(Article.Table.TOP, articleQuery.getTop());
        }
        if (articleQuery.getRecommend() != null) {
            wrapper.eq(Article.Table.RECOMMEND, articleQuery.getRecommend());
        }
        if (articleQuery.getStartDate() != null && articleQuery.getEndDate() != null) {
            wrapper.between(TableConstant.ARTICLE_ALIAS + Article.Table.CREATE_TIME, articleQuery.getStartDate(), articleQuery.getEndDate());
        }
        return articleMapper.listTableByPage(page, wrapper);
    }

    @Override
    @Cacheable
    public List<Article> listNewest() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select(Article.Table.ID, Article.Table.TITLE, Article.Table.SUMMARY, Article.Table.CREATE_TIME)
                .orderByDesc(Article.Table.CREATE_TIME)
                .last(TableConstant.LIMIT + Constant.NEWEST_PAGE_SIZE);
        return articleMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void audit(AuditVO auditVO) {
        Article article = new Article();
        article.setId(auditVO.getId());
        article.setStatus(auditVO.getStatus());
        articleMapper.updateById(article);
        //从ElasticSearch中删除
        elasticSearchUtil.deleteById(article.getId());
        //重新添加
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select(Article.Table.ID, Article.Table.TITLE, Article.Table.SUMMARY, Article.Table.CONTENT, Article.Table.PUBLISHED, Article.Table.STATUS)
                .eq(Article.Table.ID, auditVO.getId());
        Article art = articleMapper.selectOne(wrapper);
        elasticSearchUtil.save(art);
    }

    @Override
    public Integer countByLastIndexViewToNow() {
        String username = UserInfoUtil.getUsername();
        if (StringUtils.isEmpty(username)) {
            return 0;
        }
        Date date = operationLogMapper.selectLastIndexViewTimeByUsername(username);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.between(Article.Table.CREATE_TIME, date, new Date());
        return articleMapper.selectCount(wrapper);
    }

    @Override
    public Boolean reachedMaxRecommend() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq(Article.Table.RECOMMEND, true);
        Integer count = articleMapper.selectCount(wrapper);
        return count >= Constant.MAX_RECOMMEND_ARTICLES;
    }

    @Override
    public Boolean reachedMaxTop() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq(Article.Table.TOP, true);
        Integer count = articleMapper.selectCount(wrapper);
        return count >= Constant.MAX_TOP_ARTICLES;
    }


    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Article article) {
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
            //更新
            //更新文章信息
            articleMapper.updateById(article);
            //删除原有标签
            QueryWrapper<ArticleTag> articleTagWrapper = new QueryWrapper<>();
            articleTagWrapper.eq(ArticleTag.Table.ARTICLE_ID, article.getId());
            articleTagMapper.delete(articleTagWrapper);
            //从ElasticSearch中删除
            elasticSearchUtil.deleteById(article.getId());
        }
        //添加新标签
        List<Long> tagIdList = article.getTagList().stream().map(Tag::getId).collect(Collectors.toList());
        articleTagMapper.insertBatch(article.getId(), tagIdList);
        //手动清空标签缓存
        List<String> list = redisUtils.scan("tag*");
        if (!CollectionUtils.isEmpty(list)) {
            String[] keys = new String[list.size()];
            list.toArray(keys);
            redisUtils.del(keys);
        }
        //添加到ElasticSearch中
        elasticSearchUtil.save(article);
    }

}
