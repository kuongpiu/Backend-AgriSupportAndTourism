package com.example.agrisupportandtorism.repository.user;

import com.example.agrisupportandtorism.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findUserByUsername(String username);
}
