package site.alanliang.geekblog.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.alanliang.geekblog.common.Constant;
import site.alanliang.geekblog.dao.OperationLogMapper;
import site.alanliang.geekblog.model.OperationLog;
import site.alanliang.geekblog.query.LogQuery;
import site.alanliang.geekblog.service.OperationLogService;
import site.alanliang.geekblog.utils.StringUtils;

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
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, OperationLog log) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        site.alanliang.geekblog.anntation.OperationLog aopLog = method.getAnnotation(site.alanliang.geekblog.anntation.OperationLog.class);

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
                username = new JSONObject(argValues[0]).get("username").toString();
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
        operationLogMapper.insert(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdList(List<Long> idList) {
        operationLogMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        operationLogMapper.deleteById(id);
    }

    @Override
    public Page<OperationLog> listByPage(Integer current, Integer size, LogQuery logQuery) {
        Page<OperationLog> page = new Page<>(current, size);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.select("id", "request_ip", "address", "description", "browser", "time", "create_time")
                .orderByDesc("create_time");
        if (!StringUtils.isEmpty(logQuery.getRequestIp())) {
            wrapper.like("request_ip", logQuery.getRequestIp());
        }
        if (!StringUtils.isEmpty(logQuery.getDescription())) {
            wrapper.like("description", logQuery.getDescription());
        }
        if (!StringUtils.isEmpty(logQuery.getBrowser())) {
            wrapper.like("browser", logQuery.getBrowser());
        }
        if (!StringUtils.isEmpty(logQuery.getAddress())) {
            wrapper.like("address", logQuery.getAddress());
        }
        if (logQuery.getStartDate() != null && logQuery.getEndDate() != null) {
            wrapper.between("create_time", logQuery.getStartDate(), logQuery.getEndDate());
        }
        if (logQuery.getTimeRank() != null) {
            if (Objects.equals(logQuery.getTimeRank(), Constant.LOW_REQUEST_TIME_RANK)) {
                wrapper.lt("time", Constant.LOW_REQUEST_TIME);
            }
            if (Objects.equals(logQuery.getTimeRank(), Constant.MID_REQUEST_TIME_RANK)) {
                wrapper.between("time", Constant.LOW_REQUEST_TIME, Constant.HIGH_REQUEST_TIME);
            }
            if (Objects.equals(logQuery.getTimeRank(), Constant.HIGH_REQUEST_TIME_RANK)) {
                wrapper.gt("time", Constant.HIGH_REQUEST_TIME);
            }
        }
        return operationLogMapper.selectPage(page, wrapper);
    }
}
