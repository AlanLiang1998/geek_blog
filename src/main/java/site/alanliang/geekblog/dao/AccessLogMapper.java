package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.model.AccessLog;
import site.alanliang.geekblog.vo.ViewDateVO;

import java.util.List;

@Repository
public interface AccessLogMapper extends BaseMapper<AccessLog> {
    /**
     * 统计最近7天的前台流量量
     */
    List<ViewDateVO> countByLast7Days();
}
