package com.ssg.dsilbackend.dto.userManage;

import com.ssg.dsilbackend.domain.Category;
import com.ssg.dsilbackend.domain.Menu;
import com.ssg.dsilbackend.domain.Permission;
import com.ssg.dsilbackend.dto.CategoryName;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRegisterDTO {
    private String email;
    private String password;
    private String restaurantName;
    private String registerNumber;
    private String userName;
    private String userTel;
    private String restaurantTel;
    private String userAddress;
    private String restaurantAddress;
    private String postcode;
    private Long tableCount;
    private Long deposit;
    private String img;
    private String[] categories; // 열거형 배열로 변경
    private List<RegisterMenuDTO> menuDTOs; // 메뉴 DTO 리스트
}
