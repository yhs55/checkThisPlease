package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserManageRepository extends JpaRepository<Members,Integer> {
    Boolean existsByEmail(String email);
    Members findByEmail(String email);
    void updateStatusByEmail(String email, Boolean status);

    Members findByEmailAndRestaurantName(String email, String restaurantName);

    List<Members> findAllByPermission(String permission);
}
