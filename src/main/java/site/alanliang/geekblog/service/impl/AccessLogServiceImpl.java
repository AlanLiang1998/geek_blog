package site.alanliang.geekblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.common.TableConstant;
import site.alanliang.geekblog.dao.AccessLogMapper;
import site.alanliang.geekblog.dao.OperationLogMapper;
import site.alanliang.geekblog.model.AccessLog;
import site.alanliang.geekblog.query.LogQuery;
import site.alanliang.geekblog.service.AccessLogService;
import site.alanliang.geekblog.utils.StringUtils;
import site.alanliang.geekblog.utils.UserInfoUtil;
import site.alanliang.geekblog.vo.ViewDateVO;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/4/26 11:12
 * Version 1.0
 **/
@Service
public class AccessLogServiceImpl implements AccessLogService {

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public List<AccessLog> listNewest() {
        QueryWrapper<AccessLog> wrapper = new QueryWrapper<>();
        wrapper.select(AccessLog.Table.ID, AccessLog.Table.REQUEST_IP, AccessLog.Table.ADDRESS, AccessLog.Table.CREATE_TIME, AccessLog.Table.DESCRIPTION, AccessLog.Table.STATUS)
                .orderByDesc(AccessLog.Table.CREATE_TIME)
                .last(TableConstant.LIMIT + Constant.NEWEST_PAGE_SIZE);
        return accessLogMapper.selectList(wrapper);
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, AccessLog log) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        site.alanliang.geekblog.anntation.AccessLog aopLog = method.getAnnotation(site.alanliang.geekblog.anntation.AccessLog.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        if (argValues != null) {
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ip);

        String loginPath = "login";
        if (loginPath.equals(signature.getName())) {
            try {
                assert argValues != null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
        log.setCreateTime(new Date());
        accessLogMapper.insert(log);
    }

    @Override
    public Integer countAll() {
        return accessLogMapper.selectCount(null);
    }

    @Override
    public Integer countByLastIndexViewToNow() {
        String username = UserInfoUtil.getUsername();
        if (org.springframework.util.StringUtils.isEmpty(username)) {
            return 0;
        }
        Date date = operationLogMapper.selectLastIndexViewTimeByUsername(username);
        QueryWrapper<AccessLog> wrapper = new QueryWrapper<>();
        wrapper.between(AccessLog.Table.CREATE_TIME, date, new Date());
        return accessLogMapper.selectCount(wrapper);
    }

    @Override
    public List<ViewDateVO> countByLast7Days() {
        return accessLogMapper.countByLast7Days();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        accessLogMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        accessLogMapper.deleteById(id);
    }

    @Override
    public Page<AccessLog> listByPage(Integer current, Integer size, LogQuery logQuery) {
        Page<AccessLog> page = new Page<>(current, size);
        QueryWrapper<AccessLog> wrapper = new QueryWrapper<>();
        wrapper.select(AccessLog.Table.ID, AccessLog.Table.REQUEST_IP, AccessLog.Table.ADDRESS, AccessLog.Table.DESCRIPTION, AccessLog.Table.BROWSER, AccessLog.Table.TIME, AccessLog.Table.CREATE_TIME, AccessLog.Table.STATUS)
                .orderByDesc(AccessLog.Table.CREATE_TIME);
        if (!StringUtils.isEmpty(logQuery.getRequestIp())) {
            wrapper.like(AccessLog.Table.REQUEST_IP, logQuery.getRequestIp());
        }
        if (!StringUtils.isEmpty(logQuery.getDescription())) {
            wrapper.like(AccessLog.Table.DESCRIPTION, logQuery.getDescription());
        }
        if (!StringUtils.isEmpty(logQuery.getBrowser())) {
            wrapper.like(AccessLog.Table.BROWSER, logQuery.getBrowser());
        }
        if (!StringUtils.isEmpty(logQuery.getAddress())) {
            wrapper.like(AccessLog.Table.ADDRESS, logQuery.getAddress());
        }
        if (logQuery.getStartDate() != null && logQuery.getEndDate() != null) {
            wrapper.between(AccessLog.Table.CREATE_TIME, logQuery.getStartDate(), logQuery.getEndDate());
        }
        if (logQuery.getTimeRank() != null) {
            if (Objects.equals(logQuery.getTimeRank(), Constant.LOW_REQUEST_TIME_RANK)) {
                wrapper.lt(AccessLog.Table.TIME, Constant.LOW_REQUEST_TIME);
            }
            if (Objects.equals(logQuery.getTimeRank(), Constant.MID_REQUEST_TIME_RANK)) {
                wrapper.between(AccessLog.Table.TIME, Constant.LOW_REQUEST_TIME, Constant.HIGH_REQUEST_TIME);
            }
            if (Objects.equals(logQuery.getTimeRank(), Constant.HIGH_REQUEST_TIME_RANK)) {
                wrapper.gt(AccessLog.Table.TIME, Constant.HIGH_REQUEST_TIME);
            }
        }
        return accessLogMapper.selectPage(page, wrapper);
    }
}
