package com.dolphine.my_services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by PC on 3/30/2017.
 */
@Data
@Entity
@EqualsAndHashCode(exclude = {"provider","bookings"})
@Table(name = "provider_notification")
public class ProviderNotificationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "send_date")
    private Date sendDate;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private ProviderEntity provider;
}
