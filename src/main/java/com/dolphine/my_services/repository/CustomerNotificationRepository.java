package com.dolphine.my_services.repository;

import com.dolphine.my_services.model.CustomerNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerNotificationRepository extends JpaRepository<CustomerNotificationEntity,Integer> {
    List<CustomerNotificationEntity> findByCustomer_Id(int customerId);
}
