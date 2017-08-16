package android.com.technicianclient.technician;

/**
 * Created by wassa_000 on 3/23/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import android.com.technicianclient.technician.builder.ComplainFormHandler;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.controller.UiController;
import android.com.technicianclient.technician.serverconnetors.ComplainService;


public class ComplainFragment extends Fragment{

    private MultiAutoCompleteTextView tvComplain;
    private Button btnSubmit;

    private EditText phone;
    public ComplainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_complain, container, false);

//        phone = (EditText)v.findViewById(R.id.et_phone);
//
//        tvComplain = (MultiAutoCompleteTextView)v.findViewById(R.id.complain);
//        btnSubmit = (Button)v.findViewById(R.id.btnSubmit);
//
//        // Inflate the layout for this fragment

        return v;
    }

    /*final RatingBar minimumRating = (RatingBar)findViewById(R.id.myRatingBar);
    minimumRating.setOnTouchListener(new OnTouchListener()
    {
        public boolean onTouch(View view, MotionEvent event)
        {
            float touchPositionX = event.getX();
            float width = minimumRating.getWidth();
            float starsf = (touchPositionX / width) * 5.0f;
            int stars = (int)starsf + 1;
            minimumRating.setRating(stars);
            return true;
        }
    });*/

    private class ButtonOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){

            String complain = tvComplain.getText().toString();

            String p = phone.getText().toString();

            if (p.isEmpty()) {
                phone.setError("Please enter phone number");
                return;
            }

            if (complain.isEmpty()){
                UiController.showDialog("Please enter complain",getActivity());
            }
            else{
                if (!UiController.isNetworkAvailable(getContext()))
                {
                    UiController.showDialog("Please connect to network",getActivity());
                    return;
                }
                ComplainService service = new ComplainService(getActivity());
                service.execute(complain);
            }
        }
    }
    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

//        btnSubmit.setOnClickListener(new ButtonOnClickListener());

    }

}