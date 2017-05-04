package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.io.UnsupportedEncodingException;

/**
 * Created by PC on 5/1/2017.
 */
@ToString
@Data
public class CustomerForm {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    public void setName(String name) {
        this.name = convertToUTF8(name);
    }

    public void setAddress(String address) {
        this.address = convertToUTF8(address);
    }

    public CustomerForm() {
    }

    private String convertToUTF8(String string) {
        String name = null;
        try {
            name = new String(string.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return name;
    }
}
