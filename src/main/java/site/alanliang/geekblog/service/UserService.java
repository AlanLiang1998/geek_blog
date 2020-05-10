package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.model.User;
import site.alanliang.geekblog.query.UserQuery;

import java.util.List;

/**
 * @author AlanLiang
 */
public interface UserService {
    /**
     * 分页查询所有用户
     *
     * @param current   当前页码
     * @param size      页面大小
     * @param userQuery
     * @return 用户列表
     */
    Page<User> listByPage(int current, int size, UserQuery userQuery);

    /**
     * 检验用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    User checkUser(String username, String password);

    /**
     * 保存或更新用户
     *
     * @param user 用户
     */
    void saveOfUpdate(User user);

    /**
     * 根据id查询用户
     *
     * @param id 用户id
     * @return 用户
     */
    User getById(Long id);

    /**
     * 改变用户状态
     *
     * @param userId 用户ID
     */
    void changeStatus(Long userId);

    /**
     * 根据id删除用户
     *
     * @param id 用户ID
     */
    void removeById(Long id);

    /**
     * 根据id列表批量删除用户
     *
     * @param idList 用户ID列表
     */
    void removeByIdList(List<Long> idList);
}
