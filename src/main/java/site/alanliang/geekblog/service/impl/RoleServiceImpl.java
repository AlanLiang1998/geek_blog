package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import site.alanliang.geekblog.dao.RoleMapper;
import site.alanliang.geekblog.dao.RoleMenuMapper;
import site.alanliang.geekblog.dao.RoleUserMapper;
import site.alanliang.geekblog.exception.AssociationExistException;
import site.alanliang.geekblog.exception.EntityExistException;
import site.alanliang.geekblog.model.Role;
import site.alanliang.geekblog.model.RoleMenu;
import site.alanliang.geekblog.model.RoleUser;
import site.alanliang.geekblog.query.RoleQuery;
import site.alanliang.geekblog.service.RoleService;
import site.alanliang.geekblog.utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/10 10:38
 * Version 1.0
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleUserMapper roleUserMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Role getById(Long id) {
        Role role = new Role();
        //查询角色信息
        QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
        roleWrapper.select("id", "role_name", "description", "rank").eq("id", id);
        Role r = roleMapper.selectOne(roleWrapper);
        BeanUtils.copyProperties(r, role);
        //查询角色权限信息
        QueryWrapper<RoleMenu> roleMenuWrapper = new QueryWrapper<>();
        roleMenuWrapper.select("menu_id")
                .eq("role_id", id);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleMenuWrapper);
        List<Long> menuIdList = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        role.setMenuIdList(menuIdList);
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        QueryWrapper<RoleUser> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", id);
        //角色存在关联用户则不允许删除
        if (!CollectionUtils.isEmpty(roleUserMapper.selectList(wrapper))) {
            throw new AssociationExistException("该角色存在关联用户，不能删除");
        }
        roleMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        for (Long id : idList) {
            removeById(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOfUpdate(Role role) {
        if (role.getId() == null) {
            //保存
            //检查角色名称是否唯一
            QueryWrapper<Role> wrapper = new QueryWrapper<>();
            wrapper.eq("role_name", role.getRoleName());
            if (!CollectionUtils.isEmpty(roleMapper.selectList(wrapper))) {
                throw new EntityExistException("角色名称：" + role.getRoleName() + "已存在");
            }
            //保存角色
            roleMapper.insert(role);
            //保存角色权限
            roleMenuMapper.insertBatch(role.getId(), role.getMenuIdList());
        } else {
            //更新
            //检查角色名称是否唯一
            QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
            roleWrapper.eq("role_name", role.getRoleName());
            List<Role> roles = roleMapper.selectList(roleWrapper);
            roles = roles.stream().filter(r -> !r.getId().equals(role.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(roles)) {
                throw new EntityExistException("角色名称：" + role.getRoleName() + "已存在");
            }
            //更新角色
            roleMapper.updateById(role);
            //更新角色权限
            //先删除原有角色权限
            QueryWrapper<RoleMenu> roleMenuWrapper = new QueryWrapper<>();
            roleMenuWrapper.eq("role_id",role.getId());
            roleMenuMapper.delete(roleMenuWrapper);
            //再添加新的角色权限
            roleMenuMapper.insertBatch(role.getId(), role.getMenuIdList());
        }
    }

    @Override
    public Page<Role> listByPage(Integer current, Integer size, RoleQuery roleQuery) {
        Page<Role> page = new Page<>(current, size);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(roleQuery.getRoleName())) {
            wrapper.like("role_name", roleQuery.getRoleName());
        }
        if (!StringUtils.isEmpty(roleQuery.getDescription())) {
            wrapper.like("description", roleQuery.getDescription());
        }
        if (roleQuery.getStartDate() != null && roleQuery.getEndDate() != null) {
            wrapper.between("create_time", roleQuery.getStartDate(), roleQuery.getEndDate());
        }
        return roleMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Role> listAll() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.select("id", "role_name");
        return roleMapper.selectList(wrapper);
    }
}
