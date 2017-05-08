package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by PC on 5/5/2017.
 */
@ToString
@Data
public class ServiceStatistic {
    private float price;
    private int providerId;
    private String providerName;
    private String serviceName;
    private int bookingTimes;

    public ServiceStatistic() {
    }

    public ServiceStatistic(float price, int providerId, String serviceName, int bookingTimes, String providerName) {
        this.price = price;
        this.providerId = providerId;
        this.serviceName = serviceName;
        this.bookingTimes = bookingTimes;
        this.providerName = providerName;
    }
}
