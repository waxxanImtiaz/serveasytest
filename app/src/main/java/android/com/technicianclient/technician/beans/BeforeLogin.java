package android.com.technicianclient.technician.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 8/16/2017.
 */

public class BeforeLogin {

//    @SerializedName("products_brands")
//    private List<List<ProductBrands>> productBrands;
//    @SerializedName("cities_areas")
//    private List<List<ServiceAreas>> citAreas;

    @SerializedName("terms_conditions")
    private String termsAndCondtions;


    @SerializedName("app_start_without_login")
    private String withoutLogin;


    public String getWithoutLogin() {
        return withoutLogin;
    }

    public String toString(){
        return String.format("%s\n",termsAndCondtions);
    }
}
