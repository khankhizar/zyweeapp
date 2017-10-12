package com.infovita.zywee.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.infovita.zywee.Pojo.Package;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

/**
 * Created by Khizarkhan on 21-09-2016.
 */

public class PackageDetail extends AppCompatActivity {
    private static final String TAG = "PackageDetail";
    //Package aPackage;
    Package aPackage;
    TextView content;
    String cost;
    String discount;
    LinearLayout desc;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;
//     RelativeLayout RelativeLayout = (RelativeLayout)findViewById(R.id.expand);
    Button provider_book_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);
        aPackage = (Package) getIntent().getSerializableExtra("package");
        setup_toolbar(aPackage.getSubPackage().getSubPackageType());
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
                .load(Serverdatas.image_url + "packages/" + aPackage.getSubPackage().getTreatmentPackageId() + ".jpg")
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.zywee_logo)
                .error(R.drawable.zywee_logo)
                .into((ImageView) findViewById(R.id.item_image));
       /* ((TextView) findViewById(R.id.description)).setText(aPackage.getDescription().replaceAll("\\s+"," "));
        ((TextView) findViewById(R.id.desc)).setText(aPackage.getWhyUndergoThisPackage().replaceAll("\\s+"," "));
        ((TextView) findViewById(R.id.desc1)).setText(aPackage.getWhatPreparationRequired().replaceAll("\\s+"," "));
        ((TextView) findViewById(R.id.desc2)).setText(aPackage.getProcedureForPackage().replaceAll("\\s+"," "));*/
//        ((TextView) findViewById(R.id.item_name)).setText(homeService.getServiceName());
        // ((TextView) findViewById(R.id.description)).setText(aPackage.getDescription().trim().replaceAll("\\s+"," "));
        // for (int i = 0; i < package.getProviderName().size(); i++)
        /*findViewById(R.id.showmore_desc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(findViewById(R.id.package_desc_hide).getVisibility() == View.VISIBLE){
                    (findViewById(R.id.package_desc_hide)).setVisibility(View.GONE);
                    ((TextView)findViewById(R.id.showmore_desc)).setText("Show more");
                }else{
                    (findViewById(R.id.package_desc_hide)).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.showmore_desc)).setText("Show less");
                }

            }
        });*/
        for (int i = 0; i < aPackage.getEntity().size(); i++ )
            addProvider(i);
    }
    @Override
    public void onBackPressed() {
        PackageDetail.this.finish();
    }
    private void addProvider(final int i) {
        final LinearLayout providerList = (LinearLayout) findViewById(R.id.provider_list);
        final View view = LayoutInflater.from(this).inflate(R.layout.package_view1, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        providerList.addView(view);
        final String provider_name = aPackage.getEntity().get(i).getEntityHealthInstitute().getHealthInstituteName();
        ((TextView) view.findViewById(R.id.provider_name)).setText(provider_name);

        //final String cost;

        float costSize = Float.parseFloat(aPackage.getEntity().get(i).getEntityTreatmentPackage().getTreatmentPackageRate());
        ((Button) view.findViewById(R.id.Show)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((LinearLayout) view.findViewById(R.id.desc1)).getVisibility() == View.VISIBLE){
                    ((LinearLayout) view.findViewById(R.id.desc1)).setVisibility(View.GONE);
                    //((Button) view.findViewById(R.id.Show)).setText("+");
                    ((Button) view.findViewById(R.id.Show)).setBackgroundResource(R.drawable.if_icon_ios7_plus_outline_211802);
                }else{
                    ((LinearLayout) view.findViewById(R.id.desc1)).setVisibility(View.VISIBLE);
                    //((Button) view.findViewById(R.id.Show)).setText("-");
                    ((Button) view.findViewById(R.id.Show)).setBackgroundResource(R.drawable.if_icon_ios7_minus_outline_211774);
                }
            }
        });
        ((TextView) view.findViewById(R.id.treat)).setText(aPackage.getEntity().get(i).getEntityTreatmentPackage().getTreatmentPackageName());
        ((TextView) view.findViewById(R.id.des)).setText(aPackage.getEntity().get(i).getEntityTreatmentPackage().getTreatmentPackageDesc());


        float discountSize = Float.parseFloat(aPackage.getEntity().get(i).getEntityTreatmentPackage().getPackageDiscount());
        if (costSize > i||discountSize > i ) {
            cost = aPackage.getEntity().get(i).getEntityTreatmentPackage().getTreatmentPackageRate();
            //  }else cost = "N/A";

            discount = aPackage.getEntity().get(i).getEntityTreatmentPackage().getPackageDiscount().replaceAll("\\.0*$", "");

            //    }else discount = "N/A";

//        } else cost = "N/A";
            ((TextView) view.findViewById(R.id.test_cost_full)).setText("₹" + cost + "/-");
            ((TextView) view.findViewById(R.id.test_cost_full)).setPaintFlags(((TextView) view.findViewById(R.id.test_cost_full)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            ((TextView) view.findViewById(R.id.test_cost_full)).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.list_item_discount)).setText(discount + "% off");
            ((TextView) view.findViewById(R.id.list_item_discount)).setVisibility(View.GONE);

            Float total_value = Float.parseFloat(cost);
            Float discount1 = Float.parseFloat(discount);
            total_value = total_value - (total_value * (discount1 / 100));

            ((TextView) view.findViewById(R.id.test_cost)).setText("₹" + Math.round(total_value));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ((TextView) view.findViewById(R.id.test_cost)).setLayoutParams(params);
        }else{
            TextView test_cost_full = (TextView)findViewById(R.id.test_cost_full);
            test_cost_full.setVisibility(View.GONE);

            TextView discount1 = (TextView) findViewById(R.id.list_item_discount);
            discount1.setVisibility(View.GONE);

            TextView p1_price = (TextView) findViewById(R.id.test_cost);
            p1_price.setText("₹" + cost);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //params.weight = 2.3f;
            p1_price.setLayoutParams(params);
        }

        if(discount.equals("0")){
            ((TextView) view.findViewById(R.id.test_cost_full)).setVisibility(View.GONE);
        } else{
            ((TextView) view.findViewById(R.id.test_cost_full)).setVisibility(View.GONE);
        }


