package site.alanliang.geekblog.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Document(indexName = "article_document", type = "docs", shards = 1, replicas = 0)
public class ArticleDocument implements Serializable {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title; //标题

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String summary;// 概述

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content; // 内容

    @Field(type = FieldType.Boolean)
    private Boolean published;//是否已发布

    @Field(type = FieldType.Integer)
    private Integer status;//审核状态

    public interface Table {
        String ID = "ID";
        String TITLE = "title";
        String SUMMARY = "summary";
        String CONTENT = "content";
        String PUBLISHED = "published";
        String STATUS = "status";
    }
}
