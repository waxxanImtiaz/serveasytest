package android.com.technicianclient.technician.builder;

import android.content.Context;

import android.com.technicianclient.technician.ServiceFragment;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ghulam Ali on 5/20/2017.
 */
public class ServiceFormHandler  extends ServerConnectionBuilder {

    private ServiceFragment c;

    public ServiceFormHandler(ServiceFragment c) {
        this.c = c;
    }

    //call first
    public void setUrl(String link) {
        this.link = link;
    }

    //call second
    public void setRequestMethod(String requestMethod) {
        this.reqMethod = requestMethod;
    }

    public String setFormParametersAndConnect( String name,String phone,String address,String service
    ,String message) {
        try {
            Map<String, String> arguments = new HashMap<>();
            arguments.put("userid", SharedFields.userId);
            arguments.put("name", name);
            arguments.put("phone", phone);
            arguments.put("service_type", service);
            arguments.put("message", message);
            arguments.put("address", address);
            arguments.put("req_service", "true");
            StringBuilder sj = new StringBuilder();

            System.out.println("prameters:parameters are set");

            for (Map.Entry<String, String> entry : arguments.entrySet()) {
                sj.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
            }
            sj.deleteCharAt(sj.length()-1);

            Log.v("parameters_servies",sj.toString());
            byte[] out = sj.toString().getBytes();

            this.connect();

            http.setFixedLengthStreamingMode(out.length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            http.connect();
            try {
                DataOutputStream wr = new DataOutputStream(http.getOutputStream());
                wr.writeBytes(sj.toString());
                wr.flush();
                wr.close();

                int responseCode = http.getResponseCode();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(http.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                in.close();

                Log.v("parameters_servies",response.toString());
                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "error=" + e.getMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error:" + e.getMessage();
        }
    }
}
