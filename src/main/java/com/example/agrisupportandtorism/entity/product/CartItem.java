package com.example.agrisupportandtorism.entity.product;

import com.example.agrisupportandtorism.dto.product.CartItemDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Table(name = "cart")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CartItem {
    @EmbeddedId
    private CartItemId cartItemId;

    @Column(name = "username", updatable = false, insertable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;
}