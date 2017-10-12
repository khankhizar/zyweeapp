package com.infovita.zywee.Adopters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infovita.zywee.Activities.TestCartActivity;
import com.infovita.zywee.Pojo.EntityHealthInstitute1;
import com.infovita.zywee.Pojo.EntityInstituteAddress1;
import com.infovita.zywee.Pojo.List1;
import com.infovita.zywee.Pojo.MappingInstituteToDiagnosticsSpecialization1;
import com.infovita.zywee.Pojo.MasterDiagnosticsSpecialization1;
import com.infovita.zywee.Pojo.MasterLocality1;
import com.infovita.zywee.Pojo.MoreTest;
import com.infovita.zywee.Pojo.SubTest1;
import com.infovita.zywee.Pojo.TestInstituteSpecialization1;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

import java.util.List;

/**
 * Created by Khizarkhan on 19-07-2017.
 */

public class MoreTestListAdapter extends BaseAdapter {

    Context context;
    List<MoreTest> testList;
    List<SubTest1> subList;
    List<MasterDiagnosticsSpecialization1> diag;
    List<EntityHealthInstitute1> name;
    List<EntityInstituteAddress1> add;
    List<List1> list;
    List<MappingInstituteToDiagnosticsSpecialization1> map;
    List<MasterLocality1> locality;
    List<TestInstituteSpecialization1> sp;
    View row;
    int rename_flag;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    public MoreTestListAdapter(Context context, List<MoreTest> testList, int rename_flag){
        try {
            this.context = context;
            this.testList = testList;
            this.rename_flag = rename_flag;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getCount() {
        return testList.size();
    }

    @Override
    public Object getItem(int position) {
        return testList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public class ViewHolder {
        RelativeLayout relative,relative1,relative2;
        TextView list_item_name, p3_name, p3_price, list_locality_name2, discount2, test_cost_full2, description, description1, description2,
                description3,showmore_desc;
        LinearLayout package_desc_hide;
        TextView p1_name,rating,rating1,rating2;
        TextView p2_name;
        TextView p1_price;
        TextView p2_price;
        TextView list_locality_name;
        TextView discount;
        TextView distance;
        RatingBar ratingbar, ratingbar2;
        TextView test_cost_full;
        TextView list_locality_name1;
        TextView distance1;
        RatingBar ratingbar1;
        TextView test_cost_full1;
        TextView discount1;
        Button book,book1,book2;
        LinearLayout linear;


        public ViewHolder(View row) {

            this.list_item_name = (TextView) row.findViewById(R.id.list_item_name);
            this.p1_name = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.test_institute_name);
            this.test_cost_full = (TextView) row.findViewById(R.id.test_cost_full);
            this.p1_price = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.test_cost);
            this.discount = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.list_item_discount);
            this.list_locality_name = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.list_locality_name);
            // vh.distance = (TextView) row.findViewById(R.id.distance);
            this.ratingbar = (RatingBar) row.findViewById(R.id.p1_n).findViewById(R.id.ratingBar);
            this.rating = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.rating);
            this.book = (Button) row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button);
           /* this.description = (TextView) row.findViewById(R.id.description);
            this.description1 = (TextView) row.findViewById(R.id.desc);
            this.description2 = (TextView) row.findViewById(R.id.desc1);
            this.description3 = (TextView) row.findViewById(R.id.desc3);

            this.showmore_desc = (TextView) row.findViewById(R.id.viewmore);
            this.package_desc_hide = (LinearLayout) row.findViewById(R.id.lay);*/

           /* this.relative = (RelativeLayout) row.findViewById(R.id.p1_n).findViewById(R.id.lay7);
            this.relative1 = (RelativeLayout) row.findViewById(R.id.p2_n).findViewById(R.id.lay7);
            this.relative2 = (RelativeLayout) row.findViewById(R.id.p3_n).findViewById(R.id.lay7);*/

            this.linear = (LinearLayout) row.findViewById(R.id.li);


        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.moretest_views, null);
        final MoreTestListAdapter.ViewHolder vh =new MoreTestListAdapter.ViewHolder(row);
        final MoreTest test = testList.get(position);
       /* final SubTest1 sub = subList.get(position);
        final MasterDiagnosticsSpecialization1 dia = diag.get(position);
        final EntityInstituteAddress1 ad = add.get(position);
        final EntityHealthInstitute1 in = name.get(position);
        final List1 li = list.get(position);
        final MappingInstituteToDiagnosticsSpecialization1 mapp = map.get(position);
        final MasterLocality1 lo = locality.get(position);
        final TestInstituteSpecialization1 spe = sp.get(position);*/
        // logViewedContentEvent();
        // logger.logEvent(test.getId());
        //Log.d("log : ", vh + "");
        /*if(test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI Brain") ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI Lumber Spine") || test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI Cervical Spine")
                || test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI ARM")|| test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI ABDOMEN")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI ABDOMEN And PELVIS")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI ANGIOGRAM")|| test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI ANKLE")|| test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI ARTHROGRAM")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI CHEST")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI Dorsal Spine")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI Whole Spine")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI ELBOW")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI FACE")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI FEMUR")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI FINGER")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI FISTULOGRAM")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI FOREARM")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI HAND")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI HEEL")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI HIP")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI KNEE")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI KUB")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI LEG")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI MRCP")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI NECK")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI ORBIT")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI PELVIS")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI SCROTUM")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI SPINE")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI THIGH")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI THORAX")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI THUMB")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI VENOGRAM")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI WHOLE BODY")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI WHOLE SPINE")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI WRIST")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI Screening")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI WRIST")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI Contrast")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI FOOT")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI PNS")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI DORSAL SPINE")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI THORACIC")
                ||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI JOINT")||test.getMasterDiagnosticsSpecialization().getSpecializationName().equals("MRI Joint")){
            Glide.with(context)
                    .load(Serverdatas.image_url  +  "largeicon/MRIScan.jpg")
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.zywee_logo)
                    .error(R.drawable.zywee_logo)
                    .into((ImageView) row.findViewById(R.id.image1));
        }else {
            ((ImageView) row.findViewById(R.id.image1)).setVisibility(View.GONE);
        }*/

        vh.list_item_name.setText(test.getMasterDiagnosticsSpecialization().getSpecializationName());
        // Log.d("testname :", test.getSubTest().getSubTestType() + "");

        vh.p1_name.setText(test.getEntityHealthInstitute().getHealthInstituteName());
        //vh.list_locality_name.setText(test.getList().get(0).getMasterLocality().getLocalityName());
        vh.rating.setText(test.getEntityHealthInstitute().getHealthInstituteAvgRating());
        if(test.getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
            vh.ratingbar.setRating(Float.parseFloat("4.00"));
        }else{
            vh.ratingbar.setRating(Float.parseFloat(test.getEntityHealthInstitute().getHealthInstituteAvgRating()));
        }

        vh.test_cost_full.setVisibility(View.INVISIBLE);

        //  vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
        vh.discount.setVisibility(View.GONE);

        //  vh.p1_price = (TextView) row.findViewById(R.id.test_cost);
        vh.p1_price.setText("₹" + test.getDiscountPrice());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //params.weight = 2.3f;
        vh.p1_price.setLayoutParams(params);
        //vh.p1_price.setText(test.getDiscountPrice());

      /*  Glide.with(context)
                .load(Serverdatas.image_url + "test/" + test.gettes.get(0) + ".jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.zywee_logo)
                .error(R.drawable.zywee_logo)
                .into((ImageView) row.findViewById(R.id.image));*/

            if(test.getEntityHealthInstitute().getHasAppointmentBooking().equals("1")){
                row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button).setVisibility(View.VISIBLE);
            }else{
                row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button).setVisibility(View.GONE);
            }
        row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button).setOnClickListener(new View.OnClickListener() {
            //row.findViewById(R.id.p1_n).findViewById(R.id.test_cost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TestCartActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                        .putExtra("item_id", test.getTestInstituteSpecialization().getSubTestId())
                        .putExtra("item_name", test.getMasterDiagnosticsSpecialization().getSpecializationName())
                        .putExtra("specialization_id", test.getMappingInstituteToDiagnosticsSpecialization().getSpecializationId())
                        .putExtra("item_price", test.getMappingInstituteToDiagnosticsSpecialization().getDiscountPrice())
                        .putExtra("discount", "0")
                        .putExtra("health_institute_name", test.getEntityHealthInstitute().getHealthInstituteName())
                        .putExtra("health_institute_id", test.getEntityHealthInstitute().getHealthInstituteId())
                        .putExtra("payment_option",test.getEntityHealthInstitute().getPaymentOption())

                );

                Log.d("ID", test.getEntityHealthInstitute().getHealthInstituteId());

            }
        });




        if (!checkPermissionForPhone())
            requestPermissionForPhone();

        row.findViewById(R.id.p1_n).findViewById(R.id.call_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(Serverdatas.contact_number));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);
            }

        });


            /*row.findViewById(R.id.list_item_name).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, TestDetail.class)
                            .putExtra("test", test));
                }
            });
*/
      /*  row.findViewById(R.id.more_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TestDetail.class)
                        .putExtra("test", test));
            }
        });*/



        return row;

    }


    public boolean checkPermissionForPhone(){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForPhone(){
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CALL_PHONE)){
            // Toast.makeText(context, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},PHONE_PERMISSION_REQUEST_CODE);
        }
    }
}
