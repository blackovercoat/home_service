package com.dolphine.my_services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by PC on 3/30/2017.
 */
@Data
@Entity
@EqualsAndHashCode(exclude = {"customer"})
@Table(name = "customer_notification")
public class CustomerNotificationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "send_date")
    private Date sendDate;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
}
