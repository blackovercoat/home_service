package com.dolphine.my_services.service.common;

import com.dolphine.my_services.dto.ResponseMessage;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

/**
 * Created by PC on 5/1/2017.
 */
public interface CommonService {

    String uploadImage(MultipartFile file, String id) throws IOException;
    void removeImage(String img);
    ResponseMessage sendNotification(String title, String message, String clientToken) throws IOException, JSONException;
}
