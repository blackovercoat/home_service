package com.dolphine.my_services.service.providerservice;

import com.dolphine.my_services.dto.Provider;
import com.dolphine.my_services.dto.ProviderServiceWebService;
import com.dolphine.my_services.dto.Rating;
import com.dolphine.my_services.dto.ServiceWebService;
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

    private List<ProviderServiceWebService> convertListServiceEntityToDTO(List<ProviderServiceEntity> providerServiceEntities){
        List<ProviderServiceWebService> providerServiceWebServiceList = new ArrayList<>();
        for(ProviderServiceEntity providerServiceEntity : providerServiceEntities){
            List<RatingEntity> ratingEntities = providerServiceEntity.getRatings();
            List<Rating> ratings = new ArrayList<Rating>();
            for(RatingEntity ratingEntity : ratingEntities)
                ratings.add(new Rating(ratingEntity.getId()
                        ,ratingEntity.getCustomer().getId()
                        ,ratingEntity.getContent()
                        ,ratingEntity.getScore()
                        ,ratingEntity.getTitle()
                        ,ratingEntity.getRatingDate()
                        ,ratingEntity.getProviderServices().getId()));
            ServiceWebService services = new ServiceWebService(providerServiceEntity.getService().getId()
                    ,providerServiceEntity.getService().getName()
                    ,providerServiceEntity.getService().getDescription()
                    ,providerServiceEntity.getService().getImage());
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
                    ,providerServiceEntity.getFrom()
                    ,providerServiceEntity.getTo()
                    ,ratings));
        }
        return providerServiceWebServiceList;
    }
}
