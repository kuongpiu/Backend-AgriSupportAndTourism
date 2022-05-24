package com.example.agrisupportandtorism.service.farm;

import com.example.agrisupportandtorism.dto.tree.FarmTreeDTO;
import com.example.agrisupportandtorism.entity.farm.FarmTree;
import com.example.agrisupportandtorism.entity.farm.FarmTreeId;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.farm.FarmTreeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmTreeService {
    @Autowired
    private FarmTreeRepo farmTreeRepo;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<FarmTreeDTO> findAll(Integer farmId) {
        return farmTreeRepo.findByFarmId(farmId).stream().map(FarmTreeDTO::fromEntity).collect(Collectors.toList());
    }

    public FarmTree findById(FarmTreeId id) {
        return findById(id.getFarmId(), id.getTreeId());
    }

    public FarmTree findById(Integer farmId, Integer treeId) {
        if (farmId == null || treeId == null) {
            throw new ResourceNotFoundException(String.format("Cây với mã [%s] không tồn tại trong vườn với mã [%s]", treeId, farmId));
        }

        Optional<FarmTree> farmTreeOpt = farmTreeRepo.findByFarmIdAndTreeId(farmId, treeId);

        if (farmTreeOpt.isPresent()) {
            return farmTreeOpt.get();
        } else {
            throw new ResourceNotFoundException(String.format("Cây với mã [%s] không tồn tại trong vườn với mã [%s]", treeId, farmId));
        }
    }

    @Transactional
    public void update(FarmTreeDTO farmTreeDTO) {
        Integer quantityOfTrees = farmTreeDTO.getQuantity();

        try {
            FarmTree farmTreeInRepo = findById(farmTreeDTO.getFarmId(), farmTreeDTO.getTreeId());
            farmTreeInRepo.setQuantity(quantityOfTrees);
            farmTreeRepo.save(farmTreeInRepo);

            logger.info(String.format("Update quantity of trees in farm, farmTreeID=[%s]", farmTreeInRepo.getId()));
        } catch (ResourceNotFoundException e) {
            insert(farmTreeDTO);
        }
    }

    @Transactional
    public void insert(FarmTreeDTO farmTreeDTO) {
        FarmTree farmTree = FarmTree.fromDTO(farmTreeDTO);
        FarmTree result = farmTreeRepo.save(farmTree);
        logger.info(String.format("Add tree into farm, farmTreeID=[%s]", result.getId()));
    }

    public void delete(FarmTreeId id) {
        delete(id.getFarmId(), id.getTreeId());
    }

    @Transactional
    public void delete(Integer farmId, Integer treeId) {
        FarmTree farmTree = findById(farmId, treeId);
        farmTreeRepo.delete(farmTree);

        logger.info(String.format("Delete tree in farm, farmId=[%s], treeId=[%s]", farmId, treeId));
    }
}
