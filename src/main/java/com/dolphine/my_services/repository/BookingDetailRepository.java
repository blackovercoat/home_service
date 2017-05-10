package com.dolphine.my_services.repository;

import com.dolphine.my_services.model.BookingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PC on 5/10/2017.
 */
@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetailEntity, Integer> {

}
