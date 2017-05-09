package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by PC on 4/10/2017.
 */
@ToString
@Data
public class ServiceDTOWebService {

    private int id;
    private String name;
    private String description;
    private Catalog catalog;
    private String image;
    private float price;

    public ServiceDTOWebService(int id, String name, String description, Catalog catalog, String image, float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.catalog = catalog;
        this.image = image;
        this.price = price;
    }

    public ServiceDTOWebService() {
    }
}
