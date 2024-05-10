package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantListRepository extends JpaRepository<Restaurant, Long> {
}
