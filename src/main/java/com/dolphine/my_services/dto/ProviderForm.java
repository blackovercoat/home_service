package com.dolphine.my_services.dto;

import com.dolphine.my_services.service.common.CommonService;
import com.dolphine.my_services.validation.annotation.ValidProvider;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Created by PC on 4/9/2017.
 */
@ToString
@Data
@ValidProvider
public class ProviderForm {

    private int id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String address;
    @NotNull
    private String password;
    @NotNull
    private String repassword;

    private float longitude;
    private float latitude;

    private String image;

    public ProviderForm(int id, String name, String email, String phoneNumber, String address, String password, float longitude, float latitude, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.longitude = longitude;
        this.latitude = latitude;
        this.image = image;
    }

    public ProviderForm() {
    }

}
