package com.ssg.dsilbackend.service;


import com.ssg.dsilbackend.domain.Reply;
import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.domain.Restaurant;
import com.ssg.dsilbackend.domain.Review;
import com.ssg.dsilbackend.dto.reserve.ReserveDTO;
import com.ssg.dsilbackend.dto.restaurantManage.ReplyDTO;
import com.ssg.dsilbackend.dto.restaurantManage.RestaurantManageDTO;
import com.ssg.dsilbackend.dto.restaurantManage.ReviewDTO;


import java.util.List;

public interface RestaurantManageService {
    RestaurantManageDTO getRestaurant(Long id);
    List<RestaurantManageDTO> getRestaurantList(Long memberId);
    RestaurantManageDTO updateRestaurant(Long id, RestaurantManageDTO restaurant);

    List<ReserveDTO> getReservationList(Long restaurantId);
    ReviewDTO getReview(Reservation reservation);

    ReplyDTO createReply(Long reviewId, String content);
}
