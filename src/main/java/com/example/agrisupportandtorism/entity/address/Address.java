package com.example.agrisupportandtorism.entity.address;

import com.example.agrisupportandtorism.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Table(name = "address")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "ward_id", referencedColumnName = "id")
    private Ward ward;

    @Column(name = "detail")
    private String detail;

    @Column(name = "receiver")
    @Size(min = 1, max = 100)
    @NotEmpty
    private String receiver;

    @Column(name = "phone_number")
    @Size(min = 9, max = 15)
    @NotEmpty
    private String phoneNumber;
}
