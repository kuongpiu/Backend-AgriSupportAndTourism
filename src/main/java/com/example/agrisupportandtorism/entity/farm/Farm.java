package com.example.agrisupportandtorism.entity.farm;

import com.example.agrisupportandtorism.dto.FarmDTO;
import com.example.agrisupportandtorism.entity.user.User;
import com.example.agrisupportandtorism.utils.DescUntil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "username"
        ,updatable = false)
    private User owner;

    @OneToMany(mappedBy = "farm")
    private List<FarmTree> farmTrees;

    public static Farm fromDTO(FarmDTO farmDTO){
        Farm farm = new Farm();

        farm.setName(farmDTO.getName());
        farm.setAddress(farmDTO.getAddress());
        farm.setDescription(DescUntil.convertMapToString(farmDTO.getDescriptions()));

        return farm;
    }
}
