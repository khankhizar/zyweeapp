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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.infovita.zywee.Pojo.Doctor;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

public class ItemDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    public String item_name = "";
    public String item_description = "";
    public String item_price = "";
    public String health_institute_name = "";
    public String health_institute_id = "";
    public String item_id = "";
    public String locality_name = "";
    public String discount = "";
    public String rating = "3";
    public String specialization_id = "";
    public String distance = "";
    public String list_type = "";
    public String geo = "";
    public String enable = "";
    public String qualification = "";
    public String experience = "";
    public String specialization = "";

    Doctor doctors;

    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_item_details);
        setContentView(R.layout.doctor_detail);
        getSupportActionBar().setTitle("Details");




       /* SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        else Log.d("Maps", "map is not initialised");
*/

        item_name = getIntent().getExtras().getString("item_name");
        item_description = getIntent().getExtras().getString("item_description");
        item_price = getIntent().getExtras().getString("item_price");
        health_institute_name = getIntent().getExtras().getString("health_institute_name");
        item_id = getIntent().getExtras().getString("item_id");
        locality_name = getIntent().getExtras().getString("locality_name");
        discount = getIntent().getExtras().getString("discount");
        rating = getIntent().getExtras().getString("health_institute_avg_rating");
        health_institute_id = getIntent().getExtras().getString("health_institute_id");
        specialization_id = getIntent().getExtras().getString("specialization_id");
        distance = getIntent().getExtras().getString("distance").substring(0, 3) + " km";
        list_type = getIntent().getExtras().getString("list_type");
        geo = getIntent().getExtras().getString("geo");
        enable = getIntent().getExtras().getString("enable");
        //Log.d("enable", enable);
        qualification = getIntent().getExtras().getString("qualification");
        experience = getIntent().getExtras().getString("experience");
        specialization = getIntent().getExtras().getString("specialization");
       // Log.d("list_type", " > " +  list_type);
       // Log.d("qualification", qualification);

        Glide.with(getApplicationContext())
                .load(Serverdatas.image_url_base + "doctor/" + health_institute_id + "/" + item_id + ".jpg")
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.doctor_roundimage)
                .transform(new CircleTransform(getApplicationContext()))
                .error(R.drawable.doctor_roundimage)
//                .into((ImageView) row.findViewById(R.id.image));
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // do something
                        //Log.e("info_img",e+"");
                        //Log.e("link :",Serverdatas.image_url_base + "doctor/" + health_institute_id + "/" + item_id + ".jpg");
                        Glide.with(getApplicationContext())
                                .load(Serverdatas.image_url_base + "doctor/" + health_institute_id + "/" + item_id + ".jpg")
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.doctor_roundimage)
                                .into((ImageView) findViewById(R.id.image));
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // do something
                        return false;
                    }
                })
                .into((ImageView) findViewById(R.id.image));

        TextView test_name_setter = (TextView) findViewById(R.id.list_item_name);
        test_name_setter.setText(item_name);

        TextView qualification_setter = (TextView) findViewById(R.id.qualification);
        if(qualification.isEmpty()||qualification=="0") {
            qualification_setter.setText("Not Available");
        }else{
            qualification_setter.setText(qualification);
        }

        TextView specialization_setter = (TextView) findViewById(R.id.specialization);
        if(specialization.isEmpty()||specialization=="0") {
            specialization_setter.setText("Not Available");
        }else{
            specialization_setter.setText(specialization);
        }

        TextView qualification_set = (TextView) findViewById(R.id.qualify);
        if(qualification.isEmpty()||qualification=="0"){
            qualification_set.setText("Not Available");
        }else{
            qualification_set.setText(qualification);
        }


        TextView experience_setter = (TextView) findViewById(R.id.experience);
        if(experience.isEmpty()||experience=="0"){
            experience_setter.setText("Not Available");
        }else{
            experience_setter.setText(experience);
        }


       /* TextView test_description_setter = (TextView) findViewById(R.id.test_description);
        test_description_setter.setText(item_description);*/

        TextView health_institute_name_setter = (TextView) findViewById(R.id.test_institute_name);
        health_institute_name_setter.setText(health_institute_name);
        TextView locality_name_setter = (TextView) findViewById(R.id.list_locality_name);
        if(locality_name.isEmpty()||locality_name=="0"){
            locality_name_setter.setText("Not Available");
        }else{
            locality_name_setter.setText(locality_name);
        }


        TextView discount_setter = (TextView) findViewById(R.id.list_item_discount);
        TextView test_price_setter = (TextView) findViewById(R.id.test_cost);

        RatingBar rating_setter = (RatingBar) findViewById(R.id.ratingBar);
        rating_setter.setRating(Float.parseFloat(rating));

        TextView text = (TextView) findViewById(R.id.text);
        String detail = item_name ;
        text.setText( detail + " is available in the following center");

       /* TextView distance_setter = (TextView) findViewById(R.id.distance);
        distance_setter.setText(distance);*/


        if(Integer.parseInt(discount) != 0){
            TextView test_price_setter_strike= (TextView) findViewById(R.id.test_cost_full);
            test_price_setter_strike.setText("₹"+ item_price);
            test_price_setter_strike.setPaintFlags(test_price_setter_strike.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

            Float total_value = Float.parseFloat(item_price);
            Float discount_val = Float.parseFloat(discount);
            total_value = total_value - (total_value * (discount_val / 100));

            test_price_setter.setText("₹" + Math.round(total_value));
            discount_setter.setText(discount + "% off");

        } else {
            discount_setter.setVisibility(View.GONE);
            test_price_setter.setText("₹" + item_price);
        }

        if(enable.equals("1")) {
            ((Button) findViewById(R.id.test_book_button)).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.more_details)).setVisibility(View.VISIBLE);
        }else{
            ((Button) findViewById(R.id.test_book_button)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.more_details)).setVisibility(View.GONE);
        }
        Button booking_button = (Button) findViewById(R.id.test_book_button);
        booking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (list_type) {
                    case "test": {

                        Intent intent = new Intent(getApplicationContext(), TestCartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("item_id", item_id);
                        intent.putExtra("item_name", item_name);
                        intent.putExtra("specialization_id", specialization_id);
                        intent.putExtra("item_price", item_price);
                        intent.putExtra("discount", discount);
                        intent.putExtra("health_institute_id", health_institute_id);
                        intent.putExtra("health_institute_name", health_institute_name);
                        startActivity(intent);

                    }
                    break;

                    case "package": {
                        Intent intent = new Intent(getApplicationContext(), PackageCartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("item_id", item_id);
                        intent.putExtra("item_name", item_name);
                        intent.putExtra("item_price", item_price);
                        intent.putExtra("discount", discount);
                        intent.putExtra("health_institute_id", health_institute_id);
                        intent.putExtra("health_institute_name", health_institute_name);
                        startActivity(intent);
                    }
                    break;

                    case "doctor": {
                        Intent intent = new Intent(getApplicationContext(), DoctorCartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("item_id", item_id);
                        intent.putExtra("item_name", item_name);
                        intent.putExtra("item_price", item_price);
                        intent.putExtra("discount", discount);
                        intent.putExtra("health_institute_id", health_institute_id);
                        intent.putExtra("health_institute_name", health_institute_name);
                        startActivity(intent);
                    }
                    break;
                }
            }
        });



        findViewById(R.id.more_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPermissionForPhone())
                    requestPermissionForPhone();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(Serverdatas.contact_number));
                if (ActivityCompat.checkSelfPermission(ItemDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
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
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(health_institute_name));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 13));
    }
}
