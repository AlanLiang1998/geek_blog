package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.alanliang.geekblog.model.Tag;
import site.alanliang.geekblog.query.TagQuery;

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
    List<Tag> listAll();

    /**
     * 根据文章ID查询标签
     *
     * @param id 文章ID
     * @return 标签列表
     */
    List<Tag> listByArticleId(Long id);

    /**
     * 分页查询所有标签
     *
     * @param current 当前页
     * @param size    页面大小
     * @param tagQuery 条件
     * @return 标签列表
     */
    Page<Tag> listTableByPage(Integer current, Integer size, TagQuery tagQuery);

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
    void removeById(Long id);

    /**
     * 根据ID列表批量删除标签
     *
     * @param idList 标签ID列表
     */
    void removeByIdList(List<Long> idList);

    /**
     * 根据ID查询标签
     *
     * @param id 标签ID
     * @return 标签
     */
    Tag getById(Long id);

    /**
     * 查询所有标签（统计文章数目）
     *
     * @return 标签列表
     */
    List<Tag> listByArticleCount();

    /**
     * 统计标签数量
     *
     * @return 标签数量
     */
    long countAll();

    /**
     * 查询标签的所有颜色
     *
     * @return 颜色列表
     */
    List<String> listColor();
}
