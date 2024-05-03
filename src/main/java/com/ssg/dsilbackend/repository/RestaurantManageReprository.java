package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantManageReprository extends JpaRepository<Restaurant, Long> {
    //이 작업으로 findAll()등 메소드 자동으로 생성!
}
