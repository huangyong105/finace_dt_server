package cn.com.taiji.user;

import cn.com.taiji.config.JwtUtils;
import cn.com.taiji.configure.RedisReadLocker;
import cn.com.taiji.dao.RedisDao;
import cn.com.taiji.data.Result;
import cn.com.taiji.data.Token;
import cn.com.taiji.lockinterface.AquiredLockWorker;
import cn.com.taiji.user.User;
import cn.com.taiji.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuohao
 */
@RestController
public class LoginController {

    private final String USER_NAME_KEY = "USER_NAME";
    private final String USER_TOKEN = "USER_TOKEN";
    private final String PASSWORD = "PASSWORD";
    private final Long EXP_IMT = 3600000L;

    @Autowired
    private UserService userService;
/*    @Autowired
    private JedisCluster jedisCluster;*/

    @Resource(name = "redisDao")
    private RedisDao redisDao;

    @Autowired
    private RedisReadLocker redisReadLocker;

    @GetMapping("/api/test")
    public Object hellWorld(@RequestAttribute(value = USER_NAME_KEY)  String username) {
        return "Welcome! Your USER_NAME : " + username;
    }

    @PostMapping("/login")
    public Result<Token> login(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password) throws Exception {
        String key = genLockKey(USER_TOKEN,"UN",account);
        Token tokenObj = new Token();
        tokenObj.setExperin(60*60*24);
        User user = userService.findUserByAccount(account);
        if (user == null) {
            return Result.failure("1","用户不存在");
        }
        if(!isValidPassword(account,password)) {
            return Result.failure("2","密码错误");
        }
        if (redisDao.exists(key)) {
            String token =(String)redisDao.get(key);
            persistRedisToken(account, token);
            tokenObj.setToken(token);
            return Result.success(tokenObj);
        }
        // 将用户名传入并生成jwt
        Map<String,Object> map = new HashMap<>();
        map.put(USER_NAME_KEY, account);

        String token = JwtUtils.sign(map, EXP_IMT);
        persistRedisToken(account, token);
        tokenObj.setToken(token);
        return Result.success(tokenObj);

    }

    private void persistRedisToken(String account, String token) throws Exception {
        redisReadLocker.lock(USER_NAME_KEY, new AquiredLockWorker<Object>() {
            @Override
            public Object invokeAfterLockAquire() throws Exception {
                redisDao.set(genLockKey(USER_TOKEN,token),account, 60*60*24,TimeUnit.SECONDS);
                redisDao.set(genLockKey(USER_TOKEN,"UN",account),token, 60*60*24,TimeUnit.SECONDS);
                return token;
            }
        },60);
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

    @PostMapping("/getUserInfo")
    public Result<User> getUserInfo(@RequestHeader("token") String token) throws Exception {
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
        return Result.success(user);
    }

    /**
     * 验证密码是否正确，模拟
     */
    private boolean isValidPassword(String account, String password) {
        User user = userService.findUserByAccount(account);
        if (user == null) {
            return false;
        }
         BCryptPasswordEncoder  bCryptPasswordEncoder =new BCryptPasswordEncoder();
         if (StringUtils.isEmpty(account) && StringUtils.isEmpty(user.getPassword())) {
             return true;
         }
        if (StringUtils.isEmpty(account) && !StringUtils.isEmpty(user.getPassword())) {
            return false;
        }
        if (!StringUtils.isEmpty(account) && StringUtils.isEmpty(user.getPassword())) {
            return false;
        }

         if (bCryptPasswordEncoder.matches(password,user.getPassword())) {
             return true;
         }
         return false;
    }

}
