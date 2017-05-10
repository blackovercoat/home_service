package com.dolphine.my_services.service.booking_detail;

import com.dolphine.my_services.dto.BookingDetail;
import com.dolphine.my_services.model.BookingDetailEntity;
import com.dolphine.my_services.repository.BookingDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by PC on 5/10/2017.
 */
@Service
public class BookingDetailServiceImpl implements BookingDetailService {

    @Autowired
    final private BookingDetailRepository bookingDetailRepository;

    public BookingDetailServiceImpl(BookingDetailRepository bookingDetailRepository) {
        this.bookingDetailRepository = bookingDetailRepository;
    }

    @Override
    public BookingDetail addBookingDetail(BookingDetailEntity bookingDetailEntity) {
        BookingDetailEntity bdEntity = bookingDetailRepository.save(bookingDetailEntity);
        BookingDetail bookingDetail =  new BookingDetail();
        bookingDetail.setBookingId(bdEntity.getBookingDetailEntityPK().getBookingById().getId());
        bookingDetail.setStaffId(bdEntity.getBookingDetailEntityPK().getStaffById().getId());
        return bookingDetail;
    }
}
