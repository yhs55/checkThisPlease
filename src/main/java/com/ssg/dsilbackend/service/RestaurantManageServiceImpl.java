package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.*;
import com.ssg.dsilbackend.dto.AvailableTimeTable;
import com.ssg.dsilbackend.dto.Crowd;
import com.ssg.dsilbackend.dto.restaurantManage.*;
import com.ssg.dsilbackend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestaurantManageServiceImpl implements RestaurantManageService {

    @Autowired
    private AvailableTimeRepository availableTimeRepository;
    private final RestaurantManageRepository restaurantManageRepository;
    private final ReserveRepository reserveRepository;
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantManageServiceImpl(RestaurantManageRepository restaurantManageRepository, ReserveRepository reserveRepository, ReviewRepository reviewRepository, ReplyRepository replyRepository, ModelMapper modelMapper) {
        this.restaurantManageRepository = restaurantManageRepository;
        this.reserveRepository = reserveRepository;
        this.reviewRepository = reviewRepository;
        this.replyRepository = replyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RestaurantManageDTO getRestaurant(Long id) {
        Restaurant restaurant = restaurantManageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("식당 정보를 찾을 수 없습니다"));
        return modelMapper.map(restaurant, RestaurantManageDTO.class);
    }

    @Override
    public List<RestaurantManageDTO> getRestaurantList(Long memberId) {
        List<Restaurant> restaurants = restaurantManageRepository.findByMemberId(memberId);
        return restaurants.stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantManageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RestaurantManageDTO updateRestaurant(Long id, RestaurantManageDTO restaurantDTO) {
        Restaurant restaurant = restaurantManageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with ID " + id + " not found"));

        // 업데이트 가능한 필드를 설정
        restaurant.updateRestaurant(restaurantDTO.getTel(), restaurantDTO.getDeposit(), restaurantDTO.getTableCount());

        // 식당 정보 업데이트 후 저장
        Restaurant updatedRestaurant = restaurantManageRepository.save(restaurant);

        // 업데이트된 정보를 DTO로 변환하여 반환
        return convertToRestaurantDTO(updatedRestaurant);
    }

    private RestaurantManageDTO convertToRestaurantDTO(Restaurant restaurant) {
        RestaurantManageDTO dto = new RestaurantManageDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setAddress(restaurant.getAddress());
        dto.setTel(restaurant.getTel());
        dto.setDeposit(restaurant.getDeposit());
        dto.setTableCount(restaurant.getTableCount());
        dto.setCrowd(restaurant.getCrowd().toString()); // Assuming Crowd is stored as an Enum
        return dto;
    }

    //    식당의 crowd를 변환하는 메소드
    @Override
    public RestaurantManageDTO updateCrowd(Long id, Crowd crowd) throws Exception {
        Restaurant restaurant = restaurantManageRepository.findById(id)
                .orElseThrow(() -> new Exception("Restaurant not found with id: " + id));

        restaurant.setRestaurantCrowd(crowd);
        Restaurant savedRestaurant = restaurantManageRepository.save(restaurant);

        return modelMapper.map(savedRestaurant, RestaurantManageDTO.class);
    }


    @Override
    public List<ReservationDTO> getReservationList(Long restaurantId) {
        List<Reservation> reservations = reserveRepository.findByRestaurantId(restaurantId);
        return reservations.stream()
                .map(this::convertToReserveDto)
                .collect(Collectors.toList());
    }
    private ReservationDTO convertToReserveDto(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .restaurantId(reservation.getRestaurant().getId())
                .memberId(reservation.getMembers().getId())
                .reservationStateName(reservation.getReservationStateName())
                .peopleCount(reservation.getPeopleCount())
                .reservationTime(reservation.getReservationTime())
                .reservationName(reservation.getReservationName())
                .requestContent(reservation.getRequestContent())
                .reservationDate(reservation.getReservationDate())
                .reservationTel(reservation.getReservationTel())
                .build();
    }

//    @Override
//    public ReviewDTO getReview(Reservation reservation) {
//        Review review = reviewRepository.findByReservation(reservation);
//        return modelMapper.map(reviewRepository.save(review), ReviewDTO.class);
//    }

    @Override
    public ReplyDTO createReply(Long reviewId, String content) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));

        Reply newReply = new Reply();
        newReply.setContent(content);
        newReply.setRegisterDate(LocalDate.now());
        newReply.setDeleteStatus(false);

        Reply savedReply = replyRepository.save(newReply);

        // 리뷰에 답글 설정
        review.setReply(savedReply);
        reviewRepository.save(review);

        return convertToReplyDto(savedReply);
    }
    private ReplyDTO convertToReplyDto(Reply reply) {
        return ReplyDTO.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .registerDate(reply.getRegisterDate())
                .deleteStatus(reply.getDeleteStatus())
                .build();
    }


    @Override
    public AvailableTimeDTO createAvailableTime(Long restaurantId, AvailableTimeTable slot) {
        Optional<Restaurant> restaurant = restaurantManageRepository.findById(restaurantId);
        if (!restaurant.isPresent()) {
            throw new IllegalStateException("Restaurant with ID " + restaurantId + " not found");
        }

        // 동일한 시간대의 AvailableTime이 이미 존재하는지 확인
        Optional<AvailableTime> existingAvailableTime = availableTimeRepository.findByRestaurantIdAndAvailableTime(restaurantId, slot);
        if (existingAvailableTime.isPresent()) {
            throw new IllegalStateException("Available time already exists for this slot");
        }

        AvailableTime availableTime = new AvailableTime();
        availableTime.setAvailableTime(slot);
        availableTime.setRestaurant(restaurant.get());
        AvailableTime saved = availableTimeRepository.save(availableTime);
        return new AvailableTimeDTO(saved.getId(), saved.getAvailableTime().name());
    }


    @Override
    public void deleteAvailableTime(Long restaurantId, AvailableTimeTable slot) {
        availableTimeRepository.deleteByRestaurantIdAndAvailableTime(restaurantId, slot);
    }
    @Transactional
    public List<ReviewDTO> getReviewList(Long restaurantId) {
        List<Review> reviews = reviewRepository.findByReservationRestaurantId(restaurantId);

        return reviews.stream()
                .map(this::convertToReviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO updateReviewDeleteStatus(Long reviewId, boolean deleteStatus) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
        review.setDeleteStatus(deleteStatus);
        reviewRepository.save(review);
        return convertToReviewDto(review);
    }

    private ReviewDTO convertToReviewDto(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .replyId(review.getReply().getId())
                .replyContent(review.getReply().getContent())
                .reservationId(review.getReservation().getId())
                .content(review.getContent())
                .registerDate(review.getRegisterDate())  // 예제에서 LocalDate로 가정
                .score(review.getScore())
                .deleteStatus(review.isDeleteStatus())
                .img(review.getImg())
                .build();
    }

}