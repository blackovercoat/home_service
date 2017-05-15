package com.dolphine.my_services.repository;

import com.dolphine.my_services.model.ProviderNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderNotificationRepository extends JpaRepository<ProviderNotificationEntity,Integer> {

}
