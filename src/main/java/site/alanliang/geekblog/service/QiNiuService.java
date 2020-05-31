package site.alanliang.geekblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;
import site.alanliang.geekblog.model.QiniuConfig;
import site.alanliang.geekblog.model.QiniuContent;
import site.alanliang.geekblog.query.QiNiuQuery;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface QiNiuService {
    /**
     * 后台分页查询
     *
     * @param current    当前页码
     * @param size       页码大小
     * @param qiNiuQuery 查询条件
     * @return 七牛分页
     */
    Page<QiniuContent> queryAll(Integer current, Integer size, QiNiuQuery qiNiuQuery);

    /**
     * 后台查询全部
     *
     * @param qiNiuQuery 查询条件
     * @return /
     */
    List<QiniuContent> queryAll(QiNiuQuery qiNiuQuery);

    /**
     * 查配置
     *
     * @return QiniuConfig
     */
    QiniuConfig find();

    /**
     * 修改配置
     *
     * @param qiniuConfig 配置
     */
    void update(QiniuConfig qiniuConfig);

    /**
     * 上传文件
     *  @param file        文件
     * @param qiniuConfig 配置
     */
    void upload(MultipartFile[] files, QiniuConfig qiniuConfig);

    /**
     * 查询文件
     *
     * @param id 文件ID
     * @return QiniuContent
     */
    QiniuContent findByContentId(Long id);

    /**
     * 下载文件
     *
     * @param content 文件信息
     * @param config  配置
     * @return String
     */
    String download(QiniuContent content, QiniuConfig config);

    /**
     * 删除文件
     *
     * @param id     文件ID
     * @param config 配置
     */
    void delete(Long id, QiniuConfig config);

    /**
     * 同步数据
     *
     * @param config 配置
     */
    void synchronize(QiniuConfig config);

    /**
     * 删除文件
     *
     * @param idList 文件ID列表
     * @param config 配置
     */
    void deleteAll(List<Long> idList, QiniuConfig config);

    /**
     * 更新数据
     *
     * @param type 类型
     */
    void update(String type);

    /**
     * 导出数据
     *
     * @param queryAll /
     * @param response /
     * @throws IOException /
     */
    void downloadList(List<QiniuContent> queryAll, HttpServletResponse response) throws IOException;
}
