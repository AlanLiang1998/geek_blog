package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.alanliang.geekblog.dao.CommentMapper;
import site.alanliang.geekblog.model.Comment;
import site.alanliang.geekblog.service.CommentService;
import site.alanliang.geekblog.utils.LinkedListUtil;

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
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        commentMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        commentMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reply(Comment comment) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.select("article_id").eq("id", comment.getPid());
        Comment parentComment = commentMapper.selectOne(wrapper);
        comment.setArticleId(parentComment.getArticleId());
        commentMapper.insert(comment);
    }

    @Override
    public Page<Comment> listTableByPage(Integer current, Integer size) {
        Page<Comment> page = new Page<>(current, size);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.select("id", "nickname", "pid", "content", "article_id", "email", "create_time", "request_ip", "address")
                .isNotNull("article_id");
        return commentMapper.selectPage(page, wrapper);
    }

    @Override
    public Page<Comment> listByArticleId(Long articleId, Page<Comment> page) {
        Page<Comment> pageInfo = commentMapper.listRootPageByArticleId(page, articleId);
        List<Comment> comments = commentMapper.listByArticleId(articleId);
        LinkedListUtil.toCommentLinkedList(pageInfo.getRecords(), comments);
        return pageInfo;
    }
}
