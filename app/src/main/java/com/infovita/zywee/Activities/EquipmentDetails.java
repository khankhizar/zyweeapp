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
import com.infovita.zywee.Pojo.Equipment;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

public class EquipmentDetails extends AppCompatActivity {

    private static final String TAG = "EquipmentDetail";
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    Equipment equipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_details);

        equipment = (Equipment) getIntent().getSerializableExtra("equipment");

        setup_toolbar("Details");
        initUI();


    }


    /*Setup the toolbar*/
    public void setup_toolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setTitle(Html.fromHtml("<small>" + title + "</small>"));
        toolbar.setTitle(equipment.getEquipment().getEquipmentName());
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
//                .load(Serverdatas.image_url_base + "equipment/" + equipment.getEquipmentProviderId().get(0) + ".jpg")
                .load(Serverdatas.image_url + "equipment/" + equipment.getEquipment().getId() + ".jpg")
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.zywee_logo)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // do something
                        //Log.e("info_img",e+"");
                        Glide.with(getApplicationContext())
                                .load(Serverdatas.image_url + "equipments/" + equipment.getEquipment().getId() + ".jpg")
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
      //  ((TextView) findViewById(R.id.item_name)).setText(equipment.getEquipmentName());
       // ((TextView) findViewById(R.id.item_description)).setText(equipment.getDescription().trim().replaceAll("\\s+"," "));
     //   ((TextView) findViewById(R.id.provider_name)).setText(equipment.getProviderName().toString().replaceAll("[\\[\\](){}]",""));
        for (int i = 0; i < equipment.getList().size(); i++)
            addProvider(i);
    }

    @Override
    public void onBackPressed() {
        EquipmentDetails.this.finish();
    }

    private void addProvider(final int i) {
        final LinearLayout providerList = (LinearLayout) findViewById(R.id.provider_list);
        final View view = LayoutInflater.from(this).inflate(R.layout.equipment_provider, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        providerList.addView(view);


        final String provider_name = equipment.getList().get(i).getEquipmentProvider().getProviderName();

        ((TextView) view.findViewById(R.id.provider_name)).setText(provider_name);

        final String daily_cost, weekly_cost, monthly_cost;

        //int dailySize = equipment.getList().get(i).getProviderEquipmentCost().getDailyCost().length();
        int dailySize = equipment.getList().size();
        if (dailySize > i) {
            daily_cost = equipment.getList().get(i).getProviderEquipmentCost().getDailyCost();
        } else daily_cost = "0";
        //int weeklySize = equipment.getList().get(i).getProviderEquipmentCost().getWeeklyCost().length();
        int weeklySize = equipment.getList().size();
        if (weeklySize > i) {
            weekly_cost = equipment.getList().get(i).getProviderEquipmentCost().getWeeklyCost();
        } else weekly_cost = "0";
        //int monthlySize = equipment.getList().get(i).getProviderEquipmentCost().getMonthlyCost().length();
        int monthlySize = equipment.getList().size();
        if (monthlySize > i) {
            monthly_cost = equipment.getList().get(i).getProviderEquipmentCost().getMonthlyCost();
        } else monthly_cost = "0";
        ((TextView) view.findViewById(R.id.provider_price)).setText("₹" + monthly_cost + "/-");
        ((TextView) view.findViewById(R.id.rating)).setText(equipment.getList().get(i).getEquipmentProvider().getAvgRating());
        if(equipment.getList().get(i).getEquipmentProvider().getAvgRating().equals("0.00")){
            ((RatingBar) view.findViewById(R.id.ratingBar)).setRating(Float.parseFloat("4.00"));
        }else {
            ((RatingBar) view.findViewById(R.id.ratingBar)).setRating(Float.parseFloat(equipment.getList().get(i).getEquipmentProvider().getAvgRating()));
        }

        final String equipment_provider_id = equipment.getList().get(i).getProviderEquipmentCost().getEquipmentProviderId();
        final String equipment_id = equipment.getList().get(i).getProviderEquipmentCost().getEquipmentId();
        final String provider_equipment_cost_id = equipment.getList().get(i).getProviderEquipmentCost().getProviderEquipmentCostId();
        final String equipment_name = equipment.getEquipment().getEquipmentName();

        view.findViewById(R.id.provider_book_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EquipmentDetails.this, EquipmentProvider.class)
                        .putExtra("equipment_name", equipment_name)
                        .putExtra("provider_name", provider_name)
                        .putExtra("equipment_provider_id", equipment_provider_id)
                        .putExtra("provider_equipment_cost_id", provider_equipment_cost_id)
                        .putExtra("equipment_id", equipment_id)
                        .putExtra("monthly_cost", monthly_cost)
                        .putExtra("daily_cost", daily_cost)
                        .putExtra("weekly_cost", weekly_cost));
            }
        });

       /* ((RelativeLayout) view.findViewById(R.id.lay)) .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EquipmentDetails.this, EquipmentsproviderDetail.class)
                        .putExtra("equipment_name", equipment.getEquipmentName())
                        .putExtra("provider_name", equipment.getProviderName().get(i))
                        .putExtra("equipment_provider_id", equipment.getPId().get(i))
                        .putExtra("provider_equipment_cost_id", equipment.getProviderEquipmentCostId().get(i))
                        .putExtra("equipment_id", equipment.getEquipmentId().get(i))
                        .putExtra("monthly_cost", equipment.getMonthlyCost().get(i))
                        .putExtra("daily_cost", equipment.getDailyCost().get(i))
                        .putExtra("weekly_cost", equipment.getWeeklyCost().get(i))
                        .putExtra("equip_desc",equipment.getDescription())
                        .putExtra("provider_desc", equipment.getPDescription().get(i))
                        .putExtra("ratingbar",equipment.getAvgRating().get(i)));
                //  .putExtra("homeService", homeService));
            }
        });*/


       /* if (!checkPermissionForPhone())
            requestPermissionForPhone();

        view.findViewById(R.id.call_us).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(Serverdatas.contact_number));
                if (ActivityCompat.checkSelfPermission(EquipmentDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                EquipmentDetails.this.startActivity(callIntent);
            }

        });
    }
    public boolean checkPermissionForPhone(){
        int result = ContextCompat.checkSelfPermission(EquipmentDetails.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForPhone(){
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) EquipmentDetails.this, Manifest.permission.CALL_PHONE)){
          //  Toast.makeText(EquipmentDetails.this, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions((Activity) EquipmentDetails.this,new String[]{Manifest.permission.CALL_PHONE},PHONE_PERMISSION_REQUEST_CODE);
        } */
    }
}