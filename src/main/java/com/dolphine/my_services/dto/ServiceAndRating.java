package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by PC on 4/27/2017.
 */
@Data
@ToString
public class ServiceAndRating {
    private int id;
    private String name;
    private String description;
    private String image;
    private List<Rating> ratings;

    public ServiceAndRating(int id, String name, String description, String image, List<Rating> ratings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.ratings = ratings;
    }
}
