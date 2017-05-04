package com.dolphine.my_services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by PC on 3/30/2017.
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"bookings","ratings"})
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "password")
    private String password;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "latitude")
    private float latitude;

    @OneToMany(mappedBy = "customer")
    private List<BookingEntity> bookings;

    @OneToMany(mappedBy = "customer")
    private List<RatingEntity> ratings;
}
