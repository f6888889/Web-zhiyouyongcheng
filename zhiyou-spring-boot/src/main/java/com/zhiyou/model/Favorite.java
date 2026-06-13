package com.zhiyou.model;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Favorite implements Serializable {
    private Long id;
    private Long userId;
    private Long spotId;
    private LocalDateTime createTime;
}
