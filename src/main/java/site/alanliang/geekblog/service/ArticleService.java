package site.alanliang.geekblog.service;

import site.alanliang.geekblog.dto.ArticleDto;

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
}
