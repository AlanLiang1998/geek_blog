package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.model.Visitor;
import site.alanliang.geekblog.query.UserQuery;
import site.alanliang.geekblog.vo.VisitorLoginVO;

import java.util.List;

public interface VisitorService {
    /**
     * 保存访客
     *
     * @param visitor 访客
     */
    void save(Visitor visitor);

    /**
     * 访客登录
     *
     * @param visitorLoginVO 登录信息
     * @return
     */
    Visitor login(VisitorLoginVO visitorLoginVO);

    /**
     * 后台分页查询访客
     *
     * @param current   当前页码
     * @param size      页码大小
     * @param userQuery 查询条件
     * @return 访客分页
     */
    Page<Visitor> listTableByPage(Integer current, Integer size, UserQuery userQuery);

    /**
     * 根据ID修改访客状态
     *
     * @param id 访客ID
     */
    void changeStatus(Long id);

    /**
     * 根据ID删除访客
     *
     * @param id 访客ID
     */
    void removeById(Long id);

    /**
     * 根据ID列表批量删除访客
     *
     * @param idList 访客ID列表
     */
    void removeByIdList(List<Long> idList);

    /**
     * 统计访客总数
     *
     * @return 访客总数
     */
    Integer countAll();
}
