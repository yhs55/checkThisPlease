package com.ssg.dsilbackend.controller;


import com.ssg.dsilbackend.dto.userManage.OwnerManageDTO;
import com.ssg.dsilbackend.dto.userManage.RestaurantRegisterDTO;
import com.ssg.dsilbackend.dto.userManage.UserManageDTO;
import com.ssg.dsilbackend.service.UserManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/memberManage")
@Log4j2
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

    // ------------------------------------------------- user

    @GetMapping("/userMyPage")
    public UserManageDTO getUserData(@RequestParam String email){
        log.info(email);
        return userManageService.getUserInfoByEmail(email);
    }

    @PostMapping("/userMyPage")
    public void postUserData(@RequestBody UserManageDTO userManageDTO){
        log.info(userManageDTO);
        userManageService.modifyUserInfo(userManageDTO);
    }

    @DeleteMapping("/userMyPage")
    public void deleteUserData(@RequestParam String email){
        log.info(email);
        userManageService.deleteUserInfo(email);
    }

    // ------------------------------------------------- owner

    @GetMapping("/ownerMyPage")
    public List<OwnerManageDTO> getOwnerData(@RequestParam String email){
        log.info(email);
        return userManageService.getRestaurantByEmail(email);
    }


    @PostMapping("/ownerMyPage")
    public void postOwnerData(@RequestBody OwnerManageDTO ownerManageDTO){
        log.info(ownerManageDTO);
        userManageService.modifyOwnerData(ownerManageDTO);
    }

    // ------------------------------------------------- admin

    // 회원 관리 페이지
    @GetMapping("/adminManageUserPage")
    public List<UserManageDTO> getUserManageList(){
        log.info("일반회원 목록 출력");
        return userManageService.getUserInfoList();
    }

    // 회원 관리 페이지 - 수정
    @PostMapping("/adminManageUserPage")
    public void modifyUserInfo(@RequestBody UserManageDTO userManageDTO){
        log.info("일반회원 정보 수정");
        log.info(userManageDTO);
        userManageService.modifyUserInfo(userManageDTO);
    }

    // 회원 관리 페이지 - status 값 수정
    @DeleteMapping("/adminManageUserPage")
    public void deleteUserInfo(@RequestParam String email){
        log.info("일반 회원 정보 삭제");
        log.info(email);
        userManageService.deleteUserInfo(email);

    }

    // 식당 관리자 페이지
    @GetMapping("/adminManageRestaurantPage")
    public List<OwnerManageDTO> getOwnerManageList(){
        log.info("식당 관리자 목록 출력");
        return userManageService.getOwnerInfoList();
    }

    // 식당 관리자 페이지 - 수정
    @PostMapping("/adminManageRestaurantPage")
    public void modifyOwnerInfo(@RequestBody OwnerManageDTO request){
        log.info("식당 관리자 정보 수정");
        log.info(request);
        userManageService.modifyOwnerInfo(request);
    }

    // 식당 관리자 페이지 - 식당 삭제
    @DeleteMapping("/adminManageRestaurantPage")
    @Transactional
    public void deleteOwnerInfo(@RequestParam String restaurantName){
        log.info("식당 관리자 정보 삭제");
        log.info(restaurantName);
        userManageService.removeRestaurantByName(restaurantName);
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
