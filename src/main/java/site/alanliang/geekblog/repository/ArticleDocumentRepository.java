package site.alanliang.geekblog.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.dto.ArticleDocument;

import java.util.List;

@Repository
public interface ArticleDocumentRepository extends ElasticsearchCrudRepository<ArticleDocument, Long> {

    List<ArticleDocument> findDistinctByTitleLikeOrSummaryLikeOrContentLike(String title, String summary, String content);
}
