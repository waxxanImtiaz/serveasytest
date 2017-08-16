package android.com.technicianclient.technician.serverconnetors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.com.technicianclient.technician.MainActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import android.com.technicianclient.technician.MainActivity;
import android.com.technicianclient.technician.MyAccount;
import android.com.technicianclient.technician.beans.Service;
import android.com.technicianclient.technician.beans.Feedback;
import android.com.technicianclient.technician.beans.PaymentHistory;
import android.com.technicianclient.technician.builder.UserInfoHandler;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.factory.BeanFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/29/2017.
 */

public class UserInfoService extends AsyncTask<Void, Void, String> {

    private MainActivity mContext;
    private ProgressDialog progressDialog2;

    public UserInfoService(final MainActivity context) {
        this.mContext = context;

    }


    @Override
    protected String doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        try {

            UserInfoHandler handler = new UserInfoHandler(mContext);
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


    @Override
    protected void onPostExecute(final String success) {

        if (progressDialog2 != null)
            progressDialog2.dismiss();

        if (success == null) {
            Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (success != null && !success.isEmpty()) {
                JSONObject object = new JSONObject(success);
                JSONArray array;
                try {

                    //get all feedbacks
                    array = object.getJSONArray("all_feedback");
                    List<Feedback> fb = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        Feedback f = new Feedback();

                        JSONObject ob = array.getJSONObject(i);
                        f.setDate(ob.getString("submitted_at"));
                        f.setId(ob.getString("id"));
                        f.setFeedback(ob.getString("feedback"));
                        f.setRecomended(ob.getString("recommend"));
                        fb.add(f);
                    }

                    BeanFactory.setFeedbacks(fb);
                } catch (JSONException e) {
                    Log.i("exception", "," + e.toString());
                }

                try {


                    //get all payment history
                    array = object.getJSONArray("payment_history");
                    List<PaymentHistory> ph = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        PaymentHistory f = new PaymentHistory();

                        JSONObject ob = array.getJSONObject(i);
                        f.setAmountRecieved(ob.getString("amount_received"));
                        f.setId(ob.getString("id"));
                        f.setModel(ob.getString("model"));
                        ph.add(f);
                    }

                    BeanFactory.setPaymentHistories(ph);

                } catch (JSONException e) {
                    Log.i("exception", ",," + e.toString());
                }
                try {
                    //get all payment history
                    array = object.getJSONArray("all_complaints");
                    List<Service> com = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        Service f = new Service();

                        JSONObject ob = array.getJSONObject(i);
                        f.setComplain(ob.getString("complain_text"));
                        f.setId(ob.getString("id"));
                        f.setDate(ob.getString("submitted_at"));
                        f.setStatus(ob.getString("status"));
                        com.add(f);
                    }

                    BeanFactory.setService(com);

                } catch (JSONException e) {
                    Log.i("exception", ",," + e.toString());
                }


                //  Toast.makeText(mContext, "Data fetched succesfully", Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, MyAccount.class));
                Log.v("data", success + ",,");

            }
        } catch (Exception e) {
            Log.i("exception", e.toString() + ",,");
        }


    }


}


