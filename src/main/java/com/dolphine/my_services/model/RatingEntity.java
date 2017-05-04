package com.dolphine.my_services.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name ="rating")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "score")
    private float score;

    @Column(name = "title")
    private String title;

    @Column(name = "rating_date")
    private Date ratingDate;

    @ManyToOne
    @JoinColumn(name = "provider_service_id", nullable = false)
    private ProviderServiceEntity providerServices;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
}
