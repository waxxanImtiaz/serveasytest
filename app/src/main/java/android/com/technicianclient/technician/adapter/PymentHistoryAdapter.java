package android.com.technicianclient.technician.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import android.com.technicianclient.technician.R;
import android.com.technicianclient.technician.beans.PaymentHistory;

import java.util.List;

/**
 * Created by Admin on 5/30/2017.
 */

public class PymentHistoryAdapter  extends BaseAdapter {

    private LayoutInflater inflater;
    private List<PaymentHistory> paymentHistories;
    private Activity context;
    public PymentHistoryAdapter(Activity context, List<PaymentHistory> paymentHistories){
        this.context = context;
        this.paymentHistories = paymentHistories;
        inflater = context.getLayoutInflater();
    }
    @Override
    public int getCount() {
        return paymentHistories.size();
    }

    @Override
    public Object getItem(int i) {
        return paymentHistories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.payment_history_item, null);//set layout for displaying items

        TextView tvId = (TextView)view.findViewById(R.id.tvId);
        TextView tvModel = (TextView)view.findViewById(R.id.tvModel);
        TextView tvPaymentHistory = (TextView)view.findViewById(R.id.tvPaymentHistory);


        PaymentHistory history = paymentHistories.get(i);
        tvId.setText(history.getId());

        tvModel.setText(history.getModel());
        tvPaymentHistory.setText(history.getAmountRecieved());

        return view;
    }
}