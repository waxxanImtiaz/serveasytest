package android.com.technicianclient.technician.startuploader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.com.technicianclient.technician.LoginActivity;
import android.com.technicianclient.technician.beans.Customer;
import android.com.technicianclient.technician.builder.LoginFormHandler;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.controller.UiController;
import android.com.technicianclient.technician.factory.BeanFactory;
import android.com.technicianclient.technician.model.LoadOnStartupData;
import android.com.technicianclient.technician.serverconnetors.ServerConnection;
import android.com.technicianclient.technician.services.LoadOnStartupService;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 5/25/2017.
 */
public class LoadOnStartUp extends AsyncTask<Void, Void, String> {

    private Activity mContext;
    private ProgressDialog progressDialog2;

    public LoadOnStartUp(final Activity context) {
        this.mContext = context;

    }


    @Override
    protected String doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        try {

            LoadOnStartupService handler = new LoadOnStartupService(mContext);
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

        if (progressDialog2 != null)
            progressDialog2.dismiss();

        if (success == null) {
            Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
            return;
        }
//        try {
//            JSONArray array = new JSONArray(success);

           //  new LoadOnStartupData(success);
//            for(int n = 0; n < array.length(); n++)
//            {
//                JSONObject object = array.getJSONObject(n);
//                //showMessage(object);
//            }


//        } catch (JSONException e) {
//            Log.v("jsonException", "msg:" + e.toString());
//            try {
//                if (success.equalsIgnoreCase("null")){
//                    Toast.makeText(mContext, "Invalid  username or  password", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Log.v("msg",  e.toString());
//                showMessage(new JSONObject(success));
//            }catch (JSONException ex){
//            }
//        } catch (Exception e) {
//            Log.v("exception", "msg:" + e.toString());
//        }
    }
    public void showMessage(JSONObject object)throws JSONException{

        if (!isMessageAppeard) {
            isMessageAppeard = true;
            if (object.getString("id") != null && !object.getString("id").isEmpty()) {
                Customer c = BeanFactory.getCustomer();
                c.setId(object.getString("id"));
                BeanFactory.setCustomer(c);

                ServerConnection service = new ServerConnection(mContext);
                SharedFields.userId = object.getString("id");

                service.execute(SharedFields.userId);


            }else
                UiController.showDialog("Info:" + object.toString(), mContext);
        }
    }

}


