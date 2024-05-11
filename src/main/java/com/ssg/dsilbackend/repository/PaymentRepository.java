package com.ssg.dsilbackend.repository;

import com.ssg.dsilbackend.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
