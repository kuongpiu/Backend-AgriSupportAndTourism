package com.example.agrisupportandtorism.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "roles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Role {
    @Id
    @Column(name = "role")
    private String role;


    public static List<String> convertToStrings(List<Role> roles){
        return roles.stream().map(Role::convertRoleToString).collect(Collectors.toList());
    }
    public static List<Role> convertToRoles(List<String> strs){
        return strs.stream().map(Role::convertStringToRole).collect(Collectors.toList());
    }
    public static String convertRoleToString(Role role){
        return role.getRole();
    }
    public static Role convertStringToRole(String str){
        return new Role(str);
    }
}
