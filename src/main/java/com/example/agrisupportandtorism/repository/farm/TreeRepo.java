package com.example.agrisupportandtorism.repository.farm;

import com.example.agrisupportandtorism.entity.farm.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeRepo extends JpaRepository<Tree, Integer> {
    List<Tree> findByNameContaining(String name);
}
