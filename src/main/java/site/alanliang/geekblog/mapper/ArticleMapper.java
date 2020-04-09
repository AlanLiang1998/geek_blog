package site.alanliang.geekblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.domain.Article;
import site.alanliang.geekblog.vo.ArticleVo;

import java.util.List;

/**
 * @author AlanLiang
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 分页查询文章信息（附带文章分类和标签信息）
     *
     * @param page         分页参数
     * @param queryWrapper 条件
     * @return 文章列表
     */
    List<Article> selectPageWithExtra(IPage<Article> page, @Param("ew") QueryWrapper<Article> queryWrapper);
}
