package com.zhiyou.model;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order implements Serializable {
    private Long id;
    private String orderNo;
    private Long userId;
    private Long spotId;
    private String spotName;
    private Long ticketTypeId;
    private String ticketTypeName;
    private Integer quantity;
    private BigDecimal totalAmount;
    private String status;
    private String visitDate;
    private String visitTimeSlot;
    private String qrCode;
    private LocalDateTime payTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
