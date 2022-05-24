package com.example.agrisupportandtorism.entity.activity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "activity")
@Setter
@Getter

public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Tên hoạt động là thông tin bắt buộc ")
    @Size(max = 100, message = "Tên hoạt động không được quá 20 ký tự")
    private String name;

    @Column(name = "description")
    private String description;
}
