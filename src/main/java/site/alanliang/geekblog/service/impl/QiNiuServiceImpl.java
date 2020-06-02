package site.alanliang.geekblog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.alanliang.geekblog.dao.QiNiuConfigMapper;
import site.alanliang.geekblog.dao.QiNiuContentMapper;
import site.alanliang.geekblog.exception.BadRequestException;
import site.alanliang.geekblog.model.QiniuConfig;
import site.alanliang.geekblog.model.QiniuContent;
import site.alanliang.geekblog.query.QiNiuQuery;
import site.alanliang.geekblog.service.QiNiuService;
import site.alanliang.geekblog.utils.FileUtil;
import site.alanliang.geekblog.utils.QiNiuUtil;
import site.alanliang.geekblog.utils.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Service
@CacheConfig(cacheNames = "qiniu")
public class QiNiuServiceImpl implements QiNiuService {

    @Autowired
    private QiNiuConfigMapper qiNiuConfigMapper;

    @Autowired
    private QiNiuContentMapper qiNiuContentMapper;

    @Value("${qiniu.max-size}")
    private Long maxSize;

    @Override
    @Cacheable(key = "'table'+#current")
    public Page<QiniuContent> queryAll(Integer current, Integer size, QiNiuQuery qiNiuQuery) {
        Page<QiniuContent> page = new Page<>(current, size);
        QueryWrapper<QiniuContent> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(qiNiuQuery.getName())) {
            wrapper.like("name", qiNiuQuery.getName());
        }
        if (!StringUtils.isEmpty(qiNiuQuery.getStartDate()) && !StringUtils.isEmpty(qiNiuQuery.getEndDate())) {
            wrapper.between("update_time", qiNiuQuery.getStartDate(), qiNiuQuery.getEndDate());
        }
        return qiNiuContentMapper.selectPage(page, wrapper);
    }

    @Override
    @Cacheable
    public List<QiniuContent> queryAll(QiNiuQuery qiNiuQuery) {
        return qiNiuContentMapper.selectList(null);
    }

    @Override
    @Cacheable(key = "'1'")
    public QiniuConfig find() {
        return qiNiuConfigMapper.selectById(1L);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(QiniuConfig qiniuConfig) {
        String http = "http://", https = "https://";
        if (!(qiniuConfig.getHost().toLowerCase().startsWith(http) || qiniuConfig.getHost().toLowerCase().startsWith(https))) {
            throw new BadRequestException("外链域名必须以http://或者https://开头");
        }
        qiniuConfig.setId(1L);
        qiNiuConfigMapper.updateById(qiniuConfig);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void upload(MultipartFile[] files, QiniuConfig qiniuConfig) {
        for (MultipartFile file : files) {
            FileUtil.checkSize(maxSize, file.getSize());
            if (qiniuConfig.getId() == null) {
                throw new BadRequestException("请先添加相应配置，再操作");
            }
            // 构造一个带指定Zone对象的配置类
            Configuration cfg = new Configuration(QiNiuUtil.getRegion(qiniuConfig.getZone()));
            UploadManager uploadManager = new UploadManager(cfg);
            Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
            String upToken = auth.uploadToken(qiniuConfig.getBucket());
            try {
                String name = file.getOriginalFilename();
                QueryWrapper<QiniuContent> wrapper = new QueryWrapper<>();
                wrapper.eq("name", name);
                if (qiNiuContentMapper.selectOne(wrapper) != null) {
                    name = QiNiuUtil.getKey(name);
                }
                Response response = uploadManager.put(file.getBytes(), name, upToken);
                //解析上传成功的结果

                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                //存入数据库
                QiniuContent qiniuContent = new QiniuContent();
                qiniuContent.setSuffix(FileUtil.getExtensionName(putRet.key));
                qiniuContent.setBucket(qiniuConfig.getBucket());
                qiniuContent.setType(qiniuConfig.getType());
                qiniuContent.setName(FileUtil.getFileNameNoEx(putRet.key));
                qiniuContent.setUrl(qiniuConfig.getHost() + "/" + putRet.key);
                qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(file.getSize() + "")));
                qiniuContent.setFileType(FileUtil.getFileType(qiniuContent.getSuffix()));
                qiniuContent.setUpdateTime(new Date());
                qiNiuContentMapper.insert(qiniuContent);
            } catch (Exception e) {
                throw new BadRequestException(e.getMessage());
            }
        }
    }

    @Override
    @Cacheable
    public QiniuContent findByContentId(Long id) {
        return qiNiuContentMapper.selectById(id);
    }

    @Override
    public String download(QiniuContent content, QiniuConfig config) {
        String finalUrl;
        String type = "公开";
        if (type.equals(content.getType())) {
            finalUrl = content.getUrl();
        } else {
            Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
            // 1小时，可以自定义链接过期时间
            long expireInSeconds = 3600;
            finalUrl = auth.privateDownloadUrl(content.getUrl(), expireInSeconds);
        }
        return finalUrl;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id, QiniuConfig config) {
        QiniuContent content = qiNiuContentMapper.selectById(id);
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(content.getBucket(), content.getName() + "." + content.getSuffix());
            qiNiuContentMapper.deleteById(id);
        } catch (QiniuException ex) {
            qiNiuContentMapper.deleteById(id);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void synchronize(QiniuConfig config) {
        if (config.getId() == null) {
            throw new BadRequestException("请先添加相应配置，再操作");
        }
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(config.getBucket(), prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            QiniuContent qiniuContent;
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                QueryWrapper<QiniuContent> wrapper = new QueryWrapper<>();
                wrapper.eq("name", FileUtil.getFileNameNoEx(item.key));
                if (qiNiuContentMapper.selectOne(wrapper) == null) {
                    qiniuContent = new QiniuContent();
                    qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(item.fsize + "")));
                    qiniuContent.setSuffix(FileUtil.getExtensionName(item.key));
                    qiniuContent.setName(FileUtil.getFileNameNoEx(item.key));
                    qiniuContent.setType(config.getType());
                    qiniuContent.setBucket(config.getBucket());
                    qiniuContent.setUrl(config.getHost() + "/" + item.key);
                    qiniuContent.setUpdateTime(new Date());
                    qiniuContent.setFileType(FileUtil.getFileType(qiniuContent.getSuffix()));
                    qiNiuContentMapper.insert(qiniuContent);
                }
            }
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> idList, QiniuConfig config) {
        for (Long id : idList) {
            delete(id, config);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(String type) {
        QiniuContent content = new QiniuContent();
        content.setId(1L);
        content.setType(type);
        qiNiuContentMapper.updateById(content);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void downloadList(List<QiniuContent> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QiniuContent content : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("文件名", content.getName());
            map.put("文件类型", content.getSuffix());
            map.put("空间名称", content.getBucket());
            map.put("文件大小", content.getSize());
            map.put("空间类型", content.getType());
            map.put("创建日期", content.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
