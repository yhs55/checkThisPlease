package com.ssg.dsilbackend.controller;

import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.dto.myDinig.RegisterDTO;
import com.ssg.dsilbackend.service.MyDiningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/myDining")
@Log4j2
@RequiredArgsConstructor
public class MyDiningController {
    private final MyDiningService myDiningService;

    @GetMapping("/{id}")
    public List<MydiningReserveDTO> getMydiningListById(@PathVariable Long id){
        return myDiningService.getMydiningListById(id);
    }

    @PostMapping("/registerReview")
    public ResponseEntity<?> registerReview(
            @RequestParam("reviewContents") String reviewContents,
            @RequestParam("reservationId") Long reservationId,
            @RequestParam("registerDate") LocalDate registerDate,
            @RequestParam("reviewScore") Long reviewScore,
            @RequestParam("userEmail") String userEmail
            ) {

        try {
            System.out.println(reviewContents+" "+reservationId+" "+registerDate+" "+reviewScore+" "+userEmail);
            // 데이터 검증 및 처리 로직
//            ReviewDTO reviewDTO = new ReviewDTO();
//            reviewDTO.setReviewContents(reviewContents);
//            reviewDTO.setReservationId(reservationId);
//            reviewDTO.setReviewDate(LocalDate.parse(reviewDate)); // String을 LocalDate로 변환
//            reviewDTO.setReviewScore(reviewScore);
//            reviewDTO.setUserId(userId);
//            reviewDTO.setFile(file);
//
//            // 리뷰 등록 서비스 호출
//            reviewService.registerReview(reviewDTO);

            return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            // 에러 핸들링
            return ResponseEntity.badRequest().body("Error registering review: " + e.getMessage());

        }
    }

//    @PostMapping("/registerReview")
//    public ResponseEntity<?> registerReviewByRestaurantId(
//            @RequestParam("reviewContents") String reviewContents,
//            @RequestParam("reviewDate") String reviewDate,
//            @RequestParam("reviewScore") int reviewScore,
//            @RequestParam("userId") String userEmail,
//            @RequestParam(value = "file", required = false) MultipartFile file,
//            @RequestParam("reservationId") Long reservationId
//            ) {
//
//        try {
//            // 여기서 Review 객체를 생성하고 저장하는 로직을 추가하세요.
//            System.out.println(reviewContents+" "+reviewDate+" "+reviewScore+" "+userEmail+" "+reservationId
//            );
////            MyDiningService.saveReview(reviewContents, restaurantId, reviewDate, reviewScore, userEmail, file);
//            return ResponseEntity.ok("Review successfully registered.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error registering review: " + e.getMessage());
//        }
//    }

//    @RestController
//    @RequestMapping("/myDining")
//    public class ReviewController {
//
//        @PostMapping("/registerReview")
//        public ResponseEntity<String> registerReview(@RequestBody ReviewDTO reviewDTO) {
//            try {
//                // 리뷰 등록 로직
//                // 예: reviewService.registerReview(reviewDTO);
//                return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body("리뷰 등록에 실패하였습니다.");
//            }
//        }
//    }

}
