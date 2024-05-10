package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Members;
import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.domain.Restaurant;
import com.ssg.dsilbackend.dto.AvailableTimeTable;
import com.ssg.dsilbackend.dto.ReservationStateName;
import com.ssg.dsilbackend.dto.reserve.ReserveDTO;
import com.ssg.dsilbackend.repository.MemberRepository;
import com.ssg.dsilbackend.repository.ReserveRepository;
import com.ssg.dsilbackend.repository.RestaurantListRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Transactional
@RequiredArgsConstructor
@Log4j2
@Service
public class ReserveService {

    private final ReserveRepository reservationRepository;

    private final MemberRepository memberRepository;

    private final RestaurantListRepository restaurantRepository;

    public Long processReservation(ReserveDTO reserveDTO) {
        try {

//            Members member = memberRepository.findById(reserveDTO.getMemberId()).orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + reserveDTO.getMemberId()));
//
//            Restaurant restaurant = restaurantRepository.findById(reserveDTO.getRestaurantId()).orElseThrow(() -> new EntityNotFoundException("Restaurant not found with ID: " + reserveDTO.getRestaurantId()));

            Long memberId = 2L;
            Long restaurantId = 2L;

            Members member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + memberId));

            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with ID: " + restaurantId));

            String name = member.getName();

            Reservation reservation = Reservation.builder()
                    .reservationTime(AvailableTimeTable.AFTERNOON_1)
                    .reservationDate(reserveDTO.getReservationDate())
                    .peopleCount(reserveDTO.getPeopleCount())
                    .reservationStateName(ReservationStateName.RESERVED)
                    .restaurant(restaurant)
                    .members(member)
                    .requestContent(reserveDTO.getRequestContent())
                    .reservationTel(reserveDTO.getReservationTel())
//                    .reservationName(reserveDTO.getReservationName())
                    .reservationName(name)
                    .build();

            Reservation savedReservation = reservationRepository.save(reservation);
            log.info("예약 성공 : {}", savedReservation);

            return savedReservation.getId(); // 저장된 예약의 ID 반환
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Error creating reservation", e);
        }
    }
}