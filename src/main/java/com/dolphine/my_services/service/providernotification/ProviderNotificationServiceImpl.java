package com.dolphine.my_services.service.providernotification;

import com.dolphine.my_services.dto.Provider;
import com.dolphine.my_services.dto.ProviderNotification;
import com.dolphine.my_services.model.ProviderNotificationEntity;
import com.dolphine.my_services.repository.ProviderNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public ProviderNotification saveProviderNotification(ProviderNotificationEntity providerNotificationEntity) {
        providerNotificationRepository.save(providerNotificationEntity);
        Provider provider = new Provider(providerNotificationEntity.getProvider().getId()
                ,providerNotificationEntity.getProvider().getName()
                ,providerNotificationEntity.getProvider().getEmail()
                ,providerNotificationEntity.getProvider().getPhoneNumber()
                ,providerNotificationEntity.getProvider().getAddress()
                ,providerNotificationEntity.getProvider().getLongitude()
                ,providerNotificationEntity.getProvider().getLatitude()
                ,providerNotificationEntity.getProvider().getImage());
        ProviderNotification providerNoti = new ProviderNotification(
                providerNotificationEntity.getId()
                ,providerNotificationEntity.getContent()
                ,providerNotificationEntity.getSendDate()
                ,provider);
        return providerNoti;
    }

    @Override
    public List<ProviderNotification> getProviderNotificationByProviderId(int providerId) {
        List<ProviderNotification> providerNotifications = new ArrayList<>();
        List<ProviderNotificationEntity> providerNotificationEntities = providerNotificationRepository.findByProvider_Id(providerId);
        for(ProviderNotificationEntity providerNotificationEntity : providerNotificationEntities){
            Provider provider = new Provider(providerNotificationEntity.getProvider().getId()
                    ,providerNotificationEntity.getProvider().getName()
                    ,providerNotificationEntity.getProvider().getEmail()
                    ,providerNotificationEntity.getProvider().getPhoneNumber()
                    ,providerNotificationEntity.getProvider().getAddress()
                    ,providerNotificationEntity.getProvider().getLongitude()
                    ,providerNotificationEntity.getProvider().getLatitude()
                    ,providerNotificationEntity.getProvider().getImage());
            providerNotifications.add(new ProviderNotification(
                    providerNotificationEntity.getId()
                    ,providerNotificationEntity.getContent()
                    ,providerNotificationEntity.getSendDate()
                    ,provider));
        }
        return providerNotifications;
    }
}
