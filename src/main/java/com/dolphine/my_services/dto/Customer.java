package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by PC on 3/30/2017.
 */
@Data
@ToString
public class Customer {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private float longitude;
    private float latitude;

    public Customer(int id, String name, String email, String password, String phoneNumber, String address, float longitude, float latitude) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Customer() {
    }
}
