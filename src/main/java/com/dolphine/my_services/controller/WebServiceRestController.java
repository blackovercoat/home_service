package com.dolphine.my_services.controller;

import com.dolphine.my_services.dto.*;
import com.dolphine.my_services.model.*;
import com.dolphine.my_services.service.booking.BookingService;
import com.dolphine.my_services.service.catalog.CatalogService;
import com.dolphine.my_services.service.customer.CustomerService;
import com.dolphine.my_services.service.provider.ProviderService;
import com.dolphine.my_services.service.providerservice.ProviderServiceService;
import com.dolphine.my_services.service.rating.RatingService;
import com.dolphine.my_services.service.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by PC on 3/29/2017.
 */
@RestController
@RequestMapping("/api")
public class WebServiceRestController {
    @Autowired
    private final CatalogService catalogService;
    private final ProviderService providerService;
    private final CustomerService customerService;
    private final ProviderServiceService providerServiceService;
    private final BookingService bookingService;
    private final RatingService ratingService;
    private final StaffService staffService;

    public WebServiceRestController(CatalogService catalogService, ProviderService providerService, CustomerService customerService, ProviderServiceService providerServiceService, BookingService bookingService, RatingService ratingService, StaffService staffService) {
        this.catalogService = catalogService;
        this.providerService = providerService;
        this.customerService = customerService;
        this.providerServiceService = providerServiceService;
        this.bookingService = bookingService;
        this.ratingService = ratingService;
        this.staffService = staffService;
    }

