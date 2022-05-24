package com.example.agrisupportandtorism.service.activity;

import com.example.agrisupportandtorism.dto.farm.FarmCareForm;
import com.example.agrisupportandtorism.dto.farm.FarmCareHistoryDTO;
import com.example.agrisupportandtorism.dto.UserDTO;
import com.example.agrisupportandtorism.entity.activity.FarmCareHistory;
import com.example.agrisupportandtorism.entity.activity.TreeCare;
import com.example.agrisupportandtorism.entity.activity.VisitorHistory;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.activity.FarmCareHistoryRepo;
import com.example.agrisupportandtorism.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FarmCareHistoryService {
    @Autowired
    private FarmCareHistoryRepo farmCareHistoryRepo;

    @Autowired
    private VisitorHistoryService visitorHistoryService;

    @Autowired
    private TreeCareService treeCareService;

    public List<FarmCareForm> findAllByFarmId(Integer farmId) {
        List<FarmCareHistory> allFarmCareHistories = farmCareHistoryRepo.findByFarmId(farmId, Sort.by(Sort.Direction.DESC, "careDateTime"));

        List<FarmCareForm> farmCareForms = new ArrayList<>();
        for (FarmCareHistory farmCareHistory : allFarmCareHistories) {
            farmCareForms.add(fullFillFarmCareForm(farmCareHistory));
        }

        return farmCareForms;
    }

    public FarmCareForm findDTOById(Integer id) {
        return fullFillFarmCareForm(findById(id));
    }

    private FarmCareForm fullFillFarmCareForm(FarmCareHistory farmCareHistory) {
        Integer farmCareId = farmCareHistory.getId();

        List<UserDTO> visitors = visitorHistoryService.getVisitorsByCareId(farmCareId);

        return new FarmCareForm(FarmCareHistoryDTO.fromEntity(farmCareHistory), visitors);
    }

    public List<FarmCareForm> searchByDate(Integer farmId, String dateString) {
        LocalDate date = LocalDate.now();
        if (!dateString.isEmpty()) {
            date = LocalDate.parse(dateString, DateTimeUtils.DATE_FORMATTER);
        }

        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

        List<FarmCareHistory> farmCareHistories = farmCareHistoryRepo.findByFarmIdAndCareDateTimeBetween(farmId, start, end, Sort.by(Sort.Direction.DESC, "careDateTime"));

        List<FarmCareForm> result = new ArrayList<>();
        for (FarmCareHistory farmCareHistory : farmCareHistories) {
            result.add(fullFillFarmCareForm(farmCareHistory));
        }

        return result;
    }

    public FarmCareHistory findById(Integer id) {
        Optional<FarmCareHistory> farmCareHistoryOpt = farmCareHistoryRepo.findById(id);

        if (farmCareHistoryOpt.isPresent()) {
            return farmCareHistoryOpt.get();
        } else {
            throw new ResourceNotFoundException(String.format("Hoạt động chăm sóc không tồn tại, mã=[%s]", id));
        }
    }

    @Transactional
    public FarmCareForm insert(FarmCareForm farmCareForm) {
        List<TreeCare> treeCares = farmCareForm.getFarmCareHistoryDTO().getTreeCares();

        FarmCareHistory farmCareHistoryResult = insertFarmCareHistory(FarmCareHistory.fromDTO(farmCareForm.getFarmCareHistoryDTO()));

        Integer farmCareId = farmCareHistoryResult.getId();

        farmCareHistoryResult.setTreeCares(treeCareService.insertList(treeCares, farmCareHistoryResult));

        // insert visitor who joined
        List<String> visitorsUsername = farmCareForm.getVisitors().stream().map(UserDTO::getUsername).collect(Collectors.toList());
        for (String visitorUsername : visitorsUsername) {
            VisitorHistory visitorHistory = new VisitorHistory(farmCareId, visitorUsername);
            visitorHistoryService.insert(visitorHistory);
        }

        return new FarmCareForm(FarmCareHistoryDTO.fromEntity(farmCareHistoryResult), visitorHistoryService.getVisitorsByCareId(farmCareId));
    }

    @Transactional
    public FarmCareForm update(FarmCareForm farmCareForm) {
        FarmCareHistory updatedFarmCareHistory = FarmCareHistory.fromDTO(farmCareForm.getFarmCareHistoryDTO());

        visitorHistoryService.updateVisitors(farmCareForm.getVisitors(), updatedFarmCareHistory.getId());

        updatedFarmCareHistory.setUpdatedDateTime(LocalDateTime.now());

        FarmCareHistory farmCareHistory = farmCareHistoryRepo.save(updatedFarmCareHistory);

        treeCareService.updateTreeCares(updatedFarmCareHistory.getTreeCares(), farmCareHistory);

        return fullFillFarmCareForm(farmCareHistory);
    }

    @Transactional
    public FarmCareHistory insertFarmCareHistory(FarmCareHistory farmCareHistory) {
        farmCareHistory.setUpdatedDateTime(LocalDateTime.now());
        return farmCareHistoryRepo.saveAndFlush(farmCareHistory);
    }

    @Transactional
    public void deleteFarmCareById(Integer farmCareId) {
        farmCareHistoryRepo.deleteById(farmCareId);
    }
}
