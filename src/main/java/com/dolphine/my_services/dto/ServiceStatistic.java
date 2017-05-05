package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by PC on 5/5/2017.
 */
@ToString
@Data
public class ServiceStatistic {
    private float maxPrice;
    private float minPrice;
    private int providerId;
    private String serviceName;
    private int bookingTimes;

    public ServiceStatistic() {
    }

    public ServiceStatistic(float maxPrice, float minPrice, int providerId, String serviceName, int bookingTimes) {
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.providerId = providerId;
        this.serviceName = serviceName;
        this.bookingTimes = bookingTimes;
    }
}
