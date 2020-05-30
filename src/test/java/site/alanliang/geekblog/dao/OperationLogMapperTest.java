package site.alanliang.geekblog.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.alanliang.geekblog.vo.ViewDateVO;

import java.util.Date;
import java.util.List;

@SpringBootTest
class OperationLogMapperTest {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Test
    void countByLast7Days() {
        List<ViewDateVO> viewDateVOS =
                operationLogMapper.countByLast7Days();
        System.out.println(viewDateVOS);
    }

    @Test
    void selectLastIndexViewTimeByUsername() {
        Date date = operationLogMapper.selectLastIndexViewTimeByUsername("admin");
        System.out.println(date);
    }
}