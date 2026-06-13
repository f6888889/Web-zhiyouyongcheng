package com.zhiyou.model;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Spot implements Serializable {
    private Long id;
    private String name;
    private String coverImage;
    private String description;
    private String location;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String level;
    private String openTime;
    private String closeTime;
    private BigDecimal adultTicketPrice;
    private BigDecimal childTicketPrice;
    private BigDecimal seniorTicketPrice;
    private String tags;
    private Integer sort;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
