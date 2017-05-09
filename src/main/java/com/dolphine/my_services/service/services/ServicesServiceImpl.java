package com.dolphine.my_services.service.services;

import com.dolphine.my_services.dto.Catalog;
import com.dolphine.my_services.dto.ServiceDTOWebService;
import com.dolphine.my_services.dto.ServiceForm;
import com.dolphine.my_services.dto.ServiceDTO;
import com.dolphine.my_services.model.CatalogEntity;
import com.dolphine.my_services.model.ServiceEntity;
import com.dolphine.my_services.repository.CatalogRepository;
import com.dolphine.my_services.repository.ServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 4/10/2017.
 */
@Service
public class ServicesServiceImpl implements ServicesService {

    final private ServiceRepository serviceRepository;
    final private CatalogRepository catalogRepository;

    public ServicesServiceImpl(ServiceRepository serviceRepository, CatalogRepository catalogRepository) {
        this.serviceRepository = serviceRepository;
        this.catalogRepository = catalogRepository;
    }

    @Override
    public ServiceEntity addService(ServiceForm serviceForm) {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName(serviceForm.getName());
        serviceEntity.setDescription(serviceForm.getDescription());
        serviceEntity.setCatalog(catalogRepository.findOne(serviceForm.getCatalogId()));
        serviceEntity.setImage(serviceForm.getImage());
        return serviceRepository.save(serviceEntity);
    }

    @Override
    public ServiceDTOWebService addServiceWebService(ServiceEntity serviceEntity) {
        Catalog catalog = new Catalog(serviceEntity.getCatalog().getId()
                ,serviceEntity.getCatalog().getName()
                ,serviceEntity.getCatalog().getDescription()
                ,serviceEntity.getCatalog().getImage());
        ServiceDTOWebService serviceDTO = new ServiceDTOWebService();
        serviceDTO.setName(serviceEntity.getName());
        serviceDTO.setDescription(serviceEntity.getDescription());
        serviceDTO.setImage(serviceEntity.getImage());
        serviceDTO.setCatalog(catalog);
        serviceRepository.save(serviceEntity);
        return serviceDTO;
    }

    @Override
    public List<ServiceDTO> getServicesByCatalogId(int catalogId) {
        List<ServiceEntity> serviceEntities = serviceRepository.findByCatalog_Id(catalogId);
        List<ServiceDTO> services = new ArrayList<>();
        for (ServiceEntity serviceEntity : serviceEntities)
            services.add(new ServiceDTO(serviceEntity.getId()
                    ,serviceEntity.getName()
                    ,serviceEntity.getDescription()
                    ,serviceEntity.getCatalog().getId()
                    ,serviceEntity.getImage()
                    ,serviceEntity.getPrice()));
        return services;
    }

    @Transactional
    @Override
    public void removeServiceById(int serviceId) {
        serviceRepository.delete(serviceId);
    }

    @Override
    public void setServiceById(ServiceForm serviceForm, int id) {
        serviceRepository.updateById(id,serviceForm.getName()
                ,serviceForm.getDescription()
                ,serviceForm.getPrice()
                ,serviceForm.getImage());
    }

    @Override
    public ServiceEntity getServiceById(int serviceId) {
        return serviceRepository.findOne(serviceId);
    }

}
