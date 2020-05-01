package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.entity.SysAccessLog;

@Repository
public interface AccessLogMapper extends BaseMapper<SysAccessLog> {
}
