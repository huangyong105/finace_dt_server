package cn.com.taiji.sms;

import cn.com.taiji.code.CaptchaRender;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @PostMapping("/sendRegisterSmsCode")
    public void sendRegisterSmsCode (@RequestParam(value ="mobile") String mobile) throws ClientException {
        //注册账户
        render.render(mobile,1);
    }

    @PostMapping("/sendChangePasswordSmsCode")
    public void sendChangePasswordSmsCode (@RequestParam(value ="mobile") String mobile) throws ClientException {
        //改变密码
        render.render(mobile,2);
    }

    @PostMapping("/sendFindPasswordSmsCode")
    public void sendFindPasswordSmsCode (@RequestParam(value ="mobile") String mobile) throws ClientException {
        //找回密码
        render.render(mobile,3);
    }

}
