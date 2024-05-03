package com.ssg.dsilbackend.controller;

import com.ssg.dsilbackend.domain.Reply;
import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.domain.Review;
import com.ssg.dsilbackend.service.RestaurantManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ssg.dsilbackend.domain.Restaurant;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantManageController {
    private final RestaurantManage restaurantService;
    @Autowired
    public RestaurantManageController(RestaurantManage restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{restaurant-id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        if (restaurant == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    @PutMapping("/{restaurant-id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @GetMapping("/{member-id}/restaurants")
    public ResponseEntity<List<Restaurant>> getRestaurantsByMember(@PathVariable Long memberId) {
        List<Restaurant> restaurants = restaurantService.getRestaurantList(memberId);
        if (restaurants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{restaurant-id}/reservations")
    public ResponseEntity<List<Reservation>> getReservationList(@PathVariable Long restaurantId) {
        List<Reservation> reservations = restaurantService.getReservationList(restaurantId);
        if (reservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(reservations);
    }
    @GetMapping("/{restaurant-id}/reviews")
    public ResponseEntity<List<Review>> getReviewList(@PathVariable Long restaurantId) {
        List<Review> reviews = restaurantService.getReviewList(restaurantId);
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(reviews);
    }

    @GetMapping("/reviews/{review-id}")
    public ResponseEntity<Reply> createReply(@PathVariable Long reviewId, String content){
        Reply reply = restaurantService.createReply(reviewId, content);
        return ResponseEntity.ok(reply);
    }

}
