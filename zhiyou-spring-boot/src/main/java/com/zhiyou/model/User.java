package com.zhiyou.model;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private Long id;
    private String openId;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String phone;
    private Integer points;
    private String level;
    private String role;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
