package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.*;
import com.ssg.dsilbackend.repository.ReplyRepository;
import com.ssg.dsilbackend.repository.ReserveRepository;
import com.ssg.dsilbackend.repository.RestaurantManageReprository;
import com.ssg.dsilbackend.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class RestaurantManageImpl implements RestaurantManage {

    private final RestaurantManageReprository restaurantManageReprository;
    private final ReserveRepository reserveRepository;
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public RestaurantManageImpl(RestaurantManageReprository restaurantManageReprository, ReserveRepository reserveRepository, ReviewRepository reviewRepository, ReplyRepository replyRepository) {
        this.restaurantManageReprository = restaurantManageReprository;
        this.reserveRepository = reserveRepository;
        this.reviewRepository = reviewRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public Restaurant getRestaurant(Long id) {
        return restaurantManageReprository.findById(id).orElseThrow(() -> new RuntimeException("식당 정보를 찾을 수 없습니다"));
    }

    @Override
    public List<Restaurant> getRestaurantList(Long memberId) {
        return restaurantManageReprository.findByMemberId(memberId);
    }

    @Override
    public Restaurant updateRestaurant(Long id, Restaurant updateRestaurant) {
        return restaurantManageReprository.findById(id)
                .map(restaurant -> {
                    restaurant.setTel(updateRestaurant.getTel());
                    restaurant.setCrowd(updateRestaurant.getCrowd());
                    restaurant.setImg(updateRestaurant.getImg());
                    restaurant.setDeposit(updateRestaurant.getDeposit());
                    restaurant.setTableCount(updateRestaurant.getTableCount());
                    return restaurantManageReprository.save(restaurant);
                }).orElseThrow(() -> new RuntimeException("식당 정보를 찾을 수 없습니다"));
    }

    @Override
    public List<Reservation> getReservationList(Long restaurantId) {
        return reserveRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<Review> getReviewList(Long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Reply createReply(Long reviewId, String content) {
        Reply newReply = Reply.builder()
                .content(content)
                .registerDate(LocalDate.now()) // 현재 날짜를 등록일로 설정
                .deleteStatus(false) // 새로운 답글이므로 삭제 상태는 false
                .build();

        return replyRepository.save(newReply);
    }


}