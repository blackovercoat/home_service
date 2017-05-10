package com.dolphine.my_services.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class BookingDetailEntityPK implements Serializable{
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity bookingById;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffEntity staffById;
}
