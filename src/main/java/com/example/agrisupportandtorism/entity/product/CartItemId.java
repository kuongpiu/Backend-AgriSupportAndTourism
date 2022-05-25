package com.example.agrisupportandtorism.entity.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor

public class CartItemId implements Serializable {
    @Column(name = "username")
    private String username;

    @Column(name = "product_id")
    private Integer productId;
}
