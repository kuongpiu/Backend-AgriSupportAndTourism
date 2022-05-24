package com.example.agrisupportandtorism.service.activity;

import com.example.agrisupportandtorism.dto.UserDTO;
import com.example.agrisupportandtorism.entity.activity.VisitorHistory;
import com.example.agrisupportandtorism.entity.activity.VisitorHistoryId;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.activity.VisitorHistoryRepo;
import com.example.agrisupportandtorism.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class VisitorHistoryService {
    @Autowired
    private VisitorHistoryRepo visitorHistoryRepo;

    @Autowired
    private UserService userService;

    public List<VisitorHistory> getAll(){
        return visitorHistoryRepo.findAll();
    }

    @Transactional
    public void updateVisitors(List<UserDTO> visitors, Integer farmCareId) {
        Map<String, UserDTO> updatedVisitors = visitors.stream().collect(Collectors.toMap(UserDTO::getUsername, Function.identity()));

        List<UserDTO> visitorsInRepo = getVisitorsByCareId(farmCareId);
        Set<UserDTO> visitorsInRepoSet = new HashSet<>(visitorsInRepo);

        for (UserDTO visitor : visitorsInRepo) {
            String username = visitor.getUsername();
            if (updatedVisitors.containsKey(username)) {
                updatedVisitors.remove(username);
                visitorsInRepoSet.remove(visitor);
            }
        }

        for (UserDTO visitor : visitorsInRepoSet) {
            delete(new VisitorHistoryId(farmCareId, visitor.getUsername()));
        }

        for (UserDTO visitor : updatedVisitors.values()) {
            insert(new VisitorHistory(farmCareId, visitor.getUsername()));
        }
    }

    public List<UserDTO> getVisitorsByCareId(Integer careId){
        List<VisitorHistory> visitorHistories = visitorHistoryRepo.findByFarmCareId(careId);

        List<UserDTO> visitors = new ArrayList<>();

        for(VisitorHistory visitorHistory: visitorHistories){
            String visitorUsername = visitorHistory.getVisitorName();

            UserDTO visitor = userService.findUserByUsername(visitorUsername);

            visitors.add(visitor);
        }

        return visitors;
    }

    public VisitorHistory findById(VisitorHistoryId id){
        Optional<VisitorHistory> visitorHistoryOpt = visitorHistoryRepo.findById(id);

        if(visitorHistoryOpt.isPresent()){
            return visitorHistoryOpt.get();
        }else{
            throw new ResourceNotFoundException(String.format("Lịch sử du khách tham gia hoạt động không tồn tại, mã du khách=[%s], mã hoạt động=[%s]", id.getVisitorName(), id.getFarmCareId()));
        }
    }

    @Transactional
    public VisitorHistory insert(VisitorHistory visitorHistory){
        return visitorHistoryRepo.saveAndFlush(visitorHistory);
    }

    @Transactional
    public void delete(VisitorHistoryId id){
        visitorHistoryRepo.deleteById(id);
    }
}
