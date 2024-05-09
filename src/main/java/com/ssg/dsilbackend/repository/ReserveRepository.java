package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reservation,Long> {
}
