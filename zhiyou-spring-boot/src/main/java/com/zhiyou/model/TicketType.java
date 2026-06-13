package com.zhiyou.model;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TicketType implements Serializable {
    private Long id;
    private Long spotId;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String timeSlots;
    private Integer status;
    private LocalDateTime createTime;
}
