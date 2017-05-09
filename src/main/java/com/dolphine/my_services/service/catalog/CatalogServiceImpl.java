package com.dolphine.my_services.service.catalog;

import com.dolphine.my_services.dto.*;
import com.dolphine.my_services.model.CatalogEntity;
import com.dolphine.my_services.model.ProviderServiceEntity;
import com.dolphine.my_services.model.RatingEntity;
import com.dolphine.my_services.model.ServiceEntity;
import com.dolphine.my_services.repository.CatalogRepository;
import com.dolphine.my_services.repository.ProviderServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 4/10/2017.
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private final CatalogRepository catalogRepository;
    private final ProviderServiceRepository providerServiceRepository;

    public CatalogServiceImpl(CatalogRepository catalogRepository, ProviderServiceRepository providerServiceRepository) {
        this.catalogRepository = catalogRepository;
        this.providerServiceRepository = providerServiceRepository;
    }

    @Override
    public List<CatalogEntity> getAllCatalog() {
        return catalogRepository.findAll();
    }

    @Transactional
    @Override
    public List<CatalogAndService> getAllCatalogAndService() {
        List<CatalogEntity> catalogEntities = catalogRepository.findAll();
        List<CatalogAndService> catalogAndServiceList = convertListCatalogEntityToDTO(catalogEntities);
        return catalogAndServiceList;
    }

    @Override
    @Transactional
    public void removeCatalogById(int catalogId) {
        catalogRepository.delete(catalogId);
    }

    @Override
    public CatalogEntity addCatalog(CatalogForm catalogForm) {
        CatalogEntity catalogEntity = new CatalogEntity();
        catalogEntity.setName(catalogForm.getName());
        catalogEntity.setDescription(catalogForm.getDescription());
        catalogEntity.setImage(catalogForm.getImage());
        return catalogRepository.save(catalogEntity);
    }

    @Override
    public Catalog addCatalogWebService(CatalogEntity catalogEntity) {
        CatalogEntity catalogEnt = catalogRepository.save(catalogEntity);
        Catalog catalog = new Catalog();
        catalog.setId(catalogEnt.getId());
        catalog.setName(catalogEntity.getName());
        catalog.setDescription(catalogEntity.getDescription());
        catalog.setImage(catalogEntity.getImage());
        return catalog;
    }

    @Transactional
    @Override
    public List<CatalogServiceAndRating> getCatalogServiceAndRatingByProviderId(int providerId) {
        List<ProviderServiceEntity> providerServiceEntities = providerServiceRepository.findByProvider_Id(providerId);
        List<CatalogEntity> allCatalog = catalogRepository.findAll();
//        List<ServiceAndRating> serviceAndRatings = new ArrayList<>();
        List<ProviderServiceWebService> providerServiceWebServiceList = new ArrayList<>();

        List<CatalogServiceAndRating> catalogServiceAndRatingList = new ArrayList<>();
        for(ProviderServiceEntity providerServiceEntity : providerServiceEntities) {
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
        for(CatalogEntity catalogEntity : allCatalog){
            List<ProviderServiceWebService> providerService = new ArrayList<>();
            for(ProviderServiceWebService providerServiceWebService : providerServiceWebServiceList)
                if(providerServiceWebService.getServices().getCatalogId()==catalogEntity.getId()){

                    providerService.add(new ProviderServiceWebService(providerServiceWebService.getId()
                            ,providerServiceWebService.getProvider()
                            ,providerServiceWebService.getServices()
                            ,providerServiceWebService.getMaxPrice()
                            ,providerServiceWebService.getMinPrice()
                            ,providerServiceWebService.getDescription()
                            ,providerServiceWebService.getFrom()
                            ,providerServiceWebService.getTo()
                            ,providerServiceWebService.getRatingList()));
            }

//            if(!services.isEmpty())
                catalogServiceAndRatingList.add(new CatalogServiceAndRating(catalogEntity.getId()
                        ,catalogEntity.getName()
                        ,catalogEntity.getDescription()
                        ,providerService
                        ,catalogEntity.getImage()));
        }
        return catalogServiceAndRatingList;
    }

    @Transactional
    @Override
    public CatalogEntity getCatalogById(int catalogId) {
        return catalogRepository.findOne(catalogId);
    }

    @Override
    public void setCatalogById(CatalogForm catalogForm, int id) {
        catalogRepository.updateById(id,catalogForm.getName()
                ,catalogForm.getDescription()
                ,catalogForm.getImage());
    }

    private List<CatalogAndService> convertListCatalogEntityToDTO(List<CatalogEntity> catalogEntities){
        List<CatalogAndService> catalogAndServiceList = new ArrayList<>();
        for(CatalogEntity catalogEntity : catalogEntities){
            List<ServiceEntity> serviceEntities = catalogEntity.getServices();
            List<ServiceWebService> services = new ArrayList<ServiceWebService>();
            for(ServiceEntity serviceEntity : serviceEntities)
                services.add(new ServiceWebService(serviceEntity.getId()
                        ,serviceEntity.getName()
                        ,serviceEntity.getDescription()
                        ,serviceEntity.getImage()
                        ,serviceEntity.getCatalog().getId()));
            catalogAndServiceList.add(new CatalogAndService(catalogEntity.getId()
                    ,catalogEntity.getName(),catalogEntity.getDescription(),services,catalogEntity.getImage()));
        }
        return catalogAndServiceList;
    }
}
