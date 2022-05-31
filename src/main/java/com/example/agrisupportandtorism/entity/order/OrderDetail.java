package com.example.agrisupportandtorism.entity.order;

import com.example.agrisupportandtorism.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orderdetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderDetail {
    @EmbeddedId
    private OrderDetailId orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_number", referencedColumnName = "order_number", updatable = false, insertable = false)
    @JsonIgnore
    private Order order;

    @Column(name = "price")
    private Integer price;

    @Column(name = "quantity")
    private Integer quantity;
}
