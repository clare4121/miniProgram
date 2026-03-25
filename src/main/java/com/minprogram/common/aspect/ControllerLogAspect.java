package com.minprogram.common.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class ControllerLogAspect {

    private static final Logger log = LoggerFactory.getLogger(ControllerLogAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 切入点：所有 Controller 包下的 public 方法
     */
    @Pointcut("execution(public * com.minprogram..controller..*(..))")
    public void controllerPointcut() {
    }

    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        // 获取请求信息
        HttpServletRequest request = getRequest();
        String requestUrl = request != null ? request.getRequestURI() : "";
        String httpMethod = request != null ? request.getMethod() : "";

        // 获取方法参数
        Object[] args = point.getArgs();
        String params = formatParams(args);

        // 记录请求日志
        log.info("===> [{}] {} {} | Args: {}", httpMethod, className, methodName, params);

        long startTime = System.currentTimeMillis();
        Object result = null;
        Throwable error = null;

        try {
            result = point.proceed();
            return result;
        } catch (Throwable e) {
            error = e;
            throw e;
        } finally {
            long cost = System.currentTimeMillis() - startTime;

            if (error != null) {
                log.error("<=== [{}] {} {} | Error: {} | Cost: {}ms",
                        httpMethod, className, methodName, error.getMessage(), cost);
            } else {
                // 记录返回结果
                String resultStr = formatResult(result);
                log.info("<=== [{}] {} {} | Result: {} | Cost: {}ms",
                        httpMethod, className, methodName, resultStr, cost);
            }
        }
    }

    private HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attributes != null ? attributes.getRequest() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String formatParams(Object[] args) {
        if (args == null || args.length == 0) {
            return "[]";
        }
        try {
            // 过滤掉不可序列化的参数
            Object[] filteredArgs = Arrays.stream(args)
                    .filter(arg -> arg != null && isSerializable(arg))
                    .toArray();
            return objectMapper.writeValueAsString(filteredArgs);
        } catch (Exception e) {
            return "[params serialization failed]";
        }
    }

    private String formatResult(Object result) {
        if (result == null) {
            return "null";
        }
        try {
            String json = objectMapper.writeValueAsString(result);
            // 限制返回日志长度，避免日志过大
            if (json.length() > 2000) {
                return json.substring(0, 2000) + "...[truncated]";
            }
            return json;
        } catch (Exception e) {
            return result.toString();
        }
    }

    private boolean isSerializable(Object obj) {
        // 简单判断，避免文件上传等不可序列化的参数
        String className = obj.getClass().getName();
        return !className.contains("HttpServletRequest")
                && !className.contains("HttpServletResponse")
                && !className.contains("MultipartFile")
                && !className.contains("InputStream")
                && !className.contains("OutputStream");
    }
}