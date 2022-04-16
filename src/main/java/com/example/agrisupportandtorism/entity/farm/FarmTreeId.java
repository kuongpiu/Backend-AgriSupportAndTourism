package com.example.agrisupportandtorism.entity.farm;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FarmTreeId implements Serializable {
    @Column(name = "farm_id")
    private Integer farmId;

    @Column(name = "tree_id")
    private Integer treeId;

    @Override
    public String toString() {
        return "FarmTreeId{" +
                "farmId=" + farmId +
                ", treeId=" + treeId +
                '}';
    }
}
