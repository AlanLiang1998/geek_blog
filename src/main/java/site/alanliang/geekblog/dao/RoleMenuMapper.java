package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.model.RoleMenu;

import java.util.List;

@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    /**
     * 批量插入角色权限
     *
     * @param roleId     角色ID
     * @param menuIdList 权限ID列表
     */
    void insertBatch(@Param("roleId") Long roleId, @Param("menuIdList") List<Long> menuIdList);
}
