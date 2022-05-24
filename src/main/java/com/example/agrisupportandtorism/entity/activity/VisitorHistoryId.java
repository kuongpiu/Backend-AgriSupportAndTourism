package com.example.agrisupportandtorism.entity.activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class VisitorHistoryId implements Serializable {
    private Integer farmCareId;
    private String visitorName;
}
