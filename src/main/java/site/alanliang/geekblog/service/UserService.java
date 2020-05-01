package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.entity.SysUser;

/**
 * @author AlanLiang
 */
public interface UserService {
    /**
     * 分页查询所有用户
     *
     * @param current  当前页码
     * @param size 页面大小
     * @return 用户列表
     */
    Page<SysUser> listByPage(int current, int size);

    /**
     * 检验用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    SysUser checkUser(String username, String password);
}
