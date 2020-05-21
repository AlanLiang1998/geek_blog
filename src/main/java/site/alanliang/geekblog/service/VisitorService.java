package site.alanliang.geekblog.service;

import site.alanliang.geekblog.model.Visitor;
import site.alanliang.geekblog.vo.VisitorLoginVO;

public interface VisitorService {
    /**
     * 保存访客
     *
     * @param visitor 访客
     */
    void save(Visitor visitor);

    /**
     * 访客登录
     *
     * @param visitorLoginVO 登录信息
     * @return
     */
    Visitor login(VisitorLoginVO visitorLoginVO);
}
