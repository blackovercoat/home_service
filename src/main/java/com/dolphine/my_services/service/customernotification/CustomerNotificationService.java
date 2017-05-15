package com.dolphine.my_services.service.customernotification;

import com.dolphine.my_services.dto.CustomerNoti;
import com.dolphine.my_services.model.CustomerNotificationEntity;

/**
 * Created by PC on 5/15/2017.
 */
public interface CustomerNotificationService {

    CustomerNoti saveCustomerNotification(CustomerNotificationEntity customerNotificationEntity);
}
