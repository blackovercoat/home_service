package com.dolphine.my_services.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@ToString
@Entity
@Table(name = "booking_detail")
public class BookingDetailEntity {

    @EmbeddedId
    private BookingDetailEntityPK bookingDetailEntityPK;
}



