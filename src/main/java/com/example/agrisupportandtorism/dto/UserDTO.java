package com.example.agrisupportandtorism.dto;

import com.example.agrisupportandtorism.entity.Role;
import com.example.agrisupportandtorism.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class UserDTO {
    private String username;
    private String name;
    private String address;
    private String avatar;
    private List<String> roles;

    private UserDTO(String username, String name, String address, String avatar, List<String> roles){
        this.username = username;
        this.name = name;
        this.address = address;
        this.avatar = avatar;
        this.roles = roles;
    }
    public static UserDTO fromUser(User user){
        List<String> roles = Role.convertToStrings(user.getRoles());
        return new UserDTO(user.getUsername(), user.getName(), user.getAddress(), user.getAvatar(), roles);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roles=" + roles +
                '}';
    }
}
