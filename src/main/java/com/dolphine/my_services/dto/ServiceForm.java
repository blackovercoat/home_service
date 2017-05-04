package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by PC on 5/1/2017.
 */
@Data
@ToString
public class ServiceForm {
    private int id;
    private String name;
    private String description;
    private int catalogId;
    private String image;

    public ServiceForm(int id, String name, String description, int catalogId, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.catalogId = catalogId;
        this.image = image;
    }

    public ServiceForm() {
    }
}
