package org.example.systemserver.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.systemserver.commons.DataScope;
import org.example.systemserver.constants.Constants;
import org.example.systemserver.domain.TUser;
import org.example.systemserver.query.BaseQuery;
import org.example.systemserver.uitl.JWTUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Component
@Aspect
public class DataScopeAspect {
    @Pointcut(value = "@annotation(org.example.systemserver.commons.DataScope)")
    public void pointCut(){

    }

    @Around(value = "pointCut()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 拿到方法上的注解
        DataScope dataScope = signature.getMethod().getDeclaredAnnotation(DataScope.class);

        String tableAlias = dataScope.tableAlias();
        String tableField = dataScope.tableField();

        // 在spring web容器中，可以拿到当前请求的request对象
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        String token = servletRequest.getHeader(Constants.TOKEN_NAME);
        // 从toke中解析出该用户是管理员还是普通用户
        TUser tUser = JWTUtils.parseUserFromJWT(token);

        // 拿到用户的角色
        List<String> roleList = tUser.getRoleList();

        if (!roleList.contains("admin")) {
            // 如果不是是管理员，只查询自己的数据
            Object args = joinPoint.getArgs()[0];
            if(args instanceof BaseQuery query){
                query.setFilterSQL(" and " + tableAlias + "." + tableField
                        + " = " + tUser.getId());
            }
        }

        return joinPoint.proceed();
    }
}
