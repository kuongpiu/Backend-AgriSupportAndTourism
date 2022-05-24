package com.example.agrisupportandtorism.dto.tree;

import com.example.agrisupportandtorism.entity.farm.FarmTree;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FarmTreeDTO {
    private Integer farmId;
    private Integer treeId;

    private String treeName;
    private Integer quantity;

    private FarmTreeDTO(){}

    public static FarmTreeDTO fromEntity(FarmTree farmTree){
        FarmTreeDTO farmTreeDTO = new FarmTreeDTO();

        farmTreeDTO.setFarmId(farmTree.getId().getFarmId());
        farmTreeDTO.setTreeId(farmTree.getId().getTreeId());
        farmTreeDTO.setQuantity(farmTree.getQuantity());

        if(farmTree.getTree() != null){
            farmTreeDTO.setTreeName(farmTree.getTree().getName());
        }else{
            farmTreeDTO.setTreeName("");
        }

        return farmTreeDTO;
    }
}
