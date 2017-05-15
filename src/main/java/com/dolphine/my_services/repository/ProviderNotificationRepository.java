package com.dolphine.my_services.repository;

import com.dolphine.my_services.model.ProviderNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderNotificationRepository extends JpaRepository<ProviderNotificationEntity,Integer> {
    List<ProviderNotificationEntity> findByProvider_Id(int providerId);
}
