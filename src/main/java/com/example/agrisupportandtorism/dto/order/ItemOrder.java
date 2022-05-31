package com.example.agrisupportandtorism.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ItemOrder {
    private Integer productId;
    private Integer quantity;
}
