package site.alanliang.geekblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.domain.ArticleTag;

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
    void batchInsert(@Param("articleId") Long articleId, @Param("tagIdList") List<Long> tagList);
}
