package com.example.agrisupportandtorism.repository.address;

import com.example.agrisupportandtorism.entity.address.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepo extends JpaRepository<Province, String> {
    List<Province> findByNameContaining(String name);
}
