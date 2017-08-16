package android.com.technicianclient.technician.builder;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
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
 * Created by Ghulam Ali on 5/20/2017.
 */
public class FeedBackFormHandler  extends ServerConnectionBuilder{

    private Context c;

    public FeedBackFormHandler(Context c) {
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

    public String setFormParametersAndConnect( String name,String phone,String city
            ,String message,String email,String rate) {
        try {
            Map<String, String> arguments = new HashMap<>();
            arguments.put("userid", SharedFields.userId);
            arguments.put("username", name);
            arguments.put("phone", phone);
            arguments.put("city_id", city);
            arguments.put("email", email);

//            Customer c = BeanFactory.getCustomer();
//            if (!TextUtils.isEmpty(c.getFbId())){
//                arguments.put("user_fb_id", c.getFbId());
//            }
//            else arguments.put("user_fb_id", "11234353384921334791237");

//            if (TextUtils.isEmpty(c.getPassword())){
//                arguments.put("password", "not logged in");
//            }else  arguments.put("password", c.getPassword());
//            arguments.put("address", "address");
            arguments.put("area_id", "1");
            arguments.put("feedback", message);
            arguments.put("rate", rate);
            arguments.put("feedback1", "true");
            arguments.put("recommend", "7");
            StringBuilder sj = new StringBuilder();
            Log.i("parameters",arguments.toString());
            System.out.println("prameters:parameters are set");
            for (Map.Entry<String, String> entry : arguments.entrySet()) {
                sj.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
            }


            sj.deleteCharAt(sj.length()-1);
            byte[] out = sj.toString().getBytes();
            Log.i("parameters",sj.toString());
            this.connect();
            http.setFixedLengthStreamingMode(out.length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.setConnectTimeout(CONNECTION_TIME_OUT);
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

                JSONObject obj = new JSONObject(response.toString());

                Log.v("response","response="+response);



                return obj.getString("req_status");
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

