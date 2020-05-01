package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.entity.SysUser;

/**
 * @author AlanLiang
 */
@Repository
public interface UserMapper extends BaseMapper<SysUser> {

}
