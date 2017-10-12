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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.infovita.zywee.Activities.DoctorCartActivity;
import com.infovita.zywee.Pojo.Doctor;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Supports.CircleTransform;

import java.util.List;

/**
 * Created by madroid on 29-12-2015.
 */
public class CommonListAdapter extends BaseAdapter {
    public Context ctx;
    List<Doctor> doctorlist;
    View row;
    int rename_flag;

    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;


    public CommonListAdapter(Context ctx, List<Doctor> test_list, int rename_flag) {
        Log.d("Response", test_list.toString());
        this.ctx = ctx;
        this.doctorlist = test_list;
        this.rename_flag = rename_flag;
    }

    @Override
    public int getCount() {
        return doctorlist.size();
    }

    @Override
    public Object getItem(int i) {
        Log.d("Response_sssssss", doctorlist.get(i) + "");
        return doctorlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Doctor doctor = doctorlist.get(i);
        viewholder vh = new viewholder();
        row = view;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            row = layoutInflater.inflate(R.layout.test_parent_view, null);
            row = layoutInflater.inflate(R.layout.doctor_layout, null);
            row.setTag(vh);
        } else {
            vh = (viewholder) row.getTag();
        }
        vh.specialization_name = (TextView) row.findViewById(R.id.list_item_name);
        vh.specialization_name.setText(doctor.getDoctorName());
        /*vh.distance = (TextView) row.findViewById(R.id.distance);
        vh.distance.setText(sdview.getDistance().substring(0, 3) + " km");*/
        vh.time = (TextView) row.findViewById(R.id.time);
        vh.ratingBar = (RatingBar) row.findViewById(R.id.ratingBar);
        if(doctor.getHealthInstituteAvgRating().equals("0.00")){
            vh.ratingBar.setRating(Float.parseFloat("4.00"));
        }else{
            vh.ratingBar.setRating(Float.parseFloat(doctor.getHealthInstituteAvgRating()));

        }

        vh.specialization = (TextView) row.findViewById(R.id.specialization);
        vh.specialization.setText(doctor.getDoctorSpecialty());
        vh.qualification = (TextView) row.findViewById(R.id.qualification);
      //  vh.qualification.setText(sdview.getDoctorQualification());
        if(doctor.getDoctorQualification().isEmpty()||doctor.getDoctorQualification().equals("0")){
            vh.qualification.setText("Not Available");
        }else {
            vh.qualification.setText(doctor.getDoctorQualification());
        }
        vh.qualification1 = (TextView) row.findViewById(R.id.qualify);
        if(doctor.getDoctorQualification().isEmpty()||doctor.getDoctorQualification().equals("0")){
            vh.qualification1.setText("Not Available");
        }else {
            vh.qualification1.setText(doctor.getDoctorQualification());
        }
        vh.experience = (TextView) row.findViewById(R.id.experience);
        if(doctor.getDoctorExperienceYears().isEmpty()||doctor.getDoctorExperienceYears().equals("0")||doctor.getDoctorExperienceYears().equals("00")){
            vh.experience.setText("Not Available");
        }else {
            vh.experience.setText(doctor.getDoctorExperienceYears());
        }
        vh.text = (TextView) row.findViewById(R.id.text);
        String detail = doctor.getNewSpecializationName();
        vh.text.setText( detail + " is available in the following center");
       // Log.d("institute id :",doctor.getHealthInstituteId()+"");
        //Log.d("id :",doctor.getId()+"");

       // Log.d("link :",Serverdatas.image_url_base + "doctor/" + doctor.getHealthInstituteId() + "/" + doctor.getId() + ".jpg");
        Glide.with(ctx)
                .load(Serverdatas.image_url_base + "doctor/" + doctor.getHealthInstituteId() + "/" + doctor.getId() + ".jpg")
                //.fitCenter()
                .centerCrop()
                /*.override(200,200)*/
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new CircleTransform(ctx))
                .placeholder(R.drawable.doctor_roundimage)
                .error(R.drawable.doctor_roundimage)
