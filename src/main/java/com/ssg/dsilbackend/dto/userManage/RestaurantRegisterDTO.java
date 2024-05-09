package com.ssg.dsilbackend.dto.userManage;

import com.ssg.dsilbackend.domain.Category;
import com.ssg.dsilbackend.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRegisterDTO {
    private Long id;
    private String email;
    private String password;
    private String restaurantName;
    private String registerNumber;
    private String name;
    private String tel;
    private String address;
    private String postcode;
    private Long tableCount;
    private Long deposit;
    private byte[] img;
    private Category category;
    private Menu menu;
}
