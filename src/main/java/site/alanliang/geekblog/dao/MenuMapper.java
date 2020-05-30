package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.model.Menu;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/5 22:14
 * Version 1.0
 **/
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Menu> listMenuByUserId(@Param("userId") Long userId);

    List<Menu> listPermissionByUserId(@Param("userId") Long userId);
}
