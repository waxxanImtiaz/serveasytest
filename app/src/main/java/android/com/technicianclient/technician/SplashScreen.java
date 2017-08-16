package android.com.technicianclient.technician;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.com.technicianclient.technician.contentprovider.SharedFields;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //new Handler().postDelayed(new Runnable() {
         //   @Override
        //    public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                // close this activity
                finish();
        //    }
       // }, SharedFields.SPLASH_SCREEN_TIMER);
    }
}
