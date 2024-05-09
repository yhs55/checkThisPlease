package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserManageRepository extends JpaRepository<Members, Long> {
    Boolean existsByEmail(String email);

    Optional<Members> findByEmail(String email);



    //    List<Members> findMembersByPermission(String permission);
    @Query("SELECT m FROM Members m JOIN m.permission p WHERE p.name = :permissionName")
    List<Members> findMembersByPermissionName(@Param("permissionName") String permissionName);
}

