package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.entity.Tag;

import java.util.List;

/**
 * @author AlanLiang
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章ID查询标签
     *
     * @param articleId 文章ID
     * @return 标签列表
     */
    List<Tag> selectByArticleId(@Param("articleId") long articleId);

    /**
     * 查询所有分类（统计文章数目）
     *
     * @return 分类列表
     */
    List<Tag> listByArticleCount();
}
