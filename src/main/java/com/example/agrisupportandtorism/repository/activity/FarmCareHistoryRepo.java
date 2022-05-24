package com.example.agrisupportandtorism.repository.activity;

import com.example.agrisupportandtorism.entity.activity.FarmCareHistory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FarmCareHistoryRepo extends JpaRepository<FarmCareHistory, Integer> {
    List<FarmCareHistory> findByFarmId(Integer id);
    List<FarmCareHistory> findByFarmId(Integer id, Sort sort);
    List<FarmCareHistory> findByFarmIdAndCareDateTimeBetween(Integer farmId, LocalDateTime start, LocalDateTime end, Sort sort);
}
