package com.example.agrisupportandtorism.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ShortTrees {
    @NotNull(message = "Mã vườn không được trống")
    private Integer farmId;

    private List<FarmTreeDTO> trees;
}
