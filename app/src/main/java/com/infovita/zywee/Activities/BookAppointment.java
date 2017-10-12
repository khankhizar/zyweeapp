package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.infovita.zywee.Adopters.BookRecyclerAdapter;
import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.Pojo.Appointment;
import com.infovita.zywee.Pojo.Appointments;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Supports.InternetStatus;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.infovita.zywee.Sharedvalues.Serverdatas.user_phone;

/**
 * Created by Khizarkhan on 21-10-2016.
 */

public class BookAppointment extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Toolbar toolbar;
    private CharSequence mTitle;
    private ActionBar actionBar;
    String phone;
//    ListView list;

    private int rename_flag =0;

    String APPOINTMENT_ID = "appointment_id";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Appointments> appointmentsList;
    private String TAG;
    BookRecyclerAdapter adapter= null;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_results);

        setup_toolbar();

        pDialog = new ProgressDialog(BookAppointment.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        Serverdatas sd=Serverdatas.getSingletonObject();
        SharedPreferences preferences = getSharedPreferences(user_phone, MODE_PRIVATE);
        // Reading from SharedPreferences
        phone = preferences.getString("user_phone", "phone");

        if (isOnline())
        getAmbulanceList();
            //getTestList();

        //setupToolbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    private static String server_url;

/*    private class getAmbulanceList extends AsyncTask<Void, Void, Integer> {

        ProgressDialog dialog;
        String user_id;
        Serverdatas preferences;
        protected void onPreExecute() {

            server_url = sd.url + "getAppointments";
            super.onPreExecute();
            preferences = new Serverdatas();
            dialog = new ProgressDialog(BookAppointment.this);
            dialog.setMessage("Loading..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            // Showing progress dialog
        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            Serverdatas sd=Serverdatas.getSingletonObject();
            SharedPreferences preferences = getSharedPreferences(sd.user_phone, MODE_PRIVATE);
            // Reading from SharedPreferences
            phone = preferences.getString("user_phone", "phone");

            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePair = new ArrayList<>(3);
            nameValuePair.add(new BasicNameValuePair("phone", phone));
//            Log.d("Response: ", "> " + phone_number);

            // Making a request to url and getting response
            try {
                jsonData = sh.makeServiceCall(server_url, ServiceHandler.POST, nameValuePair);
                Log.d("Response return: ", "> " + jsonData);
                mAdapter = new BookRecyclerAdapter(getDataSet(),getData());
//                sd.setMain_category_list_data(jsonStr);
                return 1;
            } catch (Exception e) {
                Log.d("Response: ", "> " + e);
//                pDialog.cancel();
                return -1;
            }
        }

        public void onPostExecute(Integer arg) {
            if (arg == 1) {
                Log.d(TAG,"onPost List : "+ getDataSet() + getData() );
                mRecyclerView.setAdapter(mAdapter);
                dialog.dismiss();
            }
        }
    }


    String jsonData;*/

    private void getAmbulanceList(){
        showProgressDiaalog();
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<List<Appointment>> call = serverCall.getAppointments(phone);
        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, retrofit2.Response<List<Appointment>> response) {
                List<Appointment> res = response.body();
               // Log.d("retro", "response : " + res);
                hideProgressDiaalog();
                Collections.sort(res, new DateComparator());
                adapter = new BookRecyclerAdapter(BookAppointment.this, res, rename_flag);
                adapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                //Log.d("retro", "response : " + t.getMessage());
                hideProgressDiaalog();
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


   /* private ArrayList<Appointments> getDataSet() {
        *//*for (int index = 0; index < 20; index++) {
            OrderData obj = new OrderData("Some Primary Text " + index,
                    "Secondary " + index);
            results.add(index, obj);
        }*//*

        ArrayList<Appointments> list = new ArrayList<>();

        String ID = "id";
        String END_DATE = "end_datetime";
        String STATUS = "status";
        String PHONE_NUMBER = "phone_no";
        String BOOK_DATE = "book_datetime";
        String COST = "collection_cost";
        String NAME = "name";
        String TITLE = "appointment_title";
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    if (object != null) {
                        JSONObject orderDetail = object.getJSONObject("Appointments");
                        Log.d(TAG, "orderDetail : " + orderDetail);
                        Appointments data = new Appointments();
                        data.setId(orderDetail.getString(ID));
                        data.setCollectionCost(orderDetail.getString(COST));
                        data.setName(orderDetail.getString(NAME));
                        data.setEndDatetime(orderDetail.getString(END_DATE));
                        data.setStatus(orderDetail.getString(STATUS));
                        data.setAppointmentTitle(orderDetail.getString(TITLE));
                        data.setAppointmentId(orderDetail.getString(APPOINTMENT_ID));
                        data.setBookDatetime(orderDetail.getString(BOOK_DATE));
                        JSONObject order = object.getJSONObject("AppointmentUser");
                        AppointmentUser data1 = new AppointmentUser();
                        data1.setPhoneNo((String) order.get(PHONE_NUMBER));
                       *//* JSONObject orderData =object.getJSONObject("Appointment");
                        Appointment data2 = new Appointment();
                        data2.setAppointmentId(orderData.getString(ID));
                        data2.setCollectionCost(orderData.getString(COST));
                        data2.setName(orderData.getString(NAME));
                        data2.setEndDatetime(orderData.getString(END_DATE));
                        data2.setStatus(orderData.getString(STATUS));
                        data2.setAppointmentTitle(orderData.getString(TITLE));
                        data2.setPhone(orderData.getString(PHONE_NUMBER));
*//*
                        list.add(data);
                    }*//*else{
                        getData1();
                    }*//*
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }



    private ArrayList<AppointmentUser> getData() {
        *//*for (int index = 0; index < 20; index++) {
            OrderData obj = new OrderData("Some Primary Text " + index,
                    "Secondary " + index);
            results.add(index, obj);
        }*//*

        ArrayList<AppointmentUser> list = new ArrayList<>();

        String PHONE_NUMBER = "phone_no";
        String NAME = "name";
        String TITLE ="appointment_title";


        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    if (object != null) {
                        JSONObject order = object.getJSONObject("AppointmentUser");
                        AppointmentUser data1 = new AppointmentUser();
                        data1.setName((String) order.get(NAME));
                        data1.setPhoneNo((String) order.get(PHONE_NUMBER));
                        data1.setAppointmentTitle((String) order.get(TITLE));
                        list.add(data1);
                    }*//*else{
                        getData1();
                    }*//*
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
*/
   /* private ArrayList<Appointment> getData1() {
        *//*for (int index = 0; index < 20; index++) {
            OrderData obj = new OrderData("Some Primary Text " + index,
                    "Secondary " + index);
            results.add(index, obj);
        }*//*

        ArrayList<Appointment> list = new ArrayList<>();

        String APPOINTID = "appointment_id";
        String DATE = "end_datetime";
        String STATUS1 = "status";
        String PHONE = "phone";
        String T_COST = "collection_cost";
        String C_NAME = "name";
        String A_TITLE ="appointment_title";



        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    if (object != null) {
                        JSONObject orderDetail = object.getJSONObject("Appointment");
                        Log.d(TAG, "orderDetail1 : " + orderDetail);
                        Appointment data = new Appointment();
                        data.setAppointmentId(orderDetail.getString(APPOINTID));
                        data.setCollectionCost(orderDetail.getString(T_COST));
                        data.setName(orderDetail.getString(C_NAME));
                        data.setEndDatetime(orderDetail.getString(DATE));
                        data.setStatus(orderDetail.getString(STATUS1));
                        data.setAppointmentTitle(orderDetail.getString(A_TITLE));
                        data.setPhone(orderDetail.getString(PHONE));
                        data.setAppointmentId(orderDetail.getString(APPOINTMENT_ID));

                        list.add(data);
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
*/

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

  /*  @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }*/

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // mDrawerToggle.syncState();
    }

    public void setup_toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + "My Bookings" + "</small>"));
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
}

