package com.example.agrisupportandtorism.controller;

import com.example.agrisupportandtorism.dto.UserDTO;
import com.example.agrisupportandtorism.entity.user.UpdatableUserInfo;
import com.example.agrisupportandtorism.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:9528/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserDTO> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping
    public UserDTO getCurrentUserInfo(){
        return userService.getCurrentUserInfo();
    }

    @PutMapping("/updateAvatar")
    public UserDTO updateAvatar(@RequestBody HashMap<String, String> data){
        return userService.updateUserAvatar(data.get("avatar"));
    }
    @PutMapping
    public UserDTO updateUserInfo(@RequestBody @Valid UpdatableUserInfo updatableUserInfo){
        return userService.updateUserInfo(updatableUserInfo);
    }
}