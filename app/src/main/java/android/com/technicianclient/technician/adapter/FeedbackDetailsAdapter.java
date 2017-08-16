package android.com.technicianclient.technician.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import android.com.technicianclient.technician.R;
import android.com.technicianclient.technician.beans.Feedback;
import android.com.technicianclient.technician.beans.Service;

import java.util.List;

/**
 * Created by Admin on 5/31/2017.
 */

public class FeedbackDetailsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Feedback> feedbacks;
    private Activity context;

    public FeedbackDetailsAdapter(Activity context, List<Feedback> feedbacks) {
        this.context = context;
        this.feedbacks = feedbacks;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.v("feedback","feed");
    }

    @Override
    public int getCount() {
        return feedbacks.size();
    }

    @Override
    public Object getItem(int i) {
        return feedbacks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.feedback_details_item, null);//set layout for displaying items

        try {
            TextView tvId = (TextView) view.findViewById(R.id.tvId);
            TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
            TextView tvFeedback = (TextView) view.findViewById(R.id.tvFeedback);
            TextView tvRecomended = (TextView) view.findViewById(R.id.tvRecomended);

            Log.v("feedback", "feed");

            Feedback history = feedbacks.get(i);
            tvId.setText(history.getId());
            tvDate.setText(history.getDate());
            tvFeedback.setText(history.getFeedback());
            tvRecomended.setText(history.getRecomended());
        }catch (Exception e){
            Log.v("exception", "feed "+e.getMessage());
            e.printStackTrace();
        }
        return view;
    }
}