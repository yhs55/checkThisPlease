package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Members;
import com.ssg.dsilbackend.domain.Reservation;
import com.ssg.dsilbackend.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByMembers(Members member);


    // 예약 수가 많은 상위 10개 식당 조회
    @Query("SELECT r.restaurant FROM Reservation r " +
            "GROUP BY r.restaurant.id ORDER BY COUNT(r.id) DESC")
    Page<Restaurant> findTopByReservations(Pageable pageable);
}
