package com.example.agrisupportandtorism.service.address;

import com.example.agrisupportandtorism.entity.address.Address;
import com.example.agrisupportandtorism.entity.address.District;
import com.example.agrisupportandtorism.entity.address.Province;
import com.example.agrisupportandtorism.entity.address.Ward;
import com.example.agrisupportandtorism.entity.user.User;
import com.example.agrisupportandtorism.exception.PermissionException;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.address.AddressRepo;
import com.example.agrisupportandtorism.repository.address.DistrictRepo;
import com.example.agrisupportandtorism.repository.address.ProvinceRepo;
import com.example.agrisupportandtorism.repository.address.WardRepo;
import com.example.agrisupportandtorism.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private ProvinceRepo provinceRepo;

    @Autowired
    private DistrictRepo districtRepo;

    @Autowired
    private WardRepo wardRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<Address> getAllUserAddresses() {
        User currentUser = userService.getCurrentUser();
        return addressRepo.findAllByUser(currentUser);
    }

    public Address findById(Integer addressId) {
        Optional<Address> addressOpt = addressRepo.findById(addressId);
        if (addressOpt.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Địa-chỉ-với-mã=[%s] không tồn tại", addressId));
        }
        return addressOpt.get();
    }

    @Transactional
    public Address insertAddress(Address address) {
        User currentUser = userService.getCurrentUser();

        if(!currentUser.getUsername().equals(address.getUser().getUsername())){
            throw new PermissionException(String.format("Username=[%s] không có quyền sửa, quyền thuộc về user=[%s]", currentUser.getUsername(), address.getUser().getUsername()));
        }

        return addressRepo.saveAndFlush(address);
    }

    @Transactional
    public Address updateAddress(Address address) {
        User currentUser = userService.getCurrentUser();

        if(!currentUser.getUsername().equals(address.getUser().getUsername())){
            throw new PermissionException(String.format("Username=[%s] không có quyền sửa, quyền thuộc về user=[%s]", currentUser.getUsername(), address.getUser().getUsername()));
        }

        return addressRepo.saveAndFlush(address);
    }

    @Transactional
    public void deleteAddress(Integer addressId) {
        Address addressInRepo = findById(addressId);
        User currentUser = userService.getCurrentUser();

        if(!currentUser.getUsername().equals(addressInRepo.getUser().getUsername())){
            throw new PermissionException(String.format("Username=[%s] không có quyền sửa, quyền thuộc về user=[%s]", currentUser.getUsername(), addressInRepo.getUser().getUsername()));
        }

        addressRepo.deleteById(addressId);
    }

    public List<Province> searchProvinces(String name) {
        logger.info(String.format("search provinces contain name=[%s]", name));
        return provinceRepo.findByNameContaining(name);
    }

    public List<District> searchDistricts(String provinceId, String name) {
        logger.info(String.format("search Districts in ProvinceId=[%s] and contain name=[%s]", provinceId, name));
        return districtRepo.findByProvinceIdAndNameContaining(provinceId, name);
    }

    public List<Ward> searchWards(String districtId, String name) {
        logger.info(String.format("search Wards in DistrictId=[%s] and contain name=[%s]", districtId, name));
        return wardRepo.findByDistrictIdAndNameContaining(districtId, name);
    }

    public Province findProvinceById(String id) {
        Optional<Province> provinceOpt = provinceRepo.findById(id);
        if (provinceOpt.isPresent()) {
            return provinceOpt.get();
        } else {
            throw new ResourceNotFoundException(String.format("Tỉnh không tồn tại, mã tỉnh=[%s]", id));
        }
    }

    public District findDistrictById(String id) {
        Optional<District> districtOpt = districtRepo.findById(id);
        if (districtOpt.isPresent()) {
            return districtOpt.get();
        } else {
            throw new ResourceNotFoundException(String.format("Huyện không tồn tại, mã huyện=[%s]", id));
        }
    }

    public Ward findWardById(String id) {
        Optional<Ward> wardOpt = wardRepo.findById(id);
        if (wardOpt.isPresent()) {
            return wardOpt.get();
        } else {
            throw new ResourceNotFoundException(String.format("Xã không tồn tại, mã xã=[%s]", id));
        }
    }

}
