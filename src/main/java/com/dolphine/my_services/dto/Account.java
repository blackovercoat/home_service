package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by PC on 3/29/2017.
 */
@Data
@ToString
public class Account {
    private int id;
    private String email;
    private String password;

    public Account(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
