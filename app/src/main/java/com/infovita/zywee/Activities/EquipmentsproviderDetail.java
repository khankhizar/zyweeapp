package com.infovita.zywee.Activities;

/**
 * Created by Khizarkhan on 25-08-2016.
 */

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.infovita.zywee.Pojo.Equipment;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

/**
 * Created by Khizarkhan on 23-08-2016.
 */
public class EquipmentsproviderDetail  extends AppCompatActivity {

    private static final String TAG = "EquipmentDetail";

    Equipment equipment;
    private ProgressDialog pDialog;
    private String provider_name,equipment_name,provider_desc,item_desc,ratingbar,monthly_cost,daily_cost,weekly_cost,equip_desc,
            equipment_provider_id,provider_equipment_cost_id,equipment_id;

    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_detail);

        equipment = (Equipment) getIntent().getSerializableExtra("equipment");

        // setup_toolbar(homeService.getServiceName());
        Bundle bundle = getIntent().getExtras();
        setup_toolbar( bundle.getString("equipment_name"));
        fetchIntentData();
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
    private void fetchIntentData() {
        pDialog = new ProgressDialog(EquipmentsproviderDetail.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCanceledOnTouchOutside(false);

        Bundle bundle = getIntent().getExtras();
        equipment_name = bundle.getString("equipment_name");
        provider_name = bundle.getString("provider_name");
        monthly_cost = bundle.getString("monthly_cost");
        daily_cost = bundle.getString("daily_cost");
        weekly_cost = bundle.getString("weekly_cost");
        equip_desc = bundle.getString("equip_desc").replaceAll("\\r\\n"," ").replaceAll("\\s+"," ");
        provider_desc = bundle.getString("provider_desc");
        ratingbar = bundle.getString("ratingbar");


    }

    private void initUI() {
        /*Glide.with(this)
                .load(Serverdatas.image_url + "homeService/" + homeService.getId() + ".jpg")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.zywee_logo)
                .error(R.drawable.zywee_logo)
                .into((ImageView) findViewById(R.id.image));
                */
//        ((TextView) findViewById(R.id.item_name)).setText(homeService.getServiceName());

        ((TextView) findViewById(R.id.item_description)).setText(equip_desc);
      //  ((TextView) findViewById(R.id.test_description)).setText(provider_desc);
        TextView Textview1 = (TextView) findViewById(R.id.test_description);
        if(provider_desc .equals("")){
            Textview1.setText("Description Coming Soon");
        }else {
            Textview1.setText(provider_desc);
        }
        ((TextView) findViewById(R.id.test_institute_name)).setText(provider_name);
        ((TextView) findViewById(R.id.test_cost)).setText("₹" + monthly_cost + "/-");
        TextView Textview =(TextView) findViewById(R.id.text);
        ((TextView) findViewById(R.id.text)).setText("Cost of " + equipment_name + " per month" );
      /*  if( equipment_name.equals("ICU Equipments")) {
            Textview.setText("Cost of " + equipment_name + " per month");
        } else if ( equipment_name.equals("Beds")){
            Textview.setText("Cost of " + equipment_name + " per month");
        } else if ( equipment_name.equals("Respiratory Aids")){
            Textview.setText("Cost of " + equipment_name + " per month");
        */

        ((RatingBar) findViewById(R.id.ratingBar)).setRating(Float.parseFloat(ratingbar));


        ((Button) findViewById(R.id.test_book_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EquipmentsproviderDetail.this, EquipmentProvider.class)
                        .putExtra("equipment_name", equipment_name)
                        .putExtra("provider_name", provider_name)
                        .putExtra("provider_name", provider_name)
                        .putExtra("equipment_provider_id", equipment_provider_id)
                        .putExtra("provider_equipment_cost_id", provider_equipment_cost_id)
                        .putExtra("equipment_id", equipment_id)
                        .putExtra("monthly_cost", monthly_cost)
                        .putExtra("daily_cost", daily_cost)
                        .putExtra("weekly_cost", weekly_cost));
            }
        });

        if (!checkPermissionForPhone())
            requestPermissionForPhone();

        ((Button)findViewById(R.id.call_us)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(Serverdatas.contact_number));
                if (ActivityCompat.checkSelfPermission(EquipmentsproviderDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                EquipmentsproviderDetail.this.startActivity(callIntent);
            }

        });
    }
    public boolean checkPermissionForPhone(){
        int result = ContextCompat.checkSelfPermission(EquipmentsproviderDetail.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForPhone(){
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) EquipmentsproviderDetail.this, Manifest.permission.CALL_PHONE)){
           // Toast.makeText(EquipmentsproviderDetail.this, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions((Activity) EquipmentsproviderDetail.this,new String[]{Manifest.permission.CALL_PHONE},PHONE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onBackPressed() {
        EquipmentsproviderDetail.this.finish();
    }

}


