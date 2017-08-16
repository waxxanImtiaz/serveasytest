package android.com.technicianclient.technician.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 8/16/2017.
 */

public class ServiceAreas {
    @SerializedName("city_name")
    private String cityName;
    @SerializedName("city_id")
    private String cityId;

    @SerializedName("area_name")
    private String areaName;
    @SerializedName("area_id")
    private String areaId;

}
