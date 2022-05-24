package com.example.agrisupportandtorism.repository.activity;

import com.example.agrisupportandtorism.entity.activity.FarmCareHistory;
import com.example.agrisupportandtorism.entity.activity.TreeCare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeCareRepo extends JpaRepository<TreeCare, Integer> {
    List<TreeCare> findByFarmCareHistory(FarmCareHistory farmCareHistory);
}
