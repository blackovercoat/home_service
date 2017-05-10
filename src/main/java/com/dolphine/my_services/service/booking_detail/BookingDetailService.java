package com.dolphine.my_services.service.booking_detail;

import com.dolphine.my_services.dto.BookingDetail;
import com.dolphine.my_services.model.BookingDetailEntity;

/**
 * Created by PC on 5/10/2017.
 */
public interface BookingDetailService {
    BookingDetail addBookingDetail(BookingDetailEntity bookingDetailEntity);
}
