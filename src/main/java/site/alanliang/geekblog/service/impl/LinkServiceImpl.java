package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.dao.LinkMapper;
import site.alanliang.geekblog.exception.EntityExistException;
import site.alanliang.geekblog.model.Link;
import site.alanliang.geekblog.query.LinkQuery;
import site.alanliang.geekblog.service.LinkService;
import site.alanliang.geekblog.utils.StringUtils;
import site.alanliang.geekblog.vo.AuditVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/24 10:25
 * Version 1.0
 **/
@Service
@CacheConfig(cacheNames = "link")
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    @Cacheable
    public Page<Link> listTableByPage(Integer current, Integer size, LinkQuery linkQuery) {
        Page<Link> page = new Page<>(current, size);
        QueryWrapper<Link> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(linkQuery.getNickname())) {
            wrapper.like("nickname", linkQuery.getNickname());
        }
        if (linkQuery.getStartDate() != null && linkQuery.getEndDate() != null) {
            wrapper.between("create_time", linkQuery.getStartDate(), linkQuery.getEndDate());
        }
        if (linkQuery.getStatus() != null) {
            wrapper.eq("status", linkQuery.getStatus());
        }
        return linkMapper.selectPage(page, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void audit(AuditVO auditVO) {
        Link link = new Link();
        link.setId(auditVO.getId());
        link.setStatus(auditVO.getStatus());
        linkMapper.updateById(link);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        linkMapper.deleteBatchIds(idList);
    }

    @Override
    @Cacheable(key = "'listByPage:'+#current")
    public Page<Link> listByPage(Integer current, Integer size) {
        Page<Link> page = new Page<>(current, size);
        QueryWrapper<Link> wrapper = new QueryWrapper<>();
        wrapper.select("id", "nickname", "avatar", "introduction", "link")
                .eq("status", Constant.COMMENT_PASS)
                .orderByAsc("sort");
        return linkMapper.selectPage(page, wrapper);
    }

    @Override
    @Cacheable(key = "'getById:'+#id")
    public Link getById(Long id) {
        return linkMapper.selectById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void saveOfUpdate(Link link) {
        if (link.getId() == null) {
            //保存
            //验证昵称是否唯一
            QueryWrapper<Link> wrapper = new QueryWrapper<>();
            wrapper.eq("nickname", link.getNickname());
            if (null != linkMapper.selectOne(wrapper)) {
                throw new EntityExistException("昵称：" + link.getNickname() + "已存在");
            }
            linkMapper.insert(link);
        } else {
            //更新
            //验证昵称是否唯一
            QueryWrapper<Link> wrapper = new QueryWrapper<>();
            wrapper.eq("nickname", link.getNickname());
            List<Link> links = linkMapper.selectList(wrapper);
            links = links.stream().filter(u -> !u.getId().equals(link.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(links)) {
                throw new EntityExistException("昵称:" + link.getNickname() + " 已存在");
            }
            linkMapper.updateById(link);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        linkMapper.deleteById(id);
    }
}
