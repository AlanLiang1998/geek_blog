package site.alanliang.geekblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.domain.Article;

import java.util.List;

/**
 * @author AlanLiang
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 后台分页查询所有文章
     *
     * @param page         分页参数
     * @param queryWrapper 条件
     * @return 文章列表
     */
    List<Article> selectPageForAdmin(IPage<Article> page, @Param("ew") QueryWrapper<Article> queryWrapper);

    /**
     * 查询推荐文章
     *
     * @param limit 最大限制
     * @return 推荐文章列表
     */
    List<Article> listRecommendArticles(int limit);

    /**
     * 前台分页查询所有文章
     *
     * @param page    分页参数
     * @param queryWrapper 条件
     * @return 文章列表
     */
    Page<Article> listArticlesByPage(IPage<Article> page, @Param("ew") QueryWrapper<Article> queryWrapper);
}
