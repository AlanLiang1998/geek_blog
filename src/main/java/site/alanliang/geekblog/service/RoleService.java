package site.alanliang.geekblog.service;

import site.alanliang.geekblog.model.Role;

import java.util.List;

public interface RoleService {

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<Role> listAll();
}
