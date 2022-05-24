package com.example.agrisupportandtorism.service.activity;

import com.example.agrisupportandtorism.entity.activity.FarmCareHistory;
import com.example.agrisupportandtorism.entity.activity.TreeCare;
import com.example.agrisupportandtorism.entity.farm.Tree;
import com.example.agrisupportandtorism.repository.activity.TreeCareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TreeCareService {
    @Autowired
    private TreeCareRepo treeCareRepo;

    @Transactional
    public List<TreeCare> insertList(List<TreeCare> treeCares, FarmCareHistory farmCareHistory) {
        List<TreeCare> result = new ArrayList<>();
        for (TreeCare treeCare : treeCares) {
            treeCare.setFarmCareHistory(farmCareHistory);
            result.add(insert(treeCare));
        }
        return result;
    }

    public List<TreeCare> getTreeCaresByFarmCare(FarmCareHistory farmCareHistory) {
        return treeCareRepo.findByFarmCareHistory(farmCareHistory);
    }

    public void updateTreeCares(List<TreeCare> treeCares, FarmCareHistory farmCareHistory) {
        for (TreeCare treeCare : treeCares) {
            Integer treeCareId = treeCare.getId();
            if (treeCareId == null) {
                treeCare.setFarmCareHistory(farmCareHistory);
                insert(treeCare);
            }
        }

        Map<Integer, TreeCare> updatedTreeCares = treeCares.stream().collect(Collectors.toMap(TreeCare::getId, Function.identity()));

        List<TreeCare> treeCaresInRepo = getTreeCaresByFarmCare(farmCareHistory);
        Set<TreeCare> treeCaresSet = new HashSet<>(treeCaresInRepo);

        for (TreeCare treeCare : treeCaresInRepo) {
            Integer treeCareId = treeCare.getId();
            if (updatedTreeCares.containsKey(treeCareId)) {
                treeCaresSet.remove(treeCare);
            }
        }

        for (TreeCare treeCare : treeCaresSet) {
            deleteById(treeCare.getId());
        }

        for (TreeCare treeCare : updatedTreeCares.values()) {
            if (treeCare.getFarmCareHistory() == null) {
                System.out.println(treeCare);
                treeCare.setFarmCareHistory(farmCareHistory);
            }

            treeCareRepo.saveAndFlush(treeCare);
        }
    }

    @Transactional
    public TreeCare insert(TreeCare treeCare) {
        return treeCareRepo.saveAndFlush(treeCare);
    }

    @Transactional
    public void deleteById(Integer treeCareId) {
        treeCareRepo.deleteById(treeCareId);
    }
}
