package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.model.User;
import site.alanliang.geekblog.query.UserQuery;
import site.alanliang.geekblog.vo.UserInfoVO;
import site.alanliang.geekblog.vo.UserLoginVO;

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
     * @param userQuery 条件
     * @return 用户列表
     */
    Page<User> listTableByPage(int current, int size, UserQuery userQuery);

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

    /**
     * 修改用户密码
     *
     * @param passwordVO 用户新旧密码
     */
    void changePassword(UserLoginVO passwordVO);

    /**
     * 修改个人信息
     *
     * @param userInfoVO 个人信息
     */
    void updateInfo(UserInfoVO userInfoVO);

    /**
     * 统计用户总数
     * @return  用户总数
     */
    Integer countAll();
}
