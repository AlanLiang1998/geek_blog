package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;
import site.alanliang.geekblog.model.LocalStorage;
import site.alanliang.geekblog.query.LocalStorageQuery;

import java.util.List;

public interface LocalStorageService {
    /**
     * 后台分页查询所有本地文件
     *
     * @param current           当前页码
     * @param size              页面大小
     * @param localStorageQuery 查询条件
     * @return 本地文件分页
     */
    Page<LocalStorage> listTableByPage(Integer current, Integer size, LocalStorageQuery localStorageQuery);


    /**
     * 根据ID删除本地文件
     *
     * @param id 本地文件ID
     */
    void removeById(Long id);

    /**
     * 根据ID列表批量删除本地文件
     *
     * @param idList 本地文件ID列表
     */
    void removeByIdList(List<Long> idList);


    /**
     * 上传本地文件
     *
     * @param file 文件
     */
    void upload(MultipartFile[] file);


    /**
     * 编辑
     *
     * @param localStorage 文件信息
     */
    void update(LocalStorage localStorage);

    /**
     * 根据ID查询本地文件
     *
     * @param id 本地文件ID
     * @return 本地文件
     */
    LocalStorage getById(Long id);
}
