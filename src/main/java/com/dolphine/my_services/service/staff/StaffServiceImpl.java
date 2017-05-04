package com.dolphine.my_services.service.staff;

import com.dolphine.my_services.dto.Staff;
import com.dolphine.my_services.model.StaffEntity;
import com.dolphine.my_services.repository.StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 4/27/2017.
 */
@Service
public class StaffServiceImpl implements StaffService {

    final private StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Transactional
    @Override
    public int removeStaffById(int staffId) {
        return staffRepository.deleteById(staffId);
    }

    @Override
    public List<Staff> getAllStaffs() {
        List<StaffEntity> staffEntities = staffRepository.findAll();
        List<Staff> staffList = new ArrayList<>();
        for(StaffEntity staffEntity : staffEntities)
            staffList.add(new Staff(staffEntity.getId()
                    ,staffEntity.getName()
                    ,staffEntity.getPhoneNumber()
                    ,staffEntity.getProvider().getId()));
        return staffList;
    }

    @Override
    public List<Staff> getStaffBProviderId(int providerId) {
        List<StaffEntity> staffEntities = staffRepository.findByProvider_Id(providerId);
        List<Staff> staffs = new ArrayList<>();
        for(StaffEntity staffEntity : staffEntities)
            staffs.add(new Staff(staffEntity.getId()
                    ,staffEntity.getName()
                    ,staffEntity.getPhoneNumber()
                    ,staffEntity.getProvider().getId()));
        return staffs;
    }

    @Override
    public Staff addStaff(StaffEntity staffEntity) {
        staffRepository.save(staffEntity);
        Staff staff =  new Staff();
        staff.setId(staffEntity.getId());
        staff.setPhoneNumber(staffEntity.getPhoneNumber());
        staff.setName(staffEntity.getName());
        staff.setProviderId(staffEntity.getProvider().getId());
        return staff;
    }

    @Override
    public StaffEntity getStaffbyId(int staffId) {
        return staffRepository.findOne(staffId);
    }

    @Transactional
    @Override
    public StaffEntity getStaffByPhoneNumber(String phoneNumber) {
        StaffEntity staffEntity = staffRepository.findByPhoneNumber(phoneNumber);
        if(staffEntity==null)
            return null;
        return staffEntity;
    }
}
