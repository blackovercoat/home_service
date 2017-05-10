package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * Created by PC on 4/12/2017.
 */
@Data
@ToString
public class ProviderServiceWebService {
    private int id;
    private Provider provider;
    private ServiceWebService services;
    private float maxPrice;
    private float minPrice;
    private String description;
    private Date fromTime;
    private Date toTime;
    private List<Rating> ratingList;

    public ProviderServiceWebService(int id, Provider provider, ServiceWebService services, float maxPrice, float minPrice, String description, Date fromTime, Date toTime, List<Rating> ratingList) {
        this.id = id;
        this.provider = provider;
        this.services = services;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.description = description;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.ratingList = ratingList;
    }

    public ProviderServiceWebService() {
    }
}
