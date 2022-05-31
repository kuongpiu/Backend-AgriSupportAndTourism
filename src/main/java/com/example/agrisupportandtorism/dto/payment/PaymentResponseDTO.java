package com.example.agrisupportandtorism.dto.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PaymentResponseDTO {
    private String status;
    private String message;
    private String url;
}
