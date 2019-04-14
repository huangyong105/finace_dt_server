package cn.com.taiji.user;

import cn.com.taiji.dao.RedisDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhuohao
 */
@Service
public class UserService {

    private final String USER_TOKEN = "USER_TOKEN";

    @Autowired
    private UserRepository userRepository;

    @Resource(name = "redisDao")
    private RedisDao redisDao;

    @CacheEvict(value = "user", key = "#user.getAccount()")
    public User createUser(User user) {
        User result = null;
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        if(!userRepository.existsByAccount(user.getAccount())) {
            result = userRepository.save(user);
        }
        return result;
    }



    @Cacheable(value = "user", key = "#account")
    public User getUserByAccount(String account) {
        return userRepository.findUserByAccount(account);
    }

    @Cacheable(value = "user", key = "#account")
    public User findUserByAccount(String account) {
        return userRepository.findUserByAccount(account);
    }

    @Cacheable(value = "user",key = "#id")
    public User findUserById(Long id){
        return userRepository.findUserById(id);
    }

    @CachePut(value = "user", key = "#user.getAccount()")
    public User updateUser(User user) {
        User result = null;
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        if(userRepository.exists(user.getId())) {
            result = userRepository.save(user);
        }
        return result;
    }

    @CachePut(value = "user", key = "#user.getAccount()")
    public User realNameCertification(User user) {
        User result = null;
        result = userRepository.save(user);
        return result;
    }

    boolean exists(String account) {
        String key = genLockKey(USER_TOKEN,"UN",account);
        if (redisDao.exists(key)){
            return true;
        }
        if(userRepository.existsByAccount(account)) {
            return true;
        }
        return false;
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
