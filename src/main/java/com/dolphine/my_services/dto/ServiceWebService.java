package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by PC on 4/12/2017.
 */
@Data
@ToString
public class ServiceWebService {

    private int id;
    private String name;
    private String description;
    private String image;

    public ServiceWebService(int id, String name, String description, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
