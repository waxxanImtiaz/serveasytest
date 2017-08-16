package android.com.technicianclient.technician.builder;

import android.content.Context;
import android.widget.Toast;

import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.serverconnetors.ComplainService;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 5/19/2017.
 */

public class ComplainFormHandler extends ServerConnectionBuilder {

    private Context c;

    public ComplainFormHandler(Context c) {
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

    public String setFormParametersAndConnect(String userId, String complain) {
        try {
            Map<String, String> arguments = new HashMap<>();
            arguments.put("userid", userId);
            arguments.put("complain_text", complain);
            arguments.put("complain", "true");
            StringBuilder sj = new StringBuilder();

            System.out.println("prameters:parameters are set");
            for (Map.Entry<String, String> entry : arguments.entrySet()) {
                sj.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
            }
            sj.deleteCharAt(sj.length()-1);
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

                JSONObject object = new JSONObject(response.toString());
                return object.getString("req_status");
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
