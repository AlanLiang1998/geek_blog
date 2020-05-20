package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.alanliang.geekblog.dao.MessageMapper;
import site.alanliang.geekblog.model.Comment;
import site.alanliang.geekblog.model.Message;
import site.alanliang.geekblog.service.MessageService;
import site.alanliang.geekblog.utils.LinkedListUtil;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/19 20:28
 * Version 1.0
 **/
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Page<Message> listTableByPage(Integer current, Integer size) {
        Page<Message> page = new Page<>(current, size);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.select("id", "nickname", "pid", "content", "email", "create_time", "request_ip", "address");
        return messageMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        messageMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        messageMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reply(Message message) {
        messageMapper.insert(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public Page<Message> listByPage(Integer current, Integer size) {
        Page<Message> page = new Page<>();
        Page<Message> pageInfo = messageMapper.listRootByPage(page);
        List<Message> messages = messageMapper.listAll();
        LinkedListUtil.toMessageLinkedList(pageInfo.getRecords(), messages);
        return pageInfo;
    }
}
