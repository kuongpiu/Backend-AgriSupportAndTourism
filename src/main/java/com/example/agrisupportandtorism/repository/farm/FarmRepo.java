package com.example.agrisupportandtorism.repository.farm;

import com.example.agrisupportandtorism.entity.farm.Farm;
import com.example.agrisupportandtorism.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmRepo extends JpaRepository<Farm, Integer> {
    List<Farm> findByOwner(User owner);
}
