package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReserveRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRestaurantId(Long restaurantId);

}
