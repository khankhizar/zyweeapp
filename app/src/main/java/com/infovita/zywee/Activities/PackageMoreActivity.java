package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.infovita.zywee.Adopters.MorePackageListAdapter;
import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.Pojo.MorePackage;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Supports.InternetStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Khizarkhan on 19-07-2017.
 */

public class PackageMoreActivity extends AppCompatActivity {

    Serverdatas sd = Serverdatas.getSingletonObject();
    String server_url = sd.url;
    List<Serverdatas> package_list;
    ListView list;
    MorePackageListAdapter adapter=null;
    String search_query = "";
    boolean loading = false;
    private ProgressDialog pDialog;

    String main_type;
    String cityid;
    String health_package_id,health_institute_name;
    int rename_flag = 0;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBar actionBar;
    public String user_lat = "";
    public String user_lng = "";

    private String[] mNavigationDrawerItemTitles;

    List<Integer> drawable = new ArrayList<>();
    private ImageView indicator;

    Menu menu;

    View v;

    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        pDialog = new ProgressDialog(PackageMoreActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        //main_type = getIntent().getExtras().getString("Packagetype");
        health_institute_name = getIntent().getExtras().getString("TesttypeName");
        health_package_id = getIntent().getExtras().getString("health_institute_id");
        SharedPreferences preferences = getSharedPreferences(sd.cityid, MODE_PRIVATE);
        // Reading from SharedPreferences
        cityid = preferences.getString("city_id", "cityid");
        Log.d("city_id",cityid);
        ((Spinner) findViewById(R.id.spinner1)).setVisibility(View.GONE);
      /*  city_id = getIntent().getExtras().getString("institute_address_cityid");
        Log.d("city_id" , " > " + city_id);*/

        //Log.d("main_type", " > " + main_type);

//        Button continue_booking_parent = (Button) findViewById(R.id.test_book_button);


        list = (ListView) findViewById(R.id.test_list);
        SharedPreferences prefs = getSharedPreferences(sd.location, MODE_PRIVATE);
        user_lat = prefs.getString(sd.latitude, "0");
        user_lng = prefs.getString(sd.longitude, "0");

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            //Log.d("location:", "> " + user_lat + user_lng);
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(user_lat), Double.parseDouble(user_lng), 1);
            String address = addresses.get(0).getSubLocality();
            TextView locality_name_setter = (TextView) findViewById(R.id.loc);
            locality_name_setter.setVisibility(View.GONE);
            locality_name_setter.setText("Showing Centers around" + " " + address);

        } catch (Exception e) {
            TextView locality_name_setter = (TextView) findViewById(R.id.loc);
            locality_name_setter.setVisibility(View.GONE);
            e.printStackTrace();
        }

        if (isOnline())
        getAmbulanceList();

        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);

        setup_toolbar();

    }



    private void getAmbulanceList() {
        showProgressDiaalog();
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        // Call<List<Package>> call = serverCall.getPackage(main_type,search_query,cityid);
        Call<List<MorePackage>> call = serverCall.getMorePackage(health_package_id);
        Log.d("health_package_id" , " > " + health_package_id);
        //Log.d("main_type1", " > " + main_type);

        call.enqueue(new Callback<List<MorePackage>>() {
            @Override
            public void onResponse(Call<List<MorePackage>> call, retrofit2.Response<List<MorePackage>> response) {
                List<MorePackage> res = response.body();
                Log.d("retro", "response : " + res);
                hideProgressDiaalog();
                // Collections.sort(res, new PackageComparator());
                adapter = new MorePackageListAdapter(PackageMoreActivity.this, res, rename_flag);
                list.setAdapter(adapter);
                list.setEmptyView(findViewById(R.id.empty));
            }

            @Override
            public void onFailure(Call<List<MorePackage>> call, Throwable t) {
                //Log.d("retro", "response fail : " + t.getMessage());
                //Log.d("failure :", t.getMessage());
                hideProgressDiaalog();
            }
        });
    }



   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Log.d("Response_search", query);
                search_query = query;
                search_query = search_query.trim();
                if (isOnline())
                    getAmbulanceList();
                setToolbar(search_query);
                searchView.clearFocus();
                searchView.setFocusable(false);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }*/

    /*Setup the toolbar*/
    public void setup_toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + health_institute_name + "</small>"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    private void showProgressDiaalog() {
        pDialog.show();
    }

    private void hideProgressDiaalog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.cancel();
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

    private void openCartPage() {
        Intent intent = new Intent(PackageMoreActivity.this, PackageCartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.putExtra("item_id", "");
        intent.putExtra("item_name", "");
        intent.putExtra("item_price", "");
        intent.putExtra("discount", "");
        intent.putExtra("health_institute_name", getIntent().getExtras().getString("service_type_name"));
        intent.putExtra("health_institute_id", health_package_id);
        startActivity(intent);
        PackageMoreActivity.this.finish();
    }


    public void snakbarMessage(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

    public void snakbarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getAmbulanceList();
                    }
                });
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // mDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

}
