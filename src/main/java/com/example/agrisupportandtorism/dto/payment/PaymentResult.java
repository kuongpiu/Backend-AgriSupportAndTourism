package com.example.agrisupportandtorism.dto.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class PaymentResult {
    String status;
    Map<String, Object> data;
}
