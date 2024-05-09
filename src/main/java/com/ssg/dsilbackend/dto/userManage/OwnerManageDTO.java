package com.ssg.dsilbackend.dto.userManage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerManageDTO {
    private Long id;
    private String email;
    private String name;
    private String restaurantName;
    private String password;
    private String permission;
    private String tel;
    private String address;
    private String postcode;
    private String registerNum;
}
