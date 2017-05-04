package com.dolphine.my_services.repository;

import com.dolphine.my_services.model.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by PC on 4/19/2017.
 */
@Repository
@EnableTransactionManagement
public interface BookingRepository extends JpaRepository<BookingEntity,Integer> {

    List<BookingEntity> findByCustomer_Id(int customerId);

    @Modifying
    @Transactional
    @Query("update BookingEntity set status=:status where id=:id")
    int updateBookingStatusById(@Param(value = "id")int id,@Param(value = "status")int status);
}
