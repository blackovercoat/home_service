package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ProviderNoti {
    private int id;
    private String content;
    private Date sendDate;
    private int providerId;

    public ProviderNoti(int id, String content, Date sendDate, int providerId) {
        this.id = id;
        this.content = content;
        this.sendDate = sendDate;
        this.providerId = providerId;
    }

    public ProviderNoti() {
    }
}
