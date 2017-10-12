package com.infovita.zywee.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import com.infovita.zywee.Pojo.HomeService;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

public class HomeServiceDetails extends AppCompatActivity {

    private static final String TAG = "AmbulanceDetail";

    HomeService homeService;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_service_detail);

        homeService = (HomeService) getIntent().getSerializableExtra("homeService");

        setup_toolbar(homeService.getHomeServiceCategory().getServiceName());
        initUI();
    }


    /*Setup the toolbar*/
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

    private void initUI() {
        Glide.with(this)
                .load(Serverdatas.image_url_base + "homeservice/" + homeService.getList().get(0).getProviderHomeService().getServiceProviderId() + ".jpg")
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.zywee_logo)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // do something
                       // Log.e("info_img",e+"");
                        Glide.with(getApplicationContext())
                                .load(Serverdatas.image_url + "homeService/" + homeService.getList().get(0).getProviderHomeService().getServiceProviderId() + ".jpg")
                                //.centerCrop()
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.zywee_logo)
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
//        ((TextView) findViewById(R.id.item_name)).setText(homeService.getServiceName());
       // ((TextView) findViewById(R.id.description)).setText(homeService.getDescription().trim().replaceAll("\\s+"," "));
        ((TextView) findViewById(R.id.description)).setVisibility(View.GONE);
       /* if(homeService.getDescription().isEmpty()){
            ((TextView) findViewById(R.id.description)).setVisibility(View.GONE);
        }else{
            ((TextView) findViewById(R.id.description)).setVisibility(View.VISIBLE);
        }*/
        for (int i = 0; i < homeService.getList().size(); i++)
            addProvider(i);
    }

    @Override
    public void onBackPressed() {
        HomeServiceDetails.this.finish();
    }

    private void addProvider(final int i) {
        //Log.d("rating :",homeService.getAvgRating().get(i));
        final LinearLayout providerList = (LinearLayout) findViewById(R.id.provider_list);
        final View view = LayoutInflater.from(this).inflate(R.layout.provider_view, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        providerList.addView(view);

        final String provider_name = homeService.getList().get(i).getServiceProvider().getProviderName();

        ((TextView) view.findViewById(R.id.name)).setText(provider_name);

        final String cost;

        int costSize = homeService.getList().size();
        if (costSize > i) {
            cost = homeService.getList().get(i).getProviderServiceCharge().getCost();
        } else cost = "N/A";
        ((TextView) view.findViewById(R.id.discount_price)).setText("₹" + cost + "/-");
        if(homeService.getList().get(i).getServiceProvider().getAvgRating().equals("0.00")){
            ((RatingBar) view.findViewById(R.id.ratingBar)).setRating(Float.parseFloat("4.00"));
        }else{
            ((RatingBar) view.findViewById(R.id.ratingBar)).setRating(Float.parseFloat(homeService.getList().get(i).getServiceProvider().getAvgRating()));
        }


        final String provider_id = homeService.getList().get(0).getProviderHomeService().getServiceProviderId();
        final String service_id = homeService.getHomeServiceCategory().getId();
        final String service_name = homeService.getHomeServiceCategory().getServiceName();

        //view.findViewById(R.id.discount_price).setOnClickListener(new View.OnClickListener() {
        view.findViewById(R.id.book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeServiceDetails.this, HomeServieProvider.class)
                        .putExtra("provider_id", provider_id)
                        .putExtra("service_name", service_name)
                        .putExtra("provider_name", provider_name)
                        .putExtra("service_id", service_id)
                        .putExtra("provider_home_services_id", homeService.getList().get(i).getProviderHomeService().getServiceProviderId())
                        .putExtra("provider_service_charge_id", homeService.getList().get(i).getProviderServiceCharge().getHomeServiceChargeId()));
            }
        });
       /* ((RelativeLayout) view.findViewById(R.id.lay5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeServiceDetails.this, HomeProviderDetails.class)
                        .putExtra("provider_id", homeService.getServiceProviderId().get(i))
                        .putExtra("service_name", homeService.getServiceName())
                        .putExtra("provider_name", homeService.getProviderName().get(i))
                        .putExtra("provider_desc", homeService.getServiceProvidersDescription().get(0))
                        .putExtra("item_desc", homeService.getDescription())
                        .putExtra("cost", homeService.getCost().get(i))
                        .putExtra("service_id", homeService.getId())
                        .putExtra("ratingbar", homeService.getAvgRating().get(i))
                        .putExtra("provider_home_services_id", homeService.getProviderHomeServiceId().get(i))
                        .putExtra("provider_service_charge_id", homeService.getHomeServiceChargeId().get(i)));
                //  .putExtra("homeService", homeService));
            }
        });*/
       /* if (!checkPermissionForPhone())
            requestPermissionForPhone();

         view.findViewById(R.id.call_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(Serverdatas.contact_number));
                if (ActivityCompat.checkSelfPermission(HomeServiceDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                HomeServiceDetails.this.startActivity(callIntent);
            }

        });
    }

    public boolean checkPermissionForPhone() {
        int result = ContextCompat.checkSelfPermission(HomeServiceDetails.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForPhone() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) HomeServiceDetails.this, Manifest.permission.CALL_PHONE)) {
          //  Toast.makeText(HomeServiceDetails.this, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions((Activity) HomeServiceDetails.this, new String[]{Manifest.permission.CALL_PHONE}, PHONE_PERMISSION_REQUEST_CODE);
        }*/
    }
}
