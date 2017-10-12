package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.infovita.zywee.Adopters.HomeServiceListAdapter;
import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.Pojo.HomeService;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Supports.InternetStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeServicesResultsActivity extends AppCompatActivity {

    Serverdatas sd = Serverdatas.getSingletonObject();
    String server_url = sd.url;
    List<Serverdatas> package_list;
    ListView list;
    String search_query = "";
    boolean loading = false;
    private ProgressDialog pDialog;

    String main_type;
    String cityid = "";
    String health_institute_id;

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
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_services_results);

        pDialog = new ProgressDialog(HomeServicesResultsActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        main_type = getIntent().getExtras().getString("service_type");
        Log.d("main_type", main_type);
        health_institute_id = getIntent().getExtras().getString("health_institute_id");
        SharedPreferences preferences = getSharedPreferences(sd.cityid, MODE_PRIVATE);
        // Reading from SharedPreferences
        cityid = preferences.getString("city_id", "cityid");
        Log.d("city_id",cityid);

        //((Spinner) findViewById(R.id.spinner1)).setVisibility(View.GONE);

        //Log.d("main_type" , " > " + main_type);
        Button continue_booking_parent = (Button) findViewById(R.id.test_book_button);

        if (!health_institute_id.isEmpty()) {
            rename_flag = 1;
            /*LinearLayout button_continue = (LinearLayout) findViewById(R.id.button_continue);
            button_continue.setVisibility(View.VISIBLE);*/


            continue_booking_parent.setVisibility(View.VISIBLE);
            continue_booking_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openCartPage();
                }
            });
        } else {
            continue_booking_parent.setVisibility(View.GONE);
            /*LinearLayout parent_list_layout = (LinearLayout) findViewById(R.id.parent_list_layout);
            parent_list_layout.setPadding(0, 0, 0, 0);*/
        }


        list = (ListView) findViewById(R.id.test_list);
        SharedPreferences prefs = getSharedPreferences(sd.location, MODE_PRIVATE);
        user_lat = prefs.getString(sd.latitude, null);
        user_lng = prefs.getString(sd.longitude, null);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            //Log.d("location:","> " + user_lat+user_lng);
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(user_lat),Double.parseDouble(user_lng),1);
            String address = addresses.get(0).getSubLocality();
          /*  TextView locality_name_setter = (TextView) findViewById(R.id.loc);
            locality_name_setter.setText("Showing Centers around"+" " + address);
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        getAmbulanceList();

        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);

        setupToolbar();

    }

    @Override
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
                //search_query = search_query.replaceAll("\\s","");
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

         /* searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if (!queryTextFocused) {
                    actionBar.collapseActionView();
                    searchView.setQuery("", false);
                }
            }
        });*/
        return true;
        //return true;
    }

    private void getAmbulanceList() {
        showProgressDiaalog();
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        //Call<List<HomeService>> call = serverCall.getHomeService(main_type,search_query,cityid);
        Call<List<HomeService>> call = serverCall.getHomeService(main_type,search_query,cityid);
        call.enqueue(new Callback<List<HomeService>> () {
            @Override
            public void onResponse(Call<List<HomeService>> call, retrofit2.Response<List<HomeService>>  response) {
               /* Log.d(TAG, call.request().url().toString());
                Log.d("respond :", call.request().method().toString());*/
                List<HomeService> res = response.body();
                Log.d("retro", "response : " + res);
                hideProgressDiaalog();
                final HomeServiceListAdapter adapter = new HomeServiceListAdapter(HomeServicesResultsActivity.this,res,rename_flag);

                list.setAdapter(adapter);
                //list.setEmptyView(findViewById(R.id.empty));
                list.setEmptyView(findViewById(R.id.empty));
            }

            @Override
            public void onFailure(Call<List<HomeService>>  call, Throwable t) {
                Log.d("retro","responsesss : " + t.getMessage());
                hideProgressDiaalog();
            }
        });
    }

    private void setToolbar(String s) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + s + "</small>"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                //What to do on back clicked
//                NavUtils.navigateUpFromSameTask(ProductListFragment.this);
//                moveTaskToBack(true);
                HomeServicesResultsActivity.this.finish();
            }
        });

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        Intent intent = new Intent(HomeServicesResultsActivity.this, DoctorCartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.putExtra("item_id", "");
        intent.putExtra("item_name", "");
        intent.putExtra("item_price", "");
        intent.putExtra("discount", "");
        intent.putExtra("health_institute_name", getIntent().getExtras().getString("service_type_name"));
        intent.putExtra("health_institute_id", health_institute_id);
        startActivity(intent);
        HomeServicesResultsActivity.this.finish();
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

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(false);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setTitle(getIntent().getExtras().getString("service_type_name"));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_white_64);
       // mDrawerToggle();

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.LEFT;
        actionBar.setCustomView(
                getLayoutInflater().inflate(R.layout.toolbar, null),
                layoutParams);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 onBackPressed();

            }
        });
    }
}
