package com.example.agrisupportandtorism.entity.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Table(name = "payment")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Payment {
    @Id
    @Column(name = "txn_ref")
    @Size(max = 100)
    private String txnRef;

    @Column(name = "tmm_code")
    @Size(max = 8)
    private String tmmCode;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "bank_code")
    @Size(max = 20)
    private String bankCode;

    @Column(name = "transaction_no")
    @Size(max = 15)
    private String transactionNumber;

    @Column(name = "order_info")
    @Size(max = 255)
    private String orderInfo;

    @Column(name = "pay_date")
    private LocalDateTime payDate;
}
