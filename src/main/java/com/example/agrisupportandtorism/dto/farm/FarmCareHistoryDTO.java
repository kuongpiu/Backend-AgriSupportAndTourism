package com.example.agrisupportandtorism.dto.farm;

import com.example.agrisupportandtorism.entity.activity.Activity;
import com.example.agrisupportandtorism.entity.activity.FarmCareHistory;
import com.example.agrisupportandtorism.entity.activity.TreeCare;
import com.example.agrisupportandtorism.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class FarmCareHistoryDTO {
    private Integer id;
    private Integer farmId;
    private Activity activity;
    private List<TreeCare> treeCares;
    private String description;
    private String careDate;
    private String careTime;

    public static FarmCareHistoryDTO fromEntity(FarmCareHistory farmCareHistory) {
        FarmCareHistoryDTO farmCareHistoryDTO = new FarmCareHistoryDTO();

        farmCareHistoryDTO.setId(farmCareHistory.getId());
        farmCareHistoryDTO.setFarmId(farmCareHistory.getFarmId());
        farmCareHistoryDTO.setActivity(farmCareHistory.getActivity());
        farmCareHistoryDTO.setTreeCares(farmCareHistory.getTreeCares());
        farmCareHistoryDTO.setDescription(farmCareHistory.getDescription());

        LocalDateTime careDateTime = farmCareHistory.getCareDateTime();
        farmCareHistoryDTO.setCareDate(careDateTime.format(DateTimeUtils.DATE_FORMATTER));
        farmCareHistoryDTO.setCareTime(careDateTime.format(DateTimeUtils.TIME_FORMATTER));

        return farmCareHistoryDTO;
    }
}
