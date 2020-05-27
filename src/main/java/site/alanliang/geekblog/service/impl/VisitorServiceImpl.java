package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.dao.VisitorMapper;
import site.alanliang.geekblog.exception.BadRequestException;
import site.alanliang.geekblog.exception.EntityExistException;
import site.alanliang.geekblog.model.User;
import site.alanliang.geekblog.model.Visitor;
import site.alanliang.geekblog.query.UserQuery;
import site.alanliang.geekblog.service.VisitorService;
import site.alanliang.geekblog.utils.StringUtils;
import site.alanliang.geekblog.vo.VisitorLoginVO;

import javax.security.auth.login.Configuration;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/20 22:12
 * Version 1.0
 **/
@Service
public class VisitorServiceImpl implements VisitorService {

    @Override
    public Integer countAll() {
        return visitorMapper.selectCount(null);
    }

    @Autowired
    private VisitorMapper visitorMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Visitor visitor) {
        //保存
        //验证用户名是否唯一
        QueryWrapper<Visitor> wrapper = new QueryWrapper<>();
        wrapper.eq("username", visitor.getUsername());
        if (null != visitorMapper.selectOne(wrapper)) {
            throw new EntityExistException("用户名：" + visitor.getUsername() + "已存在");
        }
        //验证邮箱是否唯一
        wrapper.clear();
        wrapper.eq("email", visitor.getEmail());
        if (null != visitorMapper.selectOne(wrapper)) {
            throw new EntityExistException("邮箱：" + visitor.getEmail() + "已存在");
        }
        //验证昵称是否唯一
        wrapper.clear();
        wrapper.eq("nickname", visitor.getNickname());
        if (null != visitorMapper.selectOne(wrapper)) {
            throw new EntityExistException("昵称：" + visitor.getNickname() + "已存在");
        }
        visitorMapper.insert(visitor);
    }

    @Override
    public Visitor login(VisitorLoginVO visitorLoginVO) {
        QueryWrapper<Visitor> wrapper = new QueryWrapper<>();
        wrapper.select("id", "username", "password", "nickname", "avatar", "link", "status")
                .eq("username", visitorLoginVO.getCertificate())
                .or()
                .eq("email", visitorLoginVO.getCertificate());
        Visitor visitor = visitorMapper.selectOne(wrapper);
        try {
            if (visitor.getStatus().equals(Constant.VISITOR_DISABLE)) {
                throw new BadRequestException("用户已被停用");
            }
            if (!visitor.getPassword().equals(visitorLoginVO.getPassword())) {
                throw new BadRequestException("用户名或密码错误");
            }
        } catch (NullPointerException e) {
            throw new BadRequestException("用户名/邮箱不存在");
        }
        visitor.setPassword(null);
        return visitor;
    }

    @Override
    public Page<Visitor> listTableByPage(Integer current, Integer size, UserQuery userQuery) {
        Page<Visitor> page = new Page<>(current, size);
        QueryWrapper<Visitor> wrapper = new QueryWrapper<>();
        wrapper.select("id", "username", "nickname", "status", "email", "link", "create_time", "update_time");
        if (!StringUtils.isEmpty(userQuery.getUsername())) {
            wrapper.like("username", userQuery.getUsername());
        }
        if (!StringUtils.isEmpty(userQuery.getEmail())) {
            wrapper.like("email", userQuery.getEmail());
        }
        if (userQuery.getStartDate() != null && userQuery.getEndDate() != null) {
            wrapper.between("create_time", userQuery.getStartDate(), userQuery.getEndDate());
        }
        return visitorMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeStatus(Long id) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("status");
        Visitor visitor = visitorMapper.selectById(id);
        if (Objects.equals(visitor.getStatus(), Constant.VISITOR_ENABLE)) {
            visitor.setStatus(Constant.VISITOR_DISABLE);
        } else if (Objects.equals(visitor.getStatus(), Constant.VISITOR_DISABLE)) {
            visitor.setStatus(Constant.VISITOR_ENABLE);
        }
        visitor.setId(id);
        visitorMapper.updateById(visitor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        visitorMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        visitorMapper.deleteBatchIds(idList);
    }
}
