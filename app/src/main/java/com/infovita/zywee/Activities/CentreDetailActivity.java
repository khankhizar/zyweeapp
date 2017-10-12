package com.infovita.zywee.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;


/**
 * Created by Khizarkhan on 22-09-2016.
 */

public class CentreDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    public String list_item_name = "";
    public String p1_name = "";
    public String item_price = "";
    public String discount = "";
    public String locality1 = "";
    public String ratingbar = "";
    public String centre = "";
    public String geo = "";
    public String description = "";
    public String content = "";
    public String Health_Id = "";
    public String package_id = "";
    public String package_desc = "";



    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.centre_detail);
       // getSupportActionBar().setTitle("Details");

        Bundle bundle = getIntent().getExtras();
        setup_toolbar( bundle.getString("list_item_name"));

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        else Log.d("Maps", "map is not initialised");

       // Log.d("desc :", description);

        list_item_name = getIntent().getExtras().getString("list_item_name");
        p1_name = getIntent().getExtras().getString("p1_name");
        item_price = getIntent().getExtras().getString("p1_price");
        discount = getIntent().getExtras().getString("discount").replaceAll("\\.0*$", "");
        locality1 = getIntent().getExtras().getString("locality1");
        ratingbar = getIntent().getExtras().getString("ratingbar");
        centre = getIntent().getExtras().getString("centre");
        geo = getIntent().getExtras().getString("geo");
        description = getIntent().getExtras().getString("description");
        content = getIntent().getExtras().getString("content").replaceAll(",","/n").replaceAll("/n","\u2022 ");
        Health_Id = getIntent().getExtras().getString("Health_Id");
        package_id = getIntent().getExtras().getString("package_id");
        package_desc = getIntent().getExtras().getString("package_desc");



       /* TextView test_name_setter = (TextView) findViewById(R.id.list_item_name);
        test_name_setter.setText(item_name);*/

        ((Button) findViewById(R.id.showmore_package)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((TextView) findViewById(R.id.description)).getVisibility() == View.VISIBLE){
                    ((TextView) findViewById(R.id.description)).setVisibility(View.GONE);
                    ((Button) findViewById(R.id.showmore_package)).setText("Show Details");
                }else{
                    ((TextView) findViewById(R.id.description)).setVisibility(View.VISIBLE);
                    ((Button) findViewById(R.id.showmore_package)).setText("Hide Details");
                }
            }
        });


        TextView test_description_setter = (TextView) findViewById(R.id.description);
        //test_description_setter.setText(centre);
        test_description_setter.setText(content);

        TextView description_setter = (TextView) findViewById(R.id.item_description);
        description_setter.setText(description);

        TextView health_institute_name_setter = (TextView) findViewById(R.id.test_institute_name);
        health_institute_name_setter.setText(p1_name);
        TextView locality_name_setter = (TextView) findViewById(R.id.list_locality_name);
        locality_name_setter.setText(locality1);

        TextView discount_setter = (TextView) findViewById(R.id.list_item_discount);
        TextView test_price_setter = (TextView) findViewById(R.id.test_cost);

        ((RatingBar) findViewById(R.id.ratingBar)).setRating(Float.parseFloat(ratingbar));


        if(Integer.parseInt(discount) != 0){
            ((TextView) findViewById(R.id.test_cost_full)).setText("₹" + item_price + "/-");
            ((TextView) findViewById(R.id.test_cost_full)).setPaintFlags(((TextView) findViewById(R.id.test_cost_full)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            ((TextView) findViewById(R.id.test_cost_full)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.list_item_discount)).setText(discount + "% off");
            ((TextView) findViewById(R.id.list_item_discount)).setVisibility(View.GONE);

            Float total_value = Float.parseFloat(item_price);
            Float discount1 = Float.parseFloat(discount);
            total_value = total_value - (total_value * (discount1 / 100));

            ((TextView) findViewById(R.id.test_cost)).setText("₹" + Math.round(total_value));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ((TextView) findViewById(R.id.test_cost)).setLayoutParams(params);
        }else{
            TextView test_cost_full = (TextView)findViewById(R.id.test_cost_full);
            test_cost_full.setVisibility(View.INVISIBLE);

            TextView discount1 = (TextView) findViewById(R.id.list_item_discount);
            discount1.setVisibility(View.GONE);

            TextView p1_price_setter = (TextView) findViewById(R.id.test_cost);
            p1_price_setter.setText("₹" + item_price);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //params.weight = 2.3f;
            p1_price_setter.setLayoutParams(params);
        }

        /*TextView distance_setter = (TextView) findViewById(R.id.distance);
        distance_setter.setText(distance);*/

        ((Button) findViewById(R.id.test_book_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(CentreDetailActivity.this,PackageCartActivity.class)
                        .putExtra("list_item_name",list_item_name)
                        .putExtra("p1_name",p1_name)
                        .putExtra("p1_price",item_price)
                        .putExtra("discount",discount)
                        .putExtra("Health_Id",Health_Id)
                        .putExtra("package_id",package_id)
                        .putExtra("package_desc",package_desc));
                       /* .putExtra("list_item_name", aPackage.getSubPackageType())
                        .putExtra("p1_name", aPackage.getHealthInstituteName().get(i))
                        .putExtra("p1_price", aPackage.getTreatmentPackageRate().get(i))
                        .putExtra("Health_Id",aPackage.getHealthInstituteId().get(i))
                        .putExtra("package_id",aPackage.getPackageTypeId())
                        .putExtra("package_desc",aPackage.getTreatmentPackagesDescription())*/


            }
        });


        ((Button)findViewById(R.id.call_us)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(Serverdatas.contact_number));
                if (ActivityCompat.checkSelfPermission(CentreDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                CentreDetailActivity.this.startActivity(callIntent);
            }

        });
    }


       /* if(Integer.parseInt(discount) != 0){
            TextView test_price_setter_strike= (TextView) findViewById(R.id.test_cost);
            test_price_setter_strike.setText("₹"+ p1_price);
            test_price_setter_strike.setPaintFlags(test_price_setter_strike.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

            Float total_value = Float.parseFloat(p1_price);
            Float discount_val = Float.parseFloat(discount);
            total_value = total_value - (total_value * (discount_val / 100));

            test_price_setter.setText("₹" + Math.round(total_value));
            discount_setter.setText(discount + "% off");

        } else {
            discount_setter.setVisibility(View.GONE);
            test_price_setter.setText("₹" + p1_price);
        }*/



    public void setup_toolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + title + "</small>"));
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
    public boolean checkPermissionForPhone(){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForPhone(){
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this, Manifest.permission.CALL_PHONE)){
            //Toast.makeText(this, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions((Activity) this,new String[]{Manifest.permission.CALL_PHONE},PHONE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Log.d("geo", " > " + geo);
        String[] latlong =  geo.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(p1_name));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 13));
    }
}
