package com.ssg.dsilbackend.dto.restaurantManage;


import lombok.Data;

import java.time.LocalDate;

@Data
public class ReplyDTO {
    private Long id;
    private String content;
    private LocalDate registerDate;
    private Boolean deleteStatus;
}
