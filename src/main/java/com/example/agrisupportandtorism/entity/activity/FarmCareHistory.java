package com.example.agrisupportandtorism.entity.activity;

import com.example.agrisupportandtorism.dto.farm.FarmCareHistoryDTO;
import com.example.agrisupportandtorism.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "farm_care_history")
@Setter
@Getter

public class FarmCareHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "farm_id")
    private Integer farmId;

    @ManyToOne
    @JoinColumn(name = "activity_id"
            , referencedColumnName = "id"
            , updatable = false)
    private Activity activity;

    @OneToMany(mappedBy = "farmCareHistory")
    private List<TreeCare> treeCares;

    @Column(name = "care_date_time")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime careDateTime;

    @Column(name = "updated_date_time")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime updatedDateTime;

    @Column(name = "description")
    private String description;

    public static FarmCareHistory fromDTO(FarmCareHistoryDTO farmCareHistoryDTO){
        FarmCareHistory farmCareHistory = new FarmCareHistory();

        farmCareHistory.setId(farmCareHistoryDTO.getId());
        farmCareHistory.setFarmId(farmCareHistoryDTO.getFarmId());
        farmCareHistory.setActivity(farmCareHistoryDTO.getActivity());
        farmCareHistory.setTreeCares(farmCareHistoryDTO.getTreeCares());
        farmCareHistory.setDescription(farmCareHistoryDTO.getDescription());

        farmCareHistory.setCareDateTime(
                LocalDateTime.of(LocalDate.parse(farmCareHistoryDTO.getCareDate(), DateTimeUtils.DATE_FORMATTER)
                        , LocalTime.parse(farmCareHistoryDTO.getCareTime(), DateTimeUtils.TIME_FORMATTER)));

        return farmCareHistory;
    }
}
