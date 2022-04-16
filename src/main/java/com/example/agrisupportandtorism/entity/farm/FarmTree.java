package com.example.agrisupportandtorism.entity.farm;

import com.example.agrisupportandtorism.dto.FarmTreeDTO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Table(name = "farm_tree")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class FarmTree {
    @EmbeddedId
    @NotNull
    private FarmTreeId id;

    @Column(name = "quantity")
    @PositiveOrZero(message = "Số lượng cây không thể là số Âm")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "farm_id", referencedColumnName = "id"
            , updatable = false, insertable = false)
    private Farm farm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tree_id", referencedColumnName = "id"
            , updatable = false, insertable = false)
    private Tree tree;

    public static FarmTree fromDTO(FarmTreeDTO farmTreeDTO){
        FarmTree farmTree = new FarmTree();

        farmTree.setId(new FarmTreeId(farmTreeDTO.getFarmId(), farmTreeDTO.getTreeId()));
        farmTree.setQuantity(farmTreeDTO.getQuantity());

        return farmTree;
    }
}
