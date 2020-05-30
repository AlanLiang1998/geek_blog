package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.model.Visitor;

import java.util.Map;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/20 22:04
 * Version 1.0
 **/
@Repository
public interface VisitorMapper extends BaseMapper<Visitor> {
    /**
     * 根据ID查询访客
     *
     * @return 访客
     */
    Visitor selectByVisitorId(Map<String, Object> map);
}
