package com.example.agrisupportandtorism.dto.product;

import com.example.agrisupportandtorism.entity.product.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor

public class CartItemDTO {
    private ShortProductDTO shortProductDTO;
    private Integer quantity;

    public static CartItemDTO fromEntity(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setShortProductDTO(ShortProductDTO.fromEntity(cartItem.getProduct()));

        cartItemDTO.setQuantity(cartItem.getQuantity());

        return cartItemDTO;
    }
}
