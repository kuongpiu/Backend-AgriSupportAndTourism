package com.example.agrisupportandtorism.service;

import com.example.agrisupportandtorism.config.model.JwtUserDetails;
import com.example.agrisupportandtorism.dto.UserDTO;
import com.example.agrisupportandtorism.entity.User;
import com.example.agrisupportandtorism.exception.PermissionException;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public List<UserDTO> findAll(){
        return userRepo.findAll()
                .stream()
                .map(UserDTO::fromUser)
                .collect(Collectors.toList());
    }

    public UserDTO findUserByUsername(String username) throws ResourceNotFoundException{
        Optional<User> userOpt = userRepo.findUserByUsername(username);
        if(userOpt.isPresent()){
            return UserDTO.fromUser(userOpt.get());
        }else{
            throw new ResourceNotFoundException(String.format("username=[%s] not found!", username));
        }
    }
    public User getCurrentUser(){
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj instanceof JwtUserDetails){
            JwtUserDetails userDetails = (JwtUserDetails) obj;
            return userDetails.getCurrentUser();
        }else{
            throw new PermissionException("Request does not attach token");
        }
    }

    public UserDTO getCurrentUserInfo(){
        return UserDTO.fromUser(getCurrentUser());
    }

    public UserDTO updateUserInfo(UserDTO userDTO){
        User currentUser = getCurrentUser();
        if(userDTO.getUsername().equals(currentUser.getUsername())){
            currentUser.setName(userDTO.getName());
            currentUser.setAddress(userDTO.getAddress());
            currentUser.setAvatar(userDTO.getAvatar());
            return UserDTO.fromUser(userRepo.save(currentUser));
        }else{
            throw new RuntimeException("username not equals with current JWT token");
        }
    }
}
