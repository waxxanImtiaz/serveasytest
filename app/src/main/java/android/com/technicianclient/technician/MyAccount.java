package android.com.technicianclient.technician;

import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import android.com.technicianclient.technician.adapter.FeedbackDetailsAdapter;
import android.com.technicianclient.technician.adapter.PymentHistoryAdapter;
import android.com.technicianclient.technician.adapter.ServiceDetailsAdapter;
import android.com.technicianclient.technician.beans.Service;
import android.com.technicianclient.technician.beans.Customer;
import android.com.technicianclient.technician.beans.Feedback;
import android.com.technicianclient.technician.beans.PaymentHistory;
import android.com.technicianclient.technician.factory.BeanFactory;

import java.util.List;

public class MyAccount extends AppCompatActivity {

    private TextView tvEmail;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvGender;
    private TextView tvCity;
    private TextView tvMobileNumber;
    private Customer customer;
//    private List<Feedback> feedbacks;
//    private List<Service> service;
//    private List<PaymentHistory> paymentHistories;
//
//    private ListView listPayDetails;
//    private ListView listServicesDetails;
//    private ListView listFeedbackDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvName = (TextView) findViewById(R.id.tvName);
//        tvAddress = (TextView)findViewById(R.id.tvAddress);
//        tvGender = (TextView)findViewById(R.id.tvGender);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvMobileNumber = (TextView) findViewById(R.id.tvMobileNumber);


        SharedFields.isExited = false;
//        listPayDetails = (ListView) findViewById(R.id.listPayDetails);
//        listServicesDetails = (ListView) findViewById(R.id.listServicesDetails);
//        listFeedbackDetails = (ListView) findViewById(R.id.feedbackDetails);
        customer = BeanFactory.getCustomer();

        tvEmail.setText(customer.getEmail());
        tvName.setText(customer.getName());
//        tvAddress.setText(customer.getAddress());
//        for (Map.Entry e : SharedFields.cities) {
        tvCity.setText(customer.getCity());
//        }
        tvMobileNumber.setText(customer.getMobile());
//        tvGender.setText(customer.getGender());

//        feedbacks = BeanFactory.getFeedbacks();
//        service = BeanFactory.getService();
//        paymentHistories = BeanFactory.getPaymentHistories();
//
//        try {
//            List<PaymentHistory> paymentHistories = BeanFactory.getPaymentHistories();
//            Log.i("paymentHistories", "paymentHistories size=" + paymentHistories.size());
//
//            if (paymentHistories == null || paymentHistories.size() <= 0) {
//                findViewById(R.id.tvPaymentHistory).setVisibility(View.GONE);
//                listPayDetails.setVisibility(View.GONE);
//            } else {
//                PymentHistoryAdapter adapter = new PymentHistoryAdapter(MyAccount.this, paymentHistories);
//                listPayDetails.setAdapter(adapter);
//            }
//
//            List<Service> services = BeanFactory.getService();
//
//            Log.i("services", "services size=" + services.size()+",feedback size="+BeanFactory.getFeedbacks().size());
//            if (services.size() <= 0) {
//                findViewById(R.id.tvServiceDetails).setVisibility(View.GONE);
//                listServicesDetails.setVisibility(View.GONE);
//            } else {
//                ServiceDetailsAdapter adapter = new ServiceDetailsAdapter(MyAccount.this, BeanFactory.getService());
//                listServicesDetails.setAdapter(adapter);
//            }
//
//            List<Feedback> feedbacks = BeanFactory.getFeedbacks();
//
//
//            if ( feedbacks.size() <= 0) {
//                findViewById(R.id.tvFeedback).setVisibility(View.GONE);
//                listFeedbackDetails.setVisibility(View.GONE);
//            } else {
//
//                FeedbackDetailsAdapter adapter = new FeedbackDetailsAdapter(MyAccount.this, feedbacks);
//                listFeedbackDetails.setAdapter(adapter);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.i("exception","error:"+e.toString());
//        }

    }
}
