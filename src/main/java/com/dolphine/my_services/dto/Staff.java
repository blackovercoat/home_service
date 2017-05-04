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
    private int providerId;

    public Staff(int id, String name, String phoneNumber, int providerId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.providerId = providerId;
    }

    public Staff() {
    }
}
