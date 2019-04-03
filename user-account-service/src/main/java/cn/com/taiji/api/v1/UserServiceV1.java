package cn.com.taiji.api.v1;

import cn.com.taiji.user.User;
import cn.com.taiji.user.UserRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuohao
 */
@Service
public class UserServiceV1 {

    @Autowired
    private UserRepository userRepository;

    //@Cacheable(value = "user", key = "#username") // Cannot get Jedis connection ?
    @HystrixCommand
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

}

