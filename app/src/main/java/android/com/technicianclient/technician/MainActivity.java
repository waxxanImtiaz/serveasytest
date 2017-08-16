package android.com.technicianclient.technician;

import android.Manifest;
import android.app.Activity;
import android.com.technicianclient.technician.contentprovider.SharedMethods;
import android.com.technicianclient.technician.controller.DialogInnerIntializer;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.com.technicianclient.technician.contentprovider.PreferencesFactory;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.contentprovider.SharedPreferencesDataLoader;
import android.com.technicianclient.technician.customclass.CustomViewPager;
import android.com.technicianclient.technician.serverconnetors.UserInfoService;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private CustomViewPager viewPager;
    private TabLayout tabLayout;
    private File sharedPrefFile;
    private String m_Text;
    private String[] points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkPermission();

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        //       this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // drawer.setDrawerListener(toggle);
        // toggle.syncState();

        //  NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //  navigationView.setNavigationItemSelectedListener(this);

        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (SharedFields.isExited) {
            sharedPrefFile = PreferencesFactory.preferenceFileExist(this);
            if (sharedPrefFile.exists())
                sharedPrefFile.delete();
        }

    }


    public void checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.INTERNET)) {

            }

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

        } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.INTERNET},
                    SharedFields.INTERNET_PERMISSION);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
        //access network state
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_NETWORK_STATE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                        SharedFields.NETWORK_STATE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ServiceFragment(), getResources().getString(R.string.service));
        adapter.addFragment(new ComplainFragment(), getResources().getString(R.string.terms_and_conditions));
        adapter.addFragment(new FeedBackFragment(), getResources().getString(R.string.feedback));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_in) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return true;
        } else if (id == R.id.sign_up) {
            startActivity(new Intent(MainActivity.this, SignUp.class));
            return true;
        } else if (id == R.id.my_account) {
//            UserInfoService service  = new UserInfoService(this);
//            service.execute();
            startActivity(new Intent(MainActivity.this,MyAccount.class));
            return true;
        }
//        else if (id == R.id.reward_points) {
//            points = new String[]{"1","2","3","4","5","6","7","8","9","10"};
//            showRewardDialog();
//            return true;
//        }
        else if (id == R.id.contact_us) {
            tabLayout.getTabAt(2).select();
            return true;
        } else if (id == R.id.bind_email) {
            m_Text = SharedMethods.showInputDialog(this, new DialogInnerIntializer() {
                @Override
                public void execute(String input) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.repair_history_message), Toast.LENGTH_LONG).show();
                }
            });
            return true;
        } else if (id == R.id.sign_out) {

            if (sharedPrefFile.exists())
                sharedPrefFile.delete();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.clear();

        MenuInflater myMenuInflater = getMenuInflater();

        sharedPrefFile = PreferencesFactory.preferenceFileExist(this);

        if (!sharedPrefFile.exists()) {
            SharedPreferencesDataLoader.getCustomer(this);
            myMenuInflater.inflate(R.menu.main, menu);
        } else
            myMenuInflater.inflate(R.menu.logged_in_menu, menu);

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public  void showRewardDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Reward Points");
        builder.setItems(points, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
            }
        });
        builder.show();
    }
}
