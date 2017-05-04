package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by PC on 4/27/2017.
 */
@Data
@ToString
public class Booking {
    private int id;
    private int customerId;
    private int providerServiceId;
    private Date bookingDate;
    private Date workingDate;
    private String description;
    private int status;

    public Booking(int id, int customerId, int providerServiceId, Date bookingDate, Date workingDate, String description, int status) {
        this.id = id;
        this.customerId = customerId;
        this.providerServiceId = providerServiceId;
        this.bookingDate = bookingDate;
        this.workingDate = workingDate;
        this.description = description;
        this.status = status;
    }

    public Booking() {
    }
}
