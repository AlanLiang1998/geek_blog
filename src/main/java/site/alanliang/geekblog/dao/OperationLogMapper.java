package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.model.OperationLog;
import site.alanliang.geekblog.vo.ViewDateVO;

import java.util.List;

@Repository
public interface OperationLogMapper extends BaseMapper<OperationLog> {
    /**
     * 统计最近7天的前台流量量
     */
    List<ViewDateVO> countByLast7Days();
}
