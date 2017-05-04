package com.dolphine.my_services.service.booking;

import com.dolphine.my_services.dto.Booking;
import com.dolphine.my_services.dto.BookingHistory;
import com.dolphine.my_services.model.BookingEntity;

import java.util.List;

/**
 * Created by PC on 4/19/2017.
 */
public interface BookingService {

    List<BookingHistory> getBookingHistorybyCustomerId(int customerId);
    Booking saveBooking(BookingEntity bookingEntity);
    BookingEntity getBookingById(int bookingId);
    int setBookingStatusById(int bookingId,int status);
    List<Booking> getAllBookings();
    List<Booking> getBookingByProviderId(int providerId);
}
