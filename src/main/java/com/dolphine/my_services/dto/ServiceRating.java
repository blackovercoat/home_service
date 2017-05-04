package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by PC on 4/21/2017.
 */
@Data
@ToString
public class ServiceRating {
    private int providerServiceId;
    private int serviceId;
    private List<Rating> ratingList;

    public ServiceRating(int providerServiceId, int serviceId, List<Rating> ratingList) {
        this.providerServiceId = providerServiceId;
        this.serviceId = serviceId;
        this.ratingList = ratingList;
    }
}
