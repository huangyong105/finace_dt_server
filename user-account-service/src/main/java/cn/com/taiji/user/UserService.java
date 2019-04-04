package cn.com.taiji.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zhuohao
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @CacheEvict(value = "user", key = "#user.getId()")
    public User createUser(User user) {
        User result = null;
        if(!userRepository.exists(user.getId())) {
            result = userRepository.save(user);
        }
        return result;
    }

    @Cacheable(value = "user", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Cacheable(value = "user", key = "#userName")
    public User findUserByUsername(String userName) {
        return userRepository.findUserByUsername(userName);
    }

    @CachePut(value = "user", key = "#id")
    public User updateUser(Long id, User user) {
        User result = null;
        if(userRepository.exists(user.getId())) {
            result = userRepository.save(user);
        }
        return result;
    }

    @CacheEvict(value = "user", key = "#id")
    public boolean deleteUser(Long id) {
        boolean deleted = false;
        if (userRepository.exists(id)) {
            userRepository.delete(id);
            deleted = true;
        }
        return deleted;
    }
}
