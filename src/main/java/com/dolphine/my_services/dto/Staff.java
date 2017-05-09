package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by PC on 4/27/2017.
 */
@Data
@ToString
public class Staff {
    private int id;
    private String name;
    private String phoneNumber;
    private Provider provider;

    public Staff(int id, String name, String phoneNumber, Provider provider) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.provider = provider;
    }

    public Staff() {
    }
}
