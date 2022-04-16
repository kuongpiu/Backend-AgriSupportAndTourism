package com.example.agrisupportandtorism.repository.address;

import com.example.agrisupportandtorism.entity.address.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepo extends JpaRepository<District, String> {
    List<District> findByProvinceIdAndNameContaining(String provinceId, String name);
}
