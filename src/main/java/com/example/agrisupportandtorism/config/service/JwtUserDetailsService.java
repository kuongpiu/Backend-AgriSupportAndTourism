package com.example.agrisupportandtorism.config.service;

import com.example.agrisupportandtorism.config.model.JwtUserDetails;
import com.example.agrisupportandtorism.entity.User;
import com.example.agrisupportandtorism.exception.ResourceConflict;
import com.example.agrisupportandtorism.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User> userOpt = userRepo.findUserByUsername(username);
        if(userOpt.isPresent()){
            return new JwtUserDetails(userOpt.get());
        }else{
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
    public User createNewUser(User user) {
        //if user is exists, throws Exception.
        Optional<User> userOptInRepo = userRepo.findUserByUsername(user.getUsername());
        if(userOptInRepo.isPresent() && userOptInRepo.get().getUsername().equals(user.getUsername())){
            throw new ResourceConflict("this user has already exists in the repository !");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
