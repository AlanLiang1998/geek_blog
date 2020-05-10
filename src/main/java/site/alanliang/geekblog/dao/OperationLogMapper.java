package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.model.OperationLog;

@Repository
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
