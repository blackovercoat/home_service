package com.dolphine.my_services.service.customernotification;

import com.dolphine.my_services.dto.CustomerNotification;
import com.dolphine.my_services.model.CustomerNotificationEntity;

import java.util.List;

/**
 * Created by PC on 5/15/2017.
 */
public interface CustomerNotificationService {

    CustomerNotification saveCustomerNotification(CustomerNotificationEntity customerNotificationEntity);
    List<CustomerNotification> getCustomerNotificationByCustomerId(int customerId);
}
