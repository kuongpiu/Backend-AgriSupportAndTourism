package com.example.agrisupportandtorism.entity.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "district")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class District {
    @Id
    @Column(name = "id")
    @Max(value = 6)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "province_id")
    private String provinceId;
}
