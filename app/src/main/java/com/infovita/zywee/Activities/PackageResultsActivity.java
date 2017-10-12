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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.infovita.zywee.Adopters.PackageListAdapter;
import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.Pojo.Package;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Supports.InternetStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class PackageResultsActivity extends AppCompatActivity {

    Serverdatas sd = Serverdatas.getSingletonObject();
    String server_url = sd.url;
    List<Serverdatas> package_list;
    ListView list;
    PackageListAdapter adapter=null;
    String search_query = "";
    boolean loading = false;
    private ProgressDialog pDialog;

    String main_type;
    String cityid,selected;
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

    Menu menu;

    View v;

    SearchView searchView;
    Spinner sort;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        pDialog = new ProgressDialog(PackageResultsActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        main_type = getIntent().getExtras().getString("Packagetype");
        health_institute_id = getIntent().getExtras().getString("health_institute_id");
        SharedPreferences preferences = getSharedPreferences(sd.cityid, MODE_PRIVATE);
        // Reading from SharedPreferences
        cityid = preferences.getString("city_id", "cityid");
        Log.d("city_id",cityid);
      /*  city_id = getIntent().getExtras().getString("institute_address_cityid");
        Log.d("city_id" , " > " + city_id);*/

        //Log.d("main_type", " > " + main_type);

//        Button continue_booking_parent = (Button) findViewById(R.id.test_book_button);

        if (!health_institute_id.isEmpty()) {
            rename_flag = 1;
            LinearLayout button_continue = (LinearLayout) findViewById(R.id.button_continue);
            button_continue.setVisibility(View.VISIBLE);

            Button continue_booking_parent = (Button) findViewById(R.id.test_book_button);
            continue_booking_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openCartPage();
                }
            });
        } else {
            //continue_booking_parent.setVisibility(View.GONE);
            LinearLayout parent_list_layout = (LinearLayout) findViewById(R.id.parent_list_layout);
            parent_list_layout.setPadding(0, 0, 0, 0);
        }


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

        setupToolbar();

        sort = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("Sort By Order");
        list.add("A - Z");
        list.add("Rating");
        list.add("Popularity");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        sort.setAdapter(dataAdapter);

        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //private TextView selectedSpinner;


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                if (isOnline())
                getAmbulanceList();
                Log.d("selection :", selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


    }



    private void getAmbulanceList() {
        showProgressDiaalog();
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
       // Call<List<Package>> call = serverCall.getPackage(main_type,search_query,cityid);
       Call<List<Package>> call = serverCall.getPackage(main_type,search_query,cityid);
        //Log.d("city_id" , " > " + cityid);
        //Log.d("main_type1", " > " + main_type);

        call.enqueue(new Callback<List<Package>>() {
            @Override
            public void onResponse(Call<List<Package>> call, retrofit2.Response<List<Package>> response) {
                List<Package> res = response.body();
                //Log.d("retro", "response : " + res);
                hideProgressDiaalog();
                if(selected.equals("Popularity")){
                    try {
                        Collections.sort(res, new PopularComparator());
                        adapter = new PackageListAdapter(PackageResultsActivity.this, res, rename_flag);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(selected.equals("A - Z")){
                    try {
                        Collections.sort(res, new AlphabetComparator());
                        adapter = new PackageListAdapter(PackageResultsActivity.this, res, rename_flag);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(selected.equals("Rating")){
                    try {
                        Collections.sort(res, new RatingComparator());
                        adapter = new PackageListAdapter(PackageResultsActivity.this, res, rename_flag);
                   } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{
                    adapter = new PackageListAdapter(PackageResultsActivity.this, res, rename_flag);
                }
               // Collections.sort(res, new PackageComparator());
                   // adapter = new PackageListAdapter(PackageResultsActivity.this, res, rename_flag);
                    list.setAdapter(adapter);
                    list.setEmptyView(findViewById(R.id.empty));
            }

            @Override
            public void onFailure(Call<List<Package>> call, Throwable t) {
                //Log.d("retro", "response fail : " + t.getMessage());
                //Log.d("failure :", t.getMessage());
                hideProgressDiaalog();
            }
        });
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
                PackageResultsActivity.this.finish();
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
        Intent intent = new Intent(PackageResultsActivity.this, PackageCartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.putExtra("item_id", "");
        intent.putExtra("item_name", "");
        intent.putExtra("item_price", "");
        intent.putExtra("discount", "");
        intent.putExtra("health_institute_name", getIntent().getExtras().getString("service_type_name"));
        intent.putExtra("health_institute_id", health_institute_id);
        startActivity(intent);
        PackageResultsActivity.this.finish();
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

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(false);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setTitle(getIntent().getExtras().getString("PackagetypeName"));

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

    private class AlphabetComparator implements Comparator<Package> {
        @Override
        public int compare(Package emp1, Package emp2) {
            return emp1.getSubPackage().getSubPackageType().compareToIgnoreCase(emp2.getSubPackage().getSubPackageType().toString()) ;
        }
    }

    private class RatingComparator implements Comparator<Package> {
        @Override
        public int compare(Package emp1, Package emp2) {
            return emp1.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating().compareTo(emp2.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating().toString()) ;
        }
    }

    private class PopularComparator implements Comparator<Package> {
        @Override
        public int compare(Package emp1, Package emp2) {
            return emp2.getEntity().get(0).getMappingTreatmentPackage().getPackagePopularity().compareTo(emp1.getEntity().get(0).getMappingTreatmentPackage().getPackagePopularity().toString()) ;
        }
    }

   /* private class PackageComparator implements Comparator<Package> {
        @Override
        public int compare(Package emp1, Package emp2) {
            return emp2.getEntity().get(0).getEntityHealthInstitute().getHasAppointmentBooking().compareTo(emp1.getEntity().get(0).getEntityHealthInstitute().getHasAppointmentBooking().toString()) ;
        }
    }*/
}

