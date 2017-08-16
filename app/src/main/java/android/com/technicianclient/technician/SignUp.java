package android.com.technicianclient.technician;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.com.technicianclient.technician.beans.Customer;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.contentprovider.SharedMethods;
import android.com.technicianclient.technician.contentprovider.SharedPreferencesDataLoader;
import android.com.technicianclient.technician.controller.UiController;
import android.com.technicianclient.technician.factory.BeanFactory;
import android.com.technicianclient.technician.serverconnetors.SignUpService;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Spinner spGender;
    private EditText etName;
    private EditText etEmail;
    private EditText etMobile;
    private EditText etPassword;
    private Button btnSubmit;
    private EditText etConformPassword;
    private String[] cities;
    private Spinner spinnerCities;

    private LoginButton loginButton;
    private String city;
    private AccessToken accessToken;
    CallbackManager callbackManager;
    private GraphRequest request;
    private String id;
    private TextView tvLoginStatus;
    private Profile profile;
    private Customer customer;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
        setContentView(R.layout.activity_sign_up);

        spGender = (Spinner) findViewById(R.id.sp_gender);
        spinnerCities = (Spinner) findViewById(R.id.sp_cities);
        cities = new String[]{"Karachi", "Hyderabad", "Sukkur"};


        ArrayAdapter<String> servicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        spinnerCities.setAdapter(servicesArrayAdapter);



        SharedFields.isExited = false;

        city = String.valueOf(1);
        // Gender Drop down elements
        List<String> gender = new ArrayList<>();
        gender.add("Male");
        gender.add("Female");


        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);
        spGender.setAdapter(genderAdapter);

        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city = String.valueOf(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        initFields();

    }


    public void initFields() {
        etConformPassword = (EditText) findViewById(R.id.et_confirm_password);
        etEmail = (EditText) findViewById(R.id.et_email);
        etMobile = (EditText) findViewById(R.id.et_contact_number);
        etName = (EditText) findViewById(R.id.et_name_sign_up);
        etPassword = (EditText) findViewById(R.id.et_password);
        tvLoginStatus = (TextView) findViewById(R.id.tvLoginStatus);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        //intiRequest();
        //loginButton.setReadPermissions(Arrays.asList( "email"));

        LoginManager.getInstance().logOut();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                tvLoginStatus.setText(String.valueOf("Welcome onCurrentAccessTokenChanged"));
                //intiRequest();
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                //nextActivity(newProfile);

                if (oldProfile != null) {
                    profile = oldProfile;
                    //intiRequest();
                    tvLoginStatus.setText(String.valueOf(oldProfile.getFirstName()+" you are already regirstered"));

                }
                if (newProfile != null) {
                    profile = newProfile;
                    //intiRequest();
                    tvLoginStatus.setText(String.valueOf( newProfile.getFirstName()+" you are already regirstered"));

                }

            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d("login", "Login success");

                id = loginResult.getAccessToken().getUserId();

//                Thread t = new Thread() {
//
//                    public void run() {
//
//
//                        Toast.makeText(SignUp.this, "Logged in", Toast.LENGTH_SHORT).show();
//                    }
//                };
//
//                runOnUiThread(t);

                // intiRequest();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                try {
                                    //JSONObject hometown = object.getJSONObject("hometown");
//                                    final String town = hometown.getString("name");

                                    // Application code
                                    //String birthday = object.getString("birthday"); // 01/31/1980 format
                                    customer = new Customer();

                                    customer.setName(object.getString("name"));
                                    etName.setText(customer.getName());

                                    customer.setEmail(object.getString("email"));
                                    etEmail.setText(customer.getEmail());


                                    customer.setGender(object.getString("gender"));
                                    customer.setFbId(object.getString(SharedFields.fbId));
                                    if (customer.getGender().equalsIgnoreCase("male")) {
                                        spGender.setSelection(0);
                                        spGender.refreshDrawableState();
                                    }
                                    else{ spGender.setSelection(1);
                                        spGender.refreshDrawableState();}


                                    //c.setPassword("fb_user");

                                    loginButton.setVisibility(View.GONE);

                                } catch (Exception e) {
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
                tvLoginStatus.setText("Login cancelled");

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                UiController.showDialog("Please check you internet connection", SignUp.this);
                //tvLoginStatus.setText(exception.getMessage());
                Log.v("FacebookException", exception.getMessage());
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        callbackManager.onActivityResult(requestCode, responseCode, data);
        //intiRequest();
        //Toast.makeText(this, "You are logged in", Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity() {
        Intent i = new Intent(SignUp.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {

        Customer c = BeanFactory.getCustomer();

        if (TextUtils.isEmpty(etName.getText().toString())) {
            etName.setError("Enter name");
            return;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Enter password");
            return;
        }
        if (TextUtils.isEmpty(etConformPassword.getText().toString())) {
            etConformPassword.setError("Enter confirm password");
            return;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Enter email");
            return;
        }
        if (!SharedMethods.isValidEmailAddress(etEmail.getText().toString())) {
            etEmail.setError("Invalid email address");
            return;
        }
        if (TextUtils.isEmpty(etMobile.getText().toString())) {
            etMobile.setError("Enter Mobile number");
            return;
        }
        if (!SharedMethods.validatePhoneNumber(etMobile.getText().toString())) {
            etMobile.setError("Invalid phone number");
            return;
        }





        if (!TextUtils.equals(etPassword.getText().toString(),etConformPassword.getText().toString())){
            //UiController.showDialog("Password doesn't match",SignUp.this);
            etConformPassword.setError("Doesn't match with password");
            return;
        }
        if (customer == null || TextUtils.isEmpty(customer.getFbId())){
            c.setFbId("");
        }
        else c.setFbId(customer.getFbId());

        c.setName(etName.getText().toString());
        c.setMobile(etMobile.getText().toString());
        c.setEmail(etEmail.getText().toString());
        c.setCity(city);
        c.setPassword(etPassword.getText().toString());
        c.setMobile(etMobile.getText().toString());
        BeanFactory.setCustomer(c);


        SharedMethods.hideKeyBoard(this);

        SignUpService service = new SignUpService(this);
        service.execute();

    }

}
