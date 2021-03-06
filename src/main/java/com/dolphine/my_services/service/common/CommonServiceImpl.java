package com.dolphine.my_services.service.common;

import com.dolphine.my_services.dto.ResponseMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;

/**
 * Created by PC on 5/1/2017.
 */
@Service
public class CommonServiceImpl implements CommonService{
    @Autowired
    final private ServletContext context;

    public CommonServiceImpl(ServletContext context) {
        this.context = context;
    }

    @Override
    public String uploadImage(MultipartFile file, String id) {
        try {
            byte[] bytes = file.getBytes();
            String path = context.getRealPath("/resources/images");
            System.out.println("Path:"+path);
            File dir =  new File(path);
            if(!dir.exists())
                dir.mkdirs();
            String sourceFile = path + "/" + id +"n"+ file.getOriginalFilename();
            System.out.println(sourceFile);
            File serverFile  = new File(sourceFile);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile,true));
            stream.write(bytes);
            stream.close();
            return "/resources/images/" + id +"n"+ file.getOriginalFilename() ;
        } catch (IOException e) {
            return "/resources/images/android.jpg";
        }
    }

    @Override
    public void removeImage(String img){
        try {
            String root = context.getRealPath("");
            System.out.println("Root for delete: "+root);
            File fileDelete = new File(root + img);
            if (fileDelete.delete()) {
                System.out.println(fileDelete.getName() +img + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResponseMessage sendNotification(String title, String message, String clientToken) throws IOException, JSONException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://fcm.googleapis.com/fcm/send");
        post.setHeader("Content-type", "application/json");
        post.setHeader("Authorization", "key=AIzaSyA2KkUUvShs5XVrwBxEvE8zqOptu7PmyZ8");

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("to", clientToken);
        jsonMessage.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", title);
        notification.put("body", message);

        jsonMessage.put("notification", notification);

        post.setEntity(new StringEntity(jsonMessage.toString(), "UTF-8"));
        HttpResponse response = client.execute(post);
        System.out.println(response);
        System.out.println(jsonMessage);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setResponse(response+"");
        responseMessage.setMessage(jsonMessage+"");
        return responseMessage;
    }
}
