package com.infovita.zywee.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.infovita.zywee.Adopters.MaingridAdapter;
import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.Pojo.City;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Sharedvalues.ServiceHandler;
import com.infovita.zywee.Supports.CustomExpandableListAdapter;
import com.infovita.zywee.Supports.ExpandableListDataPump;
import com.infovita.zywee.Supports.InternetStatus;
import com.infovita.zywee.Supports.MarshMallowPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class LandingActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected static final String TAG = "LandingActivity";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    private ActionBarDrawerToggle mDrawerToggle;
    private CustomExpandableListAdapter expandableListAdapter1;
    private ServiceHandler sh;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    GridView gv;
    Serverdatas sd = Serverdatas.getSingletonObject();
    public String server_url = sd.url;
    public static String[] mainlist = {
            "Medical Tests",
            "Health Packages",
            "Medical Equipments",
            "Doctors",
            "Ambulance",
            "Home Services",
            "My Reports",
            "Medicine"
    };
    public static int[] mainlistImages = {
            R.drawable.tests_white_96,
            R.drawable.healthcheck_white_96,
            R.drawable.equipment_white_96,
            R.drawable.doctor_white_96,
            R.drawable.ambulance_white_96,
            R.drawable.homehealth_white_96min,
            R.drawable.homehealth_white_96min,
            R.drawable.homehealth_white_96min
    };
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    public ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBar actionBar;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<String, List<String>> expandableListDetail;

    List<Integer> drawable = new ArrayList<>();
    private ImageView indicator;

    private int lastExpandedPosition = -1;

    View testview;
    Context context;
    private Spinner city;
    String cityname,cityid;
    String id = "1";
    List<String> cityList;
    List<City> res;

    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int MY_PERMISSION_LOCATION = 2;

    private static String[] REQUEST_GPS_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        expandableListView = (ExpandableListView) findViewById(R.id.left_drawer);
        expandableListDetail = ExpandableListDataPump.getData();
        //Log.d("hashmapData", ExpandableListDataPump.getData() + "");
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        indicator = new ImageView(this);
        verifyStoragePermissions(this);
        verifyGPSPermissions(this);
        if (isOnline())
        cities();
        deleteCache(this);

        city = (Spinner) findViewById(R.id.spinner);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //private TextView selectedSpinner;


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    // TODO Auto-generated method stub
                    cityname = parent.getSelectedItem().toString();
                    cityid = res.get(position).getCityId().toString();
                    Log.d("city",cityid);
                    SharedPreferences.Editor editor = getSharedPreferences(sd.cityid, MODE_PRIVATE).edit();
                    editor.putString("city_id", cityid);
                    editor.commit();
                    Log.d("city_id",cityid);
                    //selectedSpinner.setText(str);
                } catch (NullPointerException e) {
                    Log.v(TAG, "Null Exception in connecting to server: " + e.toString());
                } catch (Exception e) {
                    Log.v(TAG, "Generic Exception in connecting to server: " + e.toString());
                }
                //Toast.makeText(getApplicationContext(), " Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        /*SharedPreferences.Editor editor = getPreferences(0).edit();
        int selectedPosition = city.getSelectedItemPosition();
        editor.putInt(sd., selectedPosition);
        editor.commit();*/



        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);

        if (!marshMallowPermission.checkPermissionForCoarseLocation())
            marshMallowPermission.requestPermissionForCoarseLocation();
        else buildGoogleApiClient();

        //Log.d("title", expandableListTitle + "");

        drawable.add(R.drawable.tests_gray_72);
        drawable.add(R.drawable.healthcheck_gray_72);
        drawable.add(R.drawable.equipment_gray_72);
        drawable.add(R.drawable.doctor_gray_72);
        drawable.add(R.drawable.ambulance_gray_72);
        drawable.add(R.drawable.homehealth_gray);
        drawable.add(R.drawable.transparent);
        drawable.add(R.drawable.transparent);
        drawable.add(R.drawable.transparent);
        drawable.add(R.drawable.transparent);


        findViewById(R.id.header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, ProfileActivity.class);
                startActivity(intent);
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }
            }
        });


        expandableListAdapter = new CustomExpandableListAdapter(this, drawable, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {


            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                if (groupPosition == 0) {

                    if (parent.isGroupExpanded(groupPosition)) {
                        parent.collapseGroup(groupPosition);
                    } else {
                        parent.expandGroup(groupPosition);

                    }

                } else if (groupPosition == 1) {

                    if (parent.isGroupExpanded(groupPosition)) {
                        parent.collapseGroup(groupPosition);
                    } else {
                        parent.expandGroup(groupPosition);

                    }

                } else if (groupPosition == 2) {

                    if (parent.isGroupExpanded(groupPosition)) {
                        parent.collapseGroup(groupPosition);
                    } else {
                        parent.expandGroup(groupPosition);

                    }
               /* } else if (groupPosition == 3) {

                    if (parent.isGroupExpanded(groupPosition)) {
                        parent.collapseGroup(groupPosition);
                    } else {
                        parent.expandGroup(groupPosition);

                    }*/

                } else if (groupPosition == 4) {

                    if (parent.isGroupExpanded(groupPosition)) {
                        parent.collapseGroup(groupPosition);
                    } else {
                        parent.expandGroup(groupPosition);

                    }
                } else if (groupPosition == 5) {

                    if (parent.isGroupExpanded(groupPosition)) {
                        parent.collapseGroup(groupPosition);
                    } else {
                        parent.expandGroup(groupPosition);

                    }

                } else if (groupPosition == 8) {

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");

                    String shareBody = "Hey! Check out Zywee Health App, Find Test Centers, Hospitals, Doctors, Ambulance, Medical equipments and Book appointment online. Download free app now  \n https://play.google.com/store/apps/details?id=com.infovita.zywee \n  @ Zywee , Thank you";
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Zywee  App");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    expandableListView.setDivider(null);
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }

                } else if (groupPosition == 9) {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.infovita.zywee"));
                    startActivity(intent);


                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }

                } else if (groupPosition == 6) {
                    Intent intent = new Intent(LandingActivity.this, BookAppointment.class);
                    // intent.putExtra("default_view", 2);
                    startActivity(intent);
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }

                } else if (groupPosition == 7) {
                    Intent intent = new Intent(LandingActivity.this, MyReports.class);
                    // intent.putExtra("default_view", 2);
                    startActivity(intent);
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }

                } else if (groupPosition == 3) {
                    Intent intent = new Intent(LandingActivity.this, MaincategoriesActivity.class);
                    intent.putExtra("default_view", 2);
                    startActivity(intent);
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                } else {
                    snakbarMessage(v, "Coming Soon");
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                }

                return true;
            }
        });


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if (groupPosition == 0 & childPosition >= 0) {
                    Intent intent = new Intent(LandingActivity.this, TestResultsActivity.class);
                    intent.putExtra("Testtype", childPosition + 1 + "");
                    intent.putExtra("TesttypeName", expandableListDetail.get("Medical Tests").get(childPosition) + "");
                    intent.putExtra("health_institute_id", "");
                    startActivity(intent);
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                } else if (groupPosition == 1 & childPosition >= 0) {
                    Intent intent = new Intent(LandingActivity.this, PackageResultsActivity.class);
                    intent.putExtra("Packagetype", childPosition + 1 + "");
                    intent.putExtra("PackagetypeName", expandableListDetail.get("Health Checks").get(childPosition) + "");
                    intent.putExtra("health_institute_id", "");
                    startActivity(intent);
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                } else if (groupPosition == 2 & childPosition >= 0) {
                    Intent intent = new Intent(LandingActivity.this, EquipmentResultsActivity.class);
                    intent.putExtra("equipment_type", childPosition + 13 + "");
                    intent.putExtra("Equipment_type_name", expandableListDetail.get("Equipment Rental").get(childPosition) + "");
                    intent.putExtra("health_institute_id", "");

                    startActivity(intent);
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
               /* } else if (groupPosition == 3 & childPosition >= 0) {
                    Log.d("position", childPosition + "");
                    Log.d("krs", childPosition + 10 + "");
                    Intent intent = new Intent(LandingActivity.this, DoctorResultsActivity.class);
                    if(childPosition < 15) {
                    intent.putExtra("doctor_type", childPosition + 1 + "");
                    intent.putExtra("doctor_type_name", expandableListDetail.get("Doctors").get(childPosition) + "");
                    intent.putExtra("health_institute_id", "");

                    startActivity(intent);
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                    }else if(childPosition > 14 || childPosition < 21)  {
                        intent.putExtra("doctor_type", childPosition + 2 + "");
                        intent.putExtra("doctor_type_name", expandableListDetail.get("Doctors").get(childPosition) + "");
                        intent.putExtra("health_institute_id", "");

                        startActivity(intent);
                        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            mDrawerLayout.closeDrawer(Gravity.LEFT);
                        }
                    } else  {
                        intent.putExtra("doctor_type", childPosition + 5 + "");
                        intent.putExtra("doctor_type_name", expandableListDetail.get("Doctors").get(childPosition) + "");
                        intent.putExtra("health_institute_id", "");


                        startActivity(intent);
                        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            mDrawerLayout.closeDrawer(Gravity.LEFT);
                        }
                    }*/


                } else if (groupPosition == 5 & childPosition >= 0) {
                    Intent intent = new Intent(LandingActivity.this, HomeServicesResultsActivity.class);
                    if (childPosition < 5) {
                        intent.putExtra("service_type", childPosition + 7 + "");
                        intent.putExtra("service_type_name", expandableListDetail.get("HomeServices").get(childPosition) + "");
                        intent.putExtra("health_institute_id", "");

                        startActivity(intent);
                        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            mDrawerLayout.closeDrawer(Gravity.LEFT);
                        }
                    } else {
                        intent.putExtra("service_type", childPosition + 8 + "");
                        intent.putExtra("service_type_name", expandableListDetail.get("HomeServices").get(childPosition) + "");
                        intent.putExtra("health_institute_id", "");
                        //Log.d("service_type", childPosition + 8 + "");
                        startActivity(intent);
                        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            mDrawerLayout.closeDrawer(Gravity.LEFT);
                        }
                    }
                } else if (groupPosition == 4 & childPosition >= 0) {
                    Intent intent = new Intent(LandingActivity.this, AmbulanceResultsActivity.class);
                    if (childPosition < 2) {
                        intent.putExtra("ambulance_type", childPosition + 5 + "");
                        intent.putExtra("ambulance_type_name", expandableListDetail.get("Ambulance").get(childPosition) + "");
                        intent.putExtra("health_institute_id", "");
                        startActivity(intent);
                        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            mDrawerLayout.closeDrawer(Gravity.LEFT);
                        }

                    } else {
                        intent.putExtra("ambulance_type", childPosition + 8 + "");
                        intent.putExtra("ambulance_type_name", expandableListDetail.get("Ambulance").get(childPosition) + "");
                        intent.putExtra("health_institute_id", "");
                        //Log.d("ambulance_type", childPosition + 8 + "");
                        startActivity(intent);
                        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            mDrawerLayout.closeDrawer(Gravity.LEFT);
                        }
                    }
                }


                return false;
            }

        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        gv = (GridView) findViewById(R.id.gridView1);
        gv.setAdapter(new MaingridAdapter(this, mainlist, mainlistImages));

        RelativeLayout mri = (RelativeLayout) findViewById(R.id.mri);
        ((TextView) mri.findViewById(R.id.title)).setText("MRI Scan");
        ((ImageView) mri.findViewById(R.id.icon)).setImageResource(R.drawable.mri_yellow_72);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((ImageView) mri.findViewById(R.id.icon)).setBackground(null);
        }

        mri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, TestResultsActivity.class);
                intent.putExtra("Testtype", 1 + "");
                intent.putExtra("TesttypeName", "MRI");
                intent.putExtra("health_institute_id", "");
                //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        RelativeLayout blood_test = (RelativeLayout) findViewById(R.id.blood_test);
        ((TextView) blood_test.findViewById(R.id.title)).setText("Blood Test");
        ((ImageView) blood_test.findViewById(R.id.icon)).setImageResource(R.drawable.bloodtest_yellow_72);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((ImageView) blood_test.findViewById(R.id.icon)).setBackground(null);
        }

        blood_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, TestResultsActivity.class);
                intent.putExtra("Testtype", 4 + "");
                intent.putExtra("TesttypeName", "Blood Test");
                intent.putExtra("health_institute_id", "");
                //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

      /*  RelativeLayout ct_scan = (RelativeLayout) findViewById(R.id.ct_scan);
        ((TextView) ct_scan.findViewById(R.id.title)).setText("CT Scan");
        ((ImageView) ct_scan.findViewById(R.id.icon)).setImageResource(R.drawable.ct_yellow_72);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((ImageView) ct_scan.findViewById(R.id.icon)).setBackground(null);
        }

        ct_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, TestResultsActivity.class);
                intent.putExtra("Testtype", 2 + "");
                intent.putExtra("TesttypeName", "CT Scan");
                intent.putExtra("health_institute_id", "");
                //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
*/

        expandableListAdapter = new CustomExpandableListAdapter(this, drawable, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        setupToolbar();


    }

    private void cities() {
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<List<City>> call = serverCall.getCityname(id);
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, retrofit2.Response<List<City>> response) {
                res = response.body();

               // City city = city.getMasterCity().getCityName();
                //Log.d("retro", "response : " + res);
               // hideProgressDiaalog();
               /* ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, res); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                city.setAdapter(spinnerArrayAdapter);*/
                showListinSpinner();
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                //Log.d("retro","response : " + t.getMessage());
               // hideProgressDiaalog();
            }
        });
    }

    private void showListinSpinner(){
        //String array to store all the book names
        String[] items = new String[0];
        String[] city_id = new String[0];
        try {
            items = new String[res.size()];
            city_id = new String[res.size()];
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Traversing through the whole list to get all the names

        try {
            for(int i=0; i<res.size(); i++){
                //Storing names to string array
                items[i] = res.get(i).getCityName();
                city_id[i] = res.get(i).getCityId();
                Log.d("city_id",city_id[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, items); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        city.setAdapter(spinnerArrayAdapter);
    }


    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startMain);
    }

    public void mDrawerToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    public void snakbarMessage(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.rgb(0, 111, 192));//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.hamburger_white_64);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDrawerToggle();


        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.LEFT;
        actionBar.setCustomView(
                getLayoutInflater().inflate(R.layout.toolbar, null),
                layoutParams);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(0).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuToUse = R.menu.menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(menuToUse, menu);

        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        if (!((LocationManager) getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
           // showSettingsAlert();
        } else {
            //gps is enabled
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        buildGoogleApiClient();
        AppEventsLogger.activateApp(this);
    }


    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    public void showSettingsAlert() {
        new AlertDialog.Builder(this)
                .setMessage("GPS is not enabled. Do you want to go to settings menu?")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            SharedPreferences.Editor editor = getSharedPreferences(sd.location, MODE_PRIVATE).edit();
            editor.putString(sd.latitude, String.valueOf(mLastLocation.getLatitude()));
            editor.putString(sd.longitude, String.valueOf(mLastLocation.getLongitude()));
            editor.commit();
            //Log.d(TAG, "latitude : " + mLastLocation.getLatitude() + "\nLongitude : " + mLastLocation.getLongitude());
        }
    }

    // for Android, you should also log app deactivation
    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        //Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        //Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    //persmission method.
    public static void verifyGPSPermissions(Activity activity) {
        // Check if we have read or write permission
        int coarsePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        int finePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (coarsePermission != PackageManager.PERMISSION_GRANTED || finePermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    REQUEST_GPS_STORAGE,
                    MY_PERMISSION_LOCATION

            );
        }
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getExternalCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    public void snakbarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cities();
                    }
                });
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.rgb(0, 111, 192));//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

    private boolean isOnline() {
        boolean status = InternetStatus.getInstance(getApplicationContext()).isOnline();
        if (status)
            return status;

        else {
            /*SharedData sharedData = new SharedData();
            sharedData.*/
            snakbarMessage("Please connect to Internet");
            return status;
        }
    }


    }


