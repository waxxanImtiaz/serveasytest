package android.com.technicianclient.technician.serverconnetors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import android.com.technicianclient.technician.MainActivity;
import android.com.technicianclient.technician.ServiceFragment;
import android.com.technicianclient.technician.beans.Customer;
import android.com.technicianclient.technician.builder.ServerConnectionBuilder;
import android.com.technicianclient.technician.builder.SignUpFormHandler;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.contentprovider.SharedPreferencesDataLoader;
import android.com.technicianclient.technician.controller.UiController;
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

public class GetUserFromServer  extends ServerConnectionBuilder {

    private Context c;

    public GetUserFromServer(Context c) {
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

    public String setFormParametersAndConnect( String userId) {
        try {
            Map<String, String> arguments = new HashMap<>();
            //arguments.put("userid", SharedFields.userId);
            arguments.put("userid", userId);
            arguments.put("app_start", "true");

            StringBuilder sj = new StringBuilder();

            Log.v("getuser",arguments.toString());
            for (Map.Entry<String, String> entry : arguments.entrySet()) {
                sj.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
            }
            sj.deleteCharAt(sj.length()-1);
            byte[] out = sj.toString().getBytes();
            Log.v("getuser",sj.toString());
            this.connect();
            http.setFixedLengthStreamingMode(out.length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.setConnectTimeout(CONNECTION_TIME_OUT);

            try {

                http.connect();
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


                Log.v("getuser",response.toString());
                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "error=" + e.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("error:","network:"+e.getMessage());
            return "Error:" + e.toString();
        }
    }
}
