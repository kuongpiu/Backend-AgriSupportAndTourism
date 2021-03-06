package com.example.agrisupportandtorism.repository.product;

import com.example.agrisupportandtorism.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> getAllByFarmId(Integer farmId);
    Page<Product> getAllByFarmId(Integer farmId, Pageable pageable);
}
