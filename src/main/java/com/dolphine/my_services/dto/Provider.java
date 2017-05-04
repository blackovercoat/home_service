package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by PC on 4/10/2017.
 */
@Data
@ToString
public class Provider {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private float longitude;
    private float latitude;
    private String image;

    public Provider(int id, String name, String email, String phoneNumber, String address, float longitude, float latitude, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.image = image;
    }

    public Provider() {
    }
}
