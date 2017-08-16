package android.com.technicianclient.technician.builder;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import android.com.technicianclient.technician.beans.Customer;
import android.com.technicianclient.technician.factory.BeanFactory;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 5/25/2017.
 */


public class LoginFormHandler extends ServerConnectionBuilder {

    private Context c;

    public LoginFormHandler(Context c) {
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

//    private JSONObject obj;
    public String setFormParametersAndConnect( ) {
        try {
            Customer customer = BeanFactory.getCustomer();
            Map<String, String> arguments = new HashMap<>();
            //arguments.put("userid", SharedFields.userId);
            if (customer.getFbId() != null && !TextUtils.isEmpty(customer.getFbId())) {
                arguments.put("user_fb_id", customer.getFbId());
            }
            else {
                arguments.put("email", customer.getEmail());
                arguments.put("password", customer.getPassword());
            }

            arguments.put("new_sign_in", "true");

            StringBuilder sj = new StringBuilder();


            Log.v("signin",arguments.toString());
            for (Map.Entry<String, String> entry : arguments.entrySet()) {
                sj.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
            }

            sj.deleteCharAt(sj.length()-1);
            byte[] out = sj.toString().getBytes();
            Log.v("logindata","data="+sj.toString());
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

                //obj = new JSONObject(response.toString());

                try {

                    Log.v("logindata", "data=" + response.toString());
                    return response.toString();
                }catch (NullPointerException e)
                {
                    return "";
                }
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

