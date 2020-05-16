package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.model.Article;
import site.alanliang.geekblog.model.User;

import java.util.List;

/**
 * @author AlanLiang
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 后台分页查询所有用户
     *
     * @param page         分页参数
     * @param queryWrapper 条件
     * @return 用户分页信息
     */
    Page<User> listTableByPage(IPage<User> page, @Param("ew") QueryWrapper<User> queryWrapper);
}
