package com.ssg.dsilbackend.dto.userManage;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserManageDTO {
    private String email;
    private String password;
    private String name;
    private String tel;
    private String address;
    private String postcode;
}
