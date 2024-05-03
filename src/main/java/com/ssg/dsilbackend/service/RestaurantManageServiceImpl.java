package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.*;
import com.ssg.dsilbackend.dto.reserve.ReserveDTO;
import com.ssg.dsilbackend.dto.restaurantManage.ReplyDTO;
import com.ssg.dsilbackend.dto.restaurantManage.RestaurantManageDTO;
import com.ssg.dsilbackend.dto.restaurantManage.ReviewDTO;
import com.ssg.dsilbackend.repository.ReplyRepository;
import com.ssg.dsilbackend.repository.ReserveRepository;
import com.ssg.dsilbackend.repository.RestaurantManageRepository;
import com.ssg.dsilbackend.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestaurantManageServiceImpl implements RestaurantManageService {

    private final RestaurantManageRepository restaurantManageReprository;
    private final ReserveRepository reserveRepository;
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public RestaurantManageServiceImpl(RestaurantManageRepository restaurantManageReprository, ReserveRepository reserveRepository, ReviewRepository reviewRepository, ReplyRepository replyRepository, ModelMapper modelMapper) {
        this.restaurantManageReprository = restaurantManageReprository;
        this.reserveRepository = reserveRepository;
        this.reviewRepository = reviewRepository;
        this.replyRepository = replyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RestaurantManageDTO getRestaurant(Long id) {
        Restaurant restaurant = restaurantManageReprository.findById(id)
                .orElseThrow(() -> new RuntimeException("식당 정보를 찾을 수 없습니다"));
        return modelMapper.map(restaurant, RestaurantManageDTO.class);
    }

    @Override
    public List<RestaurantManageDTO> getRestaurantList(Long memberId) {
        List<Restaurant> restaurants = restaurantManageReprository.findByMemberId(memberId);
        return restaurants.stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantManageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantManageDTO updateRestaurant(Long id, RestaurantManageDTO updatedRestaurantDTO) {
        Restaurant restaurant = restaurantManageReprository.findById(id)
                .orElseThrow(() -> new RuntimeException("식당 정보를 찾을 수 없습니다"));

        // 엔티티의 필드를 DTO에서 가져온 값으로 업데이트
        restaurant.setTel(updatedRestaurantDTO.getTel());
        restaurant.setImg(updatedRestaurantDTO.getImg());
        restaurant.setDeposit(updatedRestaurantDTO.getDeposit());
        restaurant.setTableCount(updatedRestaurantDTO.getTableCount());

        // 엔티티를 저장하고 업데이트된 DTO로 변환하여 반환
        return modelMapper.map(restaurantManageReprository.save(restaurant), RestaurantManageDTO.class);
    }


    @Override
    public List<ReserveDTO> getReservationList(Long restaurantId) {
        List<Reservation> reservations = reserveRepository.findByRestaurantId(restaurantId);
        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReserveDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewList(Long restaurantId) {
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReplyDTO createReply(Long reviewId, String content) {
        Reply newReply = Reply.builder()
                .content(content)
                .registerDate(LocalDate.now())
                .deleteStatus(false)
                .build();

        // 여기서 필요에 따라 reviewId에 해당하는 리뷰를 찾아서 newReply에 설정

        Reply savedReply = replyRepository.save(newReply);
        return modelMapper.map(savedReply, ReplyDTO.class);
    }


}