package com.infovita.zywee.Activities;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.infovita.zywee.Pojo.Test1;
import com.infovita.zywee.R;


/**
 * Created by Khizarkhan on 03-10-2016.
 */

public class TestDetail extends AppCompatActivity {
    private static final String TAG = "TestDetail";
    Test1 test;
    String cost;


//    TextView p1_price = (TextView) findViewById(R.id.provider_price);
//    TextView discount1 = (TextView) findViewById(R.id.list_item_discount);

    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;
    Button provider_book_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_detail);
        test = (Test1) getIntent().getSerializableExtra("test");
        setup_toolbar(test.getSubTest().getSubTestType());
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
      /*  Glide.with(this)
                .load(Serverdatas.image_url + "packages/" + aPackage.getPackageTypeId() + ".jpg")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.zywee_logo)
                .error(R.drawable.zywee_logo)
                .into((ImageView) findViewById(R.id.item_image));*//*


      */ /* ((LinearLayout) findViewById(R.id.lay)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.viewmore)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((LinearLayout) findViewById(R.id.lay)).getVisibility() == View.VISIBLE) {
                    ((LinearLayout) findViewById(R.id.lay)).setVisibility(View.GONE);
                    ((TextView) findViewById(R.id.viewmore)).setText("View Details");

                }else{
                    ((LinearLayout) findViewById(R.id.lay)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.viewmore)).setText("Hide Details");

                }
            }
        });
        if (test.getDescription().equals("")){
            ((LinearLayout) findViewById(R.id.des)).setVisibility(View.GONE);

        }else{
            ((LinearLayout) findViewById(R.id.des)).setVisibility(View.VISIBLE);

        }

        ((TextView) findViewById(R.id.description)).setText(test.getDescription().replaceAll("\\s+"," ").replaceAll("@",test.getSubTestType()));
        ((TextView) findViewById(R.id.desc)).setText(test.getHowIsTestDone().replaceAll("\\s+"," ").replaceAll("@",test.getSubTestType()));
        ((TextView) findViewById(R.id.desc1)).setText(test.getWhenToTakeTest().replaceAll("\\s+"," ").replaceAll("@",test.getSubTestType()));
        ((TextView) findViewById(R.id.desc2)).setText(test.getTestResultMean().replaceAll("\\s+"," ").replaceAll("@",test.getSubTestType()));*/
