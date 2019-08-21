package pro.dianwanjia.official.website.aop;

import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pro.dianwanjia.official.website.util.Base.LogModel;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author LX
 * @date: 2019/8/21 0:27
 * @description:
 * @version V1.0.0
 */
@Aspect
@Component
public class SystemLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);


    private LogModel logModel = new LogModel();

    //Controller层切点
    //第一个*代表所有的返回值类型
    //第一个*代表所有的包
    //第二个*代表所有的类
    //第三个*代表类所有方法
    //最后一个..代表所有的参数。
    @Pointcut("execution (* pro.dianwanjia.official.website..*.controller.*Controller.*(..))")
    public void controllerAspect() {

    }

    @Before("controllerAspect()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        logger.info("URL : " + request.getRequestURL().toString());
//        logger.info("HTTP_METHOD : " + request.getMethod());
//        logger.info("IP : " + request.getRemoteAddr());
//        Enumeration<String> enu = request.getParameterNames();
//        while (enu.hasMoreElements()) {
//            String name = (String) enu.nextElement();
//            logger.info("name:{},value:{}", name, request.getParameter(name));
//
//        }
        String s = request.getRequestURL().toString();
        for (int i = 0; i < 3; i++) {
            s = s.substring(s.indexOf("/") + 1);
        }
        String accountId = "";
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            logModel.setLogDesc(log.value());
        }
        accountId = "null".equals(accountId) ? "" : accountId;
        logModel.setOptUserName(accountId);
        logModel.setLogUrl("/" + s);
        logModel.setLogMethod(request.getMethod());
        logModel.setLogRequestIp(request.getRemoteAddr());

    }

    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) throws Throwable {
    }


    @AfterReturning(returning = "ret", pointcut = "controllerAspect()")
    public void afterReturn(Object ret) {
        //返回结果
        /*logger.info("RESPONSE : " + ret);*/
        if (ret != null) {
            logModel.setLogOutputParam(ret.toString());
        }
        //入库
//        logManageServerApi.insertLog(cataSystemLog);
    }

    @Around("controllerAspect()")
    public Object debugLog(ProceedingJoinPoint pjp) throws Throwable {
        String classAndMethod = pjp.getSignature().toShortString();
        Object o = null;
        try {
            Object[] params = pjp.getArgs();
            for (Object param : params) {
                if (param != null) {
                    logger.info("[{}]:input param:{}", classAndMethod, param.toString());
                    logModel.setLogInputParam(param.toString());
                }
            }
            o = pjp.proceed();
            if (o != null && o instanceof Void) {
                logger.info("[{}]:output param:void", classAndMethod);
            } else {
                logger.info("[{}]:output param:{}", classAndMethod, Objects.toString(o, ""));
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }
        return o;
    }


}
