package com.example.agrisupportandtorism.repository.activity;

import com.example.agrisupportandtorism.entity.activity.VisitorHistory;
import com.example.agrisupportandtorism.entity.activity.VisitorHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorHistoryRepo extends JpaRepository<VisitorHistory, VisitorHistoryId> {
    List<VisitorHistory> findByFarmCareId(Integer farmCareId);
}
