package com.example.agrisupportandtorism.repository.order;

import com.example.agrisupportandtorism.entity.order.Order;
import com.example.agrisupportandtorism.entity.order.OrderDetail;
import com.example.agrisupportandtorism.entity.order.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> getAllByOrder(Order order);
}
