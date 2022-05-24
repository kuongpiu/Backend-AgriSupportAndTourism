package com.example.agrisupportandtorism.dto.farm;

import com.example.agrisupportandtorism.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class FarmCareForm {
    private FarmCareHistoryDTO farmCareHistoryDTO;
    private List<UserDTO> visitors;

    @Override
    public String toString() {
        return "FarmCareForm{" +
                "farmCareHistoryDTO=" + farmCareHistoryDTO +
                ", visitors=" + visitors +
                '}';
    }
}
