package android.com.technicianclient.technician;

import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import android.com.technicianclient.technician.contentprovider.SharedMethods;
import android.com.technicianclient.technician.controller.UiController;
import android.com.technicianclient.technician.serverconnetors.FeedBackService;

import java.util.Map;


public class FeedBackFragment extends Fragment {
    private Spinner spinnerCities;
    private Spinner spRating;
    private String[] cities;
    private String[] rates;

    private String city;
    public EditText name;
    public EditText email;
    public EditText phone;
    public MultiAutoCompleteTextView remarks;
    private Button submit;
    public String rateText;
    public FeedBackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_back, container, false);
        spinnerCities = (Spinner) view.findViewById(R.id.sp_cities);
        spRating = (Spinner) view.findViewById(R.id.sp_rating);

        cities = new String[SharedFields.citiesName.size()];
        int i = 0;
        for (Map.Entry e : SharedFields.citiesName.entrySet()) {

            cities[i] = e.getValue().toString();
            i++;
        }
        //cities = new String[]{"Karachi", "Hyderabad", "Sukkur"};
        rates = new String[]{"Bad", "Average", "Excellent"};

        ArrayAdapter<String> ratesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rates);
        spRating.setAdapter(ratesAdapter);
        //initialize fields
        name = (EditText) view.findViewById(R.id.et_name);
        phone = (EditText) view.findViewById(R.id.et_phone);
//        email = (EditText)view.findViewById(R.id.et_email);
        submit = (Button) view.findViewById(R.id.btnSubmit);
        remarks = (MultiAutoCompleteTextView) view.findViewById(R.id.remarks);

        ArrayAdapter<String> servicesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, cities);
        spinnerCities.setAdapter(servicesArrayAdapter);

        city = cities[0];
        rateText = rates[0];
        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city = String.valueOf((i+1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rateText = String.valueOf(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                work();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    public void work() {

        String name = this.name.getText().toString();
//        String email = this.email.getText().toString();
        String phone = this.phone.getText().toString();
        String remarks = this.remarks.getText().toString();

        if (name.isEmpty()) {
            this.name.setError("Please enter name");
            return;
        }
        if (phone.isEmpty()) {
            this.phone.setError("Please enter phone number");
            return;
        }

        if (!SharedMethods.validatePhoneNumber(phone)){
            this.phone.setError("Invalid phone number");
            return;
        }
//        if (email.isEmpty()) {
//            this.email.setError("Please enter email address");
//            return;
//        }
//        if (remarks.isEmpty()) {
//            UiController.showDialog("Please enter feedback", getActivity());
//            return;
//        }

        if (!UiController.isNetworkAvailable(getContext())) {
            UiController.showDialog("Please connect to network", getActivity());
            return;
        }

        SharedMethods.hideKeyBoard(getActivity());

        FeedBackService service = new FeedBackService(this);
        service.execute(name, phone, city, remarks, "not logged in",rateText);
//        service.execute(name,phone,city,remarks,email);
    }

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);


    }

}
