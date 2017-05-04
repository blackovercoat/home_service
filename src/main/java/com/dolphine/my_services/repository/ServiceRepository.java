package com.dolphine.my_services.repository;

import com.dolphine.my_services.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by PC on 4/10/2017.
 */
@Repository
@EnableTransactionManagement
public interface ServiceRepository extends JpaRepository<ServiceEntity,Integer> {
    List<ServiceEntity> findByCatalog_Id(int catalogId);

    @Modifying
    @Transactional
    @Query("update ServiceEntity set name=:name" +
            ", description=:description" +
            ", image=:image where id=:id")
    void updateById(@Param(value = "id") int id
            ,@Param(value = "name") String name
            ,@Param(value = "description") String description
            ,@Param(value = "image") String image);
}
