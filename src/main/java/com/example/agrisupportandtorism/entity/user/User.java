package com.example.agrisupportandtorism.entity.user;

import com.example.agrisupportandtorism.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Table(name = "user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
@Setter
public class User implements Serializable{
    @Id
    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "fullname")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "role")
    )

    private List<Role> roles;

    public static User fromUserDTO(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setAvatar(userDTO.getAvatar());
        user.setRoles(Role.convertToRoles(userDTO.getRoles()));
        user.setEmail(userDTO.getEmail());
        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
