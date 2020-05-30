package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;
import site.alanliang.geekblog.model.OperationLog;
import site.alanliang.geekblog.query.LogQuery;
import site.alanliang.geekblog.vo.ViewDateVO;

import java.util.List;

public interface OperationLogService {
    /**
     * 保存日志数据
     *
     * @param username  用户
     * @param browser   浏览器
     * @param ip        请求IP
     * @param joinPoint /
     * @param log       日志实体
     */
    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, OperationLog log);

    /**
     * 分页获取所有日志
     *
     * @param current  当前页码
     * @param size     页码大小
     * @param logQuery
     * @return 日志列表
     */
    Page<OperationLog> listByPage(Integer current, Integer size, LogQuery logQuery);

    /**
     * 根据ID删除日志
     *
     * @param id 日志ID
     */
    void remove(Long id);

    /**
     * 根据ID列表批量删除日志
     *
     * @param idList 日志ID列表
     */
    void removeByIdList(List<Long> idList);

    /**
     * 查询最近的操作日志
     *
     * @return 操作日志列表
     */
    List<OperationLog> listNewest();

    /**
     * 统计最近7天的访问量
     */
    List<ViewDateVO> countByLast7Days();

}
