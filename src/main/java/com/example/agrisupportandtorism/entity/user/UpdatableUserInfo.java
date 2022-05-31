package com.example.agrisupportandtorism.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class UpdatableUserInfo {
    @NotEmpty(message = "Tên người dùng không thể trống")
    private String name;
    private String email;
}
