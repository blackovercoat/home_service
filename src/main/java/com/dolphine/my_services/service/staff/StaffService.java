package com.dolphine.my_services.service.staff;

import com.dolphine.my_services.dto.Staff;
import com.dolphine.my_services.model.StaffEntity;

import java.util.List;

/**
 * Created by PC on 4/27/2017.
 */
public interface StaffService {

    int removeStaffById(int staffId);
    List<Staff> getAllStaffs();
    List<Staff> getStaffBProviderId(int providerId);
    Staff addStaff(StaffEntity staffEntity);
    StaffEntity getStaffbyId(int staffId);
    StaffEntity getStaffByPhoneNumber(String phoneNumber);
}
