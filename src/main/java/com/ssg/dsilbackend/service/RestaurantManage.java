package com.ssg.dsilbackend.service;


import com.ssg.dsilbackend.domain.Reply;
import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.domain.Restaurant;
import com.ssg.dsilbackend.domain.Review;


import java.util.List;

public interface RestaurantManage{
    Restaurant getRestaurant(Long id);
    List<Restaurant> getRestaurantList(Long memberId);
    Restaurant updateRestaurant(Long id, Restaurant restaurant);

    List<Reservation> getReservationList(Long restaurantId);
    List<Review> getReviewList(Long restaurantId);

    Reply createReply(Long reviewId, String content);
}
