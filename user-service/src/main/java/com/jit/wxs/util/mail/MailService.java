package com.jit.wxs.util.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/20 0:41
 */
@Service
public class MailService {

    private static Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

/*    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;  //自动注入*/


    /**
     * @Description: 发送简单文本邮件
     * @author lc
     */
    public void sendSimpleMail(EmailModelDTO email){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getFrom());
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            if(email.getCc() != null){
                message.setCc(email.getCc());
            }
            mailSender.send(message);
        } catch (Exception e) {
            logger.error("发送简单文本邮件异常！",e.getMessage());
        }
    }

    /**
     * @Description: 发送html邮件
     * @author lc
     */
    public void sendHtmlMail(EmailModelDTO email){
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(email.getFrom());
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            // 发送htmltext值需要给个true，不然不生效
            helper.setText(email.getText(), true);
            if(email.getCc() != null){
                helper.setCc(email.getCc());
            }
            mailSender.send(message);
        } catch (Exception e) {
            logger.error("发送简单文本邮件异常！",e.getMessage());
        }
    }

/*    public void sendAttachmentsMail(EmailModelDTO email) {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(email.getFrom());
            helper.setTo(email.getTo());
            helper.setSubject("主题：带附件的邮件");
            helper.setText("带附件的邮件内容");
            //注意项目路径问题，自动补用项目路径
            FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/picture.jpg"));
            //加入邮件
            helper.addAttachment("图片.jpg", file);
        } catch (Exception e){
            e.printStackTrace();
        }
        mailSender.send(message);
    }*/

/*    public void sendInlineMail() {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(Sender);
            helper.setSubject("主题：带静态资源的邮件");
            //第二个参数指定发送的是HTML格式,同时cid:是固定的写法
            helper.setText("<html><body>带静态资源的邮件内容 图片:<img src='cid:picture' /></body></html>", true);

            FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/picture.jpg"));
            helper.addInline("picture",file);
        } catch (Exception e){
            e.printStackTrace();
        }
        mailSender.send(message);
    }*/

/*    public void sendTemplateMail(){
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(Sender);
            helper.setSubject("主题：模板邮件");

            Map<String, Object> model = new HashedMap();
            model.put("username", "zggdczfr");

            //修改 application.properties 文件中的读取路径
//            FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
//            configurer.setTemplateLoaderPath("classpath:templates");
            //读取 html 模板
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mail.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(html, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }*/

    /**
     * @Description: 发送带附件的邮件
     * @author lc
     */
    public void sendAttachmentsMail(EmailModelDTO email){
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(email.getFrom());
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getText());
            if(email.getCc() != null){
                helper.setCc(email.getCc());
            }
            // 用流的形式发送附件邮件
            DataSource source = new ByteArrayDataSource(email.getIs(), "application/msexcel");
            helper.addAttachment("短信发送失败记录.xls", source);
            mailSender.send(message);
        } catch (Exception e){
            logger.error("发送带附件的邮件异常！",e.getMessage());
        }
    }

}