//        ((TextView) view.findViewById(R.id.test_cost)).setText("₹" + cost + "/-");
        ((TextView) view.findViewById(R.id.rating)).setText(aPackage.getEntity().get(i).getEntityHealthInstitute().getHealthInstituteAvgRating());
        if(aPackage.getEntity().get(i).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
            ((RatingBar) view.findViewById(R.id.ratingBar)).setRating(Float.parseFloat("4.00"));
        }else{
            ((RatingBar) view.findViewById(R.id.ratingBar)).setRating(Float.parseFloat(aPackage.getEntity().get(i).getEntityHealthInstitute().getHealthInstituteAvgRating()));
        }
        //((RatingBar) view.findViewById(R.id.ratingBar)).setRating(Float.parseFloat(aPackage.getEntity().get(i).getEntityHealthInstitute().getHealthInstituteAvgRating()));
        ((TextView) view.findViewById(R.id.list_locality_name)).setText(aPackage.getEntity().get(i).getMasterLocality().getLocalityName());
       // ((TextView) view.findViewById(R.id.content)).setText(aPackage.getTreatmentPackageDesc().get(i).replaceAll(",","/n").replaceAll("/n","\u2022 "));

        //provider_book_button = (Button) view.findViewById(R.id.provider_book_button);

      /*  ((Button) view.findViewById(R.id.showmore_package)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((LinearLayout) view.findViewById(R.id.expand)).getVisibility() == View.VISIBLE){
                    ((LinearLayout) view.findViewById(R.id.expand)).setVisibility(View.GONE);
                    ((Button) view.findViewById(R.id.showmore_package)).setText("View Details");

                }else{
                    ((LinearLayout) view.findViewById(R.id.expand)).setVisibility(View.VISIBLE);
                    ((Button) view.findViewById(R.id.showmore_package)).setText("Hide Details");

                }

              *//*  ((LinearLayout) view.findViewById(R.id.lay1)).setVisibility(View.VISIBLE);
                ((LinearLayout) view.findViewById(R.id.lay2)).setVisibility(View.VISIBLE);*//*
                ((TextView) view.findViewById(R.id.packageName_title)).setText(aPackage.getTreatmentPackageName().get(i));
                ((TextView) view.findViewById(R.id.content)).setText("\u2022 "+aPackage.getTreatmentPackageDesc().get(i).replaceAll(",","/n").replaceAll("/n","\u2022 "));
            }
        });*/
       /* ((RelativeLayout) view.findViewById(R.id.lay6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PackageDetail.this, CentreDetailActivity.class)
                        .putExtra("list_item_name", aPackage.getSubPackageType())
                        .putExtra("p1_name", aPackage.getHealthInstituteName().get(i))
                        .putExtra("p1_price", aPackage.getTreatmentPackageRate().get(i))
                        .putExtra("discount", aPackage.getPackageDiscount().get(i).replaceAll("\\.0*$", ""))
                        .putExtra("locality1", aPackage.getLocalityName().get(i))
                        .putExtra("ratingbar",aPackage.getHealthInstituteAvgRating().get(i))
                        .putExtra("centre",aPackage.getHealthInstituteDescrption().get(i))
                        .putExtra("geo",aPackage.getGeo().get(i))
                        .putExtra("description",aPackage.getDescription())
                        .putExtra("content", aPackage.getTreatmentPackageDesc().get(i))
                        .putExtra("Health_Id",aPackage.getHealthInstituteId().get(0))
                        .putExtra("package_id",aPackage.getPackageTypeId())
                        .putExtra("package_desc",aPackage.getTreatmentPackagesDescription())
                        .setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)));

            }
        });*/
        if(aPackage.getEntity().get(i).getEntityHealthInstitute().getHasAppointmentBooking().equals("1") ){
            ((Button) view.findViewById(R.id.provider_book_button)).setVisibility(View.VISIBLE);
        }else{
            ((Button) view.findViewById(R.id.provider_book_button)).setVisibility(View.INVISIBLE);
        }

        //((TextView) view.findViewById(R.id.test_cost)).setOnClickListener(new View.OnClickListener() {
        ((Button) view.findViewById(R.id.provider_book_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(PackageDetail.this,PackageCartActivity.class)
                        .putExtra("list_item_name", aPackage.getSubPackage().getSubPackageType())
                        .putExtra("p1_name", aPackage.getEntity().get(i).getEntityHealthInstitute().getHealthInstituteName())
                        .putExtra("p1_price", aPackage.getEntity().get(i).getEntityTreatmentPackage().getTreatmentPackageRate())
                        .putExtra("discount", aPackage.getEntity().get(i).getEntityTreatmentPackage().getPackageDiscount().replaceAll("\\.0*$", ""))
                        .putExtra("Health_Id",aPackage.getEntity().get(i).getEntityHealthInstitute().getHealthInstituteId())
                        .putExtra("package_id",aPackage.getEntity().get(i).getEntityTreatmentPackage().getTreatmentPackageId())
                        .putExtra("package_desc",aPackage.getTreatmentPackage().getDescription())
                        .putExtra("payment_option",aPackage.getEntity().get(i).getEntityHealthInstitute().getPaymentOption())
                       // .putExtra("package_id",aPackage.getEntityTreatmentPackageId().get(i))
                );
            }
        });
      /*  if (!checkPermissionForPhone())
            requestPermissionForPhone();
        ((Button) findViewById(R.id.call_us)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(Serverdatas.contact_number));
                if (ActivityCompat.checkSelfPermission(PackageDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                PackageDetail.this.startActivity(callIntent);
            }
        });
    }
    public boolean checkPermissionForPhone() {
        int result = ContextCompat.checkSelfPermission(PackageDetail.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    public void requestPermissionForPhone() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) PackageDetail.this, Manifest.permission.CALL_PHONE)) {
            //  Toast.makeText(HomeServiceDetails.this, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions((Activity) PackageDetail.this, new String[]{Manifest.permission.CALL_PHONE}, PHONE_PERMISSION_REQUEST_CODE);
        }*/
    }
}