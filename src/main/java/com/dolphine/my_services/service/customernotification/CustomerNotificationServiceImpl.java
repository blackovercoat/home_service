package com.dolphine.my_services.service.customernotification;

import com.dolphine.my_services.dto.Customer;
import com.dolphine.my_services.dto.CustomerNotification;
import com.dolphine.my_services.model.CustomerNotificationEntity;
import com.dolphine.my_services.repository.CustomerNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 5/15/2017.
 */
@Service
public class CustomerNotificationServiceImpl implements CustomerNotificationService {

    @Autowired
    final private CustomerNotificationRepository customerNotificationRepository;

    public CustomerNotificationServiceImpl(CustomerNotificationRepository customerNotificationRepository) {
        this.customerNotificationRepository = customerNotificationRepository;
    }

    @Override
    public CustomerNotification saveCustomerNotification(CustomerNotificationEntity customerNotificationEntity) {
        customerNotificationRepository.save(customerNotificationEntity);
        Customer customer = new Customer(customerNotificationEntity.getCustomer().getId()
                ,customerNotificationEntity.getCustomer().getName()
                ,customerNotificationEntity.getCustomer().getEmail()
                ,customerNotificationEntity.getCustomer().getPassword()
                ,customerNotificationEntity.getCustomer().getPhoneNumber()
                ,customerNotificationEntity.getCustomer().getAddress()
                ,customerNotificationEntity.getCustomer().getLongitude()
                ,customerNotificationEntity.getCustomer().getLatitude());
        CustomerNotification customerNotification = new CustomerNotification(
                customerNotificationEntity.getId()
                ,customerNotificationEntity.getContent()
                ,customerNotificationEntity.getSendDate()
                ,customer);
        return customerNotification;
    }

    @Override
    public List<CustomerNotification> getCustomerNotificationByCustomerId(int customerId) {
        List<CustomerNotification> customerNotifications = new ArrayList<>();
        List<CustomerNotificationEntity> customerNotificationEntities = customerNotificationRepository.findByCustomer_Id(customerId);
        for(CustomerNotificationEntity customerNotificationEntity : customerNotificationEntities){
            Customer customer = new Customer(customerNotificationEntity.getCustomer().getId()
                    ,customerNotificationEntity.getCustomer().getName()
                    ,customerNotificationEntity.getCustomer().getEmail()
                    ,customerNotificationEntity.getCustomer().getPassword()
                    ,customerNotificationEntity.getCustomer().getPhoneNumber()
                    ,customerNotificationEntity.getCustomer().getAddress()
                    ,customerNotificationEntity.getCustomer().getLongitude()
                    ,customerNotificationEntity.getCustomer().getLatitude());
            customerNotifications.add(new CustomerNotification(customerNotificationEntity.getId()
                    , customerNotificationEntity.getContent()
                    ,customerNotificationEntity.getSendDate()
                    ,customer));
        }
        return customerNotifications;
    }
}
