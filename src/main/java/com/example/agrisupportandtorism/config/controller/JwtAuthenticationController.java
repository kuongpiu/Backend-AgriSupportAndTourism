package com.example.agrisupportandtorism.config.controller;

import com.example.agrisupportandtorism.config.model.JwtRequest;
import com.example.agrisupportandtorism.config.model.JwtResponse;
import com.example.agrisupportandtorism.config.util.JwtTokenUtil;
import com.example.agrisupportandtorism.config.service.JwtUserDetailsService;
import com.example.agrisupportandtorism.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:9528/")

public class JwtAuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception{
        System.out.println("authenticating :v");
        authenticate(request.getUsername(), request.getPassword());
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        System.out.println("okey");
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("token: " + token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody JwtRequest jwtRequest){
        User user = new User();
        user.setUsername(jwtRequest.getUsername());
        user.setPassword(jwtRequest.getPassword());
        try {
            jwtUserDetailsService.createNewUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    private void authenticate(String username, String password) throws Exception{
        try{
            System.out.println("username: " + username + ", password: " + password);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
