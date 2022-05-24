package com.example.agrisupportandtorism.controller;

import com.example.agrisupportandtorism.entity.address.District;
import com.example.agrisupportandtorism.entity.address.Province;
import com.example.agrisupportandtorism.entity.address.Ward;
import com.example.agrisupportandtorism.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/address")
@CrossOrigin("http://localhost:9528/")

public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping(value = "/province")
    public List<Province> searchProvinces(@RequestParam String name){
        return addressService.searchProvinces(name);
    }

    @GetMapping(value = "/district")
    public List<District> searchDistricts(@RequestParam String provinceId, @RequestParam String name){
        return addressService.searchDistricts(provinceId, name);
    }

    @GetMapping(value = "/ward")
    public List<Ward> searchWards(@RequestParam String districtId, @RequestParam String name){
        return addressService.searchWards(districtId, name);
    }
}
