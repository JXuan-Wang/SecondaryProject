package org.example.systemserver.config.handler;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.systemserver.constants.Constants;
import org.example.systemserver.domain.TUser;
import org.example.systemserver.result.R;
import org.example.systemserver.service.RedisService;
import org.example.systemserver.uitl.JSONUtils;
import org.example.systemserver.uitl.JWTUtils;
import org.example.systemserver.uitl.ResponseUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 登录成功，执行该方法，在该方法中返回json给前端，就行了
        TUser tUser = (TUser) authentication.getPrincipal();

        // 把tUser对象转成json作为数据格式
        String userJson=JSONUtils.toJSON(tUser);

        // 生成jwt
        String jwt= JWTUtils.createJWT(userJson);

        // 写入redis
        redisService.setValue(Constants.REDIS_JWT_KEY+tUser.getId(),jwt);

        // 设置jwt的过期时间（如果选择记住我，过期时间是7天，否则30分钟）
        String rememberMe = request.getParameter("rememberMe");
        if(Boolean.parseBoolean(rememberMe)){
            redisService.expire(Constants.REDIS_JWT_KEY+tUser.getId(),
                    Constants.EXPIRE_TIME, TimeUnit.SECONDS);
        }else{
            redisService.expire(Constants.REDIS_JWT_KEY+tUser.getId(),
                    Constants.DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
        }

        // 登录成功的统一结果
        R result = R.OK(jwt);

        // 把对象转成json
        String resultJSON=JSONUtils.toJSON(result);

        // 把R以json返回给前端
        ResponseUtils.write(response, resultJSON);
    }
}
