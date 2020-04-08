package site.alanliang.geekblog.service;

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
}
