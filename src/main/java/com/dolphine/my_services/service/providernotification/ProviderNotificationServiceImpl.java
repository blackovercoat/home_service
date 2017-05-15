package com.dolphine.my_services.service.providernotification;

import com.dolphine.my_services.dto.ProviderNoti;
import com.dolphine.my_services.model.ProviderNotificationEntity;
import com.dolphine.my_services.repository.ProviderNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by PC on 5/15/2017.
 */
@Service
public class ProviderNotificationServiceImpl implements ProviderNotificationService {

    @Autowired
    final private ProviderNotificationRepository providerNotificationRepository;

    public ProviderNotificationServiceImpl(ProviderNotificationRepository providerNotificationRepository) {
        this.providerNotificationRepository = providerNotificationRepository;
    }

    @Override
    public ProviderNoti saveProviderNotification(ProviderNotificationEntity providerNotificationEntity) {
        providerNotificationRepository.save(providerNotificationEntity);
        ProviderNoti providerNoti = new ProviderNoti(
                providerNotificationEntity.getId()
                ,providerNotificationEntity.getContent()
                ,providerNotificationEntity.getSendDate()
                ,providerNotificationEntity.getProvider().getId());
        return providerNoti;
    }
}
