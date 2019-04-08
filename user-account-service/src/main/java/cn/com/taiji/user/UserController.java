package cn.com.taiji.user;

import cn.com.taiji.code.CaptchaRender;
import cn.com.taiji.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 * @author zhuohao
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaRender render;

    @RequestMapping(path = "/users", method = RequestMethod.POST, name = "createUser")
    public Result<User> createUser(@RequestBody User user) {
        if (userService.exists(user.getAccount())) {
           return Result.failure("1","用户已经存在");
        }
        if (!render.validate(user.getCode())) {
            return Result.failure("2","验证码错误");
        }
        Assert.notNull(user);
        return Optional.ofNullable(userService.createUser(user))
                .map(result ->Result.success(result))
                .orElse(Result.failure("-1","用户注册失败"));
    }

    @RequestMapping(path = "users/{id}", method = RequestMethod.GET, name = "getUser")
    public Result<User> getUser(@PathVariable("id") Long id) {
        return Optional.ofNullable(userService.getUserById(id))
                .map(result -> Result.success(result))
                .orElse(Result.failure("-1","获取用户失败"));
    }

    @RequestMapping(path = "users/{id}", method = RequestMethod.PUT, name = "updateUser")
    public Result<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User user) {
        Assert.notNull(user);
        user.setId(id);
        return Optional.ofNullable(userService.updateUser(id, user))
                .map(result ->Result.success(result))
                .orElse(Result.failure("-1","获取用户失败"));
    }

    @RequestMapping(path = "users/{id}", method = RequestMethod.DELETE,
            name = "deleteUser")
    public Result deleteUser(@PathVariable("id") Long id) {
        return Optional.ofNullable(userService.deleteUser(id))
                .map(result -> Result.success(result))
                .orElse(Result.failure("-1","用户删除失败"));
    }

    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        User userByAccount = userService.findUserByAccount(changePasswordDTO.getAccount());
        if (!userByAccount.getPassword().equals(changePasswordDTO.getPassword())) {
            return Result.failure("3","用户不存在");
        }
        if (!render.validate(changePasswordDTO.getCode())) {
            return Result.failure("2","验证码错误");
        }
        User user = new User();
        return Optional.ofNullable(userService.createUser(user))
                .map(result ->Result.success(result))
                .orElse(Result.failure("-1","用户注册失败"));
    }
}
