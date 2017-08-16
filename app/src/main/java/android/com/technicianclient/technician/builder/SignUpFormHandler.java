package android.com.technicianclient.technician.builder;

import android.content.Context;
import android.util.Log;

import android.com.technicianclient.technician.beans.Customer;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.factory.BeanFactory;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 5/24/2017.
 */

public class SignUpFormHandler extends ServerConnectionBuilder {

    private Context c;

    public SignUpFormHandler(Context c) {
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

    private JSONObject obj;
    public String setFormParametersAndConnect( ) {
        try {
            Customer customer = BeanFactory.getCustomer();
            Map<String, String> arguments = new HashMap<>();
            //arguments.put("userid", SharedFields.userId);
            arguments.put("name", customer.getName());
            arguments.put("phone", customer.getMobile());
            arguments.put("email", customer.getEmail());
            arguments.put("address", "address");
            arguments.put("password", customer.getPassword());
            arguments.put("city_id", customer.getCity());
            arguments.put("area_id", "1");
//            arguments.put("gender", customer.getGender());
            arguments.put("user_fb_id", customer.getFbId());
            arguments.put("new_sign_up", "true");
            StringBuilder sj = new StringBuilder();

            System.out.println("prameters:parameters are set");
            Log.v("signup",arguments.toString());
            for (Map.Entry<String, String> entry : arguments.entrySet()) {
                sj.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
            }
            sj.deleteCharAt(sj.length()-1);
            byte[] out = sj.toString().getBytes();
            Log.v("signup",sj.toString());
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

                obj = new JSONObject(response.toString());

                Log.v("signup",response.toString());
                return obj.toString();
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
