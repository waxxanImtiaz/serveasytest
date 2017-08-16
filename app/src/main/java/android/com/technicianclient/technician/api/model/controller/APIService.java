package android.com.technicianclient.technician.api.model.controller;

/**
 * Created by Admin on 8/10/2017.
 */

import android.com.technicianclient.technician.beans.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("users.php")
    Call<List<User>> getUser(@Field("email") String email,
                             @Field("password") String password, @Field("new_sign_in") boolean new_sign_in);


}