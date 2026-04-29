package com.movieticket.paymentservice.repository;

import com.movieticket.paymentservice.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, UUID> {
}
