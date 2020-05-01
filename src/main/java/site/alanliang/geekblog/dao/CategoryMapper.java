package site.alanliang.geekblog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.entity.Category;

import java.util.List;

/**
 * @author AlanLiang
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 查询所有分类（统计文章数目）
     *
     * @return 分类列表
     */
    List<Category> listByArticleCount();

}
