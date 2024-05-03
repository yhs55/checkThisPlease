package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Members;
import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByMembers(Members member);
}
