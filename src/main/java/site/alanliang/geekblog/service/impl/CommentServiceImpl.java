package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.alanliang.geekblog.dao.CommentMapper;
import site.alanliang.geekblog.model.Comment;
import site.alanliang.geekblog.service.CommentService;
import site.alanliang.geekblog.utils.CommentLinkedListUtil;

import java.util.List;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/17 9:33
 * Version 1.0
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public Page<Comment> listMessageByPage(Page<Comment> page) {
        Page<Comment> pageInfo = commentMapper.listRootMessageByPage(page);
        List<Comment> comments = commentMapper.listMessage();
        CommentLinkedListUtil.toLinkedList(pageInfo.getRecords(), comments);
        return pageInfo;
    }

    @Override
    public Page<Comment> listByArticleId(Long articleId, Page<Comment> page) {
        Page<Comment> pageInfo = commentMapper.listRootPageByArticleId(page, articleId);
        List<Comment> comments = commentMapper.listByArticleId(articleId);
        CommentLinkedListUtil.toLinkedList(pageInfo.getRecords(), comments);
        return pageInfo;
    }
}
