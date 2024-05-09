package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Menu;
import com.ssg.dsilbackend.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByRestaurantId(Long id);

}
