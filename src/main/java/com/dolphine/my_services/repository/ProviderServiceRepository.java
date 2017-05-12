package com.dolphine.my_services.repository;

import com.dolphine.my_services.model.ProviderEntity;
import com.dolphine.my_services.model.ProviderServiceEntity;
import com.dolphine.my_services.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by PC on 4/12/2017.
 */
@Repository
@EnableTransactionManagement
public interface ProviderServiceRepository extends JpaRepository<ProviderServiceEntity,Integer> {
    List<ProviderServiceEntity> findByProvider_Id(int providerId);
    List<ProviderServiceEntity> findByService_Id(int serviceId);

    int deleteById(int id);

    @Modifying
    @Transactional
    @Query("update ProviderServiceEntity set " +
            "provider=:provider" +
            ", service=:service" +
            ", maxPrice=:maxPrice" +
            ", minPrice=:minPrice" +
            ", description=:description" +
            ", fromTime=:fromTime" +
            ", toTime=:toTime where id=:id")
    int updateById(@Param(value = "id") int id
            ,@Param(value = "provider") ProviderEntity provider
            ,@Param(value = "service") ServiceEntity service
            ,@Param(value = "maxPrice") float maxPrice
            ,@Param(value = "minPrice") float minPrice
            ,@Param(value = "description") String description
            ,@Param(value = "fromTime") Date fromTime
            ,@Param(value = "toTime") Date toTime);
}
