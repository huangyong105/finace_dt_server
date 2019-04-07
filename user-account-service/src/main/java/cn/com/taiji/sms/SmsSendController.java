package cn.com.taiji.sms;

import cn.com.taiji.code.CaptchaRender;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/7 23:35
 */
@RestController
public class SmsSendController {

    @Autowired
    private CaptchaRender render;

    /**
     * 发送短信验证码
     * @throws ClientException
     */
    @PostMapping("/sendSmsCode")
    public void sendSmsCode () throws ClientException {
        render.render();
    }

}
