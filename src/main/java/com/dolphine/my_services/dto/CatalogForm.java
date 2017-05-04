package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.io.UnsupportedEncodingException;

/**
 * Created by PC on 5/1/2017.
 */
@ToString
@Data
public class CatalogForm {
    private int id;
    private String name;
    private String description;
    private String image;

    public CatalogForm() {
    }

}
