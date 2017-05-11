package com.dolphine.my_services.service.customer;

import com.dolphine.my_services.dto.Customer;
import com.dolphine.my_services.dto.CustomerForm;
import com.dolphine.my_services.model.CustomerEntity;

import java.util.List;

/**
 * Created by PC on 3/30/2017.
 */
public interface CustomerService {

    CustomerEntity getCustomerByEmail(String email);
    CustomerEntity getCustomerByPhoneNumber(String phoneNumber);
    Customer addCustomer(CustomerEntity customerEntity);
    CustomerEntity getCustomerById(int customerId);
    void removeCustomerById(int customerId);
    void setCustomerById(CustomerForm customerForm, int id);
    int setCustomerById(CustomerEntity customerEntity, int id);
    List<CustomerEntity> getAllCustomer();
}
