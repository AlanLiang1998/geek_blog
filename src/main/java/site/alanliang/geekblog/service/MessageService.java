package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.model.Message;

import java.util.List;

public interface MessageService {
    /**
     * 后台分页查询所有留言
     *
     * @param current 当前页码
     * @param size    页面大小
     * @return 留言分页
     */
    Page<Message> listTableByPage(Integer current, Integer size);


    /**
     * 根据ID删除留言
     *
     * @param id 留言ID
     */
    void removeById(Long id);

    /**
     * 根据ID列表批量删除留言
     *
     * @param idList 留言ID列表
     */
    void removeByIdList(List<Long> idList);

    void reply(Message message);

    /**
     * 分页查询所有留言
     *
     * @param current 当前页码
     * @param size    页面大小
     * @return 留言分页
     */
    Page<Message> listByPage(Integer current, Integer size);

    /**
     * 保存留言
     *
     * @param message 留言
     */
    void save(Message message);
}
