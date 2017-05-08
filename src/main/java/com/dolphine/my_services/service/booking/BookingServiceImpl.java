package com.dolphine.my_services.service.booking;

import com.dolphine.my_services.dto.*;
import com.dolphine.my_services.model.BookingEntity;
import com.dolphine.my_services.model.ProviderServiceEntity;
import com.dolphine.my_services.repository.BookingRepository;
import com.dolphine.my_services.repository.ProviderServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    @Override
    public List<ServiceStatistic> getServiceStatisticByProviderId(int providerId,int month) {
        List<BookingEntity> bookingEntities = bookingRepository.findAll();
        List<ProviderServiceEntity> providerServiceList;
        if(providerId==0)
            providerServiceList = providerServiceRepository.findAll();
        else
            providerServiceList = providerServiceRepository.findByProvider_Id(providerId);

        List<ServiceStatistic> serviceStatistics = new ArrayList<>();
        for(ProviderServiceEntity providerService : providerServiceList) {
            int count = 0;
            ServiceStatistic serviceStatistic = new ServiceStatistic();
            for (ProviderServiceEntity providerServiceEntity : providerServiceList)
                for (BookingEntity bookingEntity : bookingEntities) {
                    Date bookingDate = bookingEntity.getBookingDate();
                    Calendar cal = Calendar.getInstance();
                    int currentYear = cal.get(Calendar.YEAR);
                    cal.setTime(bookingDate);
                    int bookingMonth = cal.get(Calendar.MONTH)+1;
                    int bookingYear = cal.get(Calendar.YEAR);
                    if(month==0){
                        if (providerServiceEntity.getId() == bookingEntity.getProviderServices().getId()
                                && bookingYear == currentYear
                                && providerServiceEntity.getProvider().getId() == providerService.getProvider().getId()
                                && providerService.getService().getId() == providerServiceEntity.getService().getId()){
                            if(count<=0){
                                serviceStatistic.setProviderName(providerServiceEntity.getProvider().getName());
                                serviceStatistic.setPrice(providerServiceEntity.getService().getPrice());
                                serviceStatistic.setProviderId(providerServiceEntity.getProvider().getId());
                                serviceStatistic.setServiceName(providerServiceEntity.getService().getName());
                            }
                            count++;
                        }
                    }else
                            if (providerServiceEntity.getId() == bookingEntity.getProviderServices().getId()
                                && bookingMonth == month
                                && bookingYear == currentYear
                                && providerServiceEntity.getProvider().getId() == providerService.getProvider().getId()
                                && providerService.getService().getId() == providerServiceEntity.getService().getId()){
                            if(count<=0){
                                serviceStatistic.setPrice(providerServiceEntity.getService().getPrice());
                                serviceStatistic.setProviderName(providerServiceEntity.getProvider().getName());
                                serviceStatistic.setProviderId(providerServiceEntity.getProvider().getId());
                                serviceStatistic.setServiceName(providerServiceEntity.getService().getName());
                            }
                            count++;
                        }
                }
            if(count>0){
                serviceStatistic.setBookingTimes(count);
                serviceStatistics.add(serviceStatistic);
            }
        }
        return serviceStatistics;
    }

    @Override
    public List<ServiceStatistic> getServiceStatisticAllProvider(int month) {
        return null;
    }
}
