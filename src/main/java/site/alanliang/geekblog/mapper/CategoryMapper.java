package site.alanliang.geekblog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.domain.Category;

/**
 * @author AlanLiang
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 根据ID查询分类
     *
     * @param id 分类ID
     * @return 分类
     */
    Category selectCategoryById(long id);
}
