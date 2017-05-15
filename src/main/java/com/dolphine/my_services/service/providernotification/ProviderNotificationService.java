package com.dolphine.my_services.service.providernotification;

import com.dolphine.my_services.dto.ProviderNoti;
import com.dolphine.my_services.model.ProviderNotificationEntity;

/**
 * Created by PC on 5/15/2017.
 */
public interface ProviderNotificationService {
    ProviderNoti saveProviderNotification(ProviderNotificationEntity providerNotificationEntity);
}
