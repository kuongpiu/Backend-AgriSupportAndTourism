package com.example.agrisupportandtorism.entity.farm;

import com.example.agrisupportandtorism.dto.farm.FarmDTO;
import com.example.agrisupportandtorism.entity.address.District;
import com.example.agrisupportandtorism.entity.address.Province;
import com.example.agrisupportandtorism.entity.address.Ward;
import com.example.agrisupportandtorism.entity.user.User;
import com.example.agrisupportandtorism.utils.DescUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "farm")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "username"
        ,updatable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "mentor", referencedColumnName = "username")
    private User mentor;

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "ward_id", referencedColumnName = "id")
    private Ward ward;

    @Column(name = "detail_address")
    private String detailAddress;

    @OneToMany(mappedBy = "farm")
    private List<FarmTree> farmTrees;

    public static Farm fromDTO(FarmDTO farmDTO){
        Farm farm = new Farm();

        farm.setName(farmDTO.getName());
        farm.setDescription(DescUtils.convertMapToString(farmDTO.getDescriptions()));

        farm.setProvince(farmDTO.getProvince());
        farm.setDistrict(farmDTO.getDistrict());
        farm.setWard(farmDTO.getWard());
        farm.setDetailAddress(farmDTO.getDetailAddress());

        return farm;
    }
}
