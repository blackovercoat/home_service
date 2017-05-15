package com.dolphine.my_services.service.providernotification;

import com.dolphine.my_services.dto.ProviderNotification;
import com.dolphine.my_services.model.ProviderNotificationEntity;

import java.util.List;

/**
 * Created by PC on 5/15/2017.
 */
public interface ProviderNotificationService {

    ProviderNotification saveProviderNotification(ProviderNotificationEntity providerNotificationEntity);
    List<ProviderNotification> getProviderNotificationByProviderId(int providerId);
}
