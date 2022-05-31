package com.example.agrisupportandtorism.repository.order;

import com.example.agrisupportandtorism.entity.order.Order;
import com.example.agrisupportandtorism.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
    List<Order> getAllByCustomer(User customer);
    List<Order> getAllByFarmId(Integer farmId);
}
