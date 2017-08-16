package android.com.technicianclient.technician.api.model.controller;

import android.com.technicianclient.technician.beans.BeforeLogin;
import android.com.technicianclient.technician.beans.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Admin on 8/16/2017.
 */

public interface BeforeLoginApi {

    @FormUrlEncoded
    @POST("users.php")
    Call<BeforeLogin> getBeforeLogin(@Field("app_start_without_login") boolean app_start_without_login);

}
