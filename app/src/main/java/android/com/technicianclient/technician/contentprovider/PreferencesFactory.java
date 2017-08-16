package android.com.technicianclient.technician.contentprovider;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

public class PreferencesFactory {

    private static final String TECHNICIAN = "Technician" ;
    private static SharedPreferences preference;

    public static SharedPreferences.Editor  getEditor(Context context){
        if (preference == null)
            preference = context.getSharedPreferences(TECHNICIAN, Context.MODE_PRIVATE);

        return preference.edit();
    }
    public static SharedPreferences getSharedPreferences(Context context){
        if (preference == null)
            preference = context.getSharedPreferences(TECHNICIAN, Context.MODE_PRIVATE);

        return preference;
    }

    public static File preferenceFileExist(Context context) {
        File f = new File(context.getApplicationInfo().dataDir + "/shared_prefs/"
                + TECHNICIAN + ".xml");
        return f;
    }
}
