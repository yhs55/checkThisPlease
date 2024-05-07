//package com.ssg.dsilbackend.controller;
//
//import com.ssg.dsilbackend.domain.Reply;
//import com.ssg.dsilbackend.domain.Reservation;
//import com.ssg.dsilbackend.domain.Review;
//import com.ssg.dsilbackend.dto.reserve.ReserveDTO;
//import com.ssg.dsilbackend.dto.restaurantManage.ReplyDTO;
//import com.ssg.dsilbackend.dto.restaurantManage.RestaurantManageDTO;
//import com.ssg.dsilbackend.dto.restaurantManage.ReviewDTO;
////import com.ssg.dsilbackend.service.RestaurantManageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import com.ssg.dsilbackend.domain.Restaurant;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/restaurant")
//public class RestaurantManageController {
//    private final RestaurantManageService restaurantService;
//    @Autowired
//    public RestaurantManageController(RestaurantManageService restaurantService) {
//        this.restaurantService = restaurantService;
//    }
//
//    @GetMapping("/{restaurant-id}")
//    public ResponseEntity<RestaurantManageDTO> getRestaurant(@PathVariable Long id) {
//        RestaurantManageDTO restaurantDTO = restaurantService.getRestaurant(id);
//        if (restaurantDTO == null){
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(restaurantDTO);
//    }
//
//    @PutMapping("/{restaurant-id}")
//    public ResponseEntity<RestaurantManageDTO> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantManageDTO restaurantDTO) {
//        RestaurantManageDTO updatedRestaurant = restaurantService.updateRestaurant(id, restaurantDTO);
//        return ResponseEntity.ok(updatedRestaurant);
//    }
//
//    @GetMapping("/{member-id}/restaurants")
//    public ResponseEntity<List<RestaurantManageDTO>> getRestaurantsByMember(@PathVariable Long memberId) {
//        List<RestaurantManageDTO> restaurants = restaurantService.getRestaurantList(memberId);
//        if (restaurants.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(restaurants);
//    }
//
//    @GetMapping("/{restaurant-id}/reservations")
//    public ResponseEntity<List<ReserveDTO>> getReservationList(@PathVariable Long restaurantId) {
//        List<ReserveDTO> reservations = restaurantService.getReservationList(restaurantId);
//        if (reservations.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return  ResponseEntity.ok(reservations);
//    }
//    @GetMapping("/{restaurant-id}/reviews")
//    public ResponseEntity<ReviewDTO> getReview(@PathVariable Reservation reservation) {
//        ReviewDTO reviewDTO = restaurantService.getReview(reservation);
//        if (reviewDTO.isDeleteStatus()) {
//            return ResponseEntity.noContent().build();
//        }
//        return  ResponseEntity.ok(reviewDTO);
//    }
//
//    @GetMapping("/reviews/{review-id}")
//    public ResponseEntity<ReplyDTO> createReply(@PathVariable Long reviewId, String content){
//        ReplyDTO replyDTO = restaurantService.createReply(reviewId, content);
//        return ResponseEntity.ok(replyDTO);
//    }
//
//}
