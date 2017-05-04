package com.dolphine.my_services.service.rating;

import com.dolphine.my_services.dto.Rating;
import com.dolphine.my_services.dto.ServiceRating;
import com.dolphine.my_services.model.RatingEntity;

import java.util.List;

/**
 * Created by PC on 4/20/2017.
 */
public interface RatingService {

    List<Rating> getRatingByProviderService(int providerServiceId);
    ServiceRating getServiceRatingandServiceByProviderServiceId(int providerServiceId);
    Rating saveRating(RatingEntity ratingEntity);
}
