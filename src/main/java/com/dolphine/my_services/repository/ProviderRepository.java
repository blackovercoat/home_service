package com.dolphine.my_services.repository;


import com.dolphine.my_services.model.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by PC on 4/4/2017.
 */
@Repository
@EnableTransactionManagement
public interface ProviderRepository extends JpaRepository<ProviderEntity,Integer> {

    ProviderEntity findByEmail(String email);

    ProviderEntity findByPhoneNumber(String phoneNumber);

    @Modifying
    @Transactional
    @Query("update ProviderEntity set name=:name" +
            ", email=:email" +
            ", phoneNumber=:phoneNumber" +
            ", address=:address" +
            ", longitude=:longitude" +
            ", latitude=:latitude" +
            ", image=:image" +
            ", regToken=:token" +
            ", password=:password where id=:id")
    int updateById(@Param(value = "id") int id
            , @Param(value = "name") String name
            , @Param(value = "email") String email
            , @Param(value = "phoneNumber") String phoneNumber
            , @Param(value = "address") String address
            , @Param(value = "longitude") float longitude
            , @Param(value = "latitude") float latitude
            , @Param(value = "image") String image
            , @Param(value = "token") String token
            , @Param(value = "password") String password);
}