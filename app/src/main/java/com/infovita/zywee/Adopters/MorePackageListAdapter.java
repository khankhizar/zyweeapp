package com.infovita.zywee.Adopters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.infovita.zywee.Activities.PackageCartActivity;
import com.infovita.zywee.Pojo.MorePackage;
import com.infovita.zywee.R;

import java.util.List;

/**
 * Created by Khizarkhan on 19-07-2017.
 */
public class MorePackageListAdapter extends BaseAdapter {
    Context context;
    List<MorePackage> packageList;
    View row;
    int rename_flag;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    public MorePackageListAdapter(Context context, List<MorePackage> packageList, int rename_flag) {
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
                rating2, description,description1,description2,description3,content,content1,content2,packageName1,data,
                packageName2,packageName3,showmore_desc,test_cost_full,test_cost_full1,test_cost_full2,discount,discount1,discount2,treat;
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
            this.locality = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.list_locality_name);
            this.description = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.des);
            this.rating = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.rating);
            this.data = (TextView) row.findViewById(R.id.desc1);
            this.show = (Button) row.findViewById(R.id.p1_n).findViewById(R.id.Show);
            this.treat = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.treat);

            this.show_more_package = (Button) row.findViewById(R.id.p1_n).findViewById(R.id.showmore_package);
            this.provider_book_button1 = (Button) row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button);
            this.call_us1= (Button) row.findViewById(R.id.p1_n).findViewById(R.id.call_us);

           /* this.packageName1 =(TextView) row.findViewById(R.id.p1_n).findViewById(R.id.packageName_title);
            this.packageName2 =(TextView) row.findViewById(R.id.p2_n).findViewById(R.id.packageName_title);
            this.packageName3 =(TextView) row.findViewById(R.id.p3_n).findViewById(R.id.packageName_title);*/

            this.package_desc_hide = (LinearLayout) row.findViewById(R.id.p1_n).findViewById(R.id.desc);
            /*this.content = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.content);
            this.content1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.content);
            this.content2 = (TextView) row.findViewById(R.id.p3_n).findViewById(R.id.content);*/

            // this.showmore_desc = (TextView) row.findViewById(R.id.showmore_desc);

            this.p1v1 = row.findViewById(R.id.p1_n).findViewById(R.id.expand);
            this.p1_n=row.findViewById(R.id.p1_n);
            this.relative = (RelativeLayout) row.findViewById(R.id.p1_n).findViewById(R.id.lay6);

        }

    }


    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.more_package_parent_views, null);
        final MorePackageListAdapter.ViewHolder vh =new MorePackageListAdapter.ViewHolder(row);
        final MorePackage packages = packageList.get(position);
        // logViewedContentEvent();
        logger.logEvent(packages.getEntityTreatmentPackage().getHealthInstituteId());
        // Log.d("log : ", vh + "");

        vh.p1_name.setText(packages.getEntityHealthInstitut().getHealthInstituteName());

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
        vh.treat.setText(packages.getEntityTreatmentPackage().getTreatmentPackageName());
        vh.description.setText(packages.getEntityTreatmentPackage().getTreatmentPackageDesc());
        vh.p1_price.setText("₹" + packages.getEntityTreatmentPackage().getDiscountPrice());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //params.weight = 2.3f;
        vh.p1_price.setLayoutParams(params);
       /* if(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating().equals("0.00")){
            vh.ratingbar.setRating(Float.parseFloat("4.00"));
        }else{
            vh.ratingbar.setRating(Float.parseFloat(packages.getEntity().get(0).getEntityHealthInstitute().getHealthInstituteAvgRating()));
        }

        vh.locality.setText(packages.getEntity().get(0).getMasterLocality().getLocalityName());*/


        vh.list_item_name.setText(packages.getEntityTreatmentPackage().getTreatmentPackageName());

        vh.provider_book_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logViewedContentEvent();
                context.startActivity(new Intent(context, PackageCartActivity.class)
                        .putExtra("list_item_name", packages.getEntityTreatmentPackage().getTreatmentPackageName())
                        .putExtra("p1_name", packages.getEntityHealthInstitut().getHealthInstituteName() )
                        .putExtra("p1_price", packages.getEntityTreatmentPackage().getDiscountPrice() + ".00")
                        .putExtra("discount", "0")
                        .putExtra("Health_Id",packages.getEntityTreatmentPackage().getHealthInstituteId())
                        .putExtra("package_id",packages.getEntityTreatmentPackage().getTreatmentPackageId())
                        // .putExtra("timing",packages.getEntity().get(0).getEntityTreatmentPackage().getWorkingPlan())
                        .putExtra("package_desc",packages.getEntityTreatmentPackage().getTreatmentPackageDesc())
                        .putExtra("payment_option",packages.getEntityHealthInstitut().getPaymentOption())
                         );
            }
        });

       /* if(packages.getEntity().get(1).getEntityHealthInstitute().getHasAppointmentBooking().equals("1") ){
            vh.provider_book_button2.setVisibility(View.VISIBLE);
        }else{
            vh.provider_book_button2.setVisibility(View.GONE);
        }
*/
        // vh.p2_price.setOnClickListener(new View.OnClickListener() {


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

