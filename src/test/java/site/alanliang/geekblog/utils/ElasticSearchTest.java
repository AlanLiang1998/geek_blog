package site.alanliang.geekblog.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import site.alanliang.geekblog.dao.ArticleMapper;
import site.alanliang.geekblog.dto.ArticleDocument;
import site.alanliang.geekblog.model.Article;
import site.alanliang.geekblog.repository.ArticleDocumentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ElasticSearchTest {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleDocumentRepository articleDocumentRepository;

    @Test
    public void testCreateIndex() {
        // 创建索引，会根据Item类的@Document注解信息来创建
        elasticsearchOperations.createIndex(ArticleDocument.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        elasticsearchOperations.putMapping(ArticleDocument.class);
    }

    @Test
    public void testIndexExists() {
//        if (elasticsearchOperations.indexExists("article_document")) {
//            System.out.println("存在");
//        } else {
//            System.out.println("不存在");
//        }
    }

    @Test
    public void TestDeleteIndex() {
        elasticsearchOperations.deleteIndex(ArticleDocument.class);
    }

    @Test
    public void testSave() {
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


    @Test
    public void testDelete() {
        articleDocumentRepository.deleteAll();
    }

    @Test
    public void testESQuery() {
        long start = System.currentTimeMillis();
        String keyword = "Java";
        List<ArticleDocument> articleDocuments = articleDocumentRepository.findDistinctByTitleLikeOrSummaryLikeOrContentLike(keyword, keyword, keyword);
        long time = System.currentTimeMillis() - start;
        System.out.println("-----------------" + time + "ms" + "---------------");
    }

    @Test
    public void testQuery() {
        long start = System.currentTimeMillis();
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        String keyword = "Java";
        wrapper.eq("content", keyword);
        List<Article> articles = articleMapper.selectList(wrapper);
        long time = System.currentTimeMillis() - start;
        System.out.println("-----------------" + time + "ms" + "---------------");
    }

    @Autowired
    RestHighLevelClient restHighLevelClient;

    void parseField(SearchHit hit, String field) {
        Map<String, Object> map = hit.getSourceAsMap();//原来的结果
        HighlightField title = hit.getHighlightFields().get(field);
        //解析高亮的字段
        if (title != null) {
            Text[] fragments = title.fragments();
            StringBuilder newTitle = new StringBuilder();
            for (Text fragment : fragments) {
                newTitle.append(fragment);
            }
            //高亮文本替换掉原来的内容
            map.put(field, newTitle.toString());
        }
    }

    @Test
    public void testHighLight() throws IOException {
        String keyword = "互联网";
        SearchRequest searchRequest = new SearchRequest("article_document");
        //匹配查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "summary", "content");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("published", true);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(multiMatchQueryBuilder).must(termQueryBuilder);
        sourceBuilder.query(boolQueryBuilder);
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").field("summary").field("content");
        highlightBuilder.preTags("<em class='search-keyword'>");
        highlightBuilder.postTags("</em>");
        sourceBuilder.highlighter(highlightBuilder);
        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //解析结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> map = hit.getSourceAsMap();//原来的结果
            //解析高亮的字段
            parseField(hit, "title");
            parseField(hit, "summary");
            parseField(hit, "content");
            list.add(map);
        }
        System.out.println(list);
    }
}
