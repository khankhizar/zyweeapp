package com.infovita.zywee.Adopters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.infovita.zywee.Activities.PackageCartActivity;
import com.infovita.zywee.Activities.PackageDetail;
import com.infovita.zywee.Pojo.Package;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

import java.util.List;

//import java.util.List;

/**
 * Created by Khizarkhan on 20-09-2016.
 */

public class PackageListAdapter extends BaseAdapter {
    Context context;
    List<Package> packageList;
    View row;
    int rename_flag;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    public PackageListAdapter(Context context, List<Package> packageList, int rename_flag) {
        this.context = context;
        this.packageList = packageList;
        this.rename_flag = rename_flag;
    }


    @Override
    public int getCount() {
        return packageList.size();
    }

    @Override
    public Object getItem(int position) {
        return packageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {
        RelativeLayout relative,relative1,relative2;
        TextView list_item_name, p1_name, p2_name, p1_price, p2_price,p3_name,p3_price,locality,locality1,locality2,rating,rating1,
                rating2, description,description1,description2,description3,content,content1,content2,packageName1,data,treat1,treat2,treat3,
                packageName2,packageName3,showmore_desc,test_cost_full,test_cost_full1,test_cost_full2,discount,discount1,discount2;
        RatingBar ratingbar,ratingbar1,ratingbar2;
        View p1v1,p2v2,p3v3,p3_n,p1_n,p2_n;
        LinearLayout package_desc_hide,package_desc_hide1,package_desc_hide2;
        //TextView ;
        Button call_us1,call_us2,call_us3,provider_book_button1,provider_book_button2,provider_book_button3,show_more_package, show_more_package1, show_more_package2,show,show1,show2;

        public ViewHolder (View row){
            this.list_item_name = (TextView) row.findViewById(R.id.list_item_name);
            this.p1_name = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.provider_name);
            this.p1_price = (TextView) row.findViewById(R.id.provider_price);
            this.test_cost_full = (TextView) row.findViewById(R.id.test_cost_full);
            this.p1_price = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.test_cost);
            this.discount = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.list_item_discount);
            this.ratingbar = (RatingBar) row.findViewById(R.id.p1_n).findViewById(R.id.ratingBar);
            this.p2_name = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.provider_name);
            this.p2_price = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.test_cost);
            this.discount1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.list_item_discount);
            this.test_cost_full1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.test_cost_full);
            this.ratingbar1 = (RatingBar) row.findViewById(R.id.p2_n).findViewById(R.id.ratingBar);
            this.p3_name = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.provider_name);
            this.test_cost_full2 = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.test_cost_full);
            this.p3_price = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.test_cost);
            this.discount2 = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.list_item_discount);
            this.ratingbar2 = (RatingBar) row.findViewById(R.id.p3_n).findViewById(R.id.ratingBar);
            this.locality = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.list_locality_name);
            this.locality1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.list_locality_name);
            this.locality2 = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.list_locality_name);
            this.description = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.des);
            this.description1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.des);
            this.description2 = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.des);
            /*this.description = (TextView) row.findViewById(R.id.description);
            this.description1 = (TextView) row.findViewById(R.id.desc);
            this.description2 = (TextView) row.findViewById(R.id.desc1);
            this.description3 = (TextView) row.findViewById(R.id.desc3);*/
            this.rating = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.rating);
            this.rating1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.rating);
            this.rating2 = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.rating);
            this.data = (TextView) row.findViewById(R.id.desc1);
            this.show = (Button) row.findViewById(R.id.p1_n).findViewById(R.id.Show);
            this.show1 =(Button) row.findViewById(R.id.p2_n).findViewById(R.id.Show);
            this.show2 = (Button) row.findViewById(R.id.p3_n).findViewById(R.id.Show);
            this.treat1 = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.treat);
            this.treat2 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.treat);
            this.treat3 = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.treat);


            this.show_more_package = (Button) row.findViewById(R.id.p1_n).findViewById(R.id.showmore_package);
            this.show_more_package1 = (Button) row.findViewById(R.id.p2_n).findViewById(R.id.showmore_package);
            this.show_more_package2 = (Button) row.findViewById(R.id.p3_n).findViewById(R.id.showmore_package);

            this.provider_book_button1 = (Button) row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button);
            this.provider_book_button2 = (Button) row.findViewById(R.id.p2_n).findViewById(R.id.provider_book_button);
            this.provider_book_button3 = (Button) row.findViewById(R.id.p3_n).findViewById(R.id.provider_book_button);

            this.call_us1= (Button) row.findViewById(R.id.p1_n).findViewById(R.id.call_us);
            this.call_us2= (Button) row.findViewById(R.id.p2_n).findViewById(R.id.call_us);
            this.call_us3= (Button) row.findViewById(R.id.p3_n).findViewById(R.id.call_us);

           /* this.packageName1 =(TextView) row.findViewById(R.id.p1_n).findViewById(R.id.packageName_title);
            this.packageName2 =(TextView) row.findViewById(R.id.p2_n).findViewById(R.id.packageName_title);
            this.packageName3 =(TextView) row.findViewById(R.id.p3_n).findViewById(R.id.packageName_title);*/

            this.package_desc_hide = (LinearLayout) row.findViewById(R.id.p1_n).findViewById(R.id.desc);
            this.package_desc_hide1 = (LinearLayout) row.findViewById(R.id.p2_n).findViewById(R.id.desc);
            this.package_desc_hide2 = (LinearLayout) row.findViewById(R.id.p3_n).findViewById(R.id.desc);

            /*this.content = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.content);
            this.content1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.content);
            this.content2 = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.content);*/

           // this.showmore_desc = (TextView) row.findViewById(R.id.showmore_desc);

            this.p1v1 = row.findViewById(R.id.p1_n).findViewById(R.id.expand);
            this.p2v2 = row.findViewById(R.id.p2_n).findViewById(R.id.expand);
            this.p3v3 = row.findViewById(R.id.p3_n).findViewById(R.id.expand);

            this.p3_n=row.findViewById(R.id.p3_n);
            this.p1_n=row.findViewById(R.id.p1_n);
            this.p2_n=row.findViewById(R.id.p2_n);

            this.relative = (RelativeLayout) row.findViewById(R.id.p1_n).findViewById(R.id.lay6);
            this.relative1 = (RelativeLayout) row.findViewById(R.id.p2_n).findViewById(R.id.lay6);
            this.relative2 = (RelativeLayout) row.findViewById(R.id.p3_n).findViewById(R.id.lay6);


        }

    }


    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.package_parent_views, null);
        final ViewHolder vh =new ViewHolder(row);
        final Package packages = packageList.get(position);
        // logViewedContentEvent();
        logger.logEvent(packages.getSubPackage().getId());
       // Log.d("log : ", vh + "");


        vh.list_item_name.setText(packages.getSubPackage().getSubPackageType());
       /* vh.description.setText(packages.getDescription().replaceAll("\\s+"," "));
        vh.description1.setText(packages.getWhyUndergoThisPackage().replaceAll("\\s+"," "));
        vh.description2.setText(packages.getWhatPreparationRequired().replaceAll("\\s+"," "));
        vh.description3.setText(packages.getProcedureForPackage().replaceAll("\\s+"," "));*/

        Glide.with(context)
                .load(Serverdatas.image_url + "packages/" + packages.getSubPackage().getTreatmentPackageId() + ".jpg")
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.zywee_logo)
                .error(R.drawable.zywee_logo)
                .into((ImageView) row.findViewById(R.id.image));


        int no_of_providers = packages.getEntity().size();
        if (no_of_providers > 2) {
            row.findViewById(R.id.p2_n).setVisibility(View.VISIBLE);
            vh.p1_name.setText(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteName());
           // Log.d("info_err", vh.p1_name.getResources()+"aa");
            //Log.d("info :",packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteName());
            //vh.p1_price.setText("₹" + packages.getTreatmentPackageRate().get(0) + "/-");
            vh.rating.setText(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating());
            vh.show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(vh.package_desc_hide.getVisibility() == View.VISIBLE){
                        vh.package_desc_hide.setVisibility(View.GONE);
                       // vh.show.setText("+");
                        vh.show.setBackgroundResource(R.drawable.if_icon_ios7_plus_outline_211802);
                    }else{
                        vh.package_desc_hide.setVisibility(View.VISIBLE);
                        //vh.show.setText("-");
                        vh.show.setBackgroundResource(R.drawable.if_icon_ios7_minus_outline_211774);
                    }
                }
            });
            vh.description.setText(packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageDesc());
            vh.treat1.setText(packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageName());
            if(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
                vh.ratingbar.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar.setRating(Float.parseFloat(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            }

            vh.locality.setText(packages.getEntity().get(0).getMasterLocality().getLocalityName());
           // vh.packageName1.setText(packages.getTreatmentPackageName().get(0));
            //vh.content.setText("\u2022"+packages.getTreatmentPackageDesc().get(0).replaceAll(",","/n").replaceAll("/n","\u2022 ").replaceAll("[\r\n]+", "\n"));
            vh.p2_name.setText(packages.getEntity().get(1).getEntityHealthInstitute().getHealthInstituteName());
            vh.show1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(vh.package_desc_hide1.getVisibility() == View.VISIBLE){
                        vh.package_desc_hide1.setVisibility(View.GONE);
                        //vh.show1.setText("+");
                       vh.show1.setBackgroundResource(R.drawable.if_icon_ios7_plus_outline_211802);
                    }else{
                        vh.package_desc_hide1.setVisibility(View.VISIBLE);
                        //vh.show1.setText("-");
                        vh.show1.setBackgroundResource(R.drawable.if_icon_ios7_minus_outline_211774);
                    }
                }
            });
            vh.description1.setText(packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageDesc());
            vh.treat2.setText(packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageName());
            vh.p2_price.setText("₹" + packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageRate() + "/-");
            vh.rating1.setText(packages.getEntity().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating());
            if(packages.getEntity().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
                vh.ratingbar1.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar1.setRating(Float.parseFloat(packages.getEntity().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            }
           // vh.ratingbar1.setRating(Float.parseFloat(packages.getEntity().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            vh.locality1.setText(packages.getEntity().get(1).getMasterLocality().getLocalityName());
           // vh.packageName2.setText(packages.getTreatmentPackageName().get(1));
            //vh.content1.setText("\u2022"+packages.getTreatmentPackageDesc().get(1).replaceAll(",","/n").replaceAll("/n","\u2022 ").replaceAll("[\r\n]+", "\n"));
            vh.p3_name.setText(packages.getEntity().get(2).getEntityHealthInstitute().getHealthInstituteName());
            vh.show2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(vh.package_desc_hide2.getVisibility() == View.VISIBLE){
                        vh.package_desc_hide2.setVisibility(View.GONE);
                        //vh.show2.setText("+");
                        vh.show2.setBackgroundResource(R.drawable.if_icon_ios7_plus_outline_211802);
                    }else{
                        vh.package_desc_hide2.setVisibility(View.VISIBLE);
                        //vh.show2.setText("-");
                        vh.show2.setBackgroundResource(R.drawable.if_icon_ios7_minus_outline_211774);
                    }
                }
            });
            vh.description2.setText(packages.getEntity().get(2).getEntityTreatmentPackage().getTreatmentPackageDesc());
            vh.treat3.setText(packages.getEntity().get(2).getEntityTreatmentPackage().getTreatmentPackageName());
          //  vh.p3_price.setText("₹" + packages.getTreatmentPackageRate().get(2) + "/-");
            if(packages.getEntity().get(2).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
                vh.ratingbar2.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar2.setRating(Float.parseFloat(packages.getEntity().get(2).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            }
           // vh.rating2.setText(packages.getEntity().get(2).getEntityHealthInstitute().getHealthInstituteAvgRating());
            vh.ratingbar2.setRating(Float.parseFloat(packages.getEntity().get(2).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            vh.locality2.setText(packages.getEntity().get(2).getMasterLocality().getLocalityName());
          //  vh.packageName3.setText(packages.getTreatmentPackageName().get(2));
          //  vh.content2.setText("\u2022"+packages.getTreatmentPackageDesc().get(2).replaceAll(",","/n").replaceAll("/n","\u2022 ").replaceAll("[\r\n]+", "\n"));
           // if (packages.getEntity().get().getTreatmentPackageRate().size() > 1)
                vh.p2_price.setText("₹" + packages.getEntity().get(2).getEntityTreatmentPackage().getTreatmentPackageRate().replaceAll("\\.0*$", "") + "/-");
           // else vh.p2_price.setText("N/A");


            if (packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate() != null ) {
              //  Log.d("discount_final", packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate());
                vh.test_cost_full.setText("₹" + packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate());
                vh.test_cost_full.setPaintFlags(vh.test_cost_full.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                vh.test_cost_full.setVisibility(View.GONE);

              //  vh.p1_price = (TextView) row.findViewById(R.id.provider_price);

                Float total_value = Float.parseFloat(packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate());
                Float discount = Float.parseFloat(packages.getEntity().get(0).getEntityTreatmentPackage().getPackageDiscount());
                total_value = total_value - (total_value * (discount / 100));

                vh.p1_price.setText("₹" + Math.round(total_value));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                vh.p1_price.setLayoutParams(params);

              vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
                vh.discount.setText(packages.getEntity().get(0).getEntityTreatmentPackage().getPackageDiscount() + "% off");
                vh.discount.setVisibility(View.GONE);

            } else {
                vh.test_cost_full = (TextView) row.findViewById(R.id.test_cost_full);
                vh.test_cost_full.setVisibility(View.GONE);

                vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
                vh.discount.setVisibility(View.GONE);

               // vh.p1_price = (TextView) row.findViewById(R.id.test_cost_full);
                vh.p1_price.setText("₹" + packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //params.weight = 2.3f;
                vh.p1_price.setLayoutParams(params);

            }
            String discounted = packages.getEntity().get(0).getEntityTreatmentPackage().getPackageDiscount();
            if (discounted.equals("0.00")){
                vh.test_cost_full.setVisibility(View.GONE);
            }else{
                vh.test_cost_full.setVisibility(View.GONE);
            }





            if (packages.getEntity().get(1).getEntityTreatmentPackage().getPackageDiscount() != null) {
                //Log.d("discount_final", packages.getEntity().get(1).getEntityTreatmentPackage().getPackageDiscount());
                vh.test_cost_full1.setText("₹" + packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate());
                vh.test_cost_full1.setPaintFlags(vh.test_cost_full1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                vh.test_cost_full1.setVisibility(View.GONE);

                // vh.p2_price = (TextView) row.findViewById(R.id.test_cost);

                Float total_value = Float.parseFloat(packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageRate());
                Float discount = Float.parseFloat(packages.getEntity().get(1).getEntityTreatmentPackage().getPackageDiscount());
                total_value = total_value - (total_value * (discount / 100));

                vh.p2_price.setText("₹" + Math.round(total_value));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = ;
//            params.setMargins(5, 5, 5, 5);
                vh.p2_price.setLayoutParams(params);

//                vh.discount1 = (TextView) row.findViewById(R.id.list_item_discount);
                vh.discount1.setText(packages.getEntity().get(1).getEntityTreatmentPackage().getPackageDiscount() + "% off");
                vh.discount1.setVisibility(View.GONE);

            } else {
                // vh.test_cost_full1 = (TextView) row.findViewById(R.id.test_cost_full);
                vh.test_cost_full1.setVisibility(View.GONE);

                 vh.discount1 = (TextView) row.findViewById(R.id.list_item_discount);
                 vh.discount1.setVisibility(View.GONE);

                // vh.p2_price = (TextView) row.findViewById(R.id.test_cost);
                vh.p2_price.setText("₹" + packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageRate());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //params.weight = 2.3f;
                vh.p2_price.setLayoutParams(params);

            }
            String discounted1 = packages.getEntity().get(1).getEntityTreatmentPackage().getPackageDiscount();
            if (discounted1.equals("0.00")){
                vh.test_cost_full1.setVisibility(View.GONE);
            }else{
                vh.test_cost_full1.setVisibility(View.GONE);
            }



            if (packages.getEntity().get(2).getEntityTreatmentPackage().getPackageDiscount() != null ) {
              //  Log.d("discount_final", packages.getEntity().get(2).getEntityTreatmentPackage().getPackageDiscount());
                vh.test_cost_full2.setText("₹" + packages.getEntity().get(2).getEntityTreatmentPackage().getTreatmentPackageRate());
                vh.test_cost_full2.setPaintFlags(vh.test_cost_full2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                vh.test_cost_full2.setVisibility(View.GONE);

                //  vh.p3_price = (TextView) row.findViewById(R.id.test_cost);

                Float total_value = Float.parseFloat(packages.getEntity().get(2).getEntityTreatmentPackage().getTreatmentPackageRate());
                Float discount = Float.parseFloat(packages.getEntity().get(2).getEntityTreatmentPackage().getPackageDiscount());
                total_value = total_value - (total_value * (discount / 100));

                vh.p3_price.setText("₹" + Math.round(total_value));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = ;
//            params.setMargins(5, 5, 5, 5);
                vh.p3_price.setLayoutParams(params);

                //  vh.discount2 = (TextView) row.findViewById(R.id.list_item_discount);
                vh.discount2.setText(packages.getEntity().get(2).getEntityTreatmentPackage().getPackageDiscount() + "% off");
                vh.discount2.setVisibility(View.GONE);

            } else {
                // vh.test_cost_full2 = (TextView) row.findViewById(R.id.test_cost_full);
                vh.test_cost_full2.setVisibility(View.GONE);

                //   vh.discount2 = (TextView) row.findViewById(R.id.list_item_discount);
                vh.discount2.setVisibility(View.GONE);

                //  vh.p3_price = (TextView) row.findViewById(R.id.test_cost);
                vh.p3_price.setText("₹" + packages.getEntity().get(2).getEntityTreatmentPackage().getTreatmentPackageRate());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //params.weight = 2.3f;
                vh.p3_price.setLayoutParams(params);

            }
            String discounted2 = packages.getEntity().get(2).getEntityTreatmentPackage().getPackageDiscount();
            if (discounted2.equals("0.00")){
                vh.test_cost_full2.setVisibility(View.GONE);
            }else{
                vh.test_cost_full2.setVisibility(View.GONE);
            }
            if(packages.getEntity().get(0).getEntityHealthInstitute().getHasAppointmentBooking().equals("1") ){
                vh.provider_book_button1.setVisibility(View.VISIBLE);
            }else{
                vh.provider_book_button1.setVisibility(View.INVISIBLE);
            }
            if(packages.getEntity().get(1).getEntityHealthInstitute().getHasAppointmentBooking().equals("1") ){
                vh.provider_book_button2.setVisibility(View.VISIBLE);
            }else{
                vh.provider_book_button2.setVisibility(View.INVISIBLE);
            }
            if(packages.getEntity().get(2).getEntityHealthInstitute().getHasAppointmentBooking().equals("1") ){
                vh.provider_book_button3.setVisibility(View.VISIBLE);
            }else{
                vh.provider_book_button3.setVisibility(View.INVISIBLE);
            }




        } else if (no_of_providers > 1) {
            row.findViewById(R.id.p3_n).setVisibility(View.GONE);
            vh.p1_name.setText(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteName());
          //  Log.d("info_err", vh.p1_name.getResources()+"aa");
            //vh.p1_price.setText("₹" + packages.getTreatmentPackageRate().get(0) + "/-");
            vh.show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(vh.package_desc_hide.getVisibility() == View.VISIBLE){
                        vh.package_desc_hide.setVisibility(View.GONE);
                       // vh.show.setText("Show more");
                        vh.show.setBackgroundResource(R.drawable.if_icon_ios7_plus_outline_211802);
                    }else{
                        vh.package_desc_hide.setVisibility(View.VISIBLE);
                        //vh.show.setText("Show less");
                        vh.show.setBackgroundResource(R.drawable.if_icon_ios7_minus_outline_211774);
                    }
                }
            });
            vh.description.setText(packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageDesc());
            vh.treat1.setText(packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageName());
            vh.rating.setText(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating());
            if(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
                vh.ratingbar.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar.setRating(Float.parseFloat(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            }
           // vh.ratingbar.setRating(Float.parseFloat(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            vh.locality.setText(packages.getEntity().get(0).getMasterLocality().getLocalityName());
            // vh.packageName1.setText(packages.getTreatmentPackageName().get(0));
            //vh.content.setText("\u2022"+packages.getTreatmentPackageDesc().get(0).replaceAll(",","/n").replaceAll("/n","\u2022 ").replaceAll("[\r\n]+", "\n"));
            vh.p2_name.setText(packages.getEntity().get(1).getEntityHealthInstitute().getHealthInstituteName());
            vh.show1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(vh.package_desc_hide1.getVisibility() == View.VISIBLE){
                        vh.package_desc_hide1.setVisibility(View.GONE);
                       // vh.show1.setText("Show more");
                        vh.show1.setBackgroundResource(R.drawable.if_icon_ios7_plus_outline_211802);
                    }else{
                        vh.package_desc_hide1.setVisibility(View.VISIBLE);
                       // vh.show1.setText("Show less");
                        vh.show1.setBackgroundResource(R.drawable.if_icon_ios7_minus_outline_211774);
                    }
                }
            });
            vh.description1.setText(packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageDesc());
            vh.treat2.setText(packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageName());
            //  vh.p2_price.setText("₹" + packages.getTreatmentPackageRate().get(1) + "/-");
            vh.rating1.setText(packages.getEntity().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating());
            if(packages.getEntity().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
                vh.ratingbar1.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar1.setRating(Float.parseFloat(packages.getEntity().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            }
           // vh.ratingbar1.setRating(Float.parseFloat(packages.getEntity().get(1).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            vh.locality1.setText(packages.getEntity().get(1).getMasterLocality().getLocalityName());
          //  vh.packageName2.setText(packages.getTreatmentPackageName().get(1));
          //  vh.content1.setText("\u2022"+packages.getTreatmentPackageDesc().get(1).replaceAll(",","/n").replaceAll("/n","\u2022 "));

            if (packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate() != null) {
             //   Log.d("discount_final", packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate());
                vh.test_cost_full.setText("₹" + packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate());
                vh.test_cost_full.setPaintFlags(vh.test_cost_full.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                vh.test_cost_full.setVisibility(View.GONE);

                // vh.p1_price = (TextView) row.findViewById(R.id.test_cost);

                Float total_value = Float.parseFloat(packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate());
                Float discount = Float.parseFloat(packages.getEntity().get(0).getEntityTreatmentPackage().getPackageDiscount());
                total_value = total_value - (total_value * (discount / 100));

                vh.p1_price.setText("₹" + Math.round(total_value));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = ;
//            params.setMargins(5, 5, 5, 5);
                vh.p1_price.setLayoutParams(params);

                //   vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
                vh.discount.setText(packages.getEntity().get(0).getEntityTreatmentPackage().getPackageDiscount() + "% off");
                vh.discount.setVisibility(View.GONE);

            } else {
                //  vh.test_cost_full = (TextView) row.findViewById(R.id.test_cost_full);
                vh.test_cost_full.setVisibility(View.GONE);

                //  vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
                vh.discount.setVisibility(View.GONE);

                //  vh.p1_price = (TextView) row.findViewById(R.id.test_cost);
                vh.p1_price.setText("₹" + packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //params.weight = 2.3f;
                vh.p1_price.setLayoutParams(params);

            }
            String discounted = packages.getEntity().get(0).getEntityTreatmentPackage().getPackageDiscount();
            if (discounted.equals("0.00")){
                vh.test_cost_full.setVisibility(View.GONE);
            }else{
                vh.test_cost_full.setVisibility(View.GONE);
            }



            if (packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageRate() != null ) {
              //  Log.d("discount_final", packages.getEntity().get(1).getEntityTreatmentPackage().getPackageDiscount());
                vh.test_cost_full1.setText("₹" + packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageRate());
                vh.test_cost_full1.setPaintFlags(vh.test_cost_full1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                vh.test_cost_full1.setVisibility(View.GONE);

                // vh.p2_price = (TextView) row.findViewById(R.id.test_cost);

                Float total_value = Float.parseFloat(packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageRate());
                Float discount = Float.parseFloat(packages.getEntity().get(1).getEntityTreatmentPackage().getPackageDiscount());
                total_value = total_value - (total_value * (discount / 100));

                vh.p2_price.setText("₹" + Math.round(total_value));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = ;
//            params.setMargins(5, 5, 5, 5);
                vh.p2_price.setLayoutParams(params);

                //   vh.discount1 = (TextView) row.findViewById(R.id.list_item_discount);
                vh.discount1.setText(packages.getEntity().get(1).getEntityTreatmentPackage().getPackageDiscount() + "% off");
                vh.discount1.setVisibility(View.GONE);

            } else {
                //   vh.test_cost_full1 = (TextView) row.findViewById(R.id.test_cost_full);
                vh.test_cost_full1.setVisibility(View.GONE);

                //   vh.discount1 = (TextView) row.findViewById(R.id.list_item_discount);
                vh.discount1.setVisibility(View.GONE);

                //  vh.p2_price = (TextView) row.findViewById(R.id.test_cost);
                vh.p2_price.setText("₹" + packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageRate());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //params.weight = 2.3f;
                vh.p2_price.setLayoutParams(params);

            }
            String discounted4 = packages.getEntity().get(1).getEntityTreatmentPackage().getPackageDiscount();
            if (discounted4.equals("0.00")){
                vh.test_cost_full1.setVisibility(View.GONE);
            }else{
                vh.test_cost_full1.setVisibility(View.GONE);
            }

            if(packages.getEntity().get(0).getEntityHealthInstitute().getHasAppointmentBooking().equals("1") ){
                vh.provider_book_button1.setVisibility(View.VISIBLE);
            }else{
                vh.provider_book_button1.setVisibility(View.GONE);
            }
            if(packages.getEntity().get(1).getEntityHealthInstitute().getHasAppointmentBooking().equals("1") ){
                vh.provider_book_button2.setVisibility(View.VISIBLE);
            }else{
                vh.provider_book_button2.setVisibility(View.GONE);
            }



        }  else if (no_of_providers == 1) {
            row.findViewById(R.id.p3_n).setVisibility(View.GONE);
            row.findViewById(R.id.p2_n).setVisibility(View.GONE);
            vh.p1_name.setText(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteName());
            vh.show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(vh.package_desc_hide.getVisibility() == View.VISIBLE){
                        vh.package_desc_hide.setVisibility(View.GONE);
                        //vh.show.setText("Show more");
                        vh.show.setBackgroundResource(R.drawable.if_icon_ios7_plus_outline_211802);
                    }else{
                        vh.package_desc_hide.setVisibility(View.VISIBLE);
                        //vh.show.setText("Show less");
                        vh.show.setBackgroundResource(R.drawable.if_icon_ios7_minus_outline_211774);
                    }
                }
            });
            vh.description.setText(packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageDesc());
            vh.treat1.setText(packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageName());
          //  Log.d("info_err", vh.p1_name.getResources()+"aa");
            //vh.p1_price.setText("₹" + packages.getTreatmentPackageRate().get(0) + "/-");
            vh.rating.setText(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating());
            if(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
                vh.ratingbar.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar.setRating(Float.parseFloat(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            }
           // vh.ratingbar.setRating(Float.parseFloat(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating()));
            vh.locality.setText(packages.getEntity().get(0).getMasterLocality().getLocalityName());
            // vh.packageName1.setText(packages.getTreatmentPackageName().get(0));
            //vh.content.setText("\u2022"+packages.getTreatmentPackageDesc().get(0).replaceAll(",","/n").replaceAll("/n","\u2022 ").replaceAll("[\r\n]+", "\n"));
          //  vh.packageName1.setText(packages.getTreatmentPackageName().get(0));
           // vh.content.setText("\u2022"+packages.getTreatmentPackageDesc().get(0).replaceAll(",","/n").replaceAll("/n","\u2022 "));
            if (packages.getEntity().get(0).getEntityTreatmentPackage().getPackageDiscount() != null) {
             //   Log.d("discount_final", packages.getEntity().get(0).getEntityTreatmentPackage().getPackageDiscount());
                vh.test_cost_full.setText("₹" + packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate());
                vh.test_cost_full.setPaintFlags(vh.test_cost_full.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                vh.test_cost_full.setVisibility(View.GONE);

                //  vh.p1_price = (TextView) row.findViewById(R.id.test_cost);

                Float total_value = Float.parseFloat(packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate());
                Float discount = Float.parseFloat(packages.getEntity().get(0).getEntityTreatmentPackage().getPackageDiscount());
                total_value = total_value - (total_value * (discount / 100));

                vh.p1_price.setText("₹" + Math.round(total_value));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = ;
//            params.setMargins(5, 5, 5, 5);
                vh.p1_price.setLayoutParams(params);

                //  vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
                vh.discount.setText(packages.getEntity().get(0).getEntityTreatmentPackage().getPackageDiscount() + "% off");
                vh.discount.setVisibility(View.GONE);

            } else {
                // vh.test_cost_full = (TextView) row.findViewById(R.id.test_cost_full);
                vh.test_cost_full.setVisibility(View.GONE);

                //  vh.discount = (TextView) row.findViewById(R.id.list_item_discount);
                vh.discount.setVisibility(View.GONE);

                //  vh.p1_price = (TextView) row.findViewById(R.id.test_cost);
                vh.p1_price.setText("₹" + packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //params.weight = 2.3f;
                vh.p1_price.setLayoutParams(params);

            }
            if(packages.getEntity().get(0).getEntityHealthInstitute().getHasAppointmentBooking().equals("1") ){
                vh.provider_book_button1.setVisibility(View.VISIBLE);
            }else{
                vh.provider_book_button1.setVisibility(View.GONE);
            }
        }

      else if (no_of_providers < 1)

    {
        row.findViewById(R.id.p3_n).setVisibility(View.GONE);
        row.findViewById(R.id.p2_n).setVisibility(View.GONE);
        row.findViewById(R.id.p1_n).setVisibility(View.GONE);
        row.findViewById(R.id.desc1).setVisibility(View.VISIBLE);
        row.findViewById(R.id.more_details).setVisibility(View.GONE);
    }
      /*  String discounted5 = packages.getEntity().get(0).getEntityTreatmentPackage().getPackageDiscount();
        if (discounted5.equals("0.00")){
            vh.test_cost_full.setVisibility(View.GONE);
        }else{
            vh.test_cost_full.setVisibility(View.GONE);
        }
*/


        /*vh.p1v1.setVisibility(View.GONE);
        vh.p2v2.setVisibility(View.GONE);
        vh.p3v3.setVisibility(View.GONE);*/

       /* vh.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CentreDetailActivity.class)

                        .putExtra("list_item_name", packages.getSubPackageType())
                        .putExtra("p1_name", packages.getHealthInstituteName().get(0))
                        .putExtra("p1_price", packages.getTreatmentPackageRate().get(0))
                        .putExtra("discount", packages.getPackageDiscount().get(0).replaceAll("\\.0*$", ""))
                        .putExtra("locality1", packages.getLocalityName().get(0))
                        .putExtra("ratingbar",packages.getHealthInstituteAvgRating().get(0))
                        .putExtra("centre",packages.getHealthInstituteDescrption().get(0))
                        .putExtra("geo",packages.getGeo().get(0))
                        .putExtra("description",packages.getDescription())
                        .putExtra("content", packages.getTreatmentPackageDesc().get(0))
                        .putExtra("Health_Id",packages.getHealthInstituteId().get(0))
                        .putExtra("package_id",packages.getPackageTypeId())
                        .putExtra("package_desc",packages.getTreatmentPackagesDescription())
                        .setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)));


                //  .putExtra("homeService", homeService));
            }
        });
        vh.relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CentreDetailActivity.class)
                        .putExtra("list_item_name", packages.getSubPackageType())
                        .putExtra("p1_name", packages.getHealthInstituteName().get(1))
                        .putExtra("p1_price", packages.getTreatmentPackageRate().get(1))
                        .putExtra("discount", packages.getPackageDiscount().get(1).replaceAll("\\.0*$", ""))
                        .putExtra("locality1", packages.getLocalityName().get(1))
                        .putExtra("ratingbar", packages.getHealthInstituteAvgRating().get(1))
                        .putExtra("centre",packages.getHealthInstituteDescrption().get(1))
                        .putExtra("geo",packages.getGeo().get(1))
                        .putExtra("description", packages.getDescription())
                        .putExtra("content", packages.getTreatmentPackageDesc().get(1))
                        .putExtra("Health_Id",packages.getHealthInstituteId().get(1))
                        .putExtra("package_id",packages.getPackageTypeId())
                        .putExtra("package_desc",packages.getTreatmentPackagesDescription())
                        .setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)));
                //  .putExtra("homeService", homeService));
            }
        });

        vh.relative2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CentreDetailActivity.class)
                        .putExtra("list_item_name", packages.getSubPackageType())
                        .putExtra("p1_name", packages.getHealthInstituteName().get(2))
                        .putExtra("p1_price", packages.getTreatmentPackageRate().get(2))
                        .putExtra("discount", packages.getPackageDiscount().get(2).replaceAll("\\.0*$", ""))
                        .putExtra("locality1", packages.getLocalityName().get(2))
                        .putExtra("ratingbar", packages.getHealthInstituteAvgRating().get(2))
                        .putExtra("centre",packages.getHealthInstituteDescrption().get(2))
                        .putExtra("geo",packages.getGeo().get(2))
                        .putExtra("description", packages.getDescription())
                        .putExtra("content", packages.getTreatmentPackageDesc().get(2))
                        .putExtra("Health_Id",packages.getHealthInstituteId().get(2))
                        .putExtra("package_id",packages.getPackageTypeId())
                        .putExtra("package_desc",packages.getTreatmentPackagesDescription())
                        .setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)));
                //  .putExtra("homeService", homeService));
            }
        });


*/

    /*   vh.show_more_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logViewedContentEvent();
                Log.d("click","clickp1");
                vh.p2v2.setVisibility(View.GONE);
                vh.p3v3.setVisibility(View.GONE);
                if(vh.p1v1.getVisibility() == View.VISIBLE){
                    vh.p1v1.setVisibility(View.GONE);
                    vh.show_more_package.setText("Show Details");
                }else{
                    vh.p1v1.setVisibility(View.VISIBLE);
                    vh.show_more_package.setText("Hide Details");
                }

            }
        });

        vh.show_more_package1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click","clickp2");
                vh.p1v1.setVisibility(View.GONE);
                vh.p3v3.setVisibility(View.GONE);
                if(vh.p2v2.getVisibility() == View.VISIBLE){
                    vh.p2v2.setVisibility(View.GONE);
                    vh.show_more_package1.setText("View Details");
                }else{
                    vh.p2v2.setVisibility(View.VISIBLE);
                    vh.show_more_package1.setText("Hide Details");
                }
            }
        });

        vh.show_more_package2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click","clickp3");
                vh.p1v1.setVisibility(View.GONE);
                vh.p2v2.setVisibility(View.GONE);
                if(vh.p3v3.getVisibility() == View.VISIBLE){
                    vh.p3v3.setVisibility(View.GONE);
                    vh.show_more_package2.setText("View Details");
                }else{
                    vh.p3v3.setVisibility(View.VISIBLE);
                    vh.show_more_package2.setText("Hide Details");
                }

            }
        });
*/
       /* if(packages.getEntity().get(0).getEntityHealthInstitute().getHasAppointmentBooking().equals("1") ){
            vh.provider_book_button1.setVisibility(View.VISIBLE);
        }else{
            vh.provider_book_button1.setVisibility(View.GONE);
        }*/

       // vh.p1_price.setOnClickListener(new View.OnClickListener() {
        vh.provider_book_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logViewedContentEvent();
                context.startActivity(new Intent(context, PackageCartActivity.class)
                        .putExtra("list_item_name", packages.getSubPackage().getSubPackageType())
                        .putExtra("p1_name", packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteName())
                        .putExtra("p1_price", packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageRate())
                        .putExtra("discount", packages.getEntity().get(0).getEntityTreatmentPackage().getPackageDiscount().replaceAll("\\.0*$", ""))
                        .putExtra("Health_Id",packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteId())
                        .putExtra("package_id",packages.getEntity().get(0).getEntityTreatmentPackage().getTreatmentPackageId())
                       // .putExtra("timing",packages.getEntity().get(0).getEntityTreatmentPackage().getWorkingPlan())
                        .putExtra("package_desc",packages.getTreatmentPackage().getDescription())
                        .putExtra("payment_option",packages.getEntity().get(0).getEntityHealthInstitute().getPaymentOption()));
            }
        });

       /* if(packages.getEntity().get(1).getEntityHealthInstitute().getHasAppointmentBooking().equals("1") ){
            vh.provider_book_button2.setVisibility(View.VISIBLE);
        }else{
            vh.provider_book_button2.setVisibility(View.GONE);
        }
*/
       // vh.p2_price.setOnClickListener(new View.OnClickListener() {
        vh.provider_book_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logViewedContentEvent();
                context.startActivity(new Intent(context, PackageCartActivity.class)
                        .putExtra("list_item_name", packages.getSubPackage().getSubPackageType())
                        .putExtra("p1_name", packages.getEntity().get(1).getEntityHealthInstitute().getHealthInstituteName())
                        .putExtra("p1_price", packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageRate())
                        .putExtra("discount", packages.getEntity().get(1).getEntityTreatmentPackage().getPackageDiscount().replaceAll("\\.0*$", ""))
                        .putExtra("Health_Id",packages.getEntity().get(1).getEntityHealthInstitute().getHealthInstituteId())
                        .putExtra("package_id",packages.getEntity().get(1).getEntityTreatmentPackage().getTreatmentPackageId())
                        .putExtra("package_desc",packages.getTreatmentPackage().getDescription())
                        .putExtra("payment_option",packages.getEntity().get(1).getEntityHealthInstitute().getPaymentOption()));
            }
        });

      /*  if(packages.getEntity().get(3).getEntityHealthInstitute().getHasAppointmentBooking().equals("1") ){
            vh.provider_book_button3.setVisibility(View.VISIBLE);
        }else{
            vh.provider_book_button3.setVisibility(View.GONE);
        }*/

        //vh.p3_price.setOnClickListener(new View.OnClickListener() {
        vh.provider_book_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logViewedContentEvent();
                context.startActivity(new Intent(context, PackageCartActivity.class)
                        .putExtra("list_item_name", packages.getSubPackage().getSubPackageType())
                        .putExtra("p1_name", packages.getEntity().get(2).getEntityHealthInstitute().getHealthInstituteName())
                        .putExtra("p1_price", packages.getEntity().get(2).getEntityTreatmentPackage().getTreatmentPackageRate())
                        .putExtra("discount", packages.getEntity().get(2).getEntityTreatmentPackage().getPackageDiscount().replaceAll("\\.0*$", ""))
                        .putExtra("Health_Id",packages.getEntity().get(2).getEntityHealthInstitute().getHealthInstituteId())
                        .putExtra("package_id",packages.getEntity().get(2).getEntityTreatmentPackage().getTreatmentPackageId())
                        .putExtra("package_desc",packages.getTreatmentPackage().getDescription())
                        .putExtra("payment_option",packages.getEntity().get(2).getEntityHealthInstitute().getPaymentOption()));
            }
        });

        // show more/ less chunk
       /* vh.showmore_desc.setOnClickListener(new View.OnClickListener() {
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
        });*/
        //end


      /*  if (!checkPermissionForPhone())
            requestPermissionForPhone();

        vh.call_us1.setOnClickListener(new View.OnClickListener(){
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

        vh.call_us2.setOnClickListener(new View.OnClickListener(){
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

        vh.call_us3.setOnClickListener(new View.OnClickListener(){
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

        row.findViewById(R.id.more_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PackageDetail.class)
                        .putExtra("package", packages));
            }
        });

        row.findViewById(R.id.packagetitle_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PackageDetail.class)
                        .putExtra("package", packages));
            }
        });
*/
        row.findViewById(R.id.more_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PackageDetail.class)
                        .putExtra("package", packages));
            }
        });

        return row;
    }


    AppEventsLogger logger = AppEventsLogger.newLogger(context);

    // logger.logEvent(AppEventsConstants.EVENT_NAME_{XXXXX});
    public void logViewedContentEvent(){
        Bundle parameters = new Bundle();
        parameters.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "INR");
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product");
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, "HDFU-8452");

        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART,
                54.23,
                parameters);
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
            //  Toast.makeText(context, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},PHONE_PERMISSION_REQUEST_CODE);
        }
    }


    public  interface refreshList{
        public void refreshListItems();
    }

}
