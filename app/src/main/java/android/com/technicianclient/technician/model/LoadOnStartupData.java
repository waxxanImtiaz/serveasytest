package android.com.technicianclient.technician.model;


import android.app.Activity;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;
import java.util.Vector;

/**
 * Created by Admin on 8/8/2017.
 */

public class LoadOnStartupData {
    private String array;
    private Activity activity;
    public LoadOnStartupData(final String array, Activity activity) {
        this.array = array;
        this.activity = activity;
        load();

    }

    private void load() {
        try {
            JSONObject object = new JSONObject(this.array);
            String value = object.optString("service_types");

            JSONObject ob = new JSONObject(value);
            //String name = array.getString("1");
            SharedFields.myServices  = new Vector<>();
            for (int n = 0; n < ob.length(); n++) {
                SharedFields.myServices.add(ob.getString(String.valueOf(n + 1)));
            }


            String v = object.optString("cities_areas");
            JSONArray cities = new JSONArray(v);
            SharedFields.citiesName  = new TreeMap<>();
            SharedFields.myAreas  = new TreeMap<>();
            for (int n = 0; n < cities.length(); n++) {
                JSONArray object1 = cities.getJSONArray(n);

                for (int i = 0; i < object1.length(); i++) {
                    JSONObject object2 = object1.getJSONObject(i);
                    SharedFields.citiesName.put(object2.getInt("city_id"), object2.getString("city_name"));
                    SharedFields.myAreas.put(object2.getInt("area_id"), object2.getString("area_name"));
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
            if (this.array.contains("Error")){
                String[] arr =this.array.split(":");
                Toast.makeText(activity, "Error:Server is not responding", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
