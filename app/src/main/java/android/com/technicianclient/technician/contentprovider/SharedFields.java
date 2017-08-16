package android.com.technicianclient.technician.contentprovider;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Created by Ghulam Ali on 4/15/2017.
 */
public class SharedFields {
    public static SharedPreferences.Editor editor;
    public static final String serverLink = "http://serveasy.pk/andriod_services/";
    public static final String URL = serverLink.concat("users.php");

    public static  final int CONNECTION_TIME =  100000;
    public static String userId = "2";

    public static boolean isExited = true;

    public static final String DEBUG_MESSAGE = "server_error";

    //PERMISSION CODES

    public static final int INTERNET_PERMISSION = 10;
    public static final int NETWORK_STATE = 120;

    //Facebook login fields

    public static final int REQUEST_CODE_FB = 12;
    public static final int RESULT_CODE_FB = 26;

    public static final String fbId = "id";

    public static Map<Integer,String> areas = new HashMap<>();
    public static Map<Integer,String> cities = new HashMap<>();
    public static Map<Integer,String> services = new HashMap<>();

    public static final String EMAIL_APP = "serveasy.pk@gmail.com";
    public static final String EMAIL_PASS = "providinghomeservice";
    public static final String EMAIL_SUBJECT = "Serveasy Application";
    public static final String EMAIL_BODY = "This is test email.";

    public static final int SPLASH_SCREEN_TIMER = 100;

    public static Map<Integer,String> serviceTypes = new TreeMap<>();
    public static Map<Integer,String> citiesName = new TreeMap<>();
    public static Map<Integer,String> myAreas = new TreeMap<>();
    public static Vector<String> myServices = new Vector<>();
}
