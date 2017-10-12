package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.infovita.zywee.Adopters.ReportListAdapter;
import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.Pojo.Image;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Supports.InternetStatus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Khizarkhan on 08-11-2016.
 */
public class MyReports extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressDialog pDialog;
    String phone;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ListView list;
    private ReportListAdapter adapter;
    int rename_flag = 0;
    private GridView mGridView;
    private ProgressBar mProgressBar;

    List<String> distance_type;
    Image image;


    //List<Image> res;



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
       // setContentView(R.layout.activity_grid_view);
        setup_toolbar();


        pDialog = new ProgressDialog(MyReports.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        Serverdatas sd=Serverdatas.getSingletonObject();
        SharedPreferences preferences = getSharedPreferences(sd.user_phone, MODE_PRIVATE);
        // Reading from SharedPreferences
        phone = preferences.getString("user_phone", "phone");
       // Log.d("user_phone", phone);

        list = (ListView) findViewById(R.id.test_list);

       /* mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
*/

        // getAmbulanceList();
        if (isOnline())
        getImageList();
    }


    private void getImageList() {
        // showProgressDiaalog();
        pDialog = new ProgressDialog(MyReports.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<List<Image>> call = serverCall.getImage(phone);

        distance_type = new ArrayList<>();


        //Log.d("main_type1", " > " + phone);

        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {

                   List<Image> res  = response.body();


           //     Log.d("retro1", "response : " + response.body().get(0).getBucketImage().getFileName());

               // adapter = new ReportListAdapter(MyReports.this, R.layout.grid_item_layout, res);
                adapter = new ReportListAdapter(MyReports.this, res,rename_flag);

               // for (int i = 1; i < res.size(); i++)
                list.setAdapter(adapter);
                pDialog.cancel();

            }



            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                //Log.d("retro", "response : " + t.getMessage());
                pDialog.cancel();
                //  hideProgressDiaalog();
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //Get item at position
                Image item = (Image) parent.getItemAtPosition(position);
                if(item.getBucketImage().getFileName().endsWith("jpg")) {

                    //Pass the image title and url to DetailsActivity
                    Intent intent = new Intent(MyReports.this, DetailsActivity.class);
                    intent.putExtra("title", item.getBucketImage().getImages());
                    intent.putExtra("image", item.getBucketImage().getFileName());
                    //Log.d("imagemain :", item.getBucketImage().getFileName());
                    startActivity(intent);
                }else{
                    String image = item.getBucketImage().getMobile();

                   // Log.d("kkkjkl :", item.getBucketImage().getFileName());
                  //  String pdfLink = "http://54.152.88.70/zywee/scanimages" + "/" + image + "/" + item.getScanImage().getTestName();
                   String pdfLink = "https://s3.amazonaws.com/scanimages-70/"  + image + "/" + item.getBucketImage().getFileName();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfLink));
                    MyReports.this.startActivity(browserIntent);

                }

                //Start details activity
               // startActivity(intent);
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
                        getImageList();
                    }
                });
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.rgb(0, 111, 192));//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }





    private void setup_toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + "My Reports" + "</small>"));
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
