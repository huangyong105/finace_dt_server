package cn.com.taiji.api;

import cn.com.taiji.data.Result;
import cn.com.taiji.util.code.CaptchaRender;
import com.aliyuncs.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger log = LoggerFactory.getLogger(SmsSendController.class);
    @Autowired
    private CaptchaRender render;

    /**
     * 发送短信验证码
     * @throws ClientException
     */
    @PostMapping("/sendRegisterSmsCode")
    public Result sendRegisterSmsCode (@RequestParam(value ="mobile") String mobile)  {
        //注册账户
        try {
            render.render(mobile,1);
        } catch (ClientException e) {
            log.info("找回密码信息异常:{}",e);
            return Result.failure("-1","验证码发送失败");
        }
        return Result.success(null);
    }

    @PostMapping("/sendChangePasswordSmsCode")
    public Result sendChangePasswordSmsCode (@RequestParam(value ="mobile") String mobile) {
        //改变密码
        try {
            render.render(mobile,2);
        } catch (ClientException e) {
            log.info("找回密码信息异常:{}",e);
            return Result.failure("-1","验证码发送失败");
        }
        return Result.success(null);
    }

    @PostMapping("/sendFindPasswordSmsCode")
    public Result sendFindPasswordSmsCode (@RequestParam(value ="mobile") String mobile)  {
        //找回密码
        try {
            render.render(mobile,3);
        } catch (ClientException e) {
            log.info("找回密码信息异常:{}",e);
            return Result.failure("-1","验证码发送失败");
        }
        return Result.success(null);
    }

}
