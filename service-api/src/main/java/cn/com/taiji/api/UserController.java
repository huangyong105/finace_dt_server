package cn.com.taiji.api;

import cn.com.taiji.data.Result;
import cn.com.taiji.util.code.*;
import cn.com.taiji.util.sms.*;
import cn.com.taiji.dao.RedisDao;
import cn.com.taiji.data.UserEntity;
import cn.com.taiji.data.User;
import cn.com.taiji.service.UserService;
import cn.com.taiji.util.BeanConverter;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Random;

/**
 * @author zhuohao
 */
@RestController
public class UserController {

    private final String USER_NAME_KEY = "USER_NAME";
    private final String USER_TOKEN = "USER_TOKEN";
    private final String PASSWORD = "PASSWORD";
    private final Long EXP_IMT = 3600000L;
    // 验证码随机字符数组
    protected static final char[] charArray = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

    protected static final Random random = new Random(System.nanoTime());
    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaRender render;


    @Resource(name = "redisDao")
    private RedisDao redisDao;

    @RequestMapping(path = "/users", method = RequestMethod.POST, name = "createUser")
    public Result<UserEntity> createUser(@RequestBody UserEntity userEntity) {
        if (userService.exists(userEntity.getAccount())) {
           return Result.failure("1","用户已经存在");
        }
        if (!render.validate(userEntity.getAccount(),1, userEntity.getCode())) {
            return Result.failure("2","验证码错误");
        }
        Assert.notNull(userEntity);
        User user =  BeanConverter.convert(userEntity,User.class);
        return Optional.ofNullable(userService.createUser(user))
                .map(result ->Result.success(result))
                .orElse(Result.failure("-1","用户注册失败"));
    }

    @RequestMapping(path = "users/{account}", method = RequestMethod.GET, name = "getUser")
    public Result<UserEntity> getUser(@PathVariable("account") String account) {
        User user = userService.getUserByAccount(account);
        UserEntity userEntity = BeanConverter.convert(user,UserEntity.class);
        return Optional.ofNullable(userEntity)
                .map(result -> Result.success(result))
                .orElse(Result.failure("-1","获取用户失败"));
    }

    @RequestMapping(path = "users/{id}", method = RequestMethod.PUT, name = "updateUser")
    public Result<UserEntity> updateUser(@PathVariable(value = "id") Long id, @RequestBody UserEntity userEntity) {
        Assert.notNull(userEntity);
        userEntity.setId(id);
        User user =  BeanConverter.convert(userEntity,User.class);
        return Optional.ofNullable(userService.updateUser(user))
                .map(result ->Result.success(result))
                .orElse(Result.failure("-1","获取用户失败"));
    }


    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody UserEntity userEntity) {
        if (!userService.exists(userEntity.getAccount())) {
            return Result.failure("1","用户不存在");
        }

        if (!render.validate(userEntity.getAccount(),2, userEntity.getCode())) {
            return Result.failure("1", "验证码错误");
        }
        User user = userService.findUserByAccount(userEntity.getAccount());
        user.setPassword(userEntity.getPassword());
        return Optional.ofNullable(userService.updateUser(user))
                .map(result -> Result.success(result))
                .orElse(Result.failure("-1", "用户注册失败"));
    }
    @RequestMapping(path = "findPassword",
            name = "findPassword")
    public Result  findPassword (@RequestBody UserEntity userEntity) throws ClientException {
        if (!userService.exists(userEntity.getAccount())) {
            return Result.failure("1","用户不存在");
        }
        if (!render.validate(userEntity.getAccount(),3, userEntity.getCode())) {
            return Result.failure("2","验证码错误");
        }
        User accountUserEntity = userService.findUserByAccount(userEntity.getAccount());
        String siginName = "汇致旺";
        String templateCode="SMS_162635384";
        String password = getRandomString();
        String templateJson="{\"code\":\""+password+"\"}";
        accountUserEntity.setPassword(password);
        userService.updateUser(accountUserEntity);
        //发送密码给用户
		SmsSendApi.sendSms(userEntity.getAccount(),siginName,templateCode,templateJson);
		return Result.success(null);
    }

    protected String getRandomString() {
        char[] randomChars = new char[8];
        for (int i=0; i<randomChars.length; i++) {
            randomChars[i] = charArray[random.nextInt(charArray.length)];
        }
        return String.valueOf(randomChars);
    }

    @PostMapping("/getUserInfo")
    public Result<UserEntity> getUserInfo(@RequestHeader("token") String token) throws Exception {
        String key = genLockKey(USER_TOKEN,token);
        if (!redisDao.exists(key)) {
            return Result.success(null);
        }
        String account =(String)redisDao.get(key);
        if(StringUtils.isEmpty(account)) {
            return Result.success(null);
        }
        User user = userService.findUserByAccount(account);
        if(user == null) {
            return Result.success(null);
        }
        UserEntity userEntity = BeanConverter.convert(user,UserEntity.class);
        return Result.success(userEntity);
    }


    private  String  genLockKey (String ... keys) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (String key :keys) {
            if(i>0) {
                sb.append(".");
            }
            sb.append(key);
            i ++ ;
        }
        return sb.toString();
    }

}
