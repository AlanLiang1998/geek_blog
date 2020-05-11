package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import site.alanliang.geekblog.dao.RoleMapper;
import site.alanliang.geekblog.dao.RoleUserMapper;
import site.alanliang.geekblog.exception.AssociationExistException;
import site.alanliang.geekblog.exception.EntityExistException;
import site.alanliang.geekblog.model.Role;
import site.alanliang.geekblog.model.RoleUser;
import site.alanliang.geekblog.model.User;
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


    @Override
    public Role getById(Long id) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.select("id", "role_name", "description", "rank")
                .eq("id", id);
        return roleMapper.selectOne(wrapper);
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
            roleMapper.insert(role);
        } else {
            //更新
            //检查角色名称是否唯一
            QueryWrapper<Role> wrapper = new QueryWrapper<>();
            wrapper.eq("role_name", role.getRoleName());
            List<Role> roles = roleMapper.selectList(wrapper);
            roles = roles.stream().filter(r -> !r.getId().equals(role.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(roles)) {
                throw new EntityExistException("角色名称：" + role.getRoleName() + "已存在");
            }
            roleMapper.updateById(role);
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
