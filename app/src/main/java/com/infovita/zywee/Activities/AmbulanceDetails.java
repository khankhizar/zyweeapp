package com.infovita.zywee.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.infovita.zywee.Pojo.Ambulance;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

public class AmbulanceDetails extends AppCompatActivity {

    private static final String TAG = "AmbulanceDetail";

    Ambulance ambulance;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_details);

        ambulance = (Ambulance) getIntent().getSerializableExtra("ambulance");

        setup_toolbar("Details");
        initUI();
    }


    /*Setup the toolbar*/
    public void setup_toolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  toolbar.setTitle(Html.fromHtml("<small>" + title + "</small>"));
        toolbar.setTitle(ambulance.getVehicle().getVehicleType());
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

    private void initUI() {
            Glide.with(this)
                    .load(Serverdatas.image_url_base + "/transport/" + ambulance.getVehicle().getId() + ".jpg")
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.zywee_logo)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            // do something
                           // Log.e("info_img",e+"");
                            Glide.with(getApplicationContext())
                                    .load(Serverdatas.image_url + "ambulance/vehicle/" + ambulance.getVehicle().getId() + ".jpg")
                                    //.centerCrop()
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.zywee_logo)
                                    .into((ImageView) findViewById(R.id.item_image));
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            // do something
                            return false;
                        }
                    })
                    .into((ImageView) findViewById(R.id.item_image));



       // ((TextView) findViewById(R.id.item_name)).setText(ambulance.getVehicleType());
        ((TextView) findViewById(R.id.item_description)).setText(ambulance.getVehicle().getVehicleDescription().trim().replaceAll("\\s+"," "));
      //  ((TextView) findViewById(R.id.provider_name)).setText(ambulance.getProviderName().toString().replaceAll("[\\[\\](){}]",""));
        for (int i = 0; i < ambulance.getProviders().size(); i++)
            addProvider(i);
    }


    @Override
    public void onBackPressed() {
        AmbulanceDetails.this.finish();
    }

    private void addProvider(final int i) {
        final LinearLayout providerList = (LinearLayout) findViewById(R.id.provider_list);
        final View view = LayoutInflater.from(this).inflate(R.layout.equipment_provider, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        providerList.addView(view);

        final String provider_name = ambulance.getProviders().get(i).getProvider().getProviderName();

        ((TextView) view.findViewById(R.id.provider_name)).setText(provider_name);

        final String cost;

        int costSize = ambulance.getProviders().size();
        if (costSize > i) {
            cost = ambulance.getProviders().get(i).getProviderDistance().getCost() ;
        } else cost = "N/A";
        ((TextView) view.findViewById(R.id.provider_price)).setText("₹" + cost + "/-");
        ((TextView) view.findViewById(R.id.rating)).setText(ambulance.getProviders().get(i).getProvider().getAvgRating());
        if(ambulance.getProviders().get(i).getProvider().getAvgRating().equals("0.00")){
            ((RatingBar) view.findViewById(R.id.ratingBar)).setRating(Float.parseFloat("4.00"));
        }else {
            ((RatingBar) view.findViewById(R.id.ratingBar)).setRating(Float.parseFloat(ambulance.getProviders().get(i).getProvider().getAvgRating()));
        }

        final String ambulance_provider_id = ambulance.getProviders().get(i).getProviderVehicle().getProviderId();
        final String ambulance_id = ambulance.getVehicle().getId();
        final String ambulance_name = ambulance.getVehicle().getVehicleType();
        final String provider_id = ambulance.getProviders().get(i).getProviderVehicle().getProviderId();

       // view.findViewById(R.id.provider_price).setOnClickListener(new View.OnClickListener() {
        view.findViewById(R.id.provider_book_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AmbulanceDetails.this, AmbulanceProvider.class)
                        .putExtra("provider_id", provider_id)
                        .putExtra("ambulance_name", ambulance_name)
                        .putExtra("provider_name", provider_name)
                        .putExtra("ambulance_provider_id", ambulance_provider_id)
                        .putExtra("ambulance_id", ambulance_id));
            }
        });

       /* ((RelativeLayout) view.findViewById(R.id.lay)) .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AmbulanceDetails.this, AmbulanceproviderDetail.class)
                        .putExtra("ambulance_name", ambulance.getVehicleType())
                        .putExtra("provider_id", ambulance.getProviderId().get(i))
                        .putExtra("provider_name", ambulance.getProviderName().get(i))
                        .putExtra("provider_desc", ambulance.getVehicleDescription())
                        .putExtra("ratingbar",ambulance.getAvgRating().get(i))
                        .putExtra("cost",ambulance.getCost().get(i))
                        .putExtra("ambulance_provider_id", ambulance.getProviderId().get(i))
                        .putExtra("ambulance_id", ambulance.getId()));

                //  .putExtra("homeService", homeService));
            }
        });*/

       /* if (!checkPermissionForPhone())
            requestPermissionForPhone();

        view.findViewById(R.id.call_us).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                try {
                    callIntent.setData(Uri.parse(Serverdatas.contact_number));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (ActivityCompat.checkSelfPermission(AmbulanceDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                AmbulanceDetails.this.startActivity(callIntent);
            }

        });
    }
    public boolean checkPermissionForPhone(){
        int result = ContextCompat.checkSelfPermission(AmbulanceDetails.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForPhone(){
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) AmbulanceDetails.this, Manifest.permission.CALL_PHONE)){
           // Toast.makeText(AmbulanceDetails.this, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions((Activity) AmbulanceDetails.this,new String[]{Manifest.permission.CALL_PHONE},PHONE_PERMISSION_REQUEST_CODE);
        }*/
    }
}