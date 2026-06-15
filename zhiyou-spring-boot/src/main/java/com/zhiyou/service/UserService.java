package com.zhiyou.service;

import com.zhiyou.model.User;
import com.zhiyou.repository.JsonDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserService {

    private final JsonDataRepository repository;

    public UserService(JsonDataRepository repository) {
        this.repository = repository;
    }

    private static final String FILE_NAME = "user.json";

    public List<User> getAllUsers() {
        return repository.selectList(FILE_NAME, User.class);
    }

    public User getUserById(Long id) {
        return repository.selectById(FILE_NAME, id, User.class);
    }

    public User getUserByOpenId(String openId) {
        List<User> users = repository.selectListByField(FILE_NAME, User.class, "openId", openId);
        return users.isEmpty() ? null : users.get(0);
    }

    public User login(String username, String password) {
        List<User> users = repository.selectListByField(FILE_NAME, User.class, "username", username);
        if (users.isEmpty()) {
            return null;
        }
        User user = users.get(0);
        if (!user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    public User register(String username, String password) {
        List<User> existing = repository.selectListByField(FILE_NAME, User.class, "username", username);
        if (!existing.isEmpty()) {
            return null;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(username);
        user.setPoints(0);
        user.setLevel("普通用户");
        user.setRole("user");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        boolean success = repository.insert(FILE_NAME, user);
        return success ? user : null;
    }

    public boolean addPoints(Long userId, Integer points) {
        User user = repository.selectById(FILE_NAME, userId, User.class);
        if (user == null) {
            return false;
        }
        user.setPoints(user.getPoints() + points);
        user.setUpdateTime(LocalDateTime.now());
        return repository.update(FILE_NAME, user);
    }
}
