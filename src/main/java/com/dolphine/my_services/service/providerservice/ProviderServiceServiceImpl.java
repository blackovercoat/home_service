package com.dolphine.my_services.service.providerservice;

import com.dolphine.my_services.dto.*;
import com.dolphine.my_services.model.ProviderServiceEntity;
import com.dolphine.my_services.model.RatingEntity;
import com.dolphine.my_services.repository.ProviderServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 4/12/2017.
 */
@Service
public class ProviderServiceServiceImpl implements ProviderServiceService{

    @Autowired
    private final ProviderServiceRepository providerServiceRepository;

    public ProviderServiceServiceImpl(ProviderServiceRepository providerServiceRepository) {
        this.providerServiceRepository = providerServiceRepository;
    }

    @Transactional
    @Override
    public List<ProviderServiceEntity> getProviderServiceByProviderId(int providerId) {
        return providerServiceRepository.findByProvider_Id(providerId);
    }

    @Transactional
    @Override
    public List<ProviderServiceWebService> getProviderRatingByProviderId(int providerId) {
        List<ProviderServiceEntity> providerServiceEntities = providerServiceRepository.findByProvider_Id(providerId);
        List<ProviderServiceWebService> providerServiceWebServiceList = convertListServiceEntityToDTO(providerServiceEntities);
        return providerServiceWebServiceList;
    }

    @Transactional
    @Override
    public List<ProviderServiceWebService> getProviderRatingByServiceId(int serviceId) {
        List<ProviderServiceEntity> providerServiceEntities = providerServiceRepository.findByService_Id(serviceId);
        List<ProviderServiceWebService> providerServiceWebServiceList = convertListServiceEntityToDTO(providerServiceEntities);
        return providerServiceWebServiceList;
    }

    @Override
    public ProviderServiceEntity getProviderServiceById(int providerServiceId) {
        return providerServiceRepository.findOne(providerServiceId);
    }

    @Transactional
    @Override
    public ProviderServiceWebService addProviderService(ProviderServiceEntity providerServiceEntity) {
        ProviderServiceEntity providerServiceEnt = providerServiceRepository.save(providerServiceEntity);
        Provider provider = new Provider(providerServiceEntity.getProvider().getId()
                ,providerServiceEntity.getProvider().getName()
                ,providerServiceEntity.getProvider().getEmail()
                ,providerServiceEntity.getProvider().getPhoneNumber()
                ,providerServiceEntity.getProvider().getAddress()
                ,providerServiceEntity.getProvider().getLongitude()
                ,providerServiceEntity.getProvider().getLatitude()
                ,providerServiceEntity.getProvider().getImage());
        ServiceWebService services = new ServiceWebService(providerServiceEntity.getService().getId()
                ,providerServiceEntity.getService().getName()
                ,providerServiceEntity.getService().getDescription()
                ,providerServiceEntity.getService().getImage()
                ,providerServiceEntity.getService().getCatalog().getId());
        ProviderServiceWebService providerServiceWebService = new ProviderServiceWebService();
        providerServiceWebService.setId(providerServiceEnt.getId());
        providerServiceWebService.setMinPrice(providerServiceEntity.getMinPrice());
        providerServiceWebService.setMaxPrice(providerServiceEntity.getMaxPrice());
        providerServiceWebService.setDescription(providerServiceEnt.getDescription());
        providerServiceWebService.setFromTime(providerServiceEnt.getFromTime());
        providerServiceWebService.setToTime(providerServiceEnt.getToTime());
        providerServiceWebService.setProvider(provider);
        providerServiceWebService.setServices(services);
        return providerServiceWebService;
    }

    @Override
    public int setProviderServiceById(ProviderServiceEntity providerServiceEntity, int id) {
        return providerServiceRepository.updateById(providerServiceEntity.getId()
                ,providerServiceEntity.getProvider()
                ,providerServiceEntity.getService()
                ,providerServiceEntity.getMaxPrice()
                ,providerServiceEntity.getMinPrice()
                ,providerServiceEntity.getDescription()
                ,providerServiceEntity.getFromTime()
                ,providerServiceEntity.getToTime());
    }

    private List<ProviderServiceWebService> convertListServiceEntityToDTO(List<ProviderServiceEntity> providerServiceEntities){
        List<ProviderServiceWebService> providerServiceWebServiceList = new ArrayList<>();
        for(ProviderServiceEntity providerServiceEntity : providerServiceEntities){
            List<RatingEntity> ratingEntities = providerServiceEntity.getRatings();
            List<Rating> ratings = new ArrayList<Rating>();
            for(RatingEntity ratingEntity : ratingEntities){
                Customer customer = new Customer(ratingEntity.getCustomer().getId()
                        ,ratingEntity.getCustomer().getName()
                        ,ratingEntity.getCustomer().getEmail()
                        ,ratingEntity.getCustomer().getPassword()
                        ,ratingEntity.getCustomer().getPhoneNumber()
                        ,ratingEntity.getCustomer().getAddress()
                        ,ratingEntity.getCustomer().getLongitude()
                        ,ratingEntity.getCustomer().getLatitude());
                ratings.add(new Rating(ratingEntity.getId()
                        ,customer
                        ,ratingEntity.getContent()
                        ,ratingEntity.getScore()
                        ,ratingEntity.getTitle()
                        ,ratingEntity.getRatingDate()
                        ,ratingEntity.getProviderServices().getId()));
            }
            ServiceWebService services = new ServiceWebService(providerServiceEntity.getService().getId()
                    ,providerServiceEntity.getService().getName()
                    ,providerServiceEntity.getService().getDescription()
                    ,providerServiceEntity.getService().getImage()
                    ,providerServiceEntity.getService().getCatalog().getId());
            Provider provider = new Provider(providerServiceEntity.getProvider().getId()
                    ,providerServiceEntity.getProvider().getName()
                    ,providerServiceEntity.getProvider().getEmail()
                    ,providerServiceEntity.getProvider().getPhoneNumber()
                    ,providerServiceEntity.getProvider().getAddress()
                    ,providerServiceEntity.getProvider().getLongitude()
                    ,providerServiceEntity.getProvider().getLatitude()
                    ,providerServiceEntity.getProvider().getImage());
            providerServiceWebServiceList.add(new ProviderServiceWebService(providerServiceEntity.getId()
                    ,provider
                    ,services
                    ,providerServiceEntity.getMaxPrice()
                    ,providerServiceEntity.getMinPrice()
                    ,providerServiceEntity.getDescription()
                    ,providerServiceEntity.getFromTime()
                    ,providerServiceEntity.getToTime()
                    ,ratings));
        }
        return providerServiceWebServiceList;
    }
}
