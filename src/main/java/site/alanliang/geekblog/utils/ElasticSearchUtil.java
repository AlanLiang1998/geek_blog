package site.alanliang.geekblog.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.common.TableConstant;
import site.alanliang.geekblog.dao.ArticleMapper;
import site.alanliang.geekblog.dto.ArticleDocument;
import site.alanliang.geekblog.model.Article;
import site.alanliang.geekblog.repository.ArticleDocumentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Alan
 * @Date: 2021/01/11 16:48
 */
@Component
public class ElasticSearchUtil {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ArticleDocumentRepository articleDocumentRepository;

    @Async
    public void save(Article article) {
        ArticleDocument articleDocument = new ArticleDocument();
        BeanUtils.copyProperties(article, articleDocument);
        if (articleDocument.getPublished() == null) {
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            wrapper.select(Article.Table.PUBLISHED).eq(Article.Table.ID, article.getId());
            Article temp = articleMapper.selectOne(wrapper);
            articleDocument.setPublished(temp.getPublished());
        }
        if (articleDocument.getStatus() == null) {
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            wrapper.select(Article.Table.STATUS).eq(Article.Table.ID, article.getId());
            Article temp = articleMapper.selectOne(wrapper);
            articleDocument.setStatus(temp.getStatus());
        }
        articleDocumentRepository.save(articleDocument);
    }

    @Async
    public void deleteById(long id) {
        articleDocumentRepository.deleteById(id);
    }

    @Async
    public void deleteAll(List<ArticleDocument> articleDocuments) {
        articleDocumentRepository.deleteAll(articleDocuments);
    }

    @Async
    public void sync() {
        articleDocumentRepository.deleteAll();
        List<Article> articles = articleMapper.selectList(null);
        ArrayList<ArticleDocument> articleDocuments = new ArrayList<>();
        for (Article article : articles) {
            ArticleDocument articleDocument = new ArticleDocument();
            articleDocument.setId(article.getId());
            articleDocument.setTitle(article.getTitle());
            articleDocument.setSummary(article.getSummary());
            articleDocument.setContent(HtmlUtil.removeTag(article.getContent()));
            articleDocument.setPublished(article.getPublished());
            articleDocument.setStatus(article.getStatus());
            articleDocuments.add(articleDocument);
        }
        articleDocumentRepository.saveAll(articleDocuments);
    }

    public List<ArticleDocument> listByKeyword(String keyword) throws IOException {
        SearchRequest searchRequest = new SearchRequest(TableConstant.ARTICLE_DOCUMENT);
        //匹配查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, ArticleDocument.Table.TITLE, ArticleDocument.Table.SUMMARY, ArticleDocument.Table.CONTENT);
        TermQueryBuilder termQueryBuilder1 = QueryBuilders.termQuery(ArticleDocument.Table.PUBLISHED, true);
        TermQueryBuilder termQueryBuilder2 = QueryBuilders.termQuery(ArticleDocument.Table.STATUS, Constant.AUDIT_PASS);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(multiMatchQueryBuilder).must(termQueryBuilder1).must(termQueryBuilder2);
        sourceBuilder.query(boolQueryBuilder);
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(ArticleDocument.Table.TITLE).field(ArticleDocument.Table.SUMMARY).field(ArticleDocument.Table.CONTENT);
        highlightBuilder.preTags(Constant.HIGH_LIGHT_PRE_TAGS);
        highlightBuilder.postTags(Constant.HIGH_LIGHT_POST_TAGS);
        sourceBuilder.highlighter(highlightBuilder);
        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //解析结果
        List<ArticleDocument> articleDocuments = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> map = hit.getSourceAsMap();//原来的结果
            //解析高亮的字段
            HighLightUtil.parseField(hit, ArticleDocument.Table.TITLE);
            HighLightUtil.parseField(hit, ArticleDocument.Table.SUMMARY);
            HighLightUtil.parseField(hit, ArticleDocument.Table.CONTENT);

            ArticleDocument articleDocument = new ArticleDocument();
            articleDocument.setId(Long.valueOf((Integer) map.get(ArticleDocument.Table.ID)));
            articleDocument.setTitle((String) map.get(ArticleDocument.Table.TITLE));
            articleDocument.setSummary((String) map.get(ArticleDocument.Table.SUMMARY));
            articleDocument.setContent((String) map.get(ArticleDocument.Table.CONTENT));
            articleDocuments.add(articleDocument);
        }
        return articleDocuments;
    }
}
