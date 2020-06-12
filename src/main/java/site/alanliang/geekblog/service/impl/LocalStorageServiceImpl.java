package site.alanliang.geekblog.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.alanliang.geekblog.dao.LocalStorageMapper;
import site.alanliang.geekblog.exception.BadRequestException;
import site.alanliang.geekblog.model.LocalStorage;
import site.alanliang.geekblog.query.LocalStorageQuery;
import site.alanliang.geekblog.service.LocalStorageService;
import site.alanliang.geekblog.utils.FileUtil;
import site.alanliang.geekblog.utils.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "localStorage")
public class LocalStorageServiceImpl implements LocalStorageService {

    @Value("${file.path}")
    private String path;

    @Value("${file.maxSize}")
    private long maxSize;

    @Autowired
    private LocalStorageMapper localStorageMapper;

    @Override
    @Cacheable
    public Page<LocalStorage> listTableByPage(Integer current, Integer size, LocalStorageQuery localStorageQuery) {
        Page<LocalStorage> page = new Page<>(current, size);
        QueryWrapper<LocalStorage> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name", "real_name", "suffix", "type", "size", "create_time", "update_time");
        if (!StringUtils.isEmpty(localStorageQuery.getName())) {
            wrapper.like("name", localStorageQuery.getName());
        }
        if (localStorageQuery.getStartDate() != null && localStorageQuery.getEndDate() != null) {
            wrapper.between("create_time", localStorageQuery.getStartDate(), localStorageQuery.getEndDate());
        }
        return localStorageMapper.selectPage(page, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        LocalStorage localStorage = localStorageMapper.selectById(id);
        FileUtil.del(localStorage.getPath());
        localStorageMapper.deleteById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        List<LocalStorage> localStorages = localStorageMapper.selectBatchIds(idList);
        for (LocalStorage localStorage : localStorages) {
            FileUtil.del(localStorage.getPath());
        }
        localStorageMapper.deleteBatchIds(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void upload(MultipartFile[] multipartFiles) {
        for (MultipartFile multipartFile : multipartFiles) {
            FileUtil.checkSize(maxSize, multipartFile.getSize());
            String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
            String type = FileUtil.getFileType(suffix);
            File file = FileUtil.upload(multipartFile, path + type + File.separator);
            if (ObjectUtil.isNull(file)) {
                throw new BadRequestException("上传失败");
            }
            try {
                String name = FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename());
                LocalStorage localStorage = new LocalStorage();
                localStorage.setRealName(file.getName());
                localStorage.setName(name);
                localStorage.setSuffix(suffix);
                localStorage.setPath(file.getPath());
                localStorage.setType(type);
                localStorage.setSize(FileUtil.getSize(multipartFile.getSize()));
                localStorage.setCreateTime(new Date());
                localStorage.setUpdateTime(localStorage.getCreateTime());
                localStorageMapper.insert(localStorage);
            } catch (Exception e) {
                FileUtil.del(file);
                throw e;
            }
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(LocalStorage localStorage) {
        localStorageMapper.updateById(localStorage);
    }

    @Override
    @Cacheable
    public LocalStorage getById(Long id) {
        QueryWrapper<LocalStorage> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name").eq("id", id);
        return localStorageMapper.selectOne(wrapper);
    }
}
