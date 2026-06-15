package com.zhiyou.controller;

import com.zhiyou.common.R;
import com.zhiyou.model.User;
import com.zhiyou.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public R<List<User>> list() {
        return R.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public R<User> getById(@PathVariable Long id) {
        return R.ok(userService.getUserById(id));
    }

    @GetMapping("/openid/{openId}")
    public R<User> getByOpenId(@PathVariable String openId) {
        return R.ok(userService.getUserByOpenId(openId));
    }

    @PostMapping("/register")
    public R<User> register(@RequestParam String username, @RequestParam String password) {
        User user = userService.register(username, password);
        if (user == null) {
            return R.fail("注册失败，用户名可能已存在");
        }
        return R.ok(user);
    }

    @PostMapping("/points/add")
    public R<Void> addPoints(@RequestParam Long userId, @RequestParam Integer points) {
        boolean success = userService.addPoints(userId, points);
        return success ? R.ok() : R.fail("添加积分失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user == null) {
            return R.fail("用户名或密码错误");
        }
        return R.ok(user);
    }
}
