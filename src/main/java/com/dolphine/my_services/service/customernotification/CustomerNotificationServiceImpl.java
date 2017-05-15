package com.dolphine.my_services.service.customernotification;

import com.dolphine.my_services.dto.CustomerNoti;
import com.dolphine.my_services.model.CustomerNotificationEntity;
import com.dolphine.my_services.repository.CustomerNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public CustomerNoti saveCustomerNotification(CustomerNotificationEntity customerNotificationEntity) {
        customerNotificationRepository.save(customerNotificationEntity);
        CustomerNoti customerNoti = new CustomerNoti(
                customerNotificationEntity.getId()
                ,customerNotificationEntity.getContent()
                ,customerNotificationEntity.getSendDate()
                ,customerNotificationEntity.getCustomer().getId());
        return customerNoti;
    }
}
