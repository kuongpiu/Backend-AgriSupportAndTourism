package com.example.agrisupportandtorism.repository.payment;

import com.example.agrisupportandtorism.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, String> {
}
