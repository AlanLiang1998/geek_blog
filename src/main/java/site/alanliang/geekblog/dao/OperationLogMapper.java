package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.entity.SysOperationLog;

@Repository
public interface OperationLogMapper extends BaseMapper<SysOperationLog> {
}
