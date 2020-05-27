package site.alanliang.geekblog.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.vo.ViewDateVO;

import java.util.List;

@SpringBootTest
class AccessLogMapperTest {

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Test
    void countByWeek() {
        List<ViewDateVO> viewDateVOS = accessLogMapper.countByLast7Days();
        System.out.println(viewDateVOS);
    }
}