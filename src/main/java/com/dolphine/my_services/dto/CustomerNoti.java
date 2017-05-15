package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class CustomerNoti {
    private int id;
    private String content;
    private Date sendDate;
    private int customerId;

    public CustomerNoti(int id, String content, Date sendDate, int customerId) {
        this.id = id;
        this.content = content;
        this.sendDate = sendDate;
        this.customerId = customerId;
    }

    public CustomerNoti() {
    }
}
