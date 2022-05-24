package com.example.agrisupportandtorism.entity.activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "visitor_history")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(VisitorHistoryId.class)

public class VisitorHistory implements Serializable {
    @Id
    @Column(name = "farm_care_id")
    private Integer farmCareId;

    @Id
    @Column(name = "visitor_name")
    private String visitorName;
}
