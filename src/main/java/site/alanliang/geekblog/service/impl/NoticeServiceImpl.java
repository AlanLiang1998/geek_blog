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
import site.alanliang.geekblog.dao.NoticeMapper;
import site.alanliang.geekblog.model.Notice;
import site.alanliang.geekblog.query.NoticeQuery;
import site.alanliang.geekblog.service.NoticeService;
import site.alanliang.geekblog.utils.StringUtils;

import java.util.List;

@Service
@CacheConfig(cacheNames = "notice")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    @Cacheable
    public Page<Notice> listTableByPage(Integer current, Integer size, NoticeQuery noticeQuery) {
        Page<Notice> page = new Page<>(current, size);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(noticeQuery.getTitle())) {
            wrapper.like("title", noticeQuery.getTitle());
        }
        if (!StringUtils.isEmpty(noticeQuery.getContent())) {
            wrapper.like("content", noticeQuery.getContent());
        }
        if (noticeQuery.getStartDate() != null && noticeQuery.getEndDate() != null) {
            wrapper.between("create_time", noticeQuery.getStartDate(), noticeQuery.getEndDate());
        }
        if (noticeQuery.getDisplay() != null) {
            wrapper.eq("display", noticeQuery.getDisplay());
        }
        wrapper.orderByDesc("create_time");
        return noticeMapper.selectPage(page, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        noticeMapper.deleteById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        noticeMapper.deleteBatchIds(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void saveOfUpdate(Notice notice) {
        if (notice.getId() == null) {
            noticeMapper.insert(notice);
        } else {
            noticeMapper.updateById(notice);
        }
    }

    @Override
    @Cacheable
    public Notice getById(Long id) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "content", "sort", "display")
                .eq("id", id);
        return noticeMapper.selectOne(wrapper);
    }

    @Override
    @Cacheable
    public List<Notice> listNewest() {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "content", "create_time")
                .eq("display", Constant.DISPLAY)
                .orderByAsc("sort")
                .orderByDesc("create_time")
                .last("limit " + Constant.NEWEST_PAGE_SIZE);
        return noticeMapper.selectList(wrapper);
    }
}
