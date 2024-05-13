package com.ssg.dsilbackend.controller;

import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.dto.myDinig.ReviewRequest;
import com.ssg.dsilbackend.dto.userManage.RestaurantRegisterDTO;
import com.ssg.dsilbackend.dto.userManage.ReviewReplyDTO;
import com.ssg.dsilbackend.dto.userManage.UserManageDTO;
import com.ssg.dsilbackend.service.UserManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/reviewManage")
    public ResponseEntity<?> getReviewReplyList() {
        try {
            List<ReviewReplyDTO> reviewReplyDTOS = userManageService.getReviewReplyList();
            for (ReviewReplyDTO review : reviewReplyDTOS) {
                System.out.println(review);
            }
            if (reviewReplyDTOS.isEmpty()) {
                // 데이터가 없는 경우 404 Not Found 반환
                return ResponseEntity.notFound().build();
            } else {
                // 데이터가 있는 경우 200 OK와 함께 데이터 반환
                return ResponseEntity.ok(reviewReplyDTOS);
            }
        } catch (Exception e) {
            // 예외 처리의 경우 500 Internal Server Error 반환
            return ResponseEntity.internalServerError().body("Error accessing data ");
        }
    }

    @DeleteMapping("/removeReview")
    public ResponseEntity<?> removeReview(@RequestBody Map<String, Object> payload) {
        try {
            // `reviewId`를 페이로드에서 추출
            String reviewId = String.valueOf(payload.get("reviewId"));
            System.out.println(reviewId + " 삭제할 리뷰 ID");

            userManageService.removeReview(Long.parseLong(reviewId));

            return ResponseEntity.ok().body("댓글이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            System.out.println("댓글 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.badRequest().body("댓글 삭제에 실패했습니다.");
        }
    }

    @DeleteMapping("/removeReply")
    public ResponseEntity<?> removeReply(@RequestBody Map<String, Object> payload) {
        try {
            // `reviewId`를 페이로드에서 추출
            String reviewId = String.valueOf(payload.get("reviewId"));
            System.out.println(reviewId + " 삭제할 댓글을 가진 리뷰 ID");

            userManageService.removeReply(Long.parseLong(reviewId));

            return ResponseEntity.ok().body("댓글이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            System.out.println("댓글 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.badRequest().body("댓글 삭제에 실패했습니다.");
        }
    }

}
