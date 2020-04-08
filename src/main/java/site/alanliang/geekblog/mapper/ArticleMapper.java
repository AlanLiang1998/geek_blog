package site.alanliang.geekblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.domain.Article;

/**
 * @author AlanLiang
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
}
