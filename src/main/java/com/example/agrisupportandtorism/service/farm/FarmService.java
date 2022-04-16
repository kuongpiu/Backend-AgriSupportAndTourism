package com.example.agrisupportandtorism.service.farm;

import com.example.agrisupportandtorism.dto.FarmDTO;
import com.example.agrisupportandtorism.dto.FarmTreeDTO;
import com.example.agrisupportandtorism.dto.ShortTrees;
import com.example.agrisupportandtorism.entity.farm.Farm;
import com.example.agrisupportandtorism.entity.farm.Tree;
import com.example.agrisupportandtorism.entity.user.User;
import com.example.agrisupportandtorism.exception.PermissionException;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.farm.FarmRepo;
import com.example.agrisupportandtorism.service.user.UserService;
import com.example.agrisupportandtorism.utils.DescUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FarmService {
    @Autowired
    private FarmRepo farmRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private FarmTreeService farmTreeService;

    @Autowired
    private TreeService treeService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<FarmDTO> findAllFarms() {
        User currentUser = userService.getCurrentUser();
        List<Farm> farms = farmRepo.findByOwner(currentUser);
        return farms.stream().map(FarmDTO::fromEntity).collect(Collectors.toList());
    }

    public FarmDTO findById(Integer id) {
        Optional<Farm> farmOpt = farmRepo.findById(id);
        if (farmOpt.isPresent()) {
            FarmDTO farmDTO = FarmDTO.fromEntity(farmOpt.get());
            List<FarmTreeDTO> trees = farmDTO.getTrees();

            for(FarmTreeDTO tree: trees){
                if(tree.getTreeName() == null || tree.getTreeName().trim().equals("")){
                    logger.info(String.format("Make a request to get tree, treeId=[%s]", tree.getTreeId()));
                    Tree treeInRepo = treeService.findById(tree.getTreeId());
                    tree.setTreeName(treeInRepo.getName());
                }
            }

            return farmDTO;
        } else {
            throw new ResourceNotFoundException(String.format("Vườn không tồn tại, mã vườn= [%s]", id));
        }
    }

    @Transactional
    public FarmDTO insertFarm(FarmDTO farmDTO) {
        Farm farm = Farm.fromDTO(farmDTO);
        farm.setOwner(userService.getCurrentUser());

        Farm result = farmRepo.save(farm);

        logger.info(String.format("Add farm, id=[%s]", result.getId()));

        return FarmDTO.fromEntity(result);
    }

    @Transactional
    public FarmDTO updateFarm(FarmDTO farmDTO){
        Farm farmInRepo = findFarmById(farmDTO.getId());

        User currentUser = userService.getCurrentUser();
        User farmOwner = farmInRepo.getOwner();

        if(farmOwner.equals(currentUser)){
            //start update all changeable properties
            farmInRepo.setName(farmDTO.getName());
            farmInRepo.setAddress(farmDTO.getAddress());
            farmInRepo.setDescription(DescUntil.convertMapToString(farmDTO.getDescriptions()));

            Farm updatedFarm = farmRepo.save(farmInRepo);

            logger.info(String.format("Update farm, id=[%s]", updatedFarm.getId()));

            return FarmDTO.fromEntity(updatedFarm);
        }else{
            // throw permission exception. current user is not the farm's owner.
            String farmOwnerUsername = farmOwner.getUsername();
            String currentUsername = currentUser.getUsername();
            throw new PermissionException(String.format("[%s] không có quyền sửa, quyền thuộc về [%s]", currentUsername, farmOwnerUsername));
        }
    }

    @Transactional
    public boolean deleteFarm(Integer id){
        Farm farm = findFarmById(id);

        User currentUser = userService.getCurrentUser();
        User farmOwner = farm.getOwner();

        if(farmOwner.equals(currentUser)){
            logger.info(String.format("Delete farm, id=[%s]", farm.getId()));

            farmRepo.delete(farm);
            return true;
        }else{
            // throw permission exception. current user is not the farm's owner.
            String farmOwnerUsername = farmOwner.getUsername();
            String currentUsername = currentUser.getUsername();
            throw new PermissionException(String.format("[%s] không có quyền xóa, quyền thuộc về [%s]", currentUsername, farmOwnerUsername));
        }
    }

    private Farm findFarmById(Integer id){
        Optional<Farm> farmOpt = farmRepo.findById(id);
        if (farmOpt.isPresent()) {
            return farmOpt.get();
        } else {
            throw new ResourceNotFoundException(String.format("Vườn không tồn tại, mã vườn= [%s]", id));
        }
    }

    public FarmDTO updateFarmTrees(ShortTrees shortTrees){
        if(update(shortTrees)){
            return findById(shortTrees.getFarmId());
        }else{
            throw new RuntimeException("Update Failed !");
        }
    }

    @Transactional
    public boolean update(ShortTrees shortTrees){
        Integer farmId = shortTrees.getFarmId();

        List<FarmTreeDTO> treesInRepo = farmTreeService.findAll(farmId);

        HashMap<Integer, FarmTreeDTO> neededDeleteTrees = new HashMap<>();

        for(FarmTreeDTO tree: treesInRepo){
            neededDeleteTrees.put(tree.getTreeId(), tree);
        }

        for(FarmTreeDTO tree: shortTrees.getTrees()){
            neededDeleteTrees.remove(tree.getTreeId());
        }

        neededDeleteTrees.values().forEach(
                farmTreeDTO -> {
                    farmTreeService.delete(farmTreeDTO.getFarmId(), farmTreeDTO.getTreeId());
                });

        shortTrees.getTrees().forEach(farmTreeDTO -> {
            farmTreeDTO.setFarmId(farmId);
            farmTreeService.update(farmTreeDTO);
        });
        logger.info(String.format("Update trees in farm, farmId=[%s]", farmId));
        return true;
    }
}
