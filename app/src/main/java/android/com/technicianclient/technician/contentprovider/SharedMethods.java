package android.com.technicianclient.technician.contentprovider;

import android.app.Activity;
import android.com.technicianclient.technician.R;
import android.com.technicianclient.technician.controller.DialogInnerIntializer;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.com.technicianclient.technician.mail.GMailSender;
import android.widget.EditText;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 5/23/2017.
 */

public class SharedMethods {

    public static boolean validatePhoneNumber(String phoneNo) {
        String regex = "^\\+?[0-9. ()-]{10,25}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNo);

        if (phoneNo.length() < 11 || phoneNo.length() > 11)
            return false;


        if (!phoneNo.startsWith("03"))
            return false;
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static String showInputDialog(Activity activity, final DialogInnerIntializer innerIntializer) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getString(R.string.email_message));

// Set up the input
        final EditText input = new EditText(activity);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                innerIntializer.execute(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setCancelable(false);
        builder.show();
        return input.getText().toString();
    }

    public static boolean isValidEmailAddress(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    public static void hideKeyBoard(Activity c) {
        // Check if no view has focus:
        View view = c.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static void sendEmail(final String recipent) {
        new Thread() {
            public void run() {

                try {
                    GMailSender sender = new GMailSender(SharedFields.EMAIL_APP, SharedFields.EMAIL_PASS);
                    if (sender.sendMail(SharedFields.EMAIL_SUBJECT,
                            SharedFields.EMAIL_BODY,
                            recipent,
                            recipent)) {
                        Log.d("Email", "Email sent successfully to " + recipent);
                    } else {
                        Log.d("email did not sent to", "reciepent:" + recipent);
                    }


                } catch (Exception e) {
                    Log.e("SendMail except:", e.getMessage(), e);
                }
            }
        }.start();
    }

//    public static String[] loadArray(Map map) {
//        String[] arr = new String[map.size()];
//        int i = 0;
//        for (Map.Entry e : map.entrySet()) {
//
//            arr[i] = e.getValue().toString();
//        }
//
//        return arr;
//    }
}

