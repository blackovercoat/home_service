package com.dolphine.my_services.service.rating;

import com.dolphine.my_services.dto.Rating;
import com.dolphine.my_services.dto.ServiceRating;
import com.dolphine.my_services.model.ProviderServiceEntity;
import com.dolphine.my_services.model.RatingEntity;
import com.dolphine.my_services.repository.ProviderServiceRepository;
import com.dolphine.my_services.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 4/20/2017.
 */
@Service
public class RatingServiceImpl implements RatingService {

    final private RatingRepository ratingRepository;
    final private ProviderServiceRepository providerServiceRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, ProviderServiceRepository providerServiceRepository) {
        this.ratingRepository = ratingRepository;
        this.providerServiceRepository = providerServiceRepository;
    }

    @Override
    public List<Rating> getRatingByProviderService(int providerServiceId) {
        List<RatingEntity> ratingEntities = ratingRepository.findByProviderServices_Id(providerServiceId);
        List<Rating> ratings = new ArrayList<>();
        for (RatingEntity ratingEntity : ratingEntities)
            ratings.add(new Rating(ratingEntity.getId()
                    ,ratingEntity.getCustomer().getId()
                    ,ratingEntity.getContent()
                    ,ratingEntity.getScore()
                    ,ratingEntity.getTitle()
                    ,ratingEntity.getRatingDate()
                    ,ratingEntity.getProviderServices().getId()));
        return ratings;
    }

    @Transactional
    @Override
    public ServiceRating getServiceRatingandServiceByProviderServiceId(int providerServiceId) {
        List<Rating> ratings = getRatingByProviderService(providerServiceId);
        ProviderServiceEntity providerServiceEntity = providerServiceRepository.getOne(providerServiceId);
        ServiceRating serviceRating = new ServiceRating(providerServiceId,providerServiceEntity.getService().getId(),ratings);
        return serviceRating;
    }

    @Override
    public Rating saveRating(RatingEntity ratingEntity) {
        Rating rating = new Rating();
        ratingRepository.save(ratingEntity);
        rating.setId(ratingEntity.getId());
        rating.setCustomerId(ratingEntity.getCustomer().getId());
        rating.setProviderServiceId(ratingEntity.getProviderServices().getId());
        rating.setContent(ratingEntity.getContent());
        rating.setScore(ratingEntity.getScore());
        rating.setTitle(ratingEntity.getTitle());
        rating.setRatingDate(ratingEntity.getRatingDate());
        return rating;
    }
}
