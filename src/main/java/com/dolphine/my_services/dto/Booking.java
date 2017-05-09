package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * Created by PC on 4/27/2017.
 */
@Data
@ToString
public class Booking {
    private int id;
    private Customer customer;
    private ServiceDTOWebService service;
    private Date bookingDate;
    private Date workingDate;
    private String description;
    private int status;
    private List<Staff> staffs;

    public Booking(int id, Customer customer, ServiceDTOWebService service, Date bookingDate, Date workingDate, String description, int status, List<Staff> staffs) {
        this.id = id;
        this.customer = customer;
        this.service = service;
        this.bookingDate = bookingDate;
        this.workingDate = workingDate;
        this.description = description;
        this.status = status;
        this.staffs = staffs;
    }

    public Booking() {
    }
}
