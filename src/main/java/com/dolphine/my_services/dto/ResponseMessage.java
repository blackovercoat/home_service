package com.dolphine.my_services.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by PC on 5/14/2017.
 */
@Data
@ToString
public class ResponseMessage {
    private String response;
    private String message;

    public ResponseMessage(String response, String message) {
        this.response = response;
        this.message = message;
    }

    public ResponseMessage() {
    }
}
