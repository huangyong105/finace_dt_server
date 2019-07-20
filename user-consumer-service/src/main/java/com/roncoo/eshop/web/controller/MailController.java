package com.roncoo.eshop.web.controller;

import cn.com.taiji.result.MyResult;
import com.roncoo.eshop.manager.MailManager;
import com.roncoo.eshop.model.EmailModelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/6/27 20:42
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    MailManager mailManager ;

    //读取配置文件中的参数
    @Value("${spring.mail.username}")
    private String from;

    @RequestMapping("/sendMail")
    public MyResult sendMail () {
// 发送邮件
        String[] to = new String[] {"hzwjrq@163.com"};
        String subject = "短信发送失败通知";
        EmailModelDTO email = new EmailModelDTO(from, to, null, subject, "hzwjrq@163.com", null);
        mailManager.sendSimpleMail(email);
        return MyResult.ofSuccess();
    }

}
