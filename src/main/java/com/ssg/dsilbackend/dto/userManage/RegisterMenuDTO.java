package com.ssg.dsilbackend.dto.userManage;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterMenuDTO {

    private String name;
    private Long price;
    private String img;
    private String menuInfo;
}
