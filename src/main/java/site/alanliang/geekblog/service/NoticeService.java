package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.model.Notice;
import site.alanliang.geekblog.query.NoticeQuery;

import java.util.List;

public interface NoticeService {

    /**
     * 后台分页查询所有公告
     *
     * @param current     当前页码
     * @param size        页面大小
     * @param noticeQuery 查询条件
     * @return 公告分页
     */
    Page<Notice> listTableByPage(Integer current, Integer size, NoticeQuery noticeQuery);


    /**
     * 根据ID删除公告
     *
     * @param id 公告ID
     */
    void removeById(Long id);

    /**
     * 根据ID列表批量删除公告
     *
     * @param idList 公告ID列表
     */
    void removeByIdList(List<Long> idList);


    /**
     * 保存或更新公告
     *
     * @param notice 公告
     */
    void saveOfUpdate(Notice notice);


    /**
     * 查询最近的公告
     *
     * @return 公告列表
     */
    List<Notice> listNewest();

    /**
     * 根据ID查询公告
     *
     * @param id 公告ID
     * @return 公告
     */
    Notice getById(Long id);
}
