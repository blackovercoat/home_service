package com.dolphine.my_services.service.booking;

import com.dolphine.my_services.dto.Booking;
import com.dolphine.my_services.dto.BookingHistory;
import com.dolphine.my_services.dto.Provider;
import com.dolphine.my_services.dto.ServiceWebService;
import com.dolphine.my_services.model.BookingEntity;
import com.dolphine.my_services.model.ProviderServiceEntity;
import com.dolphine.my_services.repository.BookingRepository;
import com.dolphine.my_services.repository.ProviderServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 4/19/2017.
 */
@Service
public class BookingServiceImpl implements BookingService{

    final private BookingRepository bookingRepository;
    final private ProviderServiceRepository providerServiceRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, ProviderServiceRepository providerServiceRepository) {
        this.bookingRepository = bookingRepository;
        this.providerServiceRepository = providerServiceRepository;
    }

    @Transactional
    @Override
    public List<BookingHistory> getBookingHistorybyCustomerId(int customerId) {
        List<BookingEntity> bookingEntities = bookingRepository.findByCustomer_Id(customerId);
        List<BookingHistory> bookingHistories = new ArrayList<>();
        for (BookingEntity bookingEntity : bookingEntities){
            ServiceWebService services = new ServiceWebService(bookingEntity.getProviderServices().getService().getId()
                    ,bookingEntity.getProviderServices().getService().getName()
                    ,bookingEntity.getProviderServices().getService().getDescription()
                    ,bookingEntity.getProviderServices().getService().getImage());
            Provider provider = new Provider(bookingEntity.getProviderServices().getProvider().getId()
                    ,bookingEntity.getProviderServices().getProvider().getName()
                    ,bookingEntity.getProviderServices().getProvider().getEmail()
                    ,bookingEntity.getProviderServices().getProvider().getPhoneNumber()
                    ,bookingEntity.getProviderServices().getProvider().getAddress()
                    ,bookingEntity.getProviderServices().getProvider().getLongitude()
                    ,bookingEntity.getProviderServices().getProvider().getLatitude()
                    ,bookingEntity.getProviderServices().getProvider().getImage());
            bookingHistories.add(new BookingHistory(bookingEntity.getId()
                    ,bookingEntity.getCustomer().getId()
                    ,bookingEntity.getProviderServices().getId()
                    ,bookingEntity.getBookingDate()
                    ,bookingEntity.getWorkingDate()
                    ,bookingEntity.getStatus()
                    ,bookingEntity.getDescription()
                    ,services
                    ,provider));
        }
        return bookingHistories;
    }

    @Override
    public Booking saveBooking(BookingEntity bookingEntity) {
        Booking booking = new Booking();
        bookingRepository.save(bookingEntity);
        booking.setId(bookingEntity.getId());
        booking.setCustomerId(bookingEntity.getCustomer().getId());
        booking.setProviderServiceId(bookingEntity.getProviderServices().getId());
        booking.setBookingDate(bookingEntity.getBookingDate());
        booking.setWorkingDate(bookingEntity.getWorkingDate());
        booking.setDescription(bookingEntity.getDescription());
        booking.setStatus(bookingEntity.getStatus());
        return booking;
    }

    @Override
    public BookingEntity getBookingById(int bookingId) {
        return bookingRepository.findOne(bookingId);
    }

    @Transactional
    @Override
    public int setBookingStatusById(int bookingId, int status) {
        int booking = bookingRepository.updateBookingStatusById(bookingId,status);
        return booking;
    }

    @Transactional
    @Override
    public List<Booking> getAllBookings() {
        List<BookingEntity> bookingEntities = bookingRepository.findAll();
        List<Booking> bookingList = new ArrayList<>();
        for(BookingEntity bookingEntity : bookingEntities){
            bookingList.add(new Booking(bookingEntity.getId()
                    ,bookingEntity.getCustomer().getId()
                    ,bookingEntity.getProviderServices().getId()
                    ,bookingEntity.getBookingDate()
                    ,bookingEntity.getWorkingDate()
                    ,bookingEntity.getDescription()
                    ,bookingEntity.getStatus()));
        }
        return bookingList;
    }

    @Transactional
    @Override
    public List<Booking> getBookingByProviderId(int providerId) {
        List<BookingEntity> bookingEntities = bookingRepository.findAll();
        List<ProviderServiceEntity> providerServiceEntities = providerServiceRepository.findByProvider_Id(providerId);
        List<Booking> bookings = new ArrayList<>();
        for(BookingEntity bookingEntity : bookingEntities)
            for(ProviderServiceEntity providerServiceEntity : providerServiceEntities)
                if(bookingEntity.getProviderServices().getId()==providerServiceEntity.getId())
            bookings.add(new Booking(bookingEntity.getId()
                    ,bookingEntity.getCustomer().getId()
                    ,bookingEntity.getProviderServices().getId()
                    ,bookingEntity.getBookingDate()
                    ,bookingEntity.getWorkingDate()
                    ,bookingEntity.getDescription()
                    ,bookingEntity.getStatus()));
        return bookings;
    }
}
