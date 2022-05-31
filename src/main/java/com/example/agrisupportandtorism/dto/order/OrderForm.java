package com.example.agrisupportandtorism.dto.order;

import com.example.agrisupportandtorism.entity.address.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderForm {
    private Address address;
    private List<ItemOrder> itemOrders;
    private String paymentMethod;
}
