package android.com.technicianclient.technician.serverconnetors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import android.com.technicianclient.technician.FeedBackFragment;
import android.com.technicianclient.technician.builder.ComplainFormHandler;
import android.com.technicianclient.technician.builder.FeedBackFormHandler;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.controller.UiController;

/**
 * Created by Ghulam Ali on 5/20/2017.
 */
public class FeedBackService extends AsyncTask<String, Void, String> {

    private FeedBackFragment mContext;
    private ProgressDialog progressDialog2;
    public FeedBackService(FeedBackFragment mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... params) {

        FeedBackFormHandler handler = new FeedBackFormHandler(mContext.getContext());
        handler.setUrl(SharedFields.URL);
        handler.setRequestMethod("POST");

        return handler.setFormParametersAndConnect(params[0], params[1],
                params[2], params[3], params[4],params[5]);
    }

    @Override
    protected void onPostExecute(String result) {
        progressDialog2.dismiss();
        if (result.equalsIgnoreCase("success")) {

            UiController.showDialog("Feedback submitted succesffully", mContext.getActivity());

            //mContext.email.setText("");
            mContext.name.setText("");
            mContext.remarks.setText("");
            mContext.phone.setText("");

        } else
            UiController.showDialog("Feedback not submitted", mContext.getActivity());

    }

    @Override
    protected void onPreExecute() {
        progressDialog2 = ProgressDialog.show(mContext.getContext(),"", "Loading");
    }
}
