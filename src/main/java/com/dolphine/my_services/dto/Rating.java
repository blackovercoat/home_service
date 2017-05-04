package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by PC on 4/12/2017.
 */
@Data
@ToString
public class Rating {
    private int id;
    private int customerId;
    private String content;
    private float score;
    private String title;
    private Date ratingDate;
    private int providerServiceId;

    public Rating(int id, int customerId, String content, float score,String title, Date ratingDate, int providerServiceId) {
        this.id = id;
        this.customerId = customerId;
        this.content = content;
        this.score = score;
        this.title = title;
        this.ratingDate = ratingDate;
        this.providerServiceId = providerServiceId;
    }

    public Rating() {
    }
}
