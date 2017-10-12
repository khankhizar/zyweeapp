package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.infovita.zywee.Adopters.AmbulanceListAdapter;
import com.infovita.zywee.Adopters.CommonListAdapter;
import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.Pojo.Ambulance;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Sharedvalues.ServiceHandler;
import com.infovita.zywee.Supports.InternetStatus;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AmbulanceResultsActivity extends AppCompatActivity {

    Serverdatas sd = Serverdatas.getSingletonObject();
    String server_url = sd.url;
    List<Serverdatas> package_list;
    ListView list;
    String search_query = "";
    String cityid = "";
    boolean loading = false;
    private ProgressDialog pDialog;

    String main_type;
    String health_institute_id;

    int rename_flag = 0;

    private DrawerLayout mDrawerLayout;
    public ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBar actionBar;
    public String user_lat = "";
    public String user_lng = "";

    private String[] mNavigationDrawerItemTitles;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<String, List<String>> expandableListDetail;

    List<Integer> drawable = new ArrayList<>();
    private ImageView indicator;

    private ActionBarDrawerToggle mDrawerToggle;
    private String mobile;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        pDialog = new ProgressDialog(AmbulanceResultsActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        main_type = getIntent().getExtras().getString("ambulance_type");
        health_institute_id = getIntent().getExtras().getString("health_institute_id");
        SharedPreferences preferences = getSharedPreferences(sd.cityid, MODE_PRIVATE);
        // Reading from SharedPreferences
        cityid = preferences.getString("city_id", "cityid");
        Log.d("city_id",cityid);

        ((Spinner) findViewById(R.id.spinner1)).setVisibility(View.GONE);


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
            LinearLayout parent_list_layout = (LinearLayout) findViewById(R.id.parent_list_layout);
            parent_list_layout.setPadding(0, 0, 0, 0);
        }


        list = (ListView) findViewById(R.id.test_list);
        SharedPreferences prefs = getSharedPreferences(sd.location, MODE_PRIVATE);
        user_lat = prefs.getString(sd.latitude, null);
        user_lng = prefs.getString(sd.longitude, null);
        getAmbulanceList();
        TextView locality_name_setter = (TextView) findViewById(R.id.loc);
        locality_name_setter.setVisibility(View.GONE);

        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);

        setupToolbar();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        // Retrieve the SearchView and plug it into SearchManager
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Log.d("Response_search", query);
                search_query = query;
                search_query = search_query.trim();
                //search_query = search_query.replaceAll("\\s","");
               // actionBar.collapseActionView();

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
      /*  searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if (!queryTextFocused) {
                    actionBar.collapseActionView();
                    searchView.setQuery("", true);
                }
            }
        });*/

        return true;
        //return true;
    }

    private void getAmbulanceList() {
        showProgressDiaalog();
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<List<Ambulance>> call = serverCall.getAmbulances(main_type,search_query,cityid);
        call.enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, retrofit2.Response<List<Ambulance>> response) {
                List<Ambulance> res = response.body();
                Log.d("retro", "response : " + res);
                hideProgressDiaalog();
                final AmbulanceListAdapter adapter = new AmbulanceListAdapter(AmbulanceResultsActivity.this,res,rename_flag);
                list.setAdapter(adapter);
                list.setEmptyView(findViewById(R.id.empty));


            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {
                //Log.d("retro","response : " + t.getMessage());
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
                AmbulanceResultsActivity.this.finish();
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

    private class loadmoretest extends AsyncTask<Void, Void, Integer> {

        int end_point;
        final CommonListAdapter la;
        View v;

        public loadmoretest(CommonListAdapter la, int end_point, View v) {
            this.la = la;
            this.end_point = end_point;
            this.v = v;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(AmbulanceResultsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("mobile", mobile));
          /*  nameValuePair.add(new BasicNameValuePair("search_query", search_query));
            nameValuePair.add(new BasicNameValuePair("endpoint", end_point + ""));
            nameValuePair.add(new BasicNameValuePair("health_institute_id", health_institute_id));
            nameValuePair.add(new BasicNameValuePair("latitude", user_lat));
            nameValuePair.add(new BasicNameValuePair("longitude", user_lng));*/
            // test_list= new ArrayList<>();
            // Making a request to url and getting response
            try {
                String jsonStr = sh.makeServiceCall(server_url + "getAppointments", ServiceHandler.POST, nameValuePair);
                //Log.d("jsonStr: ", "> " + jsonStr);
                JSONArray jObj = new JSONArray(jsonStr);
                if (jObj.length() > 0) {

                    for (int i = 0; i < jObj.length(); i++) {
                        JSONObject c = jObj.getJSONObject(i);
                        //Log.d("Response_ssc_new: ", "> " + c);
                        //Log.d("Responsespeciali: ", "> " + c.getString("equipment_name"));
                        Serverdatas sd_local = new Serverdatas();
                        sd_local.setAppointment_id(c.getString("appointement_id"));
                       /* sd_local.setSpecialization_id(c.getString("id"));
                        sd_local.setList_item_id(c.getString("id"));
                        sd_local.setList_item_price(c.getString("health_institute_consult_fee"));
                        sd_local.setCity_name(c.getString("city_name"));
                        sd_local.setList_locality_name(c.getString("locality_name"));
                        sd_local.setList_center_name(c.getString("health_institute_name"));
                        sd_local.setList_item_details(c.getString("doctor_specialty"));
                        sd_local.setHealth_institute_avg_rating(c.getString("health_institute_avg_rating"));
                        sd_local.setHealth_institute_id(c.getString("health_institute_id"));
                        sd_local.setDistance(c.getString("distance"));
                        sd_local.setList_item_discount("0");
                        sd_local.setList_type("doctor");*/
                        //Discount logic
                       /* if (Integer.parseInt(c.getString("flag")) == 0) { //Check if discount applicable
                            if (Integer.parseInt(c.getString("specialization_discount")) != 0) {
                            } else if (Integer.parseInt(c.getString("test_type_discount")) != 0) {
                                sd_local.setList_item_discount(c.getString("test_type_discount"));
                            } else {
                                sd_local.setList_item_discount(c.getString("health_institute_discount"));
                            }
                        }*/
                        //End discount logic
                        package_list.add(sd_local);
                    }
                    pDialog.cancel();
                    return 1;
                }
                pDialog.cancel();
                return -1;
            } catch (Exception e) {
                //Log.d("Response_exception: ", "> " + e);
                pDialog.cancel();
                return -1;
            }

        }

        public void onPostExecute(Integer arg) {
            //Log.d("Response_arg: ", "> " + arg);
            if (arg == 1) {
                la.notifyDataSetChanged();
                loading = false;
            } else {
                snakbarMessage(v, "That's all folks!");
                loading = true;
            }

        }
    }

    private void openCartPage() {
        Intent intent = new Intent(AmbulanceResultsActivity.this, DoctorCartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.putExtra("item_id", "");
        intent.putExtra("item_name", "");
        intent.putExtra("item_price", "");
        intent.putExtra("discount", "");
        intent.putExtra("health_institute_name", getIntent().getExtras().getString("Equipment_type_name"));
        intent.putExtra("health_institute_id", health_institute_id);
        startActivity(intent);

        AmbulanceResultsActivity.this.finish();
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

        getSupportActionBar().setTitle(getIntent().getExtras().getString("ambulance_type_name"));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_white_64);
       // mDrawerToggle();
/*
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        View view = getLayoutInflater().inflate(R.layout.toolbar, null);
        actionBar.setCustomView(
                getLayoutInflater().inflate(R.layout.toolbar, null),
                layoutParams);
//        ((TextView)view.findViewById(R.id.toolbar)).setText((getIntent().getExtras().getString("ambulance_type_name")));
  */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 onBackPressed();
            }
        });

    }


}