//                .into((ImageView) row.findViewById(R.id.image));
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                      //  Log.e("info_img",e+"");
                        // do something
                      /*  Log.e("info_img",e+"");
                        Log.e("link :",Serverdatas.image_url_base + "doctor/" + sdview.getHealth_institute_id() + "/" + sdview.getList_item_id() + ".jpg");
                        Glide.with(ctx)
                                .load(Serverdatas.image_url_base + "doctor/" + sdview.getHealth_institute_id() + "/" + sdview.getList_item_id() + ".jpg")
                                //.fitCenter()
                                .centerCrop()
//                                .override(200,200)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.doctor_roundimage)
                                .into((ImageView) row.findViewById(R.id.image));*/
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // do something
                        return false;
                    }
                })
                .into((ImageView) row.findViewById(R.id.image));

        if(doctor.getHasAppointmentBooking().equals("0")){
            ((Button) row.findViewById(R.id.test_book_button)).setVisibility(View.GONE);
            ((Button) row.findViewById(R.id.more_details)).setVisibility(View.GONE);
        }else{
            ((Button) row.findViewById(R.id.test_book_button)).setVisibility(View.VISIBLE);
            ((Button) row.findViewById(R.id.more_details)).setVisibility(View.VISIBLE);
        }

       // if (doctor.getList_item_discount() != null && Integer.parseInt(sdview.getList_item_discount()) != 0) {
           // Log.d("discount_final", sdview.getList_item_discount());
           /* vh.charge_full = (TextView) row.findViewById(R.id.test_cost_full);
            if (doctor.getHealthInstituteConsultFee().equals("0")|| doctor.getHealthInstituteConsultFee().isEmpty()|| doctor.getHealthInstituteConsultFee().equals("")){
                vh.charge_full.setText("Not Available");
            }else {
                vh.charge_full.setText("₹" + doctor.getHealthInstituteConsultFee());
            }
           // vh.charge_full.setText("₹" + sdview.getDoctorFee());
            vh.charge_full.setPaintFlags(vh.charge_full.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            vh.charge_full.setVisibility(View.GONE);

            vh.charge = (TextView) row.findViewById(R.id.test_cost);

            Float total_value = Float.parseFloat(doctor.getHealthInstituteConsultFee());
           // Float discount = Float.parseFloat(sdview.getList_item_discount());
           // total_value = total_value - (total_value * (discount / 100));

            vh.charge.setText("₹" + Math.round(total_value));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = ;
//            params.setMargins(5, 5, 5, 5);
            vh.charge.setLayoutParams(params);*/

           /* vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
            vh.discount.setText(sdview.getList_item_discount() + "% off");
            vh.discount.setVisibility(View.GONE);
*/
       /* } else {*/
            vh.charge_full = (TextView) row.findViewById(R.id.test_cost_full);
            vh.charge_full.setVisibility(View.INVISIBLE);

            vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
            vh.discount.setVisibility(View.GONE);

            vh.charge = (TextView) row.findViewById(R.id.test_cost);
           // Float total_value = Float.parseFloat(doctor.getHealthInstituteConsultFee());
            if (doctor.getHealthInstituteConsultFee().equals("0")|| doctor.getHealthInstituteConsultFee().isEmpty()|| doctor.getHealthInstituteConsultFee().equals("null")){
                vh.charge.setText("Not Available");
            }else {
                Float total_value = Float.parseFloat(doctor.getHealthInstituteConsultFee());
                vh.charge.setText("₹" + Math.round(total_value));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //params.weight = 2.3f;
            vh.charge.setLayoutParams(params);

       // }


        vh.health_institute_name = (TextView) row.findViewById(R.id.test_institute_name);
        vh.health_institute_name.setText(doctor.getHealthInstituteName());

        // vh.city_name = (TextView) row.findViewById(R.id.city_name);
        //vh.city_name.setText(sdview.getCity_name());

        vh.locality_name = (TextView) row.findViewById(R.id.list_locality_name);
        vh.locality_name.setText(doctor.getLocality());


        if (!checkPermissionForPhone())
            requestPermissionForPhone();
        Button call_us = (Button) row.findViewById(R.id.more_details);
        call_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(Serverdatas.contact_number));
                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                ctx.startActivity(callIntent);
            }
        });
       /* ((TextView) row.findViewById(R.id.list_item_name)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ctx, ItemDetailActivity.class);
                intent.putExtra("item_name", doctor.getNewSpecializationName());
                intent.putExtra("specialization_id", doctor.getSpecializationId());
                intent.putExtra("item_price", doctor.getDoctorFee());
                intent.putExtra("health_institute_name", doctor.getHealthInstituteName());
                intent.putExtra("city_name", doctor.getCityName());
                intent.putExtra("locality_name", doctor.getLocality());
               // intent.putExtra("item_description", sdview.getList_item_details());
                intent.putExtra("item_id", doctor.getId());
               // intent.putExtra("discount", sdview.getList_item_discount());
               // intent.putExtra("distance", sdview.getDistance());
                intent.putExtra("health_institute_avg_rating", doctor.getHealthInstituteAvgRating());
                intent.putExtra("health_institute_id", doctor.getHealthInstituteId());
                intent.putExtra("list_type", doctor.getMainInstituteType());
                //Log.d("adapter geo "," > " + sdview.getGeo());
               // intent.putExtra("geo", sdview.getGeo());
                intent.putExtra("qualification",doctor.getDoctorQualification());
                intent.putExtra("experience",doctor.getDoctorExperienceYears());
                intent.putExtra("specialization",doctor.getDoctorSpecialty());
                intent.putExtra("enable", doctor.getHasAppointmentBooking());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                ctx.startActivity(intent);
            }
        });*/

        Button book_button_list = (Button) row.findViewById(R.id.test_book_button);
        book_button_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String type = doctor.getMainInstituteType().trim();
                Intent intent = new Intent(ctx, DoctorCartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("item_id", doctor.getId());
                intent.putExtra("item_name", doctor.getDoctorName());
                intent.putExtra("item_price", doctor.getHealthInstituteConsultFee());
                // intent.putExtra("discount", sdview.getList_item_discount());
                intent.putExtra("health_institute_name", doctor.getHealthInstituteName());
                intent.putExtra("health_institute_id", doctor.getHealthInstituteId());
                intent.putExtra("specialization_id", doctor.getMasterSpecializationId());
                intent.putExtra("list_type", doctor.getMainInstituteType());
                ctx.startActivity(intent);
              /*  switch (type) {
                  *//*  case "test" :{
                        Intent intent = new Intent(ctx, TestCartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("item_id", doctor.getId());
                        intent.putExtra("item_name", doctor.getDoctorName());
                        intent.putExtra("item_price", doctor.getDoctorFee());
                       // intent.putExtra("discount", sdview.getList_item_discount());
                        intent.putExtra("health_institute_name", doctor.getHealthInstituteName());
                        intent.putExtra("health_institute_id", doctor.getHealthInstituteId());
                        intent.putExtra("specialization_id", doctor.getMasterSpecializationId());
                        intent.putExtra("list_type", type);
                        ctx.startActivity(intent);
                    }*//*
                  //  break;
             *//*       case "package" :{
                        Intent intent = new Intent(ctx, PackageCartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("item_id", sdview.getList_item_id());
                        intent.putExtra("item_name", sdview.getList_item_name());
                        intent.putExtra("item_price", sdview.getList_item_price());
                        intent.putExtra("discount", sdview.getList_item_discount());
                        intent.putExtra("health_institute_name", sdview.getList_center_name());
                        intent.putExtra("health_institute_id", sdview.getHealth_institute_id());
                        intent.putExtra("list_type", type);
                        ctx.startActivity(intent);
                    }
                    break;*//*
                    case "doctor" :{
                        Intent intent = new Intent(ctx, TestCartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("item_id", doctor.getId());
                        intent.putExtra("item_name", doctor.getDoctorName());
                        intent.putExtra("item_price", doctor.getDoctorFee());
                        // intent.putExtra("discount", sdview.getList_item_discount());
                        intent.putExtra("health_institute_name", doctor.getHealthInstituteName());
                        intent.putExtra("health_institute_id", doctor.getHealthInstituteId());
                        intent.putExtra("specialization_id", doctor.getMasterSpecializationId());
                        intent.putExtra("list_type", type);
                        ctx.startActivity(intent);
                    }
                    break;
                }*/
                ((Activity) ctx).finish();

            }
        });

        if (rename_flag == 1) {
            book_button_list.setText("Add");
        }

        return row;
    }

    public boolean checkPermissionForPhone(){
        int result = ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForPhone(){
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) ctx, Manifest.permission.CALL_PHONE)){
           // Toast.makeText(ctx, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions((Activity) ctx,new String[]{Manifest.permission.CALL_PHONE},PHONE_PERMISSION_REQUEST_CODE);
        }
    }




    public class viewholder {
        TextView specialization_name, id, charge, charge_full, health_institute_name, city_name, locality_name, discount, distance,specialization,
                qualification,qualification1,experience,text,time;
        RatingBar ratingBar;
    }
}