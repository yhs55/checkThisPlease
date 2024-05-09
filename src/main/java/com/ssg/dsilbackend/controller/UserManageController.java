package com.ssg.dsilbackend.controller;

import com.ssg.dsilbackend.dto.userManage.UserManageDTO;
import com.ssg.dsilbackend.service.UserManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/memberManage")
@RequiredArgsConstructor
public class UserManageController {

//    private final UserManageService userManageService;

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


    




}
