package site.alanliang.geekblog.service;

import site.alanliang.geekblog.dto.ArticleDto;
import site.alanliang.geekblog.vo.ArticleVo;

import java.util.List;

/**
 * @author AlanLiang
 */
public interface ArticleService {

    /**
     * 保存或编辑文章
     *
     * @param articleDto 文章DTO
     */
    void saveOrUpdate(ArticleDto articleDto);

    /**
     * 分页查询所有文章
     *
     * @param current 当前页码
     * @param size    页面大小
     * @return
     */
    List<ArticleVo> listByPage(Integer current, Integer size);

    /**
     * 查询记录总数
     *
     * @return 记录总数
     */
    long countAll();
}
