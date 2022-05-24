package com.example.agrisupportandtorism.dto.tree;

import com.example.agrisupportandtorism.dto.tree.FarmTreeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ShortTrees {
    @NotNull(message = "Mã vườn là thông tin bắt buộc")
    private Integer farmId;

    private List<FarmTreeDTO> trees;
}
