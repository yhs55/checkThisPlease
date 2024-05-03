package com.ssg.dsilbackend.service;

import com.ssg.dsilbackend.domain.Members;
import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.exception.MemberNotFoundException;
import com.ssg.dsilbackend.repository.MembersRepository;
import com.ssg.dsilbackend.repository.MyDiningRepository;
import com.ssg.dsilbackend.repository.ReservationRepository;
import com.ssg.dsilbackend.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MyDiningServiceImpl implements MyDiningService {
    private final ModelMapper modelMapper;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final MembersRepository membersRepository;

    public List<MydiningReserveDTO> getMydiningListById(Long id) {
        Members member = membersRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + id));

        // 해당 사용자가 예약한 Reservation 리스트를 가져옴
        List<Reservation> reservations = reservationRepository.findByMembers(member);

        // Reservation 리스트를 MydiningReserveDTO로 변환하여 반환
        return reservations.stream()
                .map(reservation -> {
                    MydiningReserveDTO dto = modelMapper.map(reservation, MydiningReserveDTO.class);
                    dto.setRestaurantName(reservation.getRestaurant().getName());
                    dto.setRestaurantDeposit(reservation.getRestaurant().getDeposit());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
