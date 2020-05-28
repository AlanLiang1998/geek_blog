package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.model.OperationLog;
import site.alanliang.geekblog.vo.ViewDateVO;

import java.util.Date;
import java.util.List;

@Repository
public interface OperationLogMapper extends BaseMapper<OperationLog> {
    /**
     * 统计最近7天的前台流量量
     */
    List<ViewDateVO> countByLast7Days();

    /**
     * 根据用户名查询用户最后一次访问首页的时间
     *
     * @param username 用户名
     * @return 用户最后一次访问首页的时间
     */
    Date selectLastIndexViewTimeByUsername(@Param("username") String username);
}
