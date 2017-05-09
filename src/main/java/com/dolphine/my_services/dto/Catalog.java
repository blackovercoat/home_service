package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by PC on 4/11/2017.
 */
@Data
@ToString
public class Catalog {
    private int id;
    private String name;
    private String description;
    private String image;

    public Catalog() {
    }

    public Catalog(int id, String name, String description, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }
}


