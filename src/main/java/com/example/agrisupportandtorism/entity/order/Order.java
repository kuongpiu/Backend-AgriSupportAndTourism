package com.example.agrisupportandtorism.entity.order;

import com.example.agrisupportandtorism.dto.product.ShortProductDTO;
import com.example.agrisupportandtorism.entity.address.Address;
import com.example.agrisupportandtorism.entity.user.User;
import com.example.agrisupportandtorism.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Table(name = "orders")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number")
    private Integer orderNumber;

    @ManyToOne
    @JoinColumn(name = "customer", referencedColumnName = "username", updatable = false)
    @JsonIgnore
    private User customer;

    @Column(name = "order_date")
    private LocalDateTime orderDateTime;

    @Transient
    private String orderDate, orderTime;

    @Column(name = "status")
    @Size(min = 0, max = 20)
    private String status;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(name = "farm_id", updatable = false)
    private Integer farmId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_id")
    private String paymentId;

    public void loadDateTime() {
        if (Objects.isNull(this.orderDateTime)) {
            return;
        }
        this.orderDate = this.orderDateTime.format(DateTimeUtils.DATE_FORMATTER);
        this.orderTime = this.orderDateTime.format(DateTimeUtils.TIME_FORMATTER);
    }
}
