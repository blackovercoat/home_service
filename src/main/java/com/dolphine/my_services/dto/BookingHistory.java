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
    private ProviderServiceWebService providerServiceWebService;
    private Date bookingDate;
    private Date workingDate;
    private int status;
    private String description;
    private List<Staff> staffs;

    public BookingHistory(int id, int customerId, ProviderServiceWebService providerServiceWebService, Date bookingDate, Date workingDate, int status, String description,List<Staff> staffs) {
        this.id = id;
        this.customerId = customerId;
        this.providerServiceWebService = providerServiceWebService;
        this.bookingDate = bookingDate;
        this.workingDate = workingDate;
        this.status = status;
        this.description = description;
        this.staffs = staffs;
    }
}
