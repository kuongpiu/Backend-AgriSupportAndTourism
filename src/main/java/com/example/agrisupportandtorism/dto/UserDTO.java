package com.example.agrisupportandtorism.dto;

import com.example.agrisupportandtorism.entity.address.Address;
import com.example.agrisupportandtorism.entity.user.Role;
import com.example.agrisupportandtorism.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class UserDTO {
    private String username;
    private String name;
    private String avatar;
    private String email;
    private List<String> roles;
    private List<Address> addresses;

    private UserDTO(String username, String name, String email, String avatar, List<String> roles){
        this.username = username;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.roles = roles;
    }
    public static UserDTO fromUser(User user){
        List<String> roles = Role.convertToStrings(user.getRoles());
        return new UserDTO(user.getUsername(), user.getName(), user.getEmail(), user.getAvatar(), roles);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return username.equals(userDTO.username) && Objects.equals(name, userDTO.name) && Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, name);
    }
}
