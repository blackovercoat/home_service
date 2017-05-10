package com.dolphine.my_services.service.providerservice;

import com.dolphine.my_services.dto.ProviderServiceWebService;
import com.dolphine.my_services.model.ProviderServiceEntity;

import java.util.List;

/**
 * Created by PC on 4/12/2017.
 */
public interface ProviderServiceService {
    List<ProviderServiceEntity> getProviderServiceByProviderId(int providerId);
    List<ProviderServiceWebService> getProviderRatingByProviderId(int providerId);
    List<ProviderServiceWebService> getProviderRatingByServiceId(int serviceId);
    ProviderServiceEntity getProviderServiceById(int providerServiceId);
    ProviderServiceWebService addProviderService(ProviderServiceEntity providerServiceEntity);
}