    @RequestMapping(value = "customer/login", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomerInfo(@RequestParam(name = "email") String email,
                                   @RequestParam(name = "password") String password) throws CustomException {
        CustomerEntity customerEntity = customerService.getCustomerByEmail(email);
        if(customerEntity==null)
            throw new CustomException("Email is not exist!");
        if(!password.equals(customerEntity.getPassword()))
            throw new CustomException("Your password is incorrect!");
        Customer customer = new Customer(customerEntity.getId()
                ,customerEntity.getName()
                ,customerEntity.getEmail()
                ,customerEntity.getPassword()
                ,customerEntity.getPhoneNumber()
                ,customerEntity.getAddress()
                ,customerEntity.getLongitude()
                ,customerEntity.getLatitude());
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "provider/login", method = RequestMethod.GET)
    public ResponseEntity<Provider> getProviderInfo(@RequestParam(name = "email") String email,
                                                    @RequestParam(name = "password") String password) throws CustomException {
        ProviderEntity providerEntity = providerService.getProviderByEmail(email);
        if(providerEntity==null)
            throw new CustomException("Email is not exist!");
        if(!password.equals(providerEntity.getPassword()))
            throw new CustomException("Your password is incorrect!");
        Provider provider = new Provider(providerEntity.getId()
                ,providerEntity.getName()
                ,providerEntity.getEmail()
                ,providerEntity.getPhoneNumber()
                ,providerEntity.getAddress()
                ,providerEntity.getLongitude()
                ,providerEntity.getLatitude()
                ,providerEntity.getImage());
        return new ResponseEntity<Provider>(provider, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ResponseEntity<Customer> addNewCustomer(@RequestParam(name = "email") String email,
                                   @RequestParam(name = "password") String password,
                                   @RequestParam(name = "name") String name,
                                   @RequestParam(name = "phoneNumber") String phoneNumber,
                                   @RequestParam(name = "address") String address) throws CustomException {
        if(customerService.getCustomerByEmail(email)!=null)
            throw new CustomException("This email is already in use!");
        if(customerService.getCustomerByPhoneNumber(phoneNumber)!=null)
            throw new CustomException("This phone number is already in use!");
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(email);
        customerEntity.setName(name);
        customerEntity.setPassword(password);
        customerEntity.setPhoneNumber(phoneNumber);
        customerEntity.setAddress(address);
        return new ResponseEntity<Customer>(customerService.addCustomer(customerEntity), HttpStatus.OK);
    }
    @RequestMapping(value = "provider/register", method = RequestMethod.GET)
    public ResponseEntity<Provider> addNewProvider(@RequestParam(name = "email") String email,
                                                   @RequestParam(name = "password") String password,
                                                   @RequestParam(name = "name") String name,
                                                   @RequestParam(name = "phoneNumber") String phoneNumber,
                                                   @RequestParam(name = "image") String image,
                                                   @RequestParam(name = "longitude") float longitude,
                                                   @RequestParam(name = "latitude") float latitude,
                                                   @RequestParam(name = "address") String address) throws CustomException {
        if(providerService.getProviderByEmail(email)!=null)
            throw new CustomException("This email is already in use!");
        if(providerService.getProviderByPhoneNumber(phoneNumber)!=null)
            throw new CustomException("This phone number is already in use!");
        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setName(name);
        providerEntity.setEmail(email);
        providerEntity.setAddress(address);
        providerEntity.setImage(image);
        providerEntity.setLongitude(longitude);
        providerEntity.setLatitude(latitude);
        providerEntity.setPhoneNumber(phoneNumber);
        providerEntity.setPassword(password);
        return new ResponseEntity<Provider>(providerService.addProviderWebService(providerEntity), HttpStatus.OK);
    }

    @RequestMapping(value = "/catalog/list", method = RequestMethod.GET)
    public List<CatalogAndService> showCatalog(){
        return catalogService.getAllCatalogAndService();
    }

    @RequestMapping(value = "/catalog/provider", method = RequestMethod.GET)
    public List<CatalogServiceAndRating> showCatalogByProvider(@RequestParam(name = "providerId") int providerId){
        return catalogService.getCatalogServiceAndRatingByProviderId(providerId);
    }

    @RequestMapping(value = "/providerService/provider", method = RequestMethod.GET)
    public List<ProviderServiceWebService> getProviderServiceByProvider(@RequestParam(name = "providerId") int providerId) {
        return providerServiceService.getProviderRatingByProviderId(providerId);
    }

    @RequestMapping(value = "/providerService/service", method = RequestMethod.GET)
    public List<ProviderServiceWebService> getProviderServiceByService(@RequestParam(name = "serviceId") int serviceId) {
        return providerServiceService.getProviderRatingByServiceId(serviceId);
    }

    @RequestMapping(value = "/bookingHistory", method = RequestMethod.GET)
    public List<BookingHistory> getBookingHistory(@RequestParam(name = "customerId") int customerId) {
        return bookingService.getBookingHistorybyCustomerId(customerId);
    }

    @RequestMapping(value = "/rating", method = RequestMethod.GET)
    public ResponseEntity<ServiceRating> getRating(@RequestParam(name = "providerServiceId") int providerServiceId) throws CustomException {
        ServiceRating serviceRating = ratingService.getServiceRatingandServiceByProviderServiceId(providerServiceId);
        if(serviceRating==null)
            throw new CustomException("providerServiceId is not valid!");
        return new ResponseEntity<ServiceRating>(serviceRating, HttpStatus.OK);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<ServiceStatistic> test(@RequestParam(name = "providerId") int providerId
            ,@RequestParam(name = "month") int month) {
        return bookingService.getServiceStatisticByProviderId(providerId,month);
    }

    @RequestMapping(value = "/rating/add", method = RequestMethod.GET)
    public ResponseEntity<Rating> newRating(@RequestParam(name = "customerId") int customerId
            ,@RequestParam(name = "providerServiceId") int providerServiceId
            ,@RequestParam(name = "title") String title
            ,@RequestParam(name = "score") float score
            ,@RequestParam(name = "content") String content
            ,@RequestParam(name = "ratingDate") Date ratingDate) throws CustomException {
        if(customerService.getCustomerById(customerId)==null)
            throw new CustomException("customerId not found!");
        if(providerServiceService.getProviderServiceById(providerServiceId)==null)
            throw new CustomException("providerServiceId not found!");
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setCustomer(customerService.getCustomerById(customerId));
        ratingEntity.setProviderServices(providerServiceService.getProviderServiceById(providerServiceId));
        ratingEntity.setContent(content);
        ratingEntity.setScore(score);
        ratingEntity.setTitle(title);
        ratingEntity.setRatingDate(ratingDate);
        return new ResponseEntity<Rating>(ratingService.saveRating(ratingEntity), HttpStatus.OK);
    }

    @RequestMapping(value = "/booking/add",method = RequestMethod.GET)
    public ResponseEntity<Booking> newBooking(@RequestParam(name = "customerId") int customerId,
                                                   @RequestParam(name = "providerServiceId") int providerServiceId,
                                                   @RequestParam(name = "bookingDate") Date bookingDate,
                                                   @RequestParam(name = "workingDate") Date workingDate,
                                                   @RequestParam(name = "description") String description) throws CustomException {
        if(customerService.getCustomerById(customerId)==null)
            throw new CustomException("customerId not found!");
        if(providerServiceService.getProviderServiceById(providerServiceId)==null)
            throw new CustomException("providerServiceId not found!");
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setCustomer(customerService.getCustomerById(customerId));
        bookingEntity.setProviderServices(providerServiceService.getProviderServiceById(providerServiceId));
        bookingEntity.setBookingDate(bookingDate);
        bookingEntity.setWorkingDate(workingDate);
        bookingEntity.setDescription(description);
        bookingEntity.setStatus(0);
        return new ResponseEntity<Booking>(bookingService.saveBooking(bookingEntity), HttpStatus.OK);
    }
    @RequestMapping(value = "/booking/status",method = RequestMethod.GET)
    public ResponseEntity<Integer> changeBookingStatus(@RequestParam(name = "bookingId") int bookingId
            ,@RequestParam(name = "status") int status) throws CustomException {
        if(bookingService.getBookingById(bookingId)==null)
            throw new CustomException("bookingId is not valid!");
        return new ResponseEntity<Integer>(bookingService.setBookingStatusById(bookingId,status), HttpStatus.OK);
    }

    @RequestMapping(value = "/booking/list", method = RequestMethod.GET)
    public List<Booking> showBookings(){
        return bookingService.getAllBookings();
    }

    @RequestMapping(value = "/booking/provider", method = RequestMethod.GET)
    public List<Booking> showBookingByProvider(@RequestParam(name = "providerId") int providerId){
        return bookingService.getBookingByProviderId(providerId);
    }

    @RequestMapping(value = "/staff/list", method = RequestMethod.GET)
    public List<Staff> showStaffs(){
        return staffService.getAllStaffs();
    }

    @RequestMapping(value = "/staff/provider", method = RequestMethod.GET)
    public List<Staff> showStaffByProvider(@RequestParam(name = "providerId") int providerId){
        return staffService.getStaffBProviderId(providerId);
    }

    @RequestMapping(value = "/staff/delete", method = RequestMethod.GET)
    public ResponseEntity<Integer> deleteStaff(@RequestParam(name = "staffId") int staffId) throws CustomException {
        if(staffService.getStaffbyId(staffId)==null)
            throw new CustomException("staffId is not valid!");
        return new ResponseEntity<Integer>(staffService.removeStaffById(staffId), HttpStatus.OK);
    }

    @RequestMapping(value = "/staff/add",method = RequestMethod.GET)
    public ResponseEntity<Staff> newStaff(@RequestParam(name = "name") String name,
                                              @RequestParam(name = "phoneNumber") String phoneNumber,
                                              @RequestParam(name = "providerId") int providerId) throws CustomException {
        if(staffService.getStaffByPhoneNumber(phoneNumber)!=null)
            throw new CustomException("This phone number is already in use!");
        if(providerService.getProviderById(providerId)==null)
            throw new CustomException("providerId not found!");
        StaffEntity staffEntity = new StaffEntity();
        staffEntity.setName(name);
        staffEntity.setPhoneNumber(phoneNumber);
        staffEntity.setProvider(providerService.getProviderById(providerId));
        return new ResponseEntity<Staff>(staffService.addStaff(staffEntity), HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }
}
