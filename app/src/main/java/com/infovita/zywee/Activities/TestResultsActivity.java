package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.infovita.zywee.Adopters.TestListAdapter;
import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.Pojo.EntityHealthInstitute1;
import com.infovita.zywee.Pojo.MappingInstituteToDiagnosticsSpecialization1;
import com.infovita.zywee.Pojo.MasterLocality1;
import com.infovita.zywee.Pojo.SubTest1;
import com.infovita.zywee.Pojo.Test1;
import com.infovita.zywee.Pojo.TestInstituteSpecialization1;
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

public class TestResultsActivity extends AppCompatActivity {
    Serverdatas sd = Serverdatas.getSingletonObject();

    String server_url = sd.url;
    List<Test1> test_list;
    List<SubTest1>test_sublist;
    List<EntityHealthInstitute1>test_inst;
    List<MasterLocality1>locality;
    List<MappingInstituteToDiagnosticsSpecialization1>speciality;
    List<TestInstituteSpecialization1>inst_spec;
    ListView list;
    TestListAdapter adapter=null;
    String search_query = "";
    String cityid = "";
    String main_type;
    String health_institute_id;
    int rename_flag = 0;
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    public ListView mDrawerList;
    private String user_lat = "";
    private String user_lng = "";
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBar actionBar;
    public String locality_name="";
    private ProgressDialog pDialog;
    private Spinner sort;
    String selected;

    List<Integer> drawable = new ArrayList<>();
    Context context;
    ProgressDialog dialog;

    boolean loading = false;

    int end_point;
    private boolean flag_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        pDialog = new ProgressDialog(TestResultsActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
//        getSupportActionBar().setTitle(getIntent().getExtras().getString("TesttypeName"));
        main_type = getIntent().getExtras().getString("Testtype");
        SharedPreferences preferences = getSharedPreferences(sd.cityid, MODE_PRIVATE);
        // Reading from SharedPreferences
        cityid = preferences.getString("city_id", "cityid");
        Log.d("main_type",main_type);


        health_institute_id = getIntent().getExtras().getString("health_institute_id");

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

        //code to set adapter to populate list
       /* View footerView =  ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        list.addFooterView(footerView);*/

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
            TextView locality_name_setter = (TextView) findViewById(R.id.loc);
            locality_name_setter.setVisibility(View.GONE);
            e.printStackTrace();
        }

        if (isOnline())
        getTestList();
        setupToolbar();

       /* list.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
                    if(flag_loading == false)
                    {
                        flag_loading = true;
                        getMoreTestList();
                    }
                }
            }
        });*/
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
                getTestList();
                Log.d("selection :", selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


}


