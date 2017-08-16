package android.com.technicianclient.technician;

import android.app.Activity;
import android.app.ProgressDialog;
import android.com.technicianclient.technician.api.model.controller.APIService;
import android.com.technicianclient.technician.api.model.controller.BeforeLoginApi;
import android.com.technicianclient.technician.api.utils.ApiUtils;
import android.com.technicianclient.technician.beans.BeforeLogin;
import android.com.technicianclient.technician.beans.Customer;
import android.com.technicianclient.technician.beans.User;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.factory.BeanFactory;
import android.com.technicianclient.technician.model.LoadOnStartupData;
import android.com.technicianclient.technician.serverconnetors.ServerConnection;
import android.com.technicianclient.technician.services.LoadOnStartupService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import android.com.technicianclient.technician.contentprovider.SharedMethods;
import android.com.technicianclient.technician.controller.UiController;
import android.com.technicianclient.technician.serverconnetors.ServiceDataSender;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;


public class ServiceFragment extends Fragment {

    private Spinner spinnerServices;
    private String[] services;
    private String[] cities;
    private Spinner spinnerCities;

    private String city;
    private String item;
    public EditText name;
    public EditText phone;
    public EditText address;
    public MultiAutoCompleteTextView remarks;
    private Button submit;
    private LoadOnStartUp loadOnStartUp;

    private APIService mAPIService;
    private BeforeLoginApi mBeforeLogin;
    public ServiceFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        spinnerCities = (Spinner)view.findViewById(R.id.sp_cities);
        mAPIService = ApiUtils.getAPIService();
        mBeforeLogin = ApiUtils.getApiBeforeLogin();
        try {
            loadOnStartUp = new LoadOnStartUp(getActivity());
            loadOnStartUp.execute();

        }catch (Exception e){
            e.printStackTrace();
        }






        //initialize fields
        name = (EditText) view.findViewById(R.id.et_name);
        phone = (EditText)view.findViewById(R.id.et_phone);
        address = (EditText)view.findViewById(R.id.et_address);
        submit = (Button) view.findViewById(R.id.btnSubmit);
        remarks = (MultiAutoCompleteTextView) view.findViewById(R.id.remarks);

        spinnerServices = (Spinner)view.findViewById(R.id.sp_services);





        spinnerServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = String.valueOf(i+1);
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
        return view;
    }

    public void work(){

        sendPost("wassan@gmail.com", "12345",true);

        String name = this.name.getText().toString();
        String address = this.address.getText().toString();
        String phone = this.phone.getText().toString();
        String remarks = this.remarks.getText().toString();

        if (name.isEmpty()) {
            this.name.setError("Please enter name");
            return;
        }

//        if (address.isEmpty()) {
//            this.address.setError("Please enter address");
//            return;
//        }
        if (phone.isEmpty()) {
            this.phone.setError("Please enter phone number");
            return;
        }
        if (!SharedMethods.validatePhoneNumber(phone)){
            this.phone.setError("Invalid phone number");
            return;
        }
//        if (remarks.isEmpty()) {
//            UiController.showDialog("Please enter your message",getActivity());
//            return;
//        }

        if (!UiController.isNetworkAvailable(getContext()))
        {
            UiController.showDialog("Please connect to network",getActivity());
            return;
        }


        SharedMethods.hideKeyBoard(getActivity());
        ServiceDataSender sender = new ServiceDataSender(ServiceFragment.this);

        sender.execute(name,phone,address,item,remarks);

    }


    class LoadOnStartUp extends AsyncTask<Void, Void, String> {

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

        @Override
        protected void onPostExecute(final String success) {

            if (progressDialog2 != null)
                progressDialog2.dismiss();

            if (success == null) {
                Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                new LoadOnStartupData(success,getActivity());


                cities = new String[SharedFields.citiesName.size()];
                int i = 0;
                for (Map.Entry e : SharedFields.citiesName.entrySet()) {

                    cities[i] = e.getValue().toString();
                    i++;
                }


                //city = cities[0];
                services = new String[SharedFields.myServices.size()];
                for (int j = 0;j < services.length; j++) {
                    services[j] = SharedFields.myServices.get(j);
                }
                ArrayAdapter<String> servicesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, cities);
                spinnerCities.setAdapter(servicesArrayAdapter);

                item = String.valueOf(1);
                final ArrayAdapter<String> servicesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, services);
                spinnerServices.setAdapter(servicesAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    public void sendPost(String email, String pass,boolean flag) {
        loadOnStartUp();
        mAPIService.getUser(email, pass,flag).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        //showResponse(response.body().toString());
                         List<User> user =response.body();

                        for (User u: user){
                            Log.i("server call","id="+u.getId());
                        }

                            //Log.i("server call","id="+user.size());

                        Log.i("Server call", "post submitted to API." + user.get(0).getId());
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                Log.e("Server call", "Unable to submit post to API."+t.getMessage());
            }
        });
    }

    private void loadOnStartUp(){
        mBeforeLogin.getBeforeLogin(true).enqueue(new Callback<BeforeLogin>() {
            @Override
            public void onResponse(Call<BeforeLogin> call, Response<BeforeLogin> response) {
                try {
                    if (response.isSuccessful()) {
                        //showResponse(response.body().toString());
                        BeforeLogin user =response.body();
                        //Log.i("server call","id="+user.size());
                        Log.i("Server call", "Before login,post submitted to API." + user);
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BeforeLogin> call, Throwable t) {

                Log.e("Server call", "Before login,Unable to submit post to API."+t.getMessage());
            }
        });

    }
    public void showResponse(String response) {
//        if(mResponseTv.getVisibility() == View.GONE) {
//            mResponseTv.setVisibility(View.VISIBLE);
//        }

        Toast.makeText(getContext(), "Response="+response, Toast.LENGTH_SHORT).show();
       // mResponseTv.setText(response);
    }
}
