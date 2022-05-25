package com.example.agrisupportandtorism.repository.product;

import com.example.agrisupportandtorism.entity.product.CartItem;
import com.example.agrisupportandtorism.entity.product.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<CartItem, CartItemId> {
    List<CartItem> findAllByUsername(String username);
}
