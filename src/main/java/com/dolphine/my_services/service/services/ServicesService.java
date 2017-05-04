package com.dolphine.my_services.service.services;

import com.dolphine.my_services.dto.ServiceForm;
import com.dolphine.my_services.dto.ServiceDTO;
import com.dolphine.my_services.model.ServiceEntity;

import java.util.List;

/**
 * Created by PC on 4/10/2017.
 */
public interface ServicesService {

    ServiceEntity addService(ServiceForm serviceForm);
    List<ServiceDTO> getServicesByCatalogId(int catalogId);
    void removeServiceById(int serviceId);
    void setServiceById(ServiceForm serviceForm, int id);
    ServiceEntity getServiceById(int serviceId);
}
