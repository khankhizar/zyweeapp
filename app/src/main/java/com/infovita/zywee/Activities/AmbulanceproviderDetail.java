package com.infovita.zywee.Activities;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.infovita.zywee.Pojo.Ambulance;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

/**
 * Created by Khizarkhan on 23-08-2016.
 */
public class AmbulanceproviderDetail  extends AppCompatActivity {

    private static final String TAG = "AmbulanceProviderDetail";

    Ambulance ambulance;
    private ProgressDialog pDialog;
    private String provider_name,provider_id,cost,ambulance_name,ambulance_provider_id,ambulance_id,provider_desc,ratingbar;

    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_detail);

        ambulance = (Ambulance) getIntent().getSerializableExtra("ambulance");

        // setup_toolbar(homeService.getServiceName());
        Bundle bundle = getIntent().getExtras();
        setup_toolbar( bundle.getString("ambulance_name"));
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
        pDialog = new ProgressDialog(AmbulanceproviderDetail.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCanceledOnTouchOutside(false);

        Bundle bundle = getIntent().getExtras();
        Log.d("Data : ", bundle.getString("ratingbar"));
        provider_name = bundle.getString("provider_name");
        provider_id = bundle.getString("provider_id");
        ambulance_name = bundle.getString("ambulance_name");
        provider_desc = bundle.getString("provider_desc").replaceAll("\\s+"," ");
        ambulance_provider_id = bundle.getString("ambulance_provider_id");
        ambulance_id = bundle.getString("ambulance_id");
        ratingbar = bundle.getString("ratingbar");
        cost = bundle.getString("cost");


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

        ((TextView) findViewById(R.id.item_description)).setText(provider_desc);
        ((TextView) findViewById(R.id.test_description)).setText("Description Coming Soon");
        ((TextView) findViewById(R.id.test_institute_name)).setText(provider_name);
        ((TextView) findViewById(R.id.text)).setText(ambulance_name + " charge for Half Day ");
        ((TextView) findViewById(R.id.test_cost)).setText("₹" + cost + "/-");
        ((RatingBar) findViewById(R.id.ratingBar)).setRating(Float.parseFloat(ratingbar));


        ((Button) findViewById(R.id.test_book_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AmbulanceproviderDetail.this, AmbulanceProvider.class)
                        .putExtra("provider_id", provider_id)
                        .putExtra("ambulance_name", ambulance_name)
                        .putExtra("provider_name", provider_name)
                        .putExtra("ambulance_provider_id", ambulance_provider_id)
                        .putExtra("ambulance_id", ambulance_id));
            }
        });

        if (!checkPermissionForPhone())
            requestPermissionForPhone();

        ((Button)findViewById(R.id.call_us)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(Serverdatas.contact_number));
                if (ActivityCompat.checkSelfPermission(AmbulanceproviderDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                AmbulanceproviderDetail.this.startActivity(callIntent);
            }

        });
    }
    public boolean checkPermissionForPhone(){
        int result = ContextCompat.checkSelfPermission(AmbulanceproviderDetail.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForPhone(){
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) AmbulanceproviderDetail.this, Manifest.permission.CALL_PHONE)){
            //Toast.makeText(AmbulanceproviderDetail.this, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions((Activity) AmbulanceproviderDetail.this,new String[]{Manifest.permission.CALL_PHONE},PHONE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onBackPressed() {
        AmbulanceproviderDetail.this.finish();
    }

}


