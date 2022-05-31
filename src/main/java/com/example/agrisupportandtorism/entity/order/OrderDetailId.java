package com.example.agrisupportandtorism.entity.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@NoArgsConstructor

public class OrderDetailId implements Serializable {
    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "product_id")
    private Integer productId;
}
