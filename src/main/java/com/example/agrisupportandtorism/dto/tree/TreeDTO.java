package com.example.agrisupportandtorism.dto.tree;

import com.example.agrisupportandtorism.entity.farm.Tree;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TreeDTO {
    private Integer id;

    private String name;

    private TreeDTO(){}

    public static TreeDTO fromEntity(Tree tree){
        TreeDTO treeDTO = new TreeDTO();
        treeDTO.setId(tree.getId());
        treeDTO.setName(tree.getName());
        return treeDTO;
    }
}
