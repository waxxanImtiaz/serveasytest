package android.com.technicianclient.technician.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import android.com.technicianclient.technician.R;
import android.com.technicianclient.technician.beans.PaymentHistory;
import android.com.technicianclient.technician.beans.Service;

import java.util.List;

/**
 * Created by Admin on 5/30/2017.
 */

public class ServiceDetailsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Service> services;
    private Activity context;
    public ServiceDetailsAdapter(Activity context, List<Service> services){
        this.context = context;
        this.services = services;
        inflater = context.getLayoutInflater();
    }
    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int i) {
        return services.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.payment_history_item, null);//set layout for displaying items

        TextView tvId = (TextView)view.findViewById(R.id.tvId);
        TextView tvDate = (TextView)view.findViewById(R.id.tvDate);
        TextView tvComplain = (TextView)view.findViewById(R.id.tvComplain);
        TextView tvStatus = (TextView)view.findViewById(R.id.tvStatus);


        Service history = services.get(i);
        tvId.setText(history.getId());


        tvDate.setText(history.getDate());
        tvComplain.setText(history.getComplain());
        tvStatus.setText(history.getStatus());

        return view;
    }
}