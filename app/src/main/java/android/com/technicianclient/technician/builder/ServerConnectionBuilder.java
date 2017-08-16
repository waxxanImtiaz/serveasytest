package android.com.technicianclient.technician.builder;

import android.util.Log;

import android.com.technicianclient.technician.contentprovider.SharedFields;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Admin on 5/19/2017.
 */

public abstract class ServerConnectionBuilder {

    protected String reqMethod;
    protected String link;
    protected HttpURLConnection http;
    protected final int CONNECTION_TIME_OUT = SharedFields.CONNECTION_TIME;


//    Map<String, String> arguments = new HashMap<>();
//    arguments.put("userid", userId);
//    arguments.put("app_start", "true");
    //call third
    public void connect(){
        try {
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            http = (HttpURLConnection) con;
            http.setRequestMethod(reqMethod); // PUT is another valid option
            http.setDoOutput(true);
            http.setConnectTimeout(CONNECTION_TIME_OUT);
        }catch (MalformedURLException e){
            Log.d(SharedFields.DEBUG_MESSAGE,"exception:"+e.getMessage());
        }catch (IOException e){
            Log.d(SharedFields.DEBUG_MESSAGE,"exception:"+e.getMessage());
        }
    }
    public abstract void setRequestMethod(String reqMethod);
    public abstract void setUrl(String url);

}
