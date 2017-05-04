package com.dolphine.my_services.repository;

import com.dolphine.my_services.model.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PC on 4/27/2017.
 */
@Repository
public interface StaffRepository extends JpaRepository<StaffEntity,Integer> {
    int deleteById(int staffId);
    StaffEntity findByPhoneNumber(String phoneNumber);
    List<StaffEntity> findByProvider_Id(int providerId);
}
