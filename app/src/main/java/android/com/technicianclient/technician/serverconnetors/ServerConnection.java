package android.com.technicianclient.technician.serverconnetors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import android.com.technicianclient.technician.MainActivity;
import android.com.technicianclient.technician.beans.Customer;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.contentprovider.SharedPreferencesDataLoader;
import android.com.technicianclient.technician.controller.UiController;
import android.com.technicianclient.technician.factory.BeanFactory;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Admin on 5/26/2017.
 */

public class ServerConnection extends AsyncTask<String, Void, String> {


    private Activity mContext;
    private ProgressDialog progressDialog2;

    public ServerConnection(Activity mContext) {
        this.mContext = mContext;
    }


    @Override
    protected String doInBackground(String... params) {

        GetUserFromServer handler = new GetUserFromServer(mContext);
        handler.setUrl(SharedFields.URL);
        handler.setRequestMethod("POST");

        return handler.setFormParametersAndConnect(params[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            //JSONObject object = new JSONObject(result);

           // progressDialog2.dismiss();

            JSONObject obj = new JSONObject(result);

            JSONArray userInfo = obj.getJSONArray("user_info");
            JSONObject serviceType = obj.getJSONObject("service_types");
            JSONArray cities = obj.getJSONArray("cities_areas");


            /*=================SET CUSTOMER DATA===============*/
            for (int i = 0; i < userInfo.length(); i++) {
                JSONObject object = userInfo.getJSONObject(i);

                Customer cus = new Customer();
                cus.setCity(object.getString("city_name"));
                cus.setId(object.getString("userid"));
                cus.setArea(object.getString("area_name"));
                cus.setName(object.getString("name"));
                cus.setEmail(object.getString("email"));
                cus.setMobile(object.getString("phone"));
                cus.setAddress(object.getString("address"));
                cus.setPassword(object.getString("password"));

                BeanFactory.setCustomer(cus);
                SharedPreferencesDataLoader.storeCustomerDataToSharedPreferences(mContext);
        }
            /*====================SET CITIES===================*/

            for (int i = 0; i < cities.length(); i++) {
                JSONArray arry = cities.getJSONArray(i);
                for (int j = 0; j < arry.length(); j++) {
                    JSONObject object = arry.getJSONObject(j);

                    SharedFields.cities.put(Integer.parseInt(object.getString("city_id")), object.getString("city_name"));
                    SharedFields.areas.put(Integer.parseInt(object.getString("area_id")), object.getString("area_name"));
                }

            }
            /*====================SET SERVICES===================*/

            for (int i = 0; i < serviceType.length(); i++) {
                SharedFields.services.put(i, serviceType.getString(String.valueOf(i + 1)));
            }
            Toast.makeText(mContext, "You are logged in succesfully", Toast.LENGTH_SHORT).show();
//            mContext.startActivity(new Intent(mContext,MainActivity.class));
            mContext.finish();
        } catch (Exception e) {
            Log.v("exception", "excp:" + e.getMessage());
        }
    }

    @Override
    protected void onPreExecute() {
        //progressDialog2 = ProgressDialog.show(mContext, "", "Loading");
    }
}
