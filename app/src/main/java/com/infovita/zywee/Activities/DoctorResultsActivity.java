package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.infovita.zywee.Adopters.CommonListAdapter;
import com.infovita.zywee.Pojo.Doctor;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Sharedvalues.ServiceHandler;
import com.infovita.zywee.Supports.InternetStatus;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class DoctorResultsActivity extends AppCompatActivity {

    Serverdatas sd = Serverdatas.getSingletonObject();
    String server_url = sd.url;
    List<Doctor> package_list;
    ListView list;
    String search_query = "";
    boolean loading = false;
    private ProgressDialog pDialog;

    String main_type;
    String cityid = "";
    String health_institute_id;
    //Doctor doctor;
    int end_point;

    int rename_flag = 0;

    private DrawerLayout mDrawerLayout;
    public ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
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

    CommonListAdapter la;
    private CommonListAdapter adapter = null;
    private String TAG,selected;
    Spinner sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        main_type = getIntent().getExtras().getString("doctor_type");
        //Log.d("main_type :",main_type);
        health_institute_id = getIntent().getExtras().getString("health_institute_id");
        SharedPreferences preferences = getSharedPreferences(sd.cityid, MODE_PRIVATE);
        // Reading from SharedPreferences
        cityid = preferences.getString("city_id", "cityid");
        Log.d("city_id",cityid);


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
        TextView emptyText = (TextView)findViewById(R.id.empty);
        list.setEmptyView (emptyText);
        SharedPreferences prefs = getSharedPreferences(sd.location, MODE_PRIVATE);
        user_lat = prefs.getString(sd.latitude, "0");
        user_lng = prefs.getString(sd.longitude, "0");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            //Log.d("location:","> " + user_lat+user_lng);
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(user_lat),Double.parseDouble(user_lng),1);
            String address = addresses.get(0).getSubLocality();
            TextView locality_name_setter = (TextView) findViewById(R.id.loc);
            locality_name_setter.setText("Showing Centers around"+" " + address);
            locality_name_setter.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
            TextView locality_name_setter = (TextView) findViewById(R.id.loc);
            locality_name_setter.setVisibility(View.GONE);
        }

        //getDoctorList();

        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        indicator = new ImageView(this);
        setupToolbar();
        sort = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("Sort By Order");
        list.add("A - Z");
        list.add("Rating");
       // list.add("Popularity");
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
                getDoctorList();
                Log.d("selection :", selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

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
                getDoctorList();
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
        //return true;
    }






  /*  private void getDoctorList() {
            //showProgressDiaalog();
            pDialog = new ProgressDialog(DoctorResultsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
            // Call<List<Package>> call = serverCall.getPackage(main_type,search_query,cityid);
            Call<List<Doctor>> call = serverCall.getDoctor(main_type,search_query,cityid);
            Log.d("city_id" , " > " + cityid);
            Log.d("main_type1", " > " + main_type);

            call.enqueue(new Callback<List<Doctor>>() {
                @Override
                public void onResponse(Call<List<Doctor>> call, retrofit2.Response<List<Doctor>> response) {
                    List<Doctor> res = response.body();
                    Log.d("retro", "response : " + res);
                    hideProgressDiaalog();
                   // if (adapter == null) {
                        adapter = new CommonListAdapter(DoctorResultsActivity.this, res, rename_flag);
                        list.setAdapter(adapter);
                        Log.d("no of count :", list.getAdapter().getCount()+"");
                   // }else {

                        list.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView absListView, int i) {


                            }

                            @Override
                            public void onScroll(AbsListView view, int firstVisibleItem,
                                                 int visibleItemCount, int totalItemCount) {
                                int lastInScreen = firstVisibleItem + visibleItemCount;
                                if (lastInScreen == totalItemCount && !loading) {

                                    Log.d("Response_scroll", totalItemCount + "");
                                    loading = true;


                                    new loadmoretest(adapter, totalItemCount, view).execute();
                                    // new loadmore().execute();
                                   // getDoctorList1();

                                } else {

                                    Log.d("Response_scroll_else", totalItemCount + "-" + lastInScreen + "-" + loading);
                                }
                            }
                        });
                        //List things ends here
                    //}



                    list.setEmptyView(findViewById(R.id.empty));
                }

                @Override
                public void onFailure(Call<List<Doctor>> call, Throwable t) {
                    Log.d("retro", "response fail : " + t.getMessage());
                    Log.d("failure :", t.getMessage());
                    hideProgressDiaalog();
                    *//*adapter = new CommonListAdapter(DoctorResultsActivity.this, res, rename_flag);
                    list.setAdapter(adapter);*//*
                }
            });
        }






    private void showProgressDiaalog() {
        pDialog.show();
    }

    private void hideProgressDiaalog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.cancel();
    }*/

    private void getDoctorList() {
        if (isOnline())
            new getlist().execute();
    }

    //Making http call

    private class getlist extends AsyncTask<Void, Void, Integer> {


        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(DoctorResultsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("main_type", main_type));
            nameValuePair.add(new BasicNameValuePair("search_query", search_query));
            nameValuePair.add(new BasicNameValuePair("health_institute_id", health_institute_id));
            nameValuePair.add(new BasicNameValuePair("latitude", user_lat));
            nameValuePair.add(new BasicNameValuePair("longitude", user_lng));
            nameValuePair.add(new BasicNameValuePair("cityid", cityid));
            package_list = new ArrayList<>();
            //Log.d("resp", main_type);
            // Making a request to url and getting response
            try {
                String jsonStr = sh.makeServiceCall(server_url + "zywee_app/webservices/getdoctors", ServiceHandler.POST, nameValuePair);
                Log.d("Response_json", jsonStr);
                JSONArray jObj = new JSONArray(jsonStr);
                Log.d("Response_json_len: ", "> " + jObj.length());
                if (jObj.length() > 0) {
                    for (int i = 0; i < jObj.length(); i++) {
                        JSONObject c = jObj.getJSONObject(i);
                        Log.d("Response_ssc: ", "> " + c);
                        Log.d("Responsespeciali: ", "> " + c.getString("doctor_name"));
                        Doctor sd_local = new Doctor();
                        sd_local.setDoctorName(c.getString("doctor_name"));
                        sd_local.setNewSpecializationId(c.getString("id"));
                        sd_local.setId(c.getString("id"));
                        // sd_local.setDoctorFee(c.getString("doctor_fee"));
                        if (c.has("doctor_fee")) {
                            sd_local.setHealthInstituteConsultFee(c.getString("doctor_fee"));
                        } else {
                            sd_local.setHealthInstituteConsultFee("0");
                        }
                        // sd_local.setList_item_price(c.getString("health_institute_consult_fee"));
                        sd_local.setCityName(c.getString("city_name"));
                        if (c.has("locality_name")) {
                            sd_local.setLocality(c.getString("locality_name"));
                        } else {
                            sd_local.setLocality("Not Available");
                        }
                        sd_local.setHealthInstituteName(c.getString("health_institute_name"));
                        sd_local.setNewSpecializationName(c.getString("doctor_specialty"));
                        sd_local.setHealthInstituteAvgRating(c.getString("health_institute_avg_rating"));
                        sd_local.setHealthInstituteId(c.getString("health_institute_id"));
                        // sd_local.setDistance(c.getString("distance"));
                        // doctor.setList_item_discount("0");
                        // sd_local.setList_type("doctor");
                        sd_local.setDoctorExperienceYears(c.getString("doctor_experience_years"));
                        sd_local.setDoctorSpecialty(c.getString("doctor_specialty"));
                        sd_local.setDoctorQualification(c.getString("doctor_qualification"));
                        sd_local.setHasAppointmentBooking(c.getString("has_appointment_booking"));
                        //Discount logic
                       /* if (Integer.parseInt(c.getString("flag")) == 0) { //Check if discount applicable
                            Log.d("discount_l", c.getString("specialization_discount"));
                            if (Integer.parseInt(c.getString("specialization_discount")) != 0) {
                                sd_local.setList_item_discount(c.getString("specialization_discount"));
                            } else if (Integer.parseInt(c.getString("test_type_discount")) != 0) {
                                Log.d("discount_l_h", c.getString("specialization_discount"));
                                sd_local.setList_item_discount(c.getString("test_type_discount"));
                            } else {
                                sd_local.setList_item_discount(c.getString("health_institute_discount"));
                            }

                        }*/
                        //End discount logic
                        //package_list.clear();
                        package_list.add(sd_local);
                        Log.d("listed :",sd_local.toString());

                    }
                    pDialog.cancel();
                    return jObj.length();
                }
                pDialog.cancel();
                return -1;

            } catch (Exception e) {
                Log.d("Responsejssss: ", "> " + e);
                pDialog.cancel();
                return -1;
            }

        }

        public void onPostExecute(Integer arg) {
            if(selected.equals("A - Z")){
                try {
                    Collections.sort(package_list, new AlphabetComparator());
                    la = new CommonListAdapter(DoctorResultsActivity.this, package_list, rename_flag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(selected.equals("Rating")){
                try {
                    Collections.sort(package_list, new RatingComparator());
                    la = new CommonListAdapter(DoctorResultsActivity.this, package_list, rename_flag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                 la = new CommonListAdapter(DoctorResultsActivity.this, package_list, rename_flag);
            }
           // final CommonListAdapter la = new CommonListAdapter(DoctorResultsActivity.this, package_list, rename_flag);
            if (arg != -1) {
                Log.d("list :", package_list.toString());
                Log.d("Response_list_lnt: ", "> " + arg);
               // Collections.sort(package_list, new DoctorComparator());
                list.setAdapter(la);

                if (arg > 9) {
                    //Log.d("Response_list_lnt_ex: ", "> " + arg);
                    //generate list calling server for next set of data
                    list.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView absListView, int i) {

                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem,
                                             int visibleItemCount, int totalItemCount) {
                            int lastInScreen = firstVisibleItem + visibleItemCount;
                            if (lastInScreen == totalItemCount && !loading) {

                                //Log.d("Response_scroll", totalItemCount + "");
                                loading = true;
                                // loadmore(la, totalItemCount);

                                new loadmoretest(la, totalItemCount, view).execute();
                                // new loadmore().execute();

                            } else {
                                //Log.d("Response_scroll_else", totalItemCount + "-" + lastInScreen + "-" + loading);
                            }
                        }
                    });
                    //List things ends here
                }


            } else {
                list.setAdapter(null);
               // snakbarMessage(findViewById(android.R.id.content).getRootView(), "No results found");
            }

        }
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
            pDialog = new ProgressDialog(DoctorResultsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("main_type", main_type));
            nameValuePair.add(new BasicNameValuePair("search_query", search_query));
            nameValuePair.add(new BasicNameValuePair("endpoint", end_point + ""));
            nameValuePair.add(new BasicNameValuePair("health_institute_id", health_institute_id));
            nameValuePair.add(new BasicNameValuePair("latitude", user_lat));
            nameValuePair.add(new BasicNameValuePair("longitude", user_lng));
            nameValuePair.add(new BasicNameValuePair("cityid", cityid));
            //Log.d("end_point :", end_point+"");
            // test_list= new ArrayList<>();
            // Making a request to url and getting response
            try {
                String jsonStr = sh.makeServiceCall(server_url + "zywee_app/webservices/getdoctors", ServiceHandler.POST, nameValuePair);
                Log.d("jsonStr: ", "> " + jsonStr);
                JSONArray jObj = new JSONArray(jsonStr);
                if (jObj.length() > 0) {

                    for (int i = 0; i < jObj.length(); i++) {
                        JSONObject c = jObj.getJSONObject(i);
                        //Log.d("Response_ssc_new: ", "> " + c);
                        //Log.d("Responsespeciali: ", "> " + c.getString("doctor_name"));
                        Doctor sd_local = new Doctor();
                        sd_local.setDoctorName(c.getString("doctor_name"));
                        sd_local.setNewSpecializationId(c.getString("id"));
                        sd_local.setId(c.getString("id"));
                        // sd_local.setDoctorFee(c.getString("doctor_fee"));
                        if (c.has("doctor_fee")) {
                            sd_local.setHealthInstituteConsultFee(c.getString("doctor_fee"));
                        } else {
                            sd_local.setHealthInstituteConsultFee("0");
                        }
                        // sd_local.setList_item_price(c.getString("health_institute_consult_fee"));
                        sd_local.setCityName(c.getString("city_name"));
                        if (c.has("locality_name")) {
                            sd_local.setLocality(c.getString("locality_name"));
                        } else {
                            sd_local.setLocality("Not Available");
                        }
                        sd_local.setHealthInstituteName(c.getString("health_institute_name"));
                        sd_local.setNewSpecializationName(c.getString("doctor_specialty"));
                        sd_local.setHealthInstituteAvgRating(c.getString("health_institute_avg_rating"));
                        sd_local.setHealthInstituteId(c.getString("health_institute_id"));
                        // sd_local.setDistance(c.getString("distance"));
                        // doctor.setList_item_discount("0");
                        // sd_local.setList_type("doctor");
                        sd_local.setDoctorExperienceYears(c.getString("doctor_experience_years"));
                        sd_local.setDoctorSpecialty(c.getString("doctor_specialty"));
                        sd_local.setDoctorQualification(c.getString("doctor_qualification"));
                        sd_local.setHasAppointmentBooking(c.getString("has_appointment_booking"));
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
                //snakbarMessage(v, "That's all folks!");
                loading = true;
            }

        }
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
                DoctorResultsActivity.this.finish();
            }
        });

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void openCartPage() {
        Intent intent = new Intent(DoctorResultsActivity.this, DoctorCartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.putExtra("item_id", "");
        intent.putExtra("item_name", "");
        intent.putExtra("item_price", "");
       // intent.putExtra("discount", "");
        intent.putExtra("health_institute_name", getIntent().getExtras().getString("doctor_type_name"));
        intent.putExtra("health_institute_id", health_institute_id);
        startActivity(intent);

        DoctorResultsActivity.this.finish();
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
                        getDoctorList();
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

        getSupportActionBar().setTitle(getIntent().getExtras().getString("doctor_type_name"));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_white_64);

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

    private class DoctorComparator implements Comparator<Doctor> {
        @Override
        public int compare(Doctor emp1, Doctor emp2) {
            return emp2.getHasAppointmentBooking().compareTo(emp1.getHasAppointmentBooking().toString()) ;
        }
    }

    private class AlphabetComparator implements Comparator<Doctor> {
        @Override
        public int compare(Doctor emp1, Doctor emp2) {
            return emp1.getDoctorName().compareTo(emp2.getDoctorName().toString()) ;
        }
    }

    private class RatingComparator implements Comparator<Doctor> {
        @Override
        public int compare(Doctor emp1, Doctor emp2) {
            return emp1.getHealthInstituteAvgRating().compareTo(emp2.getHealthInstituteAvgRating().toString()) ;
        }
    }

   /* private class PopularComparator implements Comparator<Doctor> {
        @Override
        public int compare(Doctor emp1, Doctor emp2) {
            return emp2.getEntity().get(0).getMappingTreatmentPackage().getPackagePopularity().compareTo(emp1.getEntity().get(0).getMappingTreatmentPackage().getPackagePopularity().toString()) ;
        }
    }*/

}
