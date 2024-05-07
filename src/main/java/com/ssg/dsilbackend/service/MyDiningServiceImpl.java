package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Members;
import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.domain.Review;
import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.dto.myDinig.ReservationUpdateRequest;
import com.ssg.dsilbackend.dto.myDinig.ReviewRequest;
import com.ssg.dsilbackend.exception.MemberNotFoundException;
import com.ssg.dsilbackend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//@Transactional
//@Service
//@RequiredArgsConstructor
//public class MyDiningServiceImpl implements MyDiningService {
//    private final ModelMapper modelMapper;
//    private final RestaurantRepository restaurantRepository;
//    private final ReservationRepository reservationRepository;
//    private final MembersRepository membersRepository;
//
//    public List<MydiningReserveDTO> getMydiningListById(Long id) {
//        Members member = membersRepository.findById(id)
//                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + id));
//
//        List<Reservation> reservations = reservationRepository.findByMembers(member);
//
//        return reservations.stream()
//                .map(reservation -> {
//                    MydiningReserveDTO dto = MydiningReserveDTO.from(reservation);
//
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//}

//@Service
//@RequiredArgsConstructor
//@Transactional
//public class MyDiningServiceImpl implements MyDiningService {
//    private final ModelMapper modelMapper;
//    private final RestaurantRepository restaurantRepository;
//    private final ReservationRepository reservationRepository;
//    private final MembersRepository membersRepository;
//    private final ReviewRepository reviewRepository; // 리뷰 리포지토리 추가
//
//    public List<MydiningReserveDTO> getMydiningListById(Long id) {
//        Members member = membersRepository.findById(id)
//                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + id));
//
//        List<Reservation> reservations = reservationRepository.findByMembers(member);
//
//        return reservations.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    private MydiningReserveDTO convertToDto(Reservation reservation) {
//        Long reviewCount = reviewRepository.countByReservationRestaurantId(reservation.getRestaurant().getId()); // 식당 ID에 대한 리뷰 수 조회
//
//        return MydiningReserveDTO.from(reservation, reviewCount);
//    }
//}

//@Service
//@RequiredArgsConstructor
//@Transactional
//public class MyDiningServiceImpl implements MyDiningService {
//        private final ModelMapper modelMapper;
//    private final RestaurantRepository restaurantRepository;
//    private final ReservationRepository reservationRepository;
//    private final MembersRepository membersRepository;
//    private final ReviewRepository reviewRepository; // 리뷰 리포지토리 추가
//
//    public List<MydiningReserveDTO> getMydiningListById(Long id) {
//        Members member = membersRepository.findById(id)
//                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + id));
//
//        List<Reservation> reservations = reservationRepository.findByMembers(member);
//
//        return reservations.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    private MydiningReserveDTO convertToDto(Reservation reservation) {
//        Double averageScore = reviewRepository.findAverageScoreByRestaurantId(reservation.getRestaurant().getId());
//        if (averageScore == null) averageScore = 0.0; // 평균 점수가 없는 경우 0.0으로 처리
//
//        return MydiningReserveDTO.from(reservation, averageScore);
//    }
//}

@Service
@RequiredArgsConstructor
@Transactional
public class MyDiningServiceImpl implements MyDiningService {
    private final ModelMapper modelMapper;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final MembersRepository membersRepository;
    private final ReviewRepository reviewRepository;


    // 사용자 아이디번호 받아서 예약리스트 출력
    public List<MydiningReserveDTO> getMydiningListById(Long id) {
        Members member = membersRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + id));

        List<Reservation> reservations = reservationRepository.findByMembers(member);

        return reservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    //식당 평점 가져오기
    private MydiningReserveDTO convertToDto(Reservation reservation) {
        Double averageScore = reviewRepository.findAverageScoreByRestaurantId(reservation.getRestaurant().getId());
        if (averageScore == null) averageScore = 0.0; // 평균 점수가 없는 경우 0.0으로 처리

        long reviewCount = reviewRepository.countByReservationRestaurantId(reservation.getRestaurant().getId()); // 리뷰 개수 조회

        return MydiningReserveDTO.from(reservation, averageScore, reviewCount);
    }

    @Transactional
    public void registerReview(ReviewRequest reviewRequest) {
        // 예약 객체 조회
        Reservation reservation = reservationRepository.findById(reviewRequest.getReservationId())
                .orElseThrow(() -> new RuntimeException("예약을 찾을 수 없습니다."));

        // Review 객체 생성
        Review review = Review.builder()
                .reservation(reservation) // 연결된 예약 객체
                .content(reviewRequest.getReviewContents()) // 리뷰 내용
                .registerDate(reviewRequest.getRegisterDate()) // 리뷰 등록 날짜
                .score(reviewRequest.getReviewScore()) // 평점
                .deleteStatus(false) // 삭제 상태 초기화
                .build();

        // Review 객체 저장
        reviewRepository.save(review);
    }

//    public void cancelReservation(Long reservationId, ReservationUpdateRequest reservationUpdateRequest){
//
//    }

}



