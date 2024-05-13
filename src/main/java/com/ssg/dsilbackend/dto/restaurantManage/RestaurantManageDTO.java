package com.ssg.dsilbackend.dto.restaurantManage;

import lombok.Data;

@Data
public class RestaurantManageDTO {
    private Long id;
    private String name;
    private String address;
    private String tel;
    private Long deposit;
    private Long tableCount;
    private String crowd;
    private String img;
    private Long memberId;
}
