package com.hgsoft.springcloud.no.duplication.provider;

import com.hgsoft.springcloud.no.duplication.provider.annotation.NoRepeatSubmit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
/**
 * @功能描述 aop解析注解
 */
public class NoRepeatSubmitAop4Redis {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Around("execution(* com.hgsoft..*Controller.*(..)) && @annotation(nrs)")
    public Object arround(ProceedingJoinPoint pjp, NoRepeatSubmit nrs) {
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = servletRequestAttributes.getRequest();
//        HttpServletResponse response = servletRequestAttributes.getResponse();

        ValueOperations<Object, Object> opsForValue = redisTemplate.opsForValue();
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();

//            String key = sessionId + "-" + request.getServletPath();
            String key = request.getServletPath();
            logger.info("key: "+key);
            if (opsForValue.get(key) == null) {// 如果缓存中有这个url视为重复提交
                Object o = pjp.proceed();
                opsForValue.set(key, 0, 2, TimeUnit.SECONDS);
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
