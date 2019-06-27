package com.roncoo.eshop.web.controller;

import com.roncoo.eshop.manager.MailManager;
import com.roncoo.eshop.model.EmailModelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/6/27 20:42
 */
@Controller
public class MailController {

    @Autowired
    MailManager mailManager ;

    //读取配置文件中的参数
    @Value("${spring.mail.username}")
    private String from;

    @RequestMapping("/sendMail")
    public  void  sendMail () {
// 发送邮件
        String[] to = new String[] {"huangyong@startdt.com"};
        String subject = "短信发送失败通知";
        EmailModelDTO email = new EmailModelDTO(from, to, null, subject, "xiaoming", null);
        mailManager.sendHtmlMail(email);
    }

}
