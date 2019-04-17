package com.jit.wxs.web;

import com.jit.wxs.security.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class PageController {
    /**
     * 跳转到登陆成功页
     */
    @RequestMapping(SecurityConstants.LOGIN_SUCCESS_URL)
    public String showSuccessPage() {
        System.out.println("--------------------");
        return "pages/application-list.html";
    }

    /**
     * 跳转到登录页
     */
    @RequestMapping(SecurityConstants.UN_AUTHENTICATION_URL)
    public String showAuthenticationPage() {
        return "pages/page-login.html";

    }
}
