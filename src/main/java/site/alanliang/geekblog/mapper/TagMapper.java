package site.alanliang.geekblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.domain.Tag;

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
}