    private void openCartPage() {
        Intent intent = new Intent(TestResultsActivity.this, TestCartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.putExtra("item_id", "");
        intent.putExtra("item_name", "");
        intent.putExtra("item_price", "");
        intent.putExtra("discount", "");
        intent.putExtra("health_institute_name", getIntent().getExtras().getString("TesttypeName"));
        intent.putExtra("health_institute_id", health_institute_id);
        startActivity(intent);

        TestResultsActivity.this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        /*searchView.setSuggestionsAdapter(new SimpleCursorAdapter(
                    context, android.R.layout.activity_list_item, null,
                    new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                    new int[]{android.R.id.text1}));*/
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Log.d("Response_search", query);
                search_query = query;
                search_query = search_query.trim();
             if (isOnline())
                 getTestList();
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

    private void getTestList(){
        try {
            showProgressDiaalog();
            ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
            Call<List<Test1>> call = serverCall.getTests(main_type,search_query,cityid);
            //Log.d("cityid : ", cityid);
            call.enqueue(new Callback<List<Test1>>() {
                @Override
                public void onResponse(Call<List<Test1>> call, retrofit2.Response<List<Test1>> response) {
                    List<Test1> res = response.body();
                    //Log.d("retro", "response : " + res);
                    hideProgressDiaalog();
                   // if (adapter == null) {
                   // Collections.sort(res, new DataComparator());
                   // if (adapter == null) {
                    //Collections.sort(res, new AlphabetComparator());
                    //Collections.sort(res, new RatingComparator());
                    if(selected.equals("Popularity")){
                        try {
                            Collections.sort(res, new PopularComparator());
                            adapter = new TestListAdapter(TestResultsActivity.this, res, rename_flag);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(selected.equals("A - Z")){
                        try {
                            Collections.sort(res, new AlphabetComparator());
                            adapter = new TestListAdapter(TestResultsActivity.this, res, rename_flag);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(selected.equals("Rating")){
                        try {
                            Collections.sort(res, new RatingComparator());
                            adapter = new TestListAdapter(TestResultsActivity.this, res, rename_flag);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        adapter = new TestListAdapter(TestResultsActivity.this, res, rename_flag);
                    }
                       // adapter = new TestListAdapter(TestResultsActivity.this, res, rename_flag);
                        list.setAdapter(adapter);
                        list.setEmptyView(findViewById(R.id.empty));
                    /*}else{
                        adapter.notifyDataSetChanged();*/

                       /* list.setOnScrollListener(new InfiniteScrollListener() {
                            @Override
                            public boolean loadMore(int page, int totalItemsCount) {
                                // Triggered only when new data needs to be appended to the list
                                // Add whatever code is needed to append new items to your AdapterView
                                customLoadMoreDataFromApi(page);
                                // or customLoadMoreDataFromApi(totalItemsCount);
                                return true; // ONLY if more data is actually being loaded; false otherwise.
                            }

                        });
*/

            //        }
                }

                @Override
                public void onFailure(Call<List<Test1>> call, Throwable t) {
                    Log.d("error", "response : " + t.getMessage());
                   /* if(t == null) {
                        getTestList();
                    }*/
                    hideProgressDiaalog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMoreTestList() {

        try {
            showProgressDiaalog();
            ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
            Call<List<Test1>> call = serverCall.getTests(main_type,search_query,cityid);
            //Log.d("cityid : ", cityid);
            call.enqueue(new Callback<List<Test1>>() {
                @Override
                public void onResponse(Call<List<Test1>> call, retrofit2.Response<List<Test1>> response) {
                    List<Test1> res = response.body();
                    //Log.d("retro", "response : " + res);
                    hideProgressDiaalog();
                    // if (adapter == null) {
                    // Collections.sort(res, new DataComparator());
                    if (adapter == null) {
                        res.addAll(response.body());
                       // adapter = new TestListAdapter(TestResultsActivity.this, res, rename_flag);
                        adapter.notifyDataSetChanged();
                        loading = false;
                        /*list.setAdapter(adapter);
                        list.setEmptyView(findViewById(R.id.empty));*/
                    }else{
                        loading = true;

                       /* list.setOnScrollListener(new InfiniteScrollListener() {
                            @Override
                            public boolean loadMore(int page, int totalItemsCount) {
                                // Triggered only when new data needs to be appended to the list
                                // Add whatever code is needed to append new items to your AdapterView
                                customLoadMoreDataFromApi(page);
                                // or customLoadMoreDataFromApi(totalItemsCount);
                                return true; // ONLY if more data is actually being loaded; false otherwise.
                            }

                        });
*/

                    }
                }

                @Override
                public void onFailure(Call<List<Test1>> call, Throwable t) {
                    //Log.d("retro", "response : " + t.getMessage());
                    hideProgressDiaalog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        getTestList();
       /* moreProgress.setVisibility(View.VISIBLE);
        Log.e(TAG, "MORE PROGRESS");*/
    }


    private void setToolbar(String s) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<medium>" + s + "</medium>"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                //What to do on back clicked
     //           NavUtils.navigateUpFromSameTask(TestsFragment.this);
//                moveTaskToBack(true);
                TestResultsActivity.this.finish();
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
                        getTestList();
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
        //mDrawerToggle.syncState();
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(false);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }



        getSupportActionBar().setTitle(getIntent().getExtras().getString("TesttypeName"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_white_64);
        //mDrawerToggle();

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
        actionBar.setCustomView(
                getLayoutInflater().inflate(R.layout.toolbar, null),
                layoutParams);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mDrawerToggle();
            onBackPressed();
            }

        });

    }

    private class AlphabetComparator implements Comparator<Test1> {
        @Override
        public int compare(Test1 emp1, Test1 emp2) {
            return emp1.getSubTest().getSubTestType().compareToIgnoreCase(emp2.getSubTest().getSubTestType().toString()) ;
        }
    }

    private class RatingComparator implements Comparator< Test1> {
        @Override
        public int compare(Test1 emp1, Test1 emp2) {
            Log.d("emp1 :",emp1.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating());
            Log.d("emp2 :",emp2.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating());
            return emp1.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating().compareTo(emp2.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating().toString()) ;
        }
    }

    private class PopularComparator implements Comparator< Test1> {
        @Override
        public int compare(Test1 emp1, Test1 emp2) {
            return emp2.getList().get(0).getTestInstituteSpecialization().getTestPopularity().compareTo(emp1.getList().get(1).getTestInstituteSpecialization().getTestPopularity().toString()) ;
        }
    }


   /* private abstract class PopularityComparator implements java.util.Comparator<? super Test1> {
        @Override
        public int compare(Test1 emp1, Test1 emp2) {
            return emp2.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating().compareTo(emp1.getList().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating()) ;
        }
    }*/
}

