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
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.infovita.zywee.Pojo.HomeService;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

/**
 * Created by Khizarkhan on 23-08-2016.
 */
public class HomeProviderDetails  extends AppCompatActivity {

    private static final String TAG = "HomeServiceProviderDetail";

    HomeService homeService;
    private ProgressDialog pDialog;
    private String provider_name,provider_id,service_name,service_id,cost,provider_home_services_id,provider_service_charge_id,
            provider_desc,item_desc,ratingbar,home_service_charges_name;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);

        homeService = (HomeService) getIntent().getSerializableExtra("homeService");

       // setup_toolbar(homeService.getServiceName());
        Bundle bundle = getIntent().getExtras();
        setup_toolbar( bundle.getString("service_name"));
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
        pDialog = new ProgressDialog(HomeProviderDetails.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCanceledOnTouchOutside(false);

        Bundle bundle = getIntent().getExtras();
        provider_name = bundle.getString("provider_name");
        provider_id = bundle.getString("provider_id");
        service_name = bundle.getString("service_name");
        service_id = bundle.getString("service_id");
        provider_home_services_id = bundle.getString("provider_home _services_id");
        provider_service_charge_id = bundle.getString("provider_service_charge_id");
        home_service_charges_name = bundle.getString("home_service_charges_name");
        provider_desc = bundle.getString("provider_desc").replaceAll(";", " :").replaceAll("[\r\n]+", "\n");
        item_desc = bundle.getString("item_desc").replaceAll("\\r\\n"," ").replace("..", ".").replaceAll("\\s+"," ");
        ratingbar = bundle.getString("ratingbar");
        cost = bundle.getString("cost");

    }

    private void initUI() {
        /*Glide.with(this)
                .load(Serverdatas.image_url + "homeService/" + homeService.getId() + ".jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.zywee_logo)
                .error(R.drawable.zywee_logo)
                .into((ImageView) findViewById(R.id.image));
                */
//        ((TextView) findViewById(R.id.item_name)).setText(homeService.getServiceName());
    /*    ((TextView) findViewById(R.id.item_description)).setText(homeService.getDescription().trim());
        ((TextView) findViewById(R.id.test_description)).setText(homeService.getServiceProvidersDescription().toString());
        ((TextView) findViewById(R.id.test_institute_name)).setText(homeService.getProviderName().toString());
        ((TextView) findViewById(R.id.test_cost)).setText("₹" +  homeService.getCost().toString() + "/-");
        ((RatingBar) findViewById(R.id.ratingBar)).setRating(Float.parseFloat(homeService.getAvgRating().get(0)));
*/
        if(item_desc !=""){
            ((TextView) findViewById(R.id.item_description)).setText(item_desc);
        }else {
            ((TextView) findViewById(R.id.item_description)).setVisibility(View.GONE);
        }
        //((TextView) findViewById(R.id.item_description)).setText(item_desc);

        ((TextView) findViewById(R.id.test_description)).setText(provider_desc);
        ((TextView) findViewById(R.id.test_institute_name)).setText(provider_name);
        ((TextView) findViewById(R.id.test_cost)).setText("₹" + cost + "/-");
        ((RatingBar) findViewById(R.id.ratingBar)).setRating(Float.parseFloat(ratingbar));
       // ((TextView) findViewById(R.id.text)).setText("Cost of " + service_name );
        TextView Textview = (TextView) findViewById(R.id.text);
        if( service_name.equals("Nurse")) {
            Textview.setText("Cost of " + service_name + " per month");
        } else if ( service_name.equals("Nurse Attender Care Giver")) {
            Textview.setText("Cost of " + service_name + " per month");
        } else {
            Textview.setText("Cost of " + service_name + " per visit");
        }

      /*  final String provider_id = homeService.getServiceProviderId().toString();
        final String service_id = homeService.getId();
        final String service_name = homeService.getServiceName();
        final String provider_name = homeService.getProviderName().toString();
*/

        ((Button) findViewById(R.id.test_book_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeProviderDetails.this, HomeServieProvider.class)
                        .putExtra("provider_id", provider_id)
                        .putExtra("service_name", service_name)
                        .putExtra("provider_name", provider_name)
                        .putExtra("service_id", service_id)
                        .putExtra("provider_home_services_id", provider_home_services_id)
                        .putExtra("provider_service_charge_id", provider_service_charge_id)
                        .putExtra("cost", cost));
            }
        });

        if (!checkPermissionForPhone())
            requestPermissionForPhone();

        ((Button)findViewById(R.id.call_us)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(Serverdatas.contact_number));
                if (ActivityCompat.checkSelfPermission(HomeProviderDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                HomeProviderDetails.this.startActivity(callIntent);
            }

        });
    }
    public boolean checkPermissionForPhone(){
        int result = ContextCompat.checkSelfPermission(HomeProviderDetails.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForPhone(){
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) HomeProviderDetails.this, Manifest.permission.CALL_PHONE)){
           // Toast.makeText(HomeProviderDetails.this, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions((Activity) HomeProviderDetails.this,new String[]{Manifest.permission.CALL_PHONE},PHONE_PERMISSION_REQUEST_CODE);
        }
    }

        @Override
    public void onBackPressed() {
        HomeProviderDetails.this.finish();
    }

}