//        ((TextView) findViewById(R.id.item_name)).setText(homeService.getServiceName());
        // ((TextView) findViewById(R.id.description)).setText(aPackage.getDescription().trim().replaceAll("\\s+"," "));
        // for (int i = 0; i < package.getProviderName().size(); i++)*//*
        for (int i = 0; i < test.getList().size(); i++ )
            addProvider(i);
    }
    @Override
    public void onBackPressed() {
        TestDetail.this.finish();
    }
    private void addProvider(final int i) {
        final LinearLayout providerList = (LinearLayout) findViewById(R.id.provider_list);
        final View view = LayoutInflater.from(this).inflate(R.layout.test_view1, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        providerList.addView(view);
        final String provider_name = test.getList().get(i).getEntityHealthInstitute().getHealthInstituteName();
        ((TextView) view.findViewById(R.id.provider_name)).setText(provider_name);

//        final String cost;

        float costSize = Float.parseFloat(test.getList().get(i).getMappingInstituteToDiagnosticsSpecialization().getCharge());
        float discountSize = Float.parseFloat(test.getList().get(i).getTestInstituteSpecialization().getTestTypeDiscount());
        if (costSize > i||discountSize > i ) {
            cost = test.getList().get(i).getMappingInstituteToDiagnosticsSpecialization().getCharge();
            //  }else cost = "N/A";
            String discount;
            //
            discount = test.getList().get(i).getTestInstituteSpecialization().getTestTypeDiscount();
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

            ((TextView) view.findViewById(R.id.provider_price)).setText("₹" + Math.round(total_value));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ((TextView) view.findViewById(R.id.provider_price)).setLayoutParams(params);
        }else{
            TextView test_cost_full = (TextView)findViewById(R.id.test_cost_full);
            test_cost_full.setVisibility(View.INVISIBLE);

            TextView discount1 = (TextView) findViewById(R.id.list_item_discount);
            discount1.setVisibility(View.GONE);

            TextView p1_price = (TextView) findViewById(R.id.provider_price);
            p1_price.setText("₹" + cost);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //params.weight = 2.3f;
            p1_price.setLayoutParams(params);
        }

        ((TextView) view.findViewById(R.id.rating)).setText(test.getList().get(i).getEntityHealthInstitute().getHealthInstituteAvgRating());
        if(test.getList().get(i).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
            ((RatingBar) view.findViewById(R.id.ratingBar)).setRating(Float.parseFloat("4.00"));
        }else {
            ((RatingBar) view.findViewById(R.id.ratingBar)).setRating(Float.parseFloat(test.getList().get(i).getEntityHealthInstitute().getHealthInstituteAvgRating()));
        }
            try {
            ((TextView) view.findViewById(R.id.list_locality_name)).setText(test.getList().get(i).getMasterLocality().getLocalityName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(test.getList().get(i).getEntityHealthInstitute().getHasAppointmentBooking().equals("1")){
            ((Button) view.findViewById(R.id.provider_book_button)).setVisibility(View.VISIBLE);
        }else{
            ((Button) view.findViewById(R.id.provider_book_button)).setVisibility(View.GONE);
        }

        Log.d("payment_option",test.getList().get(i).getEntityHealthInstitute().getPaymentOption());


        //((TextView) view.findViewById(R.id.provider_price)).setOnClickListener(new View.OnClickListener() {
        ((Button) view.findViewById(R.id.provider_book_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(TestDetail.this, TestCartActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                        .putExtra("item_id", test.getList().get(i).getTestInstituteSpecialization().getSubTestId())
                        .putExtra("item_name", test.getSubTest().getSubTestType())
                        .putExtra("specialization_id", test.getList().get(i).getTestInstituteSpecialization().getDiagnosticsSpecializationId())
                        .putExtra("item_price", test.getList().get(i).getMappingInstituteToDiagnosticsSpecialization().getCharge())
                        .putExtra("discount", test.getList().get(i).getTestInstituteSpecialization().getTestTypeDiscount())
                        .putExtra("health_institute_name", test.getList().get(i).getEntityHealthInstitute().getHealthInstituteName())
                        .putExtra("health_institute_id", test.getList().get(i).getEntityHealthInstitute().getHealthInstituteId())
                        .putExtra("payment_option", test.getList().get(i).getEntityHealthInstitute().getPaymentOption())
                );

            }
        });
       /* ((RelativeLayout) view.findViewById(R.id.lay8)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String localityname = null;
                try {
                    localityname = test.getLocalityName().get(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(TestDetail.this, TestCentreActivity1.class)
                        .putExtra("item_id", test.getSubTestId())
                        .putExtra("item_name", test.getSubTestType())
                        .putExtra("health_institute_name", test.getHealthInstituteName().get(i))
                        .putExtra("item_price", test.getCharge().get(i))
                        .putExtra("discount", test.getTestTypeDiscount().get(i))
                        .putExtra("locality1", localityname)
                        .putExtra("ratingbar",test.getHealthInstituteAvgRating().get(i))
                        .putExtra("centre",test.getHealthInstituteDescrption().get(i))
                        .putExtra("geo",test.getGeo().get(i))
                        .putExtra("description",test.getDescription())
                        .putExtra("content", test.getDescription())
                        .putExtra("health_institute_id",test.getInstituteId().get(i))
                        .putExtra("package_desc",test.getHealthInstituteDescrption().get(i))
                        .putExtra("specialization_id", test.getInstituteSpecialityId().get(0))
                        .setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)));

            }
        });*//*
    */   ((TextView) view.findViewById(R.id.provider_book_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestDetail.this, TestCartActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                        .putExtra("item_id", test.getList().get(i).getTestInstituteSpecialization().getSubTestId())
                        .putExtra("item_name", test.getSubTest().getSubTestType())
                        .putExtra("specialization_id", test.getList().get(i).getTestInstituteSpecialization().getDiagnosticsSpecializationId())
                        .putExtra("item_price", test.getList().get(i).getMappingInstituteToDiagnosticsSpecialization().getCharge())
                        .putExtra("discount", test.getList().get(i).getTestInstituteSpecialization().getTestTypeDiscount())
                        .putExtra("health_institute_name", test.getList().get(i).getEntityHealthInstitute().getHealthInstituteName())
                        .putExtra("health_institute_id", test.getList().get(i).getEntityHealthInstitute().getHealthInstituteId())
                        .putExtra("payment_option", test.getList().get(i).getEntityHealthInstitute().getPaymentOption())
                );
            }
        });
       /* if (!checkPermissionForPhone())
            requestPermissionForPhone();
        ((Button) view.findViewById(R.id.call_us)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(Serverdatas.contact_number));
                if (ActivityCompat.checkSelfPermission(TestDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                TestDetail.this.startActivity(callIntent);
            }
        });
    }
    public boolean checkPermissionForPhone() {
        int result = ContextCompat.checkSelfPermission(TestDetail.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    public void requestPermissionForPhone() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) TestDetail.this, Manifest.permission.CALL_PHONE)) {
            //  Toast.makeText(HomeServiceDetails.this, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions((Activity) TestDetail.this, new String[]{Manifest.permission.CALL_PHONE}, PHONE_PERMISSION_REQUEST_CODE);
        }*/
    }

}
