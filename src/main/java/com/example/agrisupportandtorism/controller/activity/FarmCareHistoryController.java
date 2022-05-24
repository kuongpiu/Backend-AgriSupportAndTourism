package com.example.agrisupportandtorism.controller.activity;

import com.example.agrisupportandtorism.dto.farm.FarmCareForm;
import com.example.agrisupportandtorism.service.activity.FarmCareHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:9528/")
@RequestMapping(value = "/farm-care-history")

public class FarmCareHistoryController {
    @Autowired
    private FarmCareHistoryService farmCareHistoryService;

    @GetMapping("/all")
    public List<FarmCareForm> findAllByFarmId(Integer farmId) {
        return farmCareHistoryService.findAllByFarmId(farmId);
    }

    @GetMapping
    public FarmCareForm findById(@RequestParam Integer id){
        return farmCareHistoryService.findDTOById(id);
    }

    @PostMapping
    public FarmCareForm insertFarmCare(@RequestBody FarmCareForm farmCareForm) {
        return farmCareHistoryService.insert(farmCareForm);
    }

    @PutMapping
    public FarmCareForm updateFarmCare(@RequestBody FarmCareForm farmCareForm) {
        return farmCareHistoryService.update(farmCareForm);
    }

    @GetMapping("/search-by-date")
    public List<FarmCareForm> searchByDate(@RequestParam Integer farmId, @RequestParam String date){
        return farmCareHistoryService.searchByDate(farmId, date);
    }

    @DeleteMapping
    public void deleteFarmCare(@RequestParam Integer id){
        farmCareHistoryService.deleteFarmCareById(id);
    }
}
