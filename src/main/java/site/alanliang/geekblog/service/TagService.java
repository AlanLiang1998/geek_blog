package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.domain.Tag;

import java.util.List;

/**
 * @Author AlanLiang
 **/
public interface TagService {

    /**
     * 查询所有标签
     *
     * @return 标签列表
     */
    List<Tag> list();

    /**
     * 根据文章ID查询标签
     *
     * @param id 文章ID
     * @return 标签列表
     */
    List<Tag> findByArticleId(Long id);

    /**
     * 分页查询所有标签
     *
     * @param current 当前页
     * @param size    页面大小
     * @param wrapper 条件
     * @return 标签列表
     */
    Page<Tag> listByPage(Integer current, Integer size, QueryWrapper<Tag> wrapper);

    /**
     * 新增或者更新标签
     *
     * @param tag 标签
     */
    void saveOfUpdate(Tag tag);

    /**
     * 根据ID删除标签
     *
     * @param id 标签ID
     */
    void remove(Long id);

    /**
     * 根据ID列表批量删除标签
     *
     * @param idList 标签ID列表
     */
    void batchRemove(List<Long> idList);

    /**
     * 根据ID查询标签
     *
     * @param id 标签ID
     * @return 标签
     */
    Tag findById(Long id);
}
