package com.zhiyou.model;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Coupon implements Serializable {
    private Long id;
    private String name;
    private String type;
    private Integer reduceAmount;
    private Integer minimumAmount;
    private Integer totalCount;
    private Integer remainCount;
    private String validStartDate;
    private String validEndDate;
    private Integer status;
    private LocalDateTime createTime;
}
