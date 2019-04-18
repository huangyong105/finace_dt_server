package com.jit.wxs.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jit.wxs.entity.Result;
import com.jit.wxs.entity.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认认证失败处理
 * @author jitwxs
 * @since 2019/1/8 23:29
 */
@Component
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String json = objectMapper.writeValueAsString(Result.ofError(4001, "账号或密码错误"));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
}
