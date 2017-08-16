package android.com.technicianclient.technician.api.utils;

import android.com.technicianclient.technician.api.model.controller.APIService;
import android.com.technicianclient.technician.api.model.controller.BeforeLoginApi;
import android.com.technicianclient.technician.api.model.controller.RetrofitClient;
import android.com.technicianclient.technician.beans.BeforeLogin;
import android.com.technicianclient.technician.contentprovider.SharedFields;

/**
 * Created by Admin on 8/10/2017.
 */

public class ApiUtils {

    private ApiUtils() {}


    public static APIService getAPIService() {

        return RetrofitClient.getClient(SharedFields.serverLink).create(APIService.class);
    }
    public static BeforeLoginApi getApiBeforeLogin() {

        return RetrofitClient.getClient(SharedFields.serverLink).create(BeforeLoginApi.class);
    }
}
