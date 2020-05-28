package site.alanliang.geekblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import site.alanliang.geekblog.model.Notice;

@Repository
public interface NoticeMapper extends BaseMapper<Notice> {
}
