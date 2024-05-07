package com.ssg.dsilbackend.controller;

import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.dto.myDinig.ReservationUpdateRequest;
import com.ssg.dsilbackend.dto.myDinig.ReviewRequest;
import com.ssg.dsilbackend.service.MyDiningService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/myDining")
@Log4j2
@RequiredArgsConstructor
public class MyDiningController {
    private final MyDiningService myDiningService;

    // 사용자 id 를 받아서 해당 예약리스트 출력
    @GetMapping("/{id}")
    public List<MydiningReserveDTO> getMydiningListById(@PathVariable Long id) {
        return myDiningService.getMydiningListById(id);
    }

    //리뷰 등록
    @PostMapping("/registerReview")
    public ResponseEntity<?> registerReview(@RequestBody ReviewRequest reviewRequest) {
        try {
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
        System.out.println(reservationId+" "+updateRequest.getReservationState());
        return ResponseEntity.ok().body("예약이 성공적으로 취소되었습니다.");
    }


}
