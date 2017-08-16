package android.com.technicianclient.technician.serverconnetors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.com.technicianclient.technician.LoginActivity;
import android.com.technicianclient.technician.beans.Customer;
import android.com.technicianclient.technician.builder.LoginFormHandler;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.contentprovider.SharedPreferencesDataLoader;
import android.com.technicianclient.technician.controller.UiController;
import android.com.technicianclient.technician.factory.BeanFactory;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import android.com.technicianclient.technician.LoginActivity;
import android.com.technicianclient.technician.beans.Customer;
import android.com.technicianclient.technician.builder.LoginFormHandler;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.contentprovider.SharedMethods;
import android.com.technicianclient.technician.controller.UiController;
import android.com.technicianclient.technician.factory.BeanFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 5/25/2017.
 */
public class UserLoginTask extends AsyncTask<Void, Void, String> {

    private LoginActivity mContext;
    private ProgressDialog progressDialog2;

    public UserLoginTask(final LoginActivity context) {
        this.mContext = context;

    }


    @Override
    protected String doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        try {

            LoginFormHandler handler = new LoginFormHandler(mContext);
            handler.setUrl(SharedFields.URL);

            handler.setRequestMethod("POST");

            return handler.setFormParametersAndConnect();
        } catch (Exception e) {
            return "";
        }


    }

    @Override
    protected void onPreExecute() {
        if (!((Activity) mContext).isFinishing()) {
            //show dialog
            progressDialog2 = ProgressDialog.show(mContext, "", "Loading");
        }


    }

    private boolean isMessageAppeard = false;

    @Override
    protected void onPostExecute(final String success) {


        if (success == null) {
            hideProgresss();
            Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            JSONArray array = new JSONArray(success);

            for (int n = 0; n < array.length(); n++) {
                JSONObject object = array.getJSONObject(n);
                showMessage(object);
            }


        } catch (JSONException e) {
            Log.v("jsonException", "msg:" + e.toString());

            try {
                if (success.equalsIgnoreCase("null")) {
                    hideProgresss();
                    Toast.makeText(mContext, "Invalid  username or  password", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.v("msg", e.toString());
                showMessage(new JSONObject(success));
            } catch (JSONException ex) {
                hideProgresss();
            }
        } catch (Exception e) {
            hideProgresss();
            Log.v("exception", "msg:" + e.toString());
        }
    }

    private void hideProgresss(){
        if (progressDialog2 != null)
            progressDialog2.dismiss();
    }
    public void showMessage(JSONObject object) throws JSONException {

        if (!isMessageAppeard) {
            isMessageAppeard = true;
            if (object.getString("id") != null && !object.getString("id").isEmpty()) {
                Customer c = BeanFactory.getCustomer();
                c.setId(object.getString("id"));
                BeanFactory.setCustomer(c);

                ServerConnection service = new ServerConnection(mContext);
                SharedFields.userId = object.getString("id");

                service.execute(SharedFields.userId);


            } else
                UiController.showDialog("Info:" + object.toString(), mContext);
        }
    }

    class ServerConnection extends AsyncTask<String, Void, String> {


        private Activity mContext;


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
                hideProgresss();
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


}


