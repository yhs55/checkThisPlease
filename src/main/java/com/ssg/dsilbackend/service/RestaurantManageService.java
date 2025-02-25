package com.ssg.dsilbackend.service;


import com.ssg.dsilbackend.domain.*;
import com.ssg.dsilbackend.dto.AvailableTimeTable;
import com.ssg.dsilbackend.dto.Crowd;
import com.ssg.dsilbackend.dto.restaurantManage.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public interface RestaurantManageService {

    RestaurantManageDTO getRestaurant(Long id);
    List<RestaurantManageDTO> getRestaurantList(Long memberId);
    RestaurantManageDTO updateRestaurant(Long id, RestaurantManageDTO restaurant);

    RestaurantManageDTO updateCrowd(Long id, Crowd crowd) throws Exception;

    List<ReservationDTO> getReservationList(Long restaurantId);
//    ReviewDTO getReview(Reservation reservation);

    ReplyDTO createReply(Long reviewId, String content);


    AvailableTimeDTO createAvailableTime(Long restaurantId, AvailableTimeTable slot);

    void deleteAvailableTime(Long restaurantId, AvailableTimeTable slot);

    List<AvailableTimeDTO> getAvailableTimes(Long restaurantId);

    List<ReviewDTO> getReviewList(Long restaurantId);

    ReviewDTO updateReviewDeleteStatus(Long reviewId, boolean deleteStatus);

    List<CategoryDTO> getCategoryList(Long restaurantId);

    List<FacilityDTO> getFacilityList(Long restaurantId);

    List<MenuDTO> getMenuList(Long restaurantId);


    //리뷰에 자동으로 답글달기


}
