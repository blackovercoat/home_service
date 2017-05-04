package com.dolphine.my_services.repository;

import com.dolphine.my_services.model.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PC on 4/19/2017.
 */
@Repository
public interface RatingRepository extends JpaRepository<RatingEntity,Integer> {

    List<RatingEntity> findByProviderServices_Id(int providerServiceId);
}
