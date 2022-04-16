package com.example.agrisupportandtorism.repository.farm;

import com.example.agrisupportandtorism.entity.farm.FarmTree;
import com.example.agrisupportandtorism.entity.farm.FarmTreeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmTreeRepo extends JpaRepository<FarmTree, FarmTreeId> {
    Optional<FarmTree> findByFarmIdAndTreeId(Integer farmId, Integer treeId);
    List<FarmTree> findByFarmId(Integer farmId);
}
