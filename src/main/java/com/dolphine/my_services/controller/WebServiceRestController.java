package com.dolphine.my_services.controller;

import com.dolphine.my_services.dto.*;
import com.dolphine.my_services.model.*;
import com.dolphine.my_services.service.booking.BookingService;
import com.dolphine.my_services.service.booking_detail.BookingDetailService;
import com.dolphine.my_services.service.catalog.CatalogService;
import com.dolphine.my_services.service.common.CommonService;
import com.dolphine.my_services.service.customer.CustomerService;
import com.dolphine.my_services.service.customernotification.CustomerNotificationService;
import com.dolphine.my_services.service.provider.ProviderService;
import com.dolphine.my_services.service.providernotification.ProviderNotificationService;
import com.dolphine.my_services.service.providerservice.ProviderServiceService;
import com.dolphine.my_services.service.rating.RatingService;
import com.dolphine.my_services.service.services.ServicesService;
import com.dolphine.my_services.service.staff.StaffService;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private final ServicesService servicesService;
    private final BookingDetailService bookingDetailService;
    private final CommonService commonService;
    private final CustomerNotificationService customerNotificationService;
    private final ProviderNotificationService providerNotificationService;

    public WebServiceRestController(CatalogService catalogService, ProviderService providerService, CustomerService customerService, ProviderServiceService providerServiceService, BookingService bookingService, RatingService ratingService, StaffService staffService, ServicesService servicesService, BookingDetailService bookingDetailService, CommonService commonService, CustomerNotificationService customerNotificationService, ProviderNotificationService providerNotificationService) {
        this.catalogService = catalogService;
        this.providerService = providerService;
        this.customerService = customerService;
        this.providerServiceService = providerServiceService;
        this.bookingService = bookingService;
        this.ratingService = ratingService;
        this.staffService = staffService;
        this.servicesService = servicesService;
        this.bookingDetailService = bookingDetailService;
        this.commonService = commonService;
        this.customerNotificationService = customerNotificationService;
        this.providerNotificationService = providerNotificationService;
    }

    @RequestMapping(value = "customer/login", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomerInfo(@RequestParam(name = "email") String email,
                                                    @RequestParam(name = "password") String password,
                                                    @RequestParam(name = "token") String token) throws CustomException {
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
        customerEntity.setRegToken(token);
        customerService.setCustomerById(customerEntity,customerEntity.getId());
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "customer/edit", method = RequestMethod.GET)
    public ResponseEntity<Integer> updateCustomer(@RequestParam(name = "customerId") int customerId,
                                 @RequestParam(name = "email") String email,
                                 @RequestParam(name = "password") String password,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "longitude") float longitude,
                                 @RequestParam(name = "latitude") float latitude,
                                 @RequestParam(name = "phoneNumber") String phoneNumber,
                                 @RequestParam(name = "address") String address) throws CustomException {
        if(customerService.getCustomerById(customerId)==null)
            throw new CustomException("customerId is not valid!");
        if(customerService.getCustomerByEmail(email)!=null&&!customerService.getCustomerById(customerId).getEmail().equalsIgnoreCase(email))
            throw new CustomException("This email is already in use!");
        if(customerService.getCustomerByPhoneNumber(phoneNumber)!=null&&!customerService.getCustomerById(customerId).getPhoneNumber().equalsIgnoreCase(phoneNumber))
            throw new CustomException("This phone number is already in use!");
        CustomerEntity customerEntity = customerService.getCustomerById(customerId);
        customerEntity.setEmail(email);
        customerEntity.setName(name);
        customerEntity.setPassword(password);
        customerEntity.setLongitude(longitude);
        customerEntity.setLatitude(latitude);
        customerEntity.setPhoneNumber(phoneNumber);
        customerEntity.setAddress(address);
        return new ResponseEntity<Integer>(customerService.setCustomerById(customerEntity,customerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/notification",method = RequestMethod.GET)
    public ResponseEntity<List<CustomerNotification>> listCustomerNotification(@RequestParam(name = "customerId") int customerId) throws CustomException {
        if(customerService.getCustomerById(customerId)==null)
            throw new CustomException("customerId is not valid!");
        return new ResponseEntity<List<CustomerNotification>>(customerNotificationService.getCustomerNotificationByCustomerId(customerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/provider/notification",method = RequestMethod.GET)
    public ResponseEntity<List<ProviderNotification>> listProviderNotification(@RequestParam(name = "providerId") int providerId) throws CustomException {
        if(providerService.getProviderById(providerId)==null)
            throw new CustomException("providerId is not valid!");
        return new ResponseEntity<List<ProviderNotification>>(providerNotificationService.getProviderNotificationByProviderId(providerId), HttpStatus.OK);
    }

    @RequestMapping(value = "provider/login", method = RequestMethod.GET)
    public ResponseEntity<Provider> getProviderInfo(@RequestParam(name = "email") String email,
                                                    @RequestParam(name = "password") String password,
                                                    @RequestParam(name = "token") String token) throws CustomException {
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
        providerEntity.setRegToken(token);
        providerService.setProviderById(providerEntity,providerEntity.getId());
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

    @RequestMapping(value = "provider/edit", method = RequestMethod.GET)
    public ResponseEntity<Integer> updateProvider(@RequestParam(name = "providerId") int providerId,
                                                         @RequestParam(name = "name") String name,
                                                         @RequestParam(name = "email") String email,
                                                         @RequestParam(name = "phoneNumber") String phoneNumber,
                                                         @RequestParam(name = "address") String address,
                                                         @RequestParam(name = "password") String password,
                                                         @RequestParam(name = "longitude") float longitude,
                                                         @RequestParam(name = "latitude") float latitude,
                                                         @RequestParam(name = "image") String image) throws CustomException {
        if(providerService.getProviderById(providerId)==null)
            throw new CustomException("providerId is not valid!");
        if(providerService.getProviderByEmail(email)!=null&&!providerService.getProviderById(providerId).getEmail().equalsIgnoreCase(email))
            throw new CustomException("This email is already in use!");
        if(providerService.getProviderByPhoneNumber(phoneNumber)!=null&&!providerService.getProviderById(providerId).getPhoneNumber().equalsIgnoreCase(phoneNumber))
            throw new CustomException("This phone number is already in use!");
        ProviderEntity providerEntity = providerService.getProviderById(providerId);
        providerEntity.setName(name);
        providerEntity.setEmail(email);
        providerEntity.setPassword(password);
        providerEntity.setPhoneNumber(phoneNumber);
        providerEntity.setAddress(address);
        providerEntity.setLongitude(longitude);
        providerEntity.setLatitude(latitude);
        providerEntity.setImage(image);
        return new ResponseEntity<Integer>(providerService.setProviderById(providerEntity,providerId), HttpStatus.OK);
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
    @RequestMapping(value = "/providerService/add",method = RequestMethod.GET)
    public ResponseEntity<ProviderServiceWebService> newProviderService(@RequestParam(name = "providerId") int providerId,
                                                           @RequestParam(name = "serviceId") int serviceId,
                                                           @RequestParam(name = "maxPrice") float maxPrice,
                                                           @RequestParam(name = "minPrice") float minPrice,
                                                           @RequestParam(name = "fromTime") Date fromTime,
                                                           @RequestParam(name = "toTime") Date toTime,
                                                           @RequestParam(name = "description") String description) throws CustomException {
        if(servicesService.getServiceById(serviceId)==null)
            throw new CustomException("serviceId not found!");
        if(providerService.getProviderById(providerId)==null)
            throw new CustomException("providerId not found!");
        ProviderServiceEntity providerServiceEntity = new ProviderServiceEntity();
        providerServiceEntity.setProvider(providerService.getProviderById(providerId));
        providerServiceEntity.setService(servicesService.getServiceById(serviceId));
        providerServiceEntity.setDescription(description);
        providerServiceEntity.setMinPrice(minPrice);
        providerServiceEntity.setMaxPrice(maxPrice);
        providerServiceEntity.setFromTime(fromTime);
        providerServiceEntity.setToTime(toTime);
        return new ResponseEntity<ProviderServiceWebService>(providerServiceService.addProviderService(providerServiceEntity), HttpStatus.OK);
    }

    @RequestMapping(value = "providerService/edit", method = RequestMethod.GET)
    public ResponseEntity<Integer> updateProviderService(@RequestParam(name = "providerServiceId") int providerServiceId,
                                                         @RequestParam(name = "providerId") int providerId,
                                                         @RequestParam(name = "serviceId") int serviceId,
                                                         @RequestParam(name = "maxPrice") float maxPrice,
                                                         @RequestParam(name = "minPrice") float minPrice,
                                                         @RequestParam(name = "fromTime") Date fromTime,
                                                         @RequestParam(name = "toTime") Date toTime,
                                                         @RequestParam(name = "description") String description) throws CustomException {
        if(providerServiceService.getProviderServiceById(providerServiceId)==null)
            throw new CustomException("providerServiceId is not valid!");
        if(providerService.getProviderById(providerId)==null)
            throw new CustomException("providerId is not valid!");
        if(servicesService.getServiceById(serviceId)==null)
            throw new CustomException("serviceId is not valid!");
        ProviderServiceEntity providerServiceEntity = providerServiceService.getProviderServiceById(providerServiceId);
        providerServiceEntity.setProvider(providerService.getProviderById(providerId));
        providerServiceEntity.setService(servicesService.getServiceById(serviceId));
        providerServiceEntity.setMaxPrice(maxPrice);
        providerServiceEntity.setMinPrice(minPrice);
        providerServiceEntity.setFromTime(fromTime);
        providerServiceEntity.setToTime(toTime);
        providerServiceEntity.setDescription(description);
        return new ResponseEntity<Integer>(providerServiceService.setProviderServiceById(providerServiceEntity,providerServiceId), HttpStatus.OK);
    }

    @RequestMapping(value = "providerService/delete",method = RequestMethod.GET)
    public ResponseEntity<Integer> removeProviderService(@RequestParam(name = "providerServiceId") int providerServiceId) throws CustomException {
        if(providerServiceService.getProviderServiceById(providerServiceId)==null)
            throw new CustomException("providerServiceId is not valid!");
        return new ResponseEntity<Integer>(providerServiceService.removeProviderServicebyProviderServiceId(providerServiceId), HttpStatus.OK);
    }

    @RequestMapping(value = "/rating", method = RequestMethod.GET)
    public ResponseEntity<ServiceRating> getRating(@RequestParam(name = "providerServiceId") int providerServiceId) throws CustomException {
        if(providerServiceService.getProviderServiceById(providerServiceId)==null)
            throw new CustomException("providerServiceId is not valid!");
        ServiceRating serviceRating = ratingService.getServiceRatingandServiceByProviderServiceId(providerServiceId);
        return new ResponseEntity<ServiceRating>(serviceRating, HttpStatus.OK);
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
                                                   @RequestParam(name = "description") String description) throws CustomException, IOException, JSONException {
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
        String message = "Bạn vừa nhận được 1 booking dịch vụ "
                +providerServiceService.getProviderServiceById(providerServiceId).getService().getName()
                +" từ khách hàng "+customerService.getCustomerById(customerId).getName();
        commonService.sendNotification(
                "Booking mới"
                ,message
                ,providerServiceService.getProviderServiceById(providerServiceId).getProvider().getRegToken());
        ProviderNotificationEntity providerNotificationEntity = new ProviderNotificationEntity();
        providerNotificationEntity.setContent(message);
        providerNotificationEntity.setProvider(providerServiceService.getProviderServiceById(providerServiceId).getProvider());
        Date currentDate = Calendar.getInstance().getTime();
        providerNotificationEntity.setSendDate(currentDate);
        providerNotificationService.saveProviderNotification(providerNotificationEntity);
        return new ResponseEntity<Booking>(bookingService.saveBooking(bookingEntity), HttpStatus.OK);
    }

    @RequestMapping(value = "/booking/status",method = RequestMethod.GET)
    public ResponseEntity<Integer> changeBookingStatus(@RequestParam(name = "bookingId") int bookingId
            ,@RequestParam(name = "status") int status) throws CustomException, IOException, JSONException {
        if(bookingService.getBookingById(bookingId)==null)
            throw new CustomException("bookingId is not valid!");
        String title="";
        String message="";
        String token="";
        Date dateFromUnixTime = bookingService.getBookingById(bookingId).getWorkingDate();
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm MM/dd/yyyy");
        sdfDate.format(dateFromUnixTime);
        if(status==1){
            title = "Dịch vụ được chấp nhập";
            message = "Dịch vụ "
                    + bookingService.getBookingById(bookingId).getProviderServices().getService().getName()
                    +" đã được chấp nhận! dịch vụ sẽ được tiến hành vào lúc "
                    +sdfDate.format(dateFromUnixTime);
            token = bookingService.getBookingById(bookingId).getCustomer().getRegToken();
            commonService.sendNotification(title,message,token);
            CustomerNotificationEntity customerNotificationEntity = new CustomerNotificationEntity();
            customerNotificationEntity.setContent(message);
            customerNotificationEntity.setCustomer(bookingService.getBookingById(bookingId).getCustomer());
            Date currentDate = Calendar.getInstance().getTime();
            customerNotificationEntity.setSendDate(currentDate);
            customerNotificationService.saveCustomerNotification(customerNotificationEntity);
        }
        if(status==2){
            title = "Dịch vụ bị từ chối";
            message = "Dịch vụ "
                    + bookingService.getBookingById(bookingId).getProviderServices().getService().getName()
                    +" đã bị từ chối!";
            token = bookingService.getBookingById(bookingId).getCustomer().getRegToken();
            commonService.sendNotification(title,message,token);
            CustomerNotificationEntity customerNotificationEntity = new CustomerNotificationEntity();
            customerNotificationEntity.setContent(message);
            customerNotificationEntity.setCustomer(bookingService.getBookingById(bookingId).getCustomer());
            Date currentDate = Calendar.getInstance().getTime();
            customerNotificationEntity.setSendDate(currentDate);
            customerNotificationService.saveCustomerNotification(customerNotificationEntity);
        }
        if(status==3){
            title = "Dịch vụ bị hủy bởi người dùng!";
            message = "Dịch vụ "
                    + bookingService.getBookingById(bookingId).getProviderServices().getService().getName()
                    +" đã bị hủy!";
            token = bookingService.getBookingById(bookingId).getProviderServices().getProvider().getRegToken();
            commonService.sendNotification(title,message,token);
            ProviderNotificationEntity providerNotificationEntity = new ProviderNotificationEntity();
            providerNotificationEntity.setContent(message);
            providerNotificationEntity.setProvider(bookingService.getBookingById(bookingId).getProviderServices().getProvider());
            Date currentDate = Calendar.getInstance().getTime();
            providerNotificationEntity.setSendDate(currentDate);
            providerNotificationService.saveProviderNotification(providerNotificationEntity);
        }
        return new ResponseEntity<Integer>(bookingService.setBookingStatusById(bookingId,status), HttpStatus.OK);
    }

    @RequestMapping(value = "/booking/history", method = RequestMethod.GET)
    public List<BookingHistory> getBookingHistory(@RequestParam(name = "customerId") int customerId) {
        return bookingService.getBookingHistorybyCustomerId(customerId);
    }

    @RequestMapping(value = "/bookingDetail/add",method = RequestMethod.GET)
    public ResponseEntity<BookingDetail> addStaffForBooking(@RequestParam(name = "bookingId") int bookingId
            ,@RequestParam(name = "staffId") int staffId) throws CustomException {
        if(bookingService.getBookingById(bookingId)==null)
            throw new CustomException("bookingId is not valid!");
        if(staffService.getStaffbyId(staffId)==null)
            throw new CustomException("staffId is not valid!");
        BookingDetailEntityPK bookingDetailEntityPK = new BookingDetailEntityPK();
        bookingDetailEntityPK.setBookingById(bookingService.getBookingById(bookingId));
        bookingDetailEntityPK.setStaffById(staffService.getStaffbyId(staffId));
        BookingDetailEntity bookingDetailEntity = new BookingDetailEntity();
        bookingDetailEntity.setBookingDetailEntityPK(bookingDetailEntityPK);
        return new ResponseEntity<BookingDetail>(bookingDetailService.addBookingDetail(bookingDetailEntity), HttpStatus.OK);
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

    @RequestMapping(value = "/service/add",method = RequestMethod.GET)
    public ResponseEntity<ServiceDTOWebService> newService(@RequestParam(name = "name") String name,
                                            @RequestParam(name = "description") String description,
                                            @RequestParam(name = "price") float price,
                                            @RequestParam(name = "image") String image,
                                            @RequestParam(name = "catalogId") int catalogId) throws CustomException {
        if(catalogService.getCatalogById(catalogId)==null)
            throw new CustomException("catalogId not found!");
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName(name);
        serviceEntity.setDescription(description);
        serviceEntity.setImage(image);
        serviceEntity.setPrice(price);
        serviceEntity.setCatalog(catalogService.getCatalogById(catalogId));
        return new ResponseEntity<ServiceDTOWebService>(servicesService.addServiceWebService(serviceEntity), HttpStatus.OK);
    }

    @RequestMapping(value = "/catalog/add",method = RequestMethod.GET)
    public ResponseEntity<Catalog> newCatalog(@RequestParam(name = "name") String name,
                                              @RequestParam(name = "description") String description,
                                              @RequestParam(name = "image") String image) throws CustomException {
        CatalogEntity catalogEntity = new CatalogEntity();
        catalogEntity.setName(name);
        catalogEntity.setDescription(description);
        catalogEntity.setImage(image);
        return new ResponseEntity<Catalog>(catalogService.addCatalogWebService(catalogEntity), HttpStatus.OK);
    }

    @RequestMapping(value = "/notification/send", method = RequestMethod.GET)
    public ResponseMessage sendNotification(@RequestParam(name = "title") String title,
                                   @RequestParam(name = "message") String message,
                                   @RequestParam(name = "clientToken") String clientToken) throws IOException, JSONException {
        return commonService.sendNotification(title,message,clientToken);
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }
}
