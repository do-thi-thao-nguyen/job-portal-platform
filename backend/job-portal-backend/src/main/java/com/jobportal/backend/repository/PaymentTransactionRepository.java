package com.jobportal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobportal.backend.entity.PaymentTransaction;

import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    Optional<PaymentTransaction> findByOrderId(String orderId);
}