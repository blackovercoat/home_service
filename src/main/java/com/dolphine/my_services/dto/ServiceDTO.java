package com.dolphine.my_services.dto;

import com.dolphine.my_services.validation.annotation.ValidService;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Created by PC on 4/10/2017.
 */
@ToString
@Data

public class ServiceDTO {

    private int id;
    private String name;
    private String description;
    private int catalogId;
    private String image;

    public ServiceDTO(int id, String name, String description, int catalogId, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.catalogId = catalogId;
        this.image = image;
    }

    public ServiceDTO() {
    }
}
