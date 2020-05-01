package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.entity.ArticleTag;

import java.util.List;

/**
 * @author AlanLiang
 */
@Repository
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
    /**
     * 批量删除文章标签
     *
     * @param articleId 文章ID
     * @param tagList   标签列表
     */
    void insertBatch(@Param("articleId") Long articleId, @Param("tagIdList") List<Long> tagList);
}
