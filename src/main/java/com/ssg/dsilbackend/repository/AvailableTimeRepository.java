package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.AvailableTime;
import com.ssg.dsilbackend.dto.AvailableTimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long> {
    Optional<AvailableTime> findByRestaurantIdAndAvailableTime(Long restaurantId, AvailableTimeTable slot);
}
