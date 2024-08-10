package com.example.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends BaseEntity {


    @Column(name = "apartment_number", length = 20)
    private String apartmentNumber;

    @Column(name = "floor", length = 10)
    private String floor;

    @Column(name = "building", length = 50)
    private String building;

    @Column(name = "street_number", length = 20)
    private String streetNumber;

    @Column(name = "street", length = 50)
    private String street;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "address_type")
    private Integer addressType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;



}
