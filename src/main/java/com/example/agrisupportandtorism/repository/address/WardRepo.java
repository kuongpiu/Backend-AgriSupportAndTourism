package com.example.agrisupportandtorism.repository.address;

import com.example.agrisupportandtorism.entity.address.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepo extends JpaRepository<Ward, String> {
    List<Ward> findByDistrictIdAndNameContaining(String districtId, String name);
}
