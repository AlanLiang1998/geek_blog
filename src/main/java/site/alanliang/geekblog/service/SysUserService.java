package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.domain.SysUser;

/**
 * @author AlanLiang
 */
public interface SysUserService {
    /**
     * 分页查询所有用户
     *
     * @param current  当前页码
     * @param size 页面大小
     * @return 用户列表
     */
    Page<SysUser> listByPage(int current, int size);
}
