package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.model.Photo;
import site.alanliang.geekblog.query.PhotoQuery;

import java.util.List;

public interface PhotoService {
    /**
     * 前台分页查询所有照片
     *
     * @return 照片分页
     */
    List<Photo> listAll();

    /**
     * 后台分页查询所有照片
     *
     * @param current    当前页
     * @param size       页面大小
     * @param photoQuery 查询条件
     * @return 照片分页
     */
    Page<Photo> listTableByPage(int current, int size, PhotoQuery photoQuery);

    /**
     * 根据ID删除照片
     *
     * @param id 照片ID
     */
    void removeById(Long id);

    /**
     * 根据ID列表批量删除照片
     *
     * @param idList 照片ID列表
     */
    void removeByIdList(List<Long> idList);

    /**
     * 保存或更新照片
     *
     * @param photo 照片
     */
    void saveOfUpdate(Photo photo);

    /**
     * 根据ID查询照片
     *
     * @param id 照片ID
     */
    Photo getById(Long id);
}