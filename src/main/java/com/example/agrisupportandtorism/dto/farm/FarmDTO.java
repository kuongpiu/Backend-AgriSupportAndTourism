package com.example.agrisupportandtorism.dto.farm;

import com.example.agrisupportandtorism.dto.UserDTO;
import com.example.agrisupportandtorism.dto.tree.FarmTreeDTO;
import com.example.agrisupportandtorism.entity.address.District;
import com.example.agrisupportandtorism.entity.address.Province;
import com.example.agrisupportandtorism.entity.address.Ward;
import com.example.agrisupportandtorism.entity.farm.Farm;
import com.example.agrisupportandtorism.utils.DescUtils;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    private Map<String, String> descriptions;

    @NotNull(message = "Tỉnh là thông tin bắt buộc")
    private Province province;

    @NotNull(message = "Huyện là thông tin bắt buộc")
    private District district;

    @NotNull(message = "Xã là thông tin bắt buộc")
    private Ward ward;

    private String detailAddress;

    private List<FarmTreeDTO> trees;

    private UserDTO mentor;

    private FarmDTO(){}

    public static FarmDTO fromEntity(Farm farm){
        FarmDTO farmDTO = new FarmDTO();

        farmDTO.setId(farm.getId());
        farmDTO.setName(farm.getName());

        farmDTO.setDescriptions(DescUtils.convertStringToMap(farm.getDescription()));

        farmDTO.setProvince(farm.getProvince());
        farmDTO.setDistrict(farm.getDistrict());
        farmDTO.setWard(farm.getWard());
        farmDTO.setDetailAddress(farm.getDetailAddress());

        List<FarmTreeDTO> trees;

        if(farm.getFarmTrees() != null){
            trees = farm.getFarmTrees().stream().map(FarmTreeDTO::fromEntity).collect(Collectors.toList());
        }else{
            trees = new ArrayList<>();
        }

        farmDTO.setTrees(trees);

        if(farm.getMentor() != null) {
            farmDTO.setMentor(UserDTO.fromUser(farm.getMentor()));
        }

        return farmDTO;
    }
}
