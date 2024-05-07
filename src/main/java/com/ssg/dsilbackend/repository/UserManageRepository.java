package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManageRepository extends JpaRepository<Members,Integer> {
    Boolean existsByEmail(String email);

    Members findByUsername(String username);

}
