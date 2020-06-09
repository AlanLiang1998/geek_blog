package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.model.Link;
import site.alanliang.geekblog.query.LinkQuery;
import site.alanliang.geekblog.vo.AuditVO;

import java.util.List;

public interface LinkService {
    /**
     * 分页查询所有友情链接
     *
     * @param current   当前页码
     * @param size      页面大小
     * @param linkQuery 查询条件
     * @return 友情链接分页
     */
    Page<Link> listTableByPage(Integer current, Integer size, LinkQuery linkQuery);

    /**
     * 审核友链
     *
     * @param auditVO 友链
     */
    void audit(AuditVO auditVO);

    /**
     * 根据ID列表批量删除友链
     *
     * @param idList 友链ID列表
     */
    void removeByIdList(List<Long> idList);

    /**
     * 根据ID删除友链
     *
     * @param id 友链ID
     */
    void removeById(Long id);

    /**
     * 保存或更新友链
     *
     * @param link 友链
     */
    void saveOfUpdate(Link link);

    /**
     * 根据ID查询友链
     *
     * @param id 友链ID
     * @return 友链
     */
    Link getById(Long id);

    /**
     * 分页查询所有友链
     *
     * @param current 当前页码
     * @param size    页面大小
     * @return 友链分页
     */
    Page<Link> listByPage(Integer current, Integer size);
}
