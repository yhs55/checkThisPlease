package com.ssg.dsilbackend.controller;

import com.ssg.dsilbackend.domain.Reply;
import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.domain.Review;
import com.ssg.dsilbackend.dto.AvailableTimeTable;
import com.ssg.dsilbackend.dto.Crowd;
import com.ssg.dsilbackend.dto.reserve.ReserveDTO;
import com.ssg.dsilbackend.dto.restaurantManage.AvailableTimeDTO;
import com.ssg.dsilbackend.dto.restaurantManage.ReplyDTO;
import com.ssg.dsilbackend.dto.restaurantManage.RestaurantManageDTO;
import com.ssg.dsilbackend.dto.restaurantManage.ReviewDTO;
import com.ssg.dsilbackend.service.RestaurantManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ssg.dsilbackend.domain.Restaurant;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantManageController {
    private final RestaurantManageService restaurantManageService;

    @GetMapping("/{restaurant-id}")
    public ResponseEntity<RestaurantManageDTO> getRestaurant(@PathVariable Long id) {
        RestaurantManageDTO restaurantDTO = restaurantManageService.getRestaurant(id);
        if (restaurantDTO == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(restaurantDTO);
    }

    @PutMapping("/{restaurant-id}")
    public ResponseEntity<RestaurantManageDTO> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantManageDTO restaurantDTO) {
        RestaurantManageDTO updatedRestaurant = restaurantManageService.updateRestaurant(id, restaurantDTO);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @GetMapping("/{member-id}/restaurants")
    public ResponseEntity<List<RestaurantManageDTO>> getRestaurantsByMember(@PathVariable Long memberId) {
        List<RestaurantManageDTO> restaurants = restaurantManageService.getRestaurantList(memberId);
        if (restaurants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(restaurants);
    }

    @PatchMapping("/{restaurant-id}/crowd")
    public ResponseEntity<RestaurantManageDTO> updateCrowd(@PathVariable Long id, @RequestParam("status") Crowd crowd) {
        try {
            RestaurantManageDTO updatedRestaurantDTO = restaurantManageService.updateCrowd(id, crowd);
            return ResponseEntity.ok(updatedRestaurantDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{restaurant-id}/reservations")
    public ResponseEntity<List<ReserveDTO>> getReservationList(@PathVariable Long restaurantId) {
        List<ReserveDTO> reservations = restaurantManageService.getReservationList(restaurantId);
        if (reservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(reservations);
    }
    @GetMapping("/{restaurant-id}/reviews")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Reservation reservation) {
        ReviewDTO reviewDTO = restaurantManageService.getReview(reservation);
        if (reviewDTO.isDeleteStatus()) {
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(reviewDTO);
    }

    @GetMapping("/reviews/{review-id}")
    public ResponseEntity<ReplyDTO> createReply(@PathVariable Long reviewId, String content){
        ReplyDTO replyDTO = restaurantManageService.createReply(reviewId, content);
        return ResponseEntity.ok(replyDTO);
    }

    // 새로운 AvailableTime 인스턴스를 생성하고, 해당 DTO를 반환합니다!
    @PostMapping("/{restaurantId}/available-times")
    public ResponseEntity<AvailableTimeDTO> createAvailableTime(@PathVariable Long restaurantId, @RequestParam AvailableTimeTable slot) {
        AvailableTimeDTO newAvailableTimeDTO = restaurantManageService.createAvailableTime(restaurantId, slot);
        return ResponseEntity.ok(newAvailableTimeDTO);
    }

    // 지정된 AvailableTime 인스턴스를 삭제
    @DeleteMapping("/{restaurantId}/available-times")
    public ResponseEntity<Void> deleteAvailableTime(@PathVariable Long restaurantId, @RequestParam AvailableTimeTable slot) {
        restaurantManageService.deleteAvailableTime(restaurantId, slot);
        return ResponseEntity.ok().build();
    }

}
