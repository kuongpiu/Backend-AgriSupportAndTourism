package com.example.agrisupportandtorism.service.address;

import com.example.agrisupportandtorism.entity.address.District;
import com.example.agrisupportandtorism.entity.address.Province;
import com.example.agrisupportandtorism.entity.address.Ward;
import com.example.agrisupportandtorism.repository.address.DistrictRepo;
import com.example.agrisupportandtorism.repository.address.ProvinceRepo;
import com.example.agrisupportandtorism.repository.address.WardRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private ProvinceRepo provinceRepo;

    @Autowired
    private DistrictRepo districtRepo;

    @Autowired
    private WardRepo wardRepo;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<Province> searchProvinces(String name){
        logger.info(String.format("search provinces contain name=[%s]", name));
        return provinceRepo.findByNameContaining(name);
    }
    public List<District> searchDistricts(String provinceId, String name){
        logger.info(String.format("search Districts in ProvinceId=[%s] and contain name=[%s]", provinceId, name));
        return districtRepo.findByProvinceIdAndNameContaining(provinceId, name);
    }
    public List<Ward> searchWards(String districtId, String name){
        logger.info(String.format("search Wards in DistrictId=[%s] and contain name=[%s]", districtId, name));
        return wardRepo.findByDistrictIdAndNameContaining(districtId, name);
    }


}
