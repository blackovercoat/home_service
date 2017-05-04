package com.dolphine.my_services.service.provider;

import com.dolphine.my_services.dto.ProviderForm;
import com.dolphine.my_services.model.ProviderEntity;

import java.util.List;

/**
 * Created by PC on 4/4/2017.
 */
public interface ProviderService {
    List<ProviderEntity> getAllProvider();
    ProviderEntity addProvider(ProviderForm providerForm);
    ProviderEntity getProviderByPhoneNumber(String phoneNumber);
    ProviderEntity getProviderById(int Id);
    ProviderEntity getProviderByEmail(String email);
    void setProviderById(ProviderForm providerForm,int id);
    void removeProviderById(int providerId);
}
