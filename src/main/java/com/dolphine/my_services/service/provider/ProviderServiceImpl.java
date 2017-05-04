package com.dolphine.my_services.service.provider;

import com.dolphine.my_services.dto.ProviderForm;
import com.dolphine.my_services.model.ProviderEntity;
import com.dolphine.my_services.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 4/4/2017.
 */
@Service
public class ProviderServiceImpl implements ProviderService{

    @Autowired
    private final ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public List<ProviderEntity> getAllProvider() {
        return providerRepository.findAll();
    }

    @Override
    public ProviderEntity addProvider(ProviderForm providerForm) {
        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setName(providerForm.getName());
        providerEntity.setEmail(providerForm.getEmail());
        providerEntity.setAddress(providerForm.getAddress());
        providerEntity.setLongitude(providerForm.getLongitude());
        providerEntity.setLatitude(providerForm.getLatitude());
        providerEntity.setImage(providerForm.getImage());
        providerEntity.setPhoneNumber(providerForm.getPhoneNumber());
        providerEntity.setPassword(providerForm.getPassword());

        return providerRepository.save(providerEntity);
    }

    @Transactional
    @Override
    public ProviderEntity getProviderByPhoneNumber(String phoneNumber) {
        ProviderEntity providerEntity = providerRepository.findByPhoneNumber(phoneNumber);
        if(providerEntity==null)
            return null;
        return providerEntity;
    }

    @Transactional
    @Override
    public ProviderEntity getProviderById(int Id) {
        return providerRepository.findOne(Id);
    }

    @Transactional
    @Override
    public ProviderEntity getProviderByEmail(String email) {
        ProviderEntity providerEntity = providerRepository.findByEmail(email);
        if(providerEntity==null)
            return null;
        return providerEntity;
    }

    @Override
    public void setProviderById(ProviderForm providerForm, int id) {
        providerRepository.updateById(id,providerForm.getName()
                ,providerForm.getEmail()
                ,providerForm.getPhoneNumber()
                ,providerForm.getAddress()
                ,providerForm.getLongitude()
                ,providerForm.getLatitude()
                ,providerForm.getImage()
                ,providerForm.getPassword());
    }

    @Transactional
    @Override
    public void removeProviderById(int providerId) {
        providerRepository.delete(providerId);
    }
}
