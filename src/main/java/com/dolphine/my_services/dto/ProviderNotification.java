package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ProviderNotification {
    private int id;
    private String content;
    private Date sendDate;
    private Provider provider;

    public ProviderNotification(int id, String content, Date sendDate, Provider provider) {
        this.id = id;
        this.content = content;
        this.sendDate = sendDate;
        this.provider = provider;
    }

    public ProviderNotification() {
    }
}
