package com.zhiyou.model;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FlowData implements Serializable {
    private Long id;
    private Long spotId;
    private String areaName;
    private Integer currentCount;
    private Integer maxCapacity;
    private String densityLevel;
    private LocalDateTime updateTime;
}
