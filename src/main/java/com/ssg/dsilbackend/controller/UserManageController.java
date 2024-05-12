package com.ssg.dsilbackend.controller;

import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.dto.myDinig.ReviewRequest;
import com.ssg.dsilbackend.dto.userManage.RestaurantRegisterDTO;
import com.ssg.dsilbackend.dto.userManage.UserManageDTO;
import com.ssg.dsilbackend.service.UserManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/memberManage")
@RequiredArgsConstructor
public class UserManageController {

    private final UserManageService userManageService;

    // ------------------------------------------------- login

    @GetMapping("/login")
    public String getLogin() {
    return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@RequestBody UserManageDTO userManageDTO) {


        // 로그인 폼 데이터를 받아 처리하는 로직 작성
        return ResponseEntity.ok().build(); // 성공했을 경우 응답
    }

    // ------------------------------------------------- signup

    @GetMapping("/signup")
    public void getSignup() {
    }

    @PostMapping("/signup")
    public void postSignup() {
    }

    @PostMapping("/registerRestaurant")
    public ResponseEntity<?> registerRestaurant(@RequestBody RestaurantRegisterDTO restaurantRegisterDTO) {
        try {
            userManageService.registerRestaurantInfo(restaurantRegisterDTO);
            return ResponseEntity.ok("식당 정보가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            // 예외 발생 시, 500 Internal Server Error와 함께 오류 메시지를 반환합니다.
            return ResponseEntity.internalServerError().body("식당 정보 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestParam("test") String test) {
        System.out.println("test");
        System.out.println(test);

        return ResponseEntity.ok(test);
    }

}
