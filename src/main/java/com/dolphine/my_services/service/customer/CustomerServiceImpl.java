package com.dolphine.my_services.service.customer;

import com.dolphine.my_services.dto.Customer;
import com.dolphine.my_services.dto.CustomerForm;
import com.dolphine.my_services.model.CustomerEntity;
import com.dolphine.my_services.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by PC on 3/30/2017.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public CustomerEntity getCustomerByEmail(String email) {
        CustomerEntity customerEntity = customerRepository.findByEmail(email);
        if(customerEntity==null)
            return null;
        return customerEntity;
    }

    @Transactional
    @Override
    public CustomerEntity getCustomerByPhoneNumber(String phoneNumber) {
        CustomerEntity customerEntity = customerRepository.findByPhoneNumber(phoneNumber);
        if(customerEntity==null)
            return null;
        return customerEntity;
    }

    @Override
    public Customer addCustomer(CustomerEntity customerEntity) {
        Customer customer = new Customer();
        customerRepository.save(customerEntity);
        customer.setId(customerEntity.getId());
        customer.setEmail(customerEntity.getEmail());
        customer.setName(customerEntity.getName());
        customer.setAddress(customerEntity.getAddress());
        customer.setLatitude(customerEntity.getLongitude());
        customer.setLatitude(customerEntity.getLatitude());
        customer.setPassword(customerEntity.getPassword());
        customer.setPhoneNumber(customerEntity.getPhoneNumber());
        return customer;
    }

    @Override
    public CustomerEntity getCustomerById(int customerId) {
        return customerRepository.findOne(customerId);
    }

    @Override
    public void removeCustomerById(int customerId) {
        customerRepository.delete(customerId);
    }

    @Override
    public void setCustomerById(CustomerForm customerForm, int id) {
        CustomerEntity customerEntity = customerRepository.getOne(id);
        customerRepository.updateById(id,customerEntity.getName()
                ,customerEntity.getEmail()
                ,customerEntity.getPhoneNumber()
                ,customerEntity.getLongitude()
                ,customerEntity.getLatitude()
                ,customerEntity.getPassword()
                ,customerEntity.getAddress());
    }

    @Override
    public int setCustomerById(CustomerEntity customerEntity, int id) {
        return customerRepository.updateById(id,customerEntity.getName()
                ,customerEntity.getEmail()
                ,customerEntity.getPhoneNumber()
                ,customerEntity.getLongitude()
                ,customerEntity.getLatitude()
                ,customerEntity.getPassword()
                ,customerEntity.getAddress());
    }

    @Override
    public List<CustomerEntity> getAllCustomer() {
        return customerRepository.findAll();
    }
}
