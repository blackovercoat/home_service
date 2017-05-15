package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class CustomerNotification {
    private int id;
    private String content;
    private Date sendDate;
    private Customer customer;

    public CustomerNotification(int id, String content, Date sendDate, Customer customer) {
        this.id = id;
        this.content = content;
        this.sendDate = sendDate;
        this.customer = customer;
    }

    public CustomerNotification() {
    }
}
