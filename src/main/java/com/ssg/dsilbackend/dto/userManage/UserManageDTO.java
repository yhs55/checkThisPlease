package com.ssg.dsilbackend.dto.userManage;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserManageDTO {
    private Long id;
    private String email;
    private String password;
    private String permission;
    private Long point;
    private String name;
    private String tel;
    private String address;
    private String postcode;
    private String status;
}
