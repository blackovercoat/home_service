package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by PC on 5/10/2017.
 */
@Data
@ToString
public class BookingDetail {
    private int staffId;
    private int bookingId;

    public BookingDetail(int staffId, int bookingId) {
        this.staffId = staffId;
        this.bookingId = bookingId;
    }

    public BookingDetail() {
    }
}
