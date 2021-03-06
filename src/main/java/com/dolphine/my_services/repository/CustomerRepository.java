package com.dolphine.my_services.repository;

import com.dolphine.my_services.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by PC on 3/30/2017.
 */
@Repository
@EnableTransactionManagement
public interface CustomerRepository extends JpaRepository<CustomerEntity,Integer> {

    CustomerEntity findByEmail(String email);
    CustomerEntity findByPhoneNumber(String phoneNumber);

    @Modifying
    @Transactional
    @Query("update CustomerEntity set name=:name" +
            ", email=:email" +
            ", phoneNumber=:phoneNumber" +
            ", longitude=:longitude" +
            ", latitude=:latitude" +
            ", password=:password" +
            ", regToken=:token" +
            ", address=:address where id=:id")
    int updateById(@Param(value = "id") int id
            ,@Param(value = "name") String name
            ,@Param(value = "email") String email
            ,@Param(value = "phoneNumber") String phoneNumber
            ,@Param(value = "longitude") float longitude
            ,@Param(value = "latitude") float latitude
            ,@Param(value = "password") String password
            ,@Param(value = "token") String token
            ,@Param(value = "address") String address);
}
