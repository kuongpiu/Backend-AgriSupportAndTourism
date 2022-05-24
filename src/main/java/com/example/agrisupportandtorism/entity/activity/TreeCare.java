package com.example.agrisupportandtorism.entity.activity;

import com.example.agrisupportandtorism.entity.farm.Tree;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tree_care")
@Setter
@Getter
@NoArgsConstructor

public class TreeCare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "farm_care_id"
            , referencedColumnName = "id"
            , updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private FarmCareHistory farmCareHistory;

    @ManyToOne
    @JoinColumn(name = "tree_id"
            , referencedColumnName = "id"
            , updatable = false)
    private Tree tree;

    @Column(name = "status")
    private String status;

    @Override
    public String toString() {
        String farmCareId = farmCareHistory != null ? String.valueOf(farmCareHistory.getId()) : "null";
        String treeId = tree != null ? String.valueOf(tree.getId()) : "null";
        return "TreeCare{" +
                "id=" + id +
                ", farmCareId=" +  farmCareId+
                ", treeId=" + treeId +
                ", status='" + status + '\'' +
                '}';
    }
}
