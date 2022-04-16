package com.example.agrisupportandtorism.dto;

import com.example.agrisupportandtorism.entity.farm.Farm;
import com.example.agrisupportandtorism.utils.DescUntil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class FarmDTO {
    private Integer id;

    @NotBlank(message = "Tên vườn không được trống")
    @Size(min = 1, max = 200, message = "Độ dài tên vườn phải lớn hơn 1 và nhỏ hơn 200")
    private String name;

    @NotBlank(message = "Địa chỉ không được trống")
    private String address;

    private Map<String, String> descriptions;

    private List<FarmTreeDTO> trees;

    private FarmDTO(){}

    public static FarmDTO fromEntity(Farm farm){
        FarmDTO farmDTO = new FarmDTO();

        farmDTO.setId(farm.getId());
        farmDTO.setName(farm.getName());
        farmDTO.setAddress(farm.getAddress());

        farmDTO.setDescriptions(DescUntil.convertStringToMap(farm.getDescription()));

        List<FarmTreeDTO> trees;

        if(farm.getFarmTrees() != null){
            trees = farm.getFarmTrees().stream().map(FarmTreeDTO::fromEntity).collect(Collectors.toList());
        }else{
            trees = new ArrayList<>();
        }

        farmDTO.setTrees(trees);

        return farmDTO;
    }
}
