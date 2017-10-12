package com.infovita.zywee.Adopters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.infovita.zywee.Activities.TestCartActivity;
import com.infovita.zywee.Activities.TestDetail;
import com.infovita.zywee.Pojo.EntityHealthInstitute1;
import com.infovita.zywee.Pojo.EntityInstituteAddress1;
import com.infovita.zywee.Pojo.List1;
import com.infovita.zywee.Pojo.MappingInstituteToDiagnosticsSpecialization1;
import com.infovita.zywee.Pojo.MasterDiagnosticsSpecialization1;
import com.infovita.zywee.Pojo.MasterLocality1;
import com.infovita.zywee.Pojo.SubTest1;
import com.infovita.zywee.Pojo.Test1;
import com.infovita.zywee.Pojo.TestInstituteSpecialization1;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

import java.util.List;

/**
 * Created by Khizarkhan on 30-09-2016.
 */

public class TestListAdapter  extends BaseAdapter {
    Context context;
    List<Test1> testList;
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

    public TestListAdapter(Context context, List<Test1> testList, int rename_flag){
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
            this.p2_name = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.test_institute_name);
            this.p2_price = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.test_cost);
            this.list_locality_name1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.list_locality_name);
            this.discount1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.list_item_discount);
            //vh.distance1 = (TextView) row.findViewById(R.id.distance1);
            this.ratingbar1 = (RatingBar) row.findViewById(R.id.p2_n).findViewById(R.id.ratingBar);
            this.test_cost_full1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.test_cost_full);
            this.p3_name = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.test_institute_name);
            this.p3_price = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.test_cost);
            this.list_locality_name2 = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.list_locality_name);
            this.discount2 = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.list_item_discount);
            //vh.distance1 = (TextView) row.findViewById(R.id.distance1);
            this.ratingbar2 = (RatingBar) row.findViewById(R.id.p3_n).findViewById(R.id.ratingBar);
            this.test_cost_full2 = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.test_cost_full);
            this.rating = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.rating);
            this.rating1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.rating);
            this.rating2 = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.rating);
            this.book = (Button) row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button);
            this.book1 = (Button) row.findViewById(R.id.p2_n).findViewById(R.id.provider_book_button);
            this.book2 = (Button) row.findViewById(R.id.p3_n).findViewById(R.id.provider_book_button);
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
        row = layoutInflater.inflate(R.layout.test_parent_views, null);
        final ViewHolder vh =new ViewHolder(row);
        final Test1 test = testList.get(position);
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
        if(test.getSubTest().getSubTestType().equals("MRI Brain") ||test.getSubTest().getSubTestType().equals("MRI Lumber Spine") || test.getSubTest().getSubTestType().equals("MRI Cervical Spine")
                || test.getSubTest().getSubTestType().equals("MRI ARM")|| test.getSubTest().getSubTestType().equals("MRI ABDOMEN")||test.getSubTest().getSubTestType().equals("MRI ABDOMEN And PELVIS")
                ||test.getSubTest().getSubTestType().equals("MRI ANGIOGRAM")|| test.getSubTest().getSubTestType().equals("MRI ANKLE")|| test.getSubTest().getSubTestType().equals("MRI ARTHROGRAM")
                ||test.getSubTest().getSubTestType().equals("MRI CHEST")||test.getSubTest().getSubTestType().equals("MRI Dorsal Spine")||test.getSubTest().getSubTestType().equals("MRI Whole Spine")
                ||test.getSubTest().getSubTestType().equals("MRI ELBOW")||test.getSubTest().getSubTestType().equals("MRI FACE")||test.getSubTest().getSubTestType().equals("MRI FEMUR")
                ||test.getSubTest().getSubTestType().equals("MRI FINGER")||test.getSubTest().getSubTestType().equals("MRI FISTULOGRAM")||test.getSubTest().getSubTestType().equals("MRI FOREARM")
                ||test.getSubTest().getSubTestType().equals("MRI HAND")||test.getSubTest().getSubTestType().equals("MRI HEEL")
                ||test.getSubTest().getSubTestType().equals("MRI HIP")||test.getSubTest().getSubTestType().equals("MRI KNEE")||test.getSubTest().getSubTestType().equals("MRI KUB")
                ||test.getSubTest().getSubTestType().equals("MRI LEG")||test.getSubTest().getSubTestType().equals("MRI MRCP")||test.getSubTest().getSubTestType().equals("MRI NECK")
                ||test.getSubTest().getSubTestType().equals("MRI ORBIT")||test.getSubTest().getSubTestType().equals("MRI PELVIS")||test.getSubTest().getSubTestType().equals("MRI SCROTUM")
                ||test.getSubTest().getSubTestType().equals("MRI SPINE")||test.getSubTest().getSubTestType().equals("MRI THIGH")||test.getSubTest().getSubTestType().equals("MRI THORAX")
                ||test.getSubTest().getSubTestType().equals("MRI THUMB")||test.getSubTest().getSubTestType().equals("MRI VENOGRAM")||test.getSubTest().getSubTestType().equals("MRI WHOLE BODY")
                ||test.getSubTest().getSubTestType().equals("MRI WHOLE SPINE")||test.getSubTest().getSubTestType().equals("MRI WRIST")||test.getSubTest().getSubTestType().equals("MRI Screening")
                ||test.getSubTest().getSubTestType().equals("MRI WRIST")||test.getSubTest().getSubTestType().equals("MRI Contrast")||test.getSubTest().getSubTestType().equals("MRI FOOT")
                ||test.getSubTest().getSubTestType().equals("MRI PNS")||test.getSubTest().getSubTestType().equals("MRI DORSAL SPINE")||test.getSubTest().getSubTestType().equals("MRI THORACIC")
                ||test.getSubTest().getSubTestType().equals("MRI JOINT")||test.getSubTest().getSubTestType().equals("MRI Joint")){
            Glide.with(context)
                    .load(Serverdatas.image_url  +  "largeicon/MRIScan.jpg")
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.zywee_logo)
                    .error(R.drawable.zywee_logo)
                    .into((ImageView) row.findViewById(R.id.image1));
        }else {
            ((ImageView) row.findViewById(R.id.image1)).setVisibility(View.GONE);
              }
       /* ((LinearLayout) row.findViewById(R.id.lay)).setVisibility(View.GONE);

        vh.showmore_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vh.package_desc_hide.getVisibility() == View.VISIBLE){
                    vh.package_desc_hide.setVisibility(View.GONE);
                    vh.showmore_desc.setText("Show more");
                }else{
                    vh.package_desc_hide.setVisibility(View.VISIBLE);
                    vh.showmore_desc.setText("Show less");
                }
            }
        });
       *//* ((TextView) row.findViewById(R.id.viewmore)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((LinearLayout) row.findViewById(R.id.lay)).getVisibility() == View.VISIBLE) {
                    ((LinearLayout) row.findViewById(R.id.lay)).setVisibility(View.GONE);
                    ((TextView) row.findViewById(R.id.viewmore)).setText("View Details");

                }else{
                    ((LinearLayout) row.findViewById(R.id.lay)).setVisibility(View.VISIBLE);
                    ((TextView) row.findViewById(R.id.viewmore)).setText("Hide Details");

                }
            }
        });*//*
        vh.list_item_name.setText(test.getSubTestType());

        if(test.getDescription().equals("")){
            ((LinearLayout) row.findViewById(R.id.des)).setVisibility(View.GONE);
            ((TextView) row.findViewById(R.id.viewmore)).setVisibility(View.GONE);
            ((View) row.findViewById(R.id.v4)).setVisibility(View.GONE);
        }else {
            ((LinearLayout) row.findViewById(R.id.des)).setVisibility(View.VISIBLE);
            ((TextView) row.findViewById(R.id.viewmore)).setVisibility(View.VISIBLE);
            ((View) row.findViewById(R.id.v4)).setVisibility(View.VISIBLE);
        }

        vh.description.setText( test.getDescription().replaceAll("\\s+"," " ).replaceAll("@",test.getSubTestType()));
        vh.description1.setText(test.getHowIsTestDone().replaceAll("\\s+"," " ).replaceAll("@",test.getSubTestType()));
        vh.description2.setText(test.getWhenToTakeTest().replaceAll("\\s+"," " ).replaceAll("@",test.getSubTestType()));
        vh.description3.setText(test.getTestResultMean().replaceAll("\\s+"," " ).replaceAll("@",test.getSubTestType()));


*/
            vh.list_item_name.setText(test.getSubTest().getSubTestType());
           // Log.d("testname :", test.getSubTest().getSubTestType() + "");

      /*  Glide.with(context)
                .load(Serverdatas.image_url + "test/" + test.gettes.get(0) + ".jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.zywee_logo)
                .error(R.drawable.zywee_logo)
                .into((ImageView) row.findViewById(R.id.image));*/
            int no_of_providers = test.getList().size();
            ;
            //Log.d("no of providers :", String.valueOf(dia.getHealthInstituteName().size()));
        if (test.getList() == null){
            vh.linear.setVisibility(View.GONE);
        }
            else if (no_of_providers > 2) {
                row.findViewById(R.id.p3_n).setVisibility(View.VISIBLE);
                vh.p1_name.setText(test.getList().get(0).getEntityHealthInstitute().getHealthInstituteName());
                vh.list_locality_name.setText(test.getList().get(0).getMasterLocality().getLocalityName());
                vh.rating.setText(test.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating());
                vh.rating1.setText(test.getList().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating());
                vh.rating2.setText(test.getList().get(2).getEntityHealthInstitute().getHealthInstituteAvgRating());
            if(test.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
                vh.ratingbar.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar.setRating(Float.parseFloat(test.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            }
                vh.p2_name.setText(test.getList().get(1).getEntityHealthInstitute().getHealthInstituteName());
            if(test.getList().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
                vh.ratingbar1.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar1.setRating(Float.parseFloat(test.getList().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            }
               // vh.ratingbar1.setRating(Float.parseFloat(test.getList().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating()));
                vh.list_locality_name1.setText(test.getList().get(1).getMasterLocality().getLocalityName());
                vh.p3_name.setText(test.getList().get(2).getEntityHealthInstitute().getHealthInstituteName());
            if(test.getList().get(2).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
                vh.ratingbar2.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar2.setRating(Float.parseFloat(test.getList().get(2).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            }
               // vh.ratingbar2.setRating(Float.parseFloat(test.getList().get(2).getEntityHealthInstitute().getHealthInstituteAvgRating()));
                try {
                    vh.list_locality_name2.setText(test.getList().get(2).getMasterLocality().getLocalityName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount() != null) {
                    //Log.d("discount_final", test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount());
                    vh.test_cost_full.setText("₹" + test.getList().get(0).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    vh.test_cost_full.setPaintFlags(vh.test_cost_full.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    // vh.test_cost_full.setVisibility(View.VISIBLE);
                    vh.test_cost_full.setVisibility(View.GONE);

                    vh.p1_price = (TextView) row.findViewById(R.id.test_cost);

                    Float total_value = Float.parseFloat(test.getList().get(0).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    Float discount = Float.parseFloat(test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount());
                    total_value = total_value - (total_value * (discount / 100));

                    vh.p1_price.setText("₹" + Math.round(total_value));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    vh.p1_price.setLayoutParams(params);

                    vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
                    vh.discount.setText(test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount() + "% off");
                    vh.discount.setVisibility(View.GONE);

                } else {
                    vh.test_cost_full = (TextView) row.findViewById(R.id.test_cost_full);
                    vh.test_cost_full.setVisibility(View.INVISIBLE);

                    vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
                    vh.discount.setVisibility(View.GONE);

                    vh.p1_price = (TextView) row.findViewById(R.id.test_cost);
                    vh.p1_price.setText("₹" + test.getList().get(0).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.weight = 2.3f;
                    vh.p1_price.setLayoutParams(params);

                }

                if (test.getList().get(1).getTestInstituteSpecialization().getTestTypeDiscount() != null) {
                    //Log.d("discount_final1", test.getList().get(1).getTestInstituteSpecialization().getTestTypeDiscount());
                    vh.test_cost_full1.setText("₹" + test.getList().get(1).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    vh.test_cost_full1.setPaintFlags(vh.test_cost_full1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    // vh.test_cost_full1.setVisibility(View.VISIBLE);
                    vh.test_cost_full1.setVisibility(View.GONE);

                    // vh.p2_price = (TextView) row.findViewById(R.id.test_cost);

                    Float total_value = Float.parseFloat(test.getList().get(1).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    Float discount = Float.parseFloat(test.getList().get(1).getTestInstituteSpecialization().getTestTypeDiscount());
                    total_value = total_value - (total_value * (discount / 100));

                    vh.p2_price.setText("₹" + Math.round(total_value));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = ;
//            params.setMargins(5, 5, 5, 5);
                    vh.p2_price.setLayoutParams(params);

//                vh.discount1 = (TextView) row.findViewById(R.id.list_item_discount);
                    vh.discount1.setText(test.getList().get(1).getTestInstituteSpecialization().getTestTypeDiscount() + "% off");
                    vh.discount1.setVisibility(View.GONE);

                } else {
                    // vh.test_cost_full1 = (TextView) row.findViewById(R.id.test_cost_full);
                    vh.test_cost_full1.setVisibility(View.INVISIBLE);

                    // vh.discount1 = (TextView) row.findViewById(R.id.list_item_discount);
                    vh.discount1.setVisibility(View.GONE);

                    // vh.p2_price = (TextView) row.findViewById(R.id.test_cost);
                    vh.p2_price.setText("₹" + test.getList().get(1).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.weight = 2.3f;
                    vh.p2_price.setLayoutParams(params);

                }

                if (test.getList().get(2).getTestInstituteSpecialization().getTestTypeDiscount() != null) {
                    //Log.d("discount_final", test.getList().get(2).getTestInstituteSpecialization().getTestTypeDiscount());
                    vh.test_cost_full2.setText("₹" + test.getList().get(2).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    vh.test_cost_full2.setPaintFlags(vh.test_cost_full2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    //vh.test_cost_full2.setVisibility(View.VISIBLE);
                    vh.test_cost_full2.setVisibility(View.GONE);

                    //  vh.p3_price = (TextView) row.findViewById(R.id.test_cost);

                    Float total_value = Float.parseFloat(test.getList().get(2).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    Float discount = Float.parseFloat(test.getList().get(2).getTestInstituteSpecialization().getTestTypeDiscount());
                    total_value = total_value - (total_value * (discount / 100));

                    vh.p3_price.setText("₹" + Math.round(total_value));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = ;
//            params.setMargins(5, 5, 5, 5);
                    vh.p3_price.setLayoutParams(params);

                    //  vh.discount2 = (TextView) row.findViewById(R.id.list_item_discount);
                    vh.discount2.setText(test.getList().get(2).getTestInstituteSpecialization().getTestTypeDiscount() + "% off");
                    vh.discount2.setVisibility(View.GONE);

                } else {
                    // vh.test_cost_full2 = (TextView) row.findViewById(R.id.test_cost_full);
                    vh.test_cost_full2.setVisibility(View.INVISIBLE);

                    //   vh.discount2 = (TextView) row.findViewById(R.id.list_item_discount);
                    vh.discount2.setVisibility(View.GONE);

                    //  vh.p3_price = (TextView) row.findViewById(R.id.test_cost);
                    vh.p3_price.setText("₹" + test.getList().get(2).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.weight = 2.3f;
                    vh.p3_price.setLayoutParams(params);

                }
            if(test.getList().get(0).getEntityHealthInstitute().getHasAppointmentBooking().equals("1")){
                row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button).setVisibility(View.VISIBLE);
            }else{
                row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button).setVisibility(View.GONE);
            }
            if(test.getList().get(1).getEntityHealthInstitute().getHasAppointmentBooking().equals("1")){
                row.findViewById(R.id.p2_n).findViewById(R.id.provider_book_button).setVisibility(View.VISIBLE);
            }else{
                row.findViewById(R.id.p2_n).findViewById(R.id.provider_book_button).setVisibility(View.GONE);
            }
            if(test.getList().get(2).getEntityHealthInstitute().getHasAppointmentBooking().equals("1")){
                row.findViewById(R.id.p3_n).findViewById(R.id.provider_book_button).setVisibility(View.VISIBLE);
            }else{
                row.findViewById(R.id.p3_n).findViewById(R.id.provider_book_button).setVisibility(View.GONE);
            }

            } else if (no_of_providers > 1) {
                row.findViewById(R.id.p3_n).setVisibility(View.GONE);
                vh.p1_name.setText(test.getList().get(0).getEntityHealthInstitute().getHealthInstituteName());
//            vh.p1_price.setText("₹" + test.getCharge().get(0) + "/-");
//            vh.test_cost_full.setText(test.getTestTypeDiscount().get(0));
                vh.list_locality_name.setText(test.getList().get(0).getMasterLocality().getLocalityName());
                // vh.distance.setText(sd_local.getDistance());
            if(test.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
                vh.ratingbar.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar.setRating(Float.parseFloat(test.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            }
                //vh.ratingbar.setRating(Float.parseFloat(test.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating()));
                vh.p2_name.setText(test.getList().get(1).getEntityHealthInstitute().getHealthInstituteName());
                // vh.p2_price.setText("₹" + test.getCharge().get(1) + "/-");
            if(test.getList().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
                vh.ratingbar1.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar1.setRating(Float.parseFloat(test.getList().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            }
                //vh.ratingbar1.setRating(Float.parseFloat(test.getList().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating()));

                try {
                    vh.list_locality_name1.setText(test.getList().get(1).getMasterLocality().getLocalityName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //  vh.test_cost_full1.setText(test.getTestTypeDiscount().get(1));
                if (test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount() != null) {
                    //Log.d("discount_final", test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount());
                    vh.test_cost_full.setText("₹" + test.getList().get(0).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    vh.test_cost_full.setPaintFlags(vh.test_cost_full.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    //vh.test_cost_full.setVisibility(View.VISIBLE);
                    vh.test_cost_full.setVisibility(View.GONE);

                    // vh.p1_price = (TextView) row.findViewById(R.id.test_cost);

                    Float total_value = Float.parseFloat(test.getList().get(0).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    Float discount = Float.parseFloat(test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount());
                    total_value = total_value - (total_value * (discount / 100));

                    vh.p1_price.setText("₹" + Math.round(total_value));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = ;
//            params.setMargins(5, 5, 5, 5);
                    vh.p1_price.setLayoutParams(params);

                    //   vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
                    vh.discount.setText(test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount() + "% off");
                    vh.discount.setVisibility(View.GONE);

                } else {
                    //  vh.test_cost_full = (TextView) row.findViewById(R.id.test_cost_full);
                    vh.test_cost_full.setVisibility(View.INVISIBLE);

                    //  vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
                    vh.discount.setVisibility(View.GONE);

                    //  vh.p1_price = (TextView) row.findViewById(R.id.test_cost);
                    vh.p1_price.setText("₹" + test.getList().get(0).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.weight = 2.3f;
                    vh.p1_price.setLayoutParams(params);

                }

                if (test.getList().get(1).getTestInstituteSpecialization().getTestTypeDiscount() != null) {
                    //Log.d("discount_final", test.getList().get(1).getTestInstituteSpecialization().getTestTypeDiscount());
                    vh.test_cost_full1.setText("₹" + test.getList().get(1).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    vh.test_cost_full1.setPaintFlags(vh.test_cost_full1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    //vh.test_cost_full1.setVisibility(View.VISIBLE);
                    vh.test_cost_full1.setVisibility(View.GONE);

                    // vh.p2_price = (TextView) row.findViewById(R.id.test_cost);

                    Float total_value = Float.parseFloat(test.getList().get(1).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    Float discount = Float.parseFloat(test.getList().get(1).getTestInstituteSpecialization().getTestTypeDiscount());
                    total_value = total_value - (total_value * (discount / 100));

                    vh.p2_price.setText("₹" + Math.round(total_value));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = ;
//            params.setMargins(5, 5, 5, 5);
                    vh.p2_price.setLayoutParams(params);

                    //   vh.discount1 = (TextView) row.findViewById(R.id.list_item_discount);
                    vh.discount1.setText(test.getList().get(1).getTestInstituteSpecialization().getTestTypeDiscount() + "% off");
                    vh.discount1.setVisibility(View.GONE);

                } else {
                    //   vh.test_cost_full1 = (TextView) row.findViewById(R.id.test_cost_full);
                    vh.test_cost_full1.setVisibility(View.INVISIBLE);

                    //   vh.discount1 = (TextView) row.findViewById(R.id.list_item_discount);
                    vh.discount1.setVisibility(View.GONE);

                    //  vh.p2_price = (TextView) row.findViewById(R.id.test_cost);
                    vh.p2_price.setText("₹" + test.getList().get(1).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.weight = 2.3f;
                    vh.p2_price.setLayoutParams(params);

                }

            if(test.getList().get(0).getEntityHealthInstitute().getHasAppointmentBooking().equals("1")){
                row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button).setVisibility(View.VISIBLE);
            }else{
                row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button).setVisibility(View.GONE);
            }
            if(test.getList().get(1).getEntityHealthInstitute().getHasAppointmentBooking().equals("1")){
                row.findViewById(R.id.p2_n).findViewById(R.id.provider_book_button).setVisibility(View.VISIBLE);
            }else{
                row.findViewById(R.id.p2_n).findViewById(R.id.provider_book_button).setVisibility(View.GONE);
            }

            } else if (no_of_providers == 1) {
                row.findViewById(R.id.p3_n).setVisibility(View.GONE);
                row.findViewById(R.id.p2_n).setVisibility(View.GONE);
                vh.p1_name.setText(test.getList().get(0).getEntityHealthInstitute().getHealthInstituteName());
                //  vh.p1_price.setText("₹" +test.getCharge().get(0) + "/-");
            if(test.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
                vh.ratingbar.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar.setRating(Float.parseFloat(test.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            }
                //vh.ratingbar.setRating(Float.parseFloat(test.getList().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating()));
                // vh.list_locality_name.setText(sd_local.getList_locality_name());
                // vh.distance.setText(sd_local.getDistance());
                if (test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount() != null) {
                    //Log.d("discount_final", test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount());
                    vh.test_cost_full.setText("₹" + test.getList().get(0).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    vh.test_cost_full.setPaintFlags(vh.test_cost_full.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    //vh.test_cost_full.setVisibility(View.VISIBLE);
                    vh.test_cost_full.setVisibility(View.GONE);

                    //  vh.p1_price = (TextView) row.findViewById(R.id.test_cost);

                    Float total_value = Float.parseFloat(test.getList().get(0).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    Float discount = Float.parseFloat(test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount());
                    total_value = total_value - (total_value * (discount / 100));

                    vh.p1_price.setText("₹" + Math.round(total_value));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = ;
//            params.setMargins(5, 5, 5, 5);
                    vh.p1_price.setLayoutParams(params);

                    //  vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
                    vh.discount.setText(test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount() + "% off");
                    vh.discount.setVisibility(View.GONE);

                } else {
                    // vh.test_cost_full = (TextView) row.findViewById(R.id.test_cost_full);
                    vh.test_cost_full.setVisibility(View.INVISIBLE);

                    //  vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
                    vh.discount.setVisibility(View.GONE);

                    //  vh.p1_price = (TextView) row.findViewById(R.id.test_cost);
                    vh.p1_price.setText("₹" + test.getList().get(0).getMappingInstituteToDiagnosticsSpecialization().getCharge());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.weight = 2.3f;
                    vh.p1_price.setLayoutParams(params);

                }
            if(test.getList().get(0).getEntityHealthInstitute().getHasAppointmentBooking().equals("1")){
                row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button).setVisibility(View.VISIBLE);
            }else{
                row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button).setVisibility(View.GONE);
            }

            }else{
            row.findViewById(R.id.p3_n).setVisibility(View.GONE);
            row.findViewById(R.id.p2_n).setVisibility(View.GONE);
            row.findViewById(R.id.p1_n).setVisibility(View.GONE);
            row.findViewById(R.id.desc1).setVisibility(View.VISIBLE);
            row.findViewById(R.id.more_details).setVisibility(View.GONE);
        }
        /*vh. relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TestCentreActivity1.class)
                        .putExtra("item_id", test.getSubTestId())
                        .putExtra("item_name", test.getSubTestType())
                        .putExtra("health_institute_name", test.getHealthInstituteName().get(0))
                        .putExtra("item_price", test.getCharge().get(0))
                        .putExtra("discount", test.getTestTypeDiscount().get(0))
                        .putExtra("locality1", test.getLocalityName().get(0))
                        .putExtra("ratingbar",test.getHealthInstituteAvgRating().get(0))
                        .putExtra("centre",test.getHealthInstituteDescrption().get(0))
                        .putExtra("geo",test.getGeo().get(0))
                        .putExtra("description",test.getDescription())
                        .putExtra("content", test.getDescription())
                        .putExtra("health_institute_id",test.getInstituteId().get(0))
                        .putExtra("package_desc",test.getHealthInstituteDescrption().get(0))
                        .putExtra("specialization_id", test.getInstituteSpecialityId().get(0))
                        .setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)));

                //  .putExtra("homeService", homeService));
            }
        });
       vh.relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String localityname = null;
                try {
                    localityname = test.getLocalityName().get(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                context.startActivity(new Intent(context, TestCentreActivity1.class)
                        .putExtra("item_id", test.getSubTestId())
                        .putExtra("item_name", test.getSubTestType())
                        .putExtra("health_institute_name", test.getHealthInstituteName().get(1))
                        .putExtra("item_price", test.getCharge().get(1))
                        .putExtra("discount", test.getTestTypeDiscount().get(1))
                        .putExtra("locality1", localityname)
                        .putExtra("ratingbar", test.getHealthInstituteAvgRating().get(1))
                        .putExtra("centre", test.getHealthInstituteDescrption().get(1))
                        .putExtra("geo", test.getGeo().get(1))
                        .putExtra("description", test.getDescription())
                        .putExtra("content", test.getDescription())
                        .putExtra("health_institute_id", test.getInstituteId().get(1))
                        .putExtra("package_desc", test.getHealthInstituteDescrption().get(1))
                        .putExtra("specialization_id", test.getInstituteSpecialityId().get(0))
                        .setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)));

            }
        });

        vh.relative2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TestCentreActivity1.class)
                        .putExtra("item_id", test.getSubTestId())
                        .putExtra("item_name", test.getSubTestType())
                        .putExtra("health_institute_name", test.getHealthInstituteName().get(2))
                        .putExtra("item_price", test.getCharge().get(2))
                        .putExtra("discount", test.getTestTypeDiscount().get(2))
                        .putExtra("locality1", test.getLocalityName().get(2))
                        .putExtra("ratingbar",test.getHealthInstituteAvgRating().get(2))
                        .putExtra("centre",test.getHealthInstituteDescrption().get(2))
                        .putExtra("geo",test.getGeo().get(2))
                        .putExtra("description",test.getDescription())
                        .putExtra("content", test.getDescription())
                        .putExtra("health_institute_id",test.getInstituteId().get(2))
                        .putExtra("package_desc",test.getHealthInstituteDescrption().get(2))
                        .putExtra("specialization_id", test.getInstituteSpecialityId().get(0))
                        .setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)));

            }
        });
*/


            row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button).setOnClickListener(new View.OnClickListener() {
                //row.findViewById(R.id.p1_n).findViewById(R.id.test_cost).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, TestCartActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                            .putExtra("item_id", test.getList().get(0).getTestInstituteSpecialization().getSubTestId())
                            .putExtra("item_name", test.getSubTest().getSubTestType())
                            .putExtra("specialization_id", test.getList().get(0).getTestInstituteSpecialization().getDiagnosticsSpecializationId())
                            .putExtra("item_price", test.getList().get(0).getMappingInstituteToDiagnosticsSpecialization().getCharge())
                            .putExtra("discount", test.getList().get(0).getTestInstituteSpecialization().getTestTypeDiscount())
                            .putExtra("health_institute_name", test.getList().get(0).getEntityHealthInstitute().getHealthInstituteName())
                            .putExtra("health_institute_id", test.getList().get(0).getEntityHealthInstitute().getHealthInstituteId())
                            .putExtra("payment_option",test.getList().get(0).getEntityHealthInstitute().getPaymentOption())
                    );
                    Log.d("ID",test.getList().get(0).getEntityHealthInstitute().getHealthInstituteId());

                }
            });


            row.findViewById(R.id.p2_n).findViewById(R.id.provider_book_button).setOnClickListener(new View.OnClickListener() {
                //row.findViewById(R.id.p2_n).findViewById(R.id.test_cost).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, TestCartActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                            .putExtra("item_id", test.getList().get(1).getTestInstituteSpecialization().getSubTestId())
                            .putExtra("item_name", test.getSubTest().getSubTestType())
                            .putExtra("specialization_id", test.getList().get(1).getTestInstituteSpecialization().getDiagnosticsSpecializationId())
                            .putExtra("item_price", test.getList().get(1).getMappingInstituteToDiagnosticsSpecialization().getCharge())
                            .putExtra("discount", test.getList().get(1).getTestInstituteSpecialization().getTestTypeDiscount())
                            .putExtra("health_institute_name", test.getList().get(1).getEntityHealthInstitute().getHealthInstituteName())
                            .putExtra("health_institute_id", test.getList().get(1).getEntityHealthInstitute().getHealthInstituteId())
                            .putExtra("payment_option",test.getList().get(1).getEntityHealthInstitute().getPaymentOption())
                    );
                }
            });


            row.findViewById(R.id.p3_n).findViewById(R.id.provider_book_button).setOnClickListener(new View.OnClickListener() {
                ;        //row.findViewById(R.id.p3_n).findViewById(R.id.test_cost).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, TestCartActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                            .putExtra("item_id", test.getList().get(2).getTestInstituteSpecialization().getSubTestId())
                            .putExtra("item_name", test.getSubTest().getSubTestType())
                            .putExtra("specialization_id", test.getList().get(2).getTestInstituteSpecialization().getDiagnosticsSpecializationId())
                            .putExtra("item_price", test.getList().get(2).getMappingInstituteToDiagnosticsSpecialization().getCharge())
                            .putExtra("discount", test.getList().get(2).getTestInstituteSpecialization().getTestTypeDiscount())
                            .putExtra("health_institute_name", test.getList().get(2).getEntityHealthInstitute().getHealthInstituteName())
                            .putExtra("health_institute_id", test.getList().get(2).getEntityHealthInstitute().getHealthInstituteId())
                            .putExtra("payment_option",test.getList().get(2).getEntityHealthInstitute().getPaymentOption())
                    );
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


            if (!checkPermissionForPhone())
                requestPermissionForPhone();

            row.findViewById(R.id.p2_n).findViewById(R.id.call_us).setOnClickListener(new View.OnClickListener() {
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

            if (!checkPermissionForPhone())
                requestPermissionForPhone();

            row.findViewById(R.id.p3_n).findViewById(R.id.call_us).setOnClickListener(new View.OnClickListener() {
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
            row.findViewById(R.id.more_details).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, TestDetail.class)
                            .putExtra("test", test));
                }
            });



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
