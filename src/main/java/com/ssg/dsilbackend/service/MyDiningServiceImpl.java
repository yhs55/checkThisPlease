package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Members;
import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.exception.MemberNotFoundException;
import com.ssg.dsilbackend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

//    public void saveReview()

    public List<MydiningReserveDTO> getMydiningListById(Long id) {
        Members member = membersRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + id));

        List<Reservation> reservations = reservationRepository.findByMembers(member);

        return reservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MydiningReserveDTO convertToDto(Reservation reservation) {
        Double averageScore = reviewRepository.findAverageScoreByRestaurantId(reservation.getRestaurant().getId());
        if (averageScore == null) averageScore = 0.0; // 평균 점수가 없는 경우 0.0으로 처리

        long reviewCount = reviewRepository.countByReservationRestaurantId(reservation.getRestaurant().getId()); // 리뷰 개수 조회

        return MydiningReserveDTO.from(reservation, averageScore, reviewCount);
    }
}



