package com.ssg.dsilbackend.controller;

import com.ssg.dsilbackend.dto.myDinig.*;
import com.ssg.dsilbackend.service.MyDiningService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/myDining")
@Log4j2
@RequiredArgsConstructor
public class MyDiningController {
    private final MyDiningService myDiningService;

    // 사용자 id 를 받아서 해당 예약리스트 출력
    @GetMapping("/reservations/{id}")
    public List<MydiningReserveDTO> getMydiningListById(@PathVariable Long id) {
        return myDiningService.getMydiningReserveListById(id);
    }

    // 사용자 id 를 받아서 해당 즐겨찾기 출력
    @GetMapping("/bookmarks/{id}")
    public List<MydiningBookmarkDTO> getMydiningBookmarksListById(@PathVariable Long id) {
        return myDiningService.getMydiningBookmarksListById(id);
    }

    // 사용자 id 를 받아서 해당 리뷰 출력
    @GetMapping("/reviews/{id}")
    public List<MydiningReviewsDTO> getMydiningReviewsListById(@PathVariable Long id) {
        System.out.println(id);
        System.out.println(id);
        return myDiningService.getMydiningReviewsListById(id);
    }


    //리뷰 등록
    @PostMapping("/registerReview")
    public ResponseEntity<?> registerReview(@RequestBody ReviewRequest reviewRequest) {
        try {
            System.out.println(reviewRequest);
            myDiningService.registerReview(reviewRequest);
            return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering review: " + e.getMessage());
        }
    }

    // 예약 취소 (CANCLED 상태 변경)
    @PutMapping("/reservation-cancel/{reservationId}")
    @Transactional
    public ResponseEntity<?> cancelReservation(@PathVariable Long reservationId, @RequestBody ReservationUpdateRequest updateRequest) {
        boolean result = myDiningService.cancelReservation(reservationId, updateRequest);

        if (result) {
            return ResponseEntity.ok().body("예약이 성공적으로 취소되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("예약 취소 실패: 해당 예약을 찾을 수 없습니다.");
        }
    }

    // 리뷰 취소 요청
    @PutMapping("/reviewRemoveRequest/{reviewId}")
    @Transactional
    public ResponseEntity<?> removeRequestReview(@PathVariable Long reviewId){
        System.out.println(reviewId+"삭제요청할 아이디!!");
        boolean result = myDiningService.removeRequestReview(reviewId);
        if (result) {
            return ResponseEntity.ok().body("취소 요청이 성공적으로 되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("취소 요청 실패: 해당 취소를 찾을 수 없습니다.");
        }
    }

    // 즐겨찾기 아이디 받아서 즐겨찾기 삭제
    @DeleteMapping("/bookmark-cancel/{id}")
    @Transactional
    public ResponseEntity<?> removeBookmarkById(@PathVariable Long id){
        boolean isDeleted = myDiningService.removeBookmark(id);
        if (isDeleted) {
            return ResponseEntity.ok().body("즐겨찾기가 성공적으로 취소되었습니다.");
        } else {
            return ResponseEntity.notFound().build(); // 존재하지 않는 ID로 삭제 요청 시
        }
    }

}
