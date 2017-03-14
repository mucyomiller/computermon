/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computermon;

import com.google.gson.Gson;
import static computermon.GetProcesses.JSON;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 *
 * @author admin
 * this will handler all logging in actions
 */
public class HandleRegister {
    
    String firstname;
    String lastname;
    String email;
    String password;
    String mac;
       public static final MediaType JSON
    = MediaType.parse("application/json; charset=utf-8");

OkHttpClient client = new OkHttpClient();
    

    public HandleRegister(String email,String firstname,String lastname, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
    
    public void  register() throws IOException{ 
        InetAddress ip = InetAddress.getLocalHost(); 

    NetworkInterface network = NetworkInterface.getByInetAddress(ip);

    byte[] mac = network.getHardwareAddress();

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < mac.length; i++) {
        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
    }
        User  mUser = new User(firstname,lastname,email,password,sb.toString());
        String jsonString = new Gson().toJson( mUser);
            System.out.println(jsonString);
          
            post("https://computermon-b9477.firebaseio.com/users.json",jsonString); 
    }
    
    String post(String url, String json) throws IOException {
  RequestBody body = RequestBody.create(JSON, json);
  Request request = new Request.Builder()
      .url(url)
      .post(body)
      .build();
  Response response = client.newCall(request).execute();
  return response.body().string();
}
}
    