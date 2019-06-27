package com.roncoo.eshop.manager;

import com.roncoo.eshop.model.EmailModelDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/6/27 20:13
 */
@Service
public class MailManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

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
