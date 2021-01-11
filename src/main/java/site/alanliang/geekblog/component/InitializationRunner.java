package site.alanliang.geekblog.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import site.alanliang.geekblog.repository.ArticleDocumentRepository;
import site.alanliang.geekblog.utils.ElasticSearchUtil;

/**
 * @Description:
 * @Author: Alan
 * @Date: 2021/01/11 16:03
 */
@Component
public class InitializationRunner implements ApplicationRunner {

    @Autowired
    private ElasticSearchUtil elasticSearchUtil;

    @Autowired
    private ArticleDocumentRepository articleDocumentRepository;

    @Override
    public void run(ApplicationArguments args) {
        long count = articleDocumentRepository.count();
        if (count == 0) {
            elasticSearchUtil.sync();
        }
    }
}
