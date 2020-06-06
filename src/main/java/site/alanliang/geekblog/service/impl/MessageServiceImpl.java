package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.dao.MessageMapper;
import site.alanliang.geekblog.dao.OperationLogMapper;
import site.alanliang.geekblog.model.Message;
import site.alanliang.geekblog.query.MessageQuery;
import site.alanliang.geekblog.service.MessageService;
import site.alanliang.geekblog.utils.LinkedListUtil;
import site.alanliang.geekblog.utils.StringUtils;
import site.alanliang.geekblog.utils.UserInfoUtil;
import site.alanliang.geekblog.vo.AuditVO;

import java.util.Date;
import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/19 20:28
 * Version 1.0
 **/
@Service
@CacheConfig(cacheNames = "message")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public Integer countByLastIndexViewToNow() {
        String username = UserInfoUtil.getUsername();
        if (org.springframework.util.StringUtils.isEmpty(username)) {
            return 0;
        }
        Date date = operationLogMapper.selectLastIndexViewTimeByUsername(username);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.between("create_time", date, new Date());
        return messageMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "'listNewest'")
    public List<Message> listNewest() {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.select("id", "nickname", "content", "create_time", "status")
                .orderByDesc("create_time")
                .last("limit " + Constant.NEWEST_PAGE_SIZE);
        return messageMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void audit(AuditVO auditVO) {
        Message message = new Message();
        message.setId(auditVO.getId());
        message.setStatus(auditVO.getStatus());
        messageMapper.updateById(message);
    }

    @Override
    @Cacheable
    public Page<Message> listTableByPage(Integer current, Integer size, MessageQuery messageQuery) {
        Page<Message> page = new Page<>(current, size);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.select("id", "nickname", "pid", "content", "email", "create_time", "request_ip", "address", "status");
        if (!StringUtils.isEmpty(messageQuery.getNickname())) {
            wrapper.like("nickname", messageQuery.getNickname());
        }
        if (messageQuery.getStartDate() != null && messageQuery.getEndDate() != null) {
            wrapper.between("create_time", messageQuery.getStartDate(), messageQuery.getEndDate());
        }
        if (messageQuery.getStatus() != null) {
            wrapper.eq("status", messageQuery.getStatus());
        }
        wrapper.orderByDesc("create_time");
        return messageMapper.selectPage(page, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        messageMapper.deleteById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        messageMapper.deleteBatchIds(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void reply(Message message) {
        messageMapper.insert(message);
    }

    @Override
    @Cacheable(key = "'countAll'")
    public Integer countAll() {
        return messageMapper.selectCount(null);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void save(Message message) {
        messageMapper.insert(message);
    }

    @Override
    @Cacheable(key = "'listByPage:'+current")
    public Page<Message> listByPage(Integer current, Integer size) {
        Page<Message> page = new Page<>(current, size);
        Page<Message> pageInfo = messageMapper.listRootByPage(page);
        List<Message> messages = messageMapper.listAll();
        LinkedListUtil.toMessageLinkedList(pageInfo.getRecords(), messages);
        return pageInfo;
    }
}
