package com.example.agrisupportandtorism.controller.farm;

import com.example.agrisupportandtorism.dto.farm.FarmDTO;
import com.example.agrisupportandtorism.dto.tree.ShortTrees;
import com.example.agrisupportandtorism.service.farm.FarmService;
import com.example.agrisupportandtorism.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/farm")
@CrossOrigin("http://localhost:9528/")

public class FarmController {
    @Autowired
    private FarmService farmService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<FarmDTO> findAllFarms() {
        return farmService.findAllFarms();
    }

    @GetMapping
    public FarmDTO findById(@RequestParam Integer id) {
        return farmService.findById(id);
    }

    @PostMapping
    public FarmDTO insertFarm(@RequestBody @Valid FarmDTO farmDTO) {
        userService.addFarmerRole();
        return farmService.insertFarm(farmDTO);
    }

    @PutMapping
    public FarmDTO updateFarm(@RequestBody @Valid FarmDTO farmDTO) {
        return farmService.updateFarm(farmDTO);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFarm(@RequestParam @Valid Integer id) {
        if (farmService.deleteFarm(id)) {
            return ResponseEntity.ok().body("Xóa thành công");
        }
        return ResponseEntity.internalServerError().body("Xóa thất bại");
    }

    @PutMapping("/tree")
    public FarmDTO updateFarmTrees(@RequestBody @Valid ShortTrees shortTrees) {
        return farmService.updateFarmTrees(shortTrees);
    }
}

