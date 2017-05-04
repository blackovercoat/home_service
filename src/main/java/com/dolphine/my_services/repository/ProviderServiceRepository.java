package com.dolphine.my_services.repository;

import com.dolphine.my_services.model.ProviderServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PC on 4/12/2017.
 */
@Repository
public interface ProviderServiceRepository extends JpaRepository<ProviderServiceEntity,Integer> {
    List<ProviderServiceEntity> findByProvider_Id(int providerId);
    List<ProviderServiceEntity> findByService_Id(int serviceId);
}
