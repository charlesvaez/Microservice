package com.hgsoft.springcloud.no.duplication.provider;

import com.google.common.cache.Cache;
import com.hgsoft.springcloud.no.duplication.provider.annotation.NoRepeatSubmit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Aspect
//@Component
public class NoRepeatSubmitAop4Guava {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Cache<String, Integer> cache;

    @Around("execution(* com.hgsoft..*Controller.*(..)) && @annotation(nrs)")
    public Object arround(ProceedingJoinPoint pjp, NoRepeatSubmit nrs) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();//通过feign调用是不同的session
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();

            String key = sessionId + "-" + request.getServletPath();
            logger.info("key: "+key);
            if (cache.getIfPresent(key) == null) {// 如果缓存中有这个url视为重复提交
                Object o = pjp.proceed();
                cache.put(key, 0);
                return o;
            } else {
                response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
                logger.error("重复提交");
                return null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("验证重复提交时出现未知异常!");
            return "{\"code\":-889,\"message\":\"验证重复提交时出现未知异常!\"}";
        }

    }

}
