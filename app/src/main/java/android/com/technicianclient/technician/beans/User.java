package android.com.technicianclient.technician.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 8/10/2017.
 */

public class User {
    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;


    @SerializedName("password")
    private String password;

    @SerializedName("new_sign_in")
    private boolean newSignIn;

    public String getId() {
        return id;
    }
}
