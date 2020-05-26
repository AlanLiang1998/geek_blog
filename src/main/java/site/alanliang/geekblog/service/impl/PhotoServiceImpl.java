package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.dao.PhotoMapper;
import site.alanliang.geekblog.model.Photo;
import site.alanliang.geekblog.query.PhotoQuery;
import site.alanliang.geekblog.service.PhotoService;
import site.alanliang.geekblog.utils.StringUtils;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoMapper photoMapper;

    @Override
    public List<Photo> listAll() {
        QueryWrapper<Photo> wrapper = new QueryWrapper<>();
        wrapper.select("url", "description").orderByAsc("sort").eq("display", Constant.DISPLAY);
        return photoMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        photoMapper.deleteById(id);
    }

    @Override
    public Photo getById(Long id) {
        return photoMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        photoMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOfUpdate(Photo photo) {
        if (photo.getId() == null) {
            photoMapper.insert(photo);
        } else {
            photoMapper.updateById(photo);
        }
    }

    @Override
    public Page<Photo> listTableByPage(int current, int size, PhotoQuery photoQuery) {
        Page<Photo> page = new Page<>(current, size);
        QueryWrapper<Photo> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(photoQuery.getDescription())) {
            wrapper.like("description", photoQuery.getDescription());
        }
        if (photoQuery.getStartDate() != null && photoQuery.getEndDate() != null) {
            wrapper.between("create_time", photoQuery.getStartDate(), photoQuery.getEndDate());
        }
        if (photoQuery.getDisplay() != null) {
            wrapper.eq("display", photoQuery.getDisplay());
        }
        return photoMapper.selectPage(page, wrapper);
    }
}
