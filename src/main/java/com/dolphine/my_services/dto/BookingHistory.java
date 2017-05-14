package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * Created by PC on 4/19/2017.
 */
@Data
@ToString
public class BookingHistory {
    private int id;
    private int customerId;
    private int providerServiceId;
    private Date bookingDate;
    private Date workingDate;
    private int status;
    private String description;
    private ServiceWebService services;
    private Provider provider;
    private List<Staff> staffs;
    private List<Rating> ratings;

    public BookingHistory(int id, int customerId, int providerServiceId, Date bookingDate, Date workingDate, int status, String description, ServiceWebService services, Provider provider,List<Staff> staffs,List<Rating> ratings) {
        this.id = id;
        this.customerId = customerId;
        this.providerServiceId = providerServiceId;
        this.bookingDate = bookingDate;
        this.workingDate = workingDate;
        this.status = status;
        this.description = description;
        this.services = services;
        this.provider = provider;
        this.staffs = staffs;
        this.ratings = ratings;
    }
}
