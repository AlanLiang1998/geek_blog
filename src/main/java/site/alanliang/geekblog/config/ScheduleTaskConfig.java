package site.alanliang.geekblog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import site.alanliang.geekblog.utils.ElasticSearchUtil;

/**
 * @Description:
 * @Author: Alan
 * @Date: 2021/01/11 17:15
 */
@Slf4j
@Configuration
@EnableScheduling
public class ScheduleTaskConfig {

    @Autowired
    private ElasticSearchUtil elasticSearchUtil;

    /**
     * 每天凌晨5点重置elasticsearch
     */
    @Scheduled(cron = "0 0 5 * * ?")
    private void resetElasticSearch() {
        log.info("重置elasticsearch");
        elasticSearchUtil.sync();
    }
}
