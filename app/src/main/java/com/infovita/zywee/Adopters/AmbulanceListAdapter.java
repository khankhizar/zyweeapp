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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.infovita.zywee.Activities.AmbulanceDetails;
import com.infovita.zywee.Activities.AmbulanceProvider;
import com.infovita.zywee.Pojo.Ambulance;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

import java.util.List;

/**
 * Created by praveen on 13/7/16.
 */
public class AmbulanceListAdapter extends BaseAdapter {

    Context context;
    List<Ambulance> ambulanceList;
    View row;
    int rename_flag;

    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    public AmbulanceListAdapter(Context context, List<Ambulance> ambulanceList, int rename_flag) {
        this.context = context;
        this.ambulanceList = ambulanceList;
        this.rename_flag = rename_flag;
    }



    @Override
    public int getCount() {
        return ambulanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return ambulanceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        ViewHolder vh = new ViewHolder();
        final Ambulance ambulance = (Ambulance) getItem(position);
       // logViewedContentEvent();
        logger.logEvent(ambulance.getVehicle().getVehicleType());

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.equipment_parent_view, null);
            row.setTag(vh);

        } else {
            vh = (ViewHolder) row.getTag();
        }

        vh.list_item_name = (TextView) row.findViewById(R.id.list_item_name);
        vh.p1_name = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.provider_name);

        vh.p1_price = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.provider_price);
        vh.rating = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.rating);
        vh.ratingbar = (RatingBar) row.findViewById(R.id.p1_n).findViewById(R.id.ratingBar);
        vh.p2_name = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.provider_name);
        vh.p2_price = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.provider_price);
        vh.ratingbar1 = (RatingBar) row.findViewById(R.id.p2_n).findViewById(R.id.ratingBar);
        vh.rating1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.rating);
//        vh.relative = (RelativeLayout) row.findViewById(R.id.p1_n).findViewById(R.id.re);
//        vh.relative1 = (RelativeLayout) row.findViewById(R.id.p2_n).findViewById(R.id.re);


        vh.list_item_name.setText(ambulance.getVehicle().getVehicleType());


       /* Log.d("ambulance", "> " + Serverdatas.image_url + "images/mainimages/transport/" + ambulance.getProviderId() + ".jpg" + "\n");
        Log.d("value : ", ambulance.getProviderName().get(0));
        Log.d("rating value:", ambulance.getAvgRating().get(0));*/

        Glide.with(context)
                .load(Serverdatas.image_url + "ambulance/vehicle/" + ambulance.getVehicle().getId() + ".jpg")
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.zywee_logo)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // do something
                     /*   Log.e("info_img",e+"");
                        Glide.with(context)
                                .load(Serverdatas.image_url + "ambulance/vehicle/" + ambulance.getId() + ".jpg")
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.zywee_logo)
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
        int no_of_providers = ambulance.getProviders().size();
        if (no_of_providers > 1) {
            row.findViewById(R.id.p2_n).setVisibility(View.VISIBLE);
            vh.p1_name.setText(ambulance.getProviders().get(0).getProvider().getProviderName());
            vh.p1_price.setText("₹" + ambulance.getProviders().get(0).getProviderDistance().getCost() + "/-");
            vh.rating.setText(ambulance.getProviders().get(0).getProvider().getAvgRating());
            if(ambulance.getProviders().get(0).getProvider().getAvgRating().equals("0.00")){
                vh.ratingbar.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar.setRating(Float.parseFloat(ambulance.getProviders().get(0).getProvider().getAvgRating()));
            }
            vh.p2_name.setText(ambulance.getProviders().get(1).getProvider().getProviderName());
            vh.ratingbar1.setRating(Float.parseFloat(ambulance.getProviders().get(1).getProvider().getAvgRating()));
            if(ambulance.getProviders().get(1).getProvider().getAvgRating().equals("0.00")){
                vh.ratingbar1.setRating(Float.parseFloat("4.00"));
            }else {
                vh.rating1.setText(ambulance.getProviders().get(1).getProvider().getAvgRating());
            }
            if (ambulance.getProviders().size() > 1)
                vh.p2_price.setText("₹" + ambulance.getProviders().get(1).getProviderDistance().getCost() + "/-");
            else vh.p2_price.setText("N/A");
        } else if (no_of_providers == 1){
            row.findViewById(R.id.p2_n).setVisibility(View.GONE);
            vh.p1_name.setText(ambulance.getProviders().get(0).getProvider().getProviderName());
            vh.p1_price.setText("₹" + ambulance.getProviders().get(0).getProviderDistance().getCost() + "/-");
            vh.rating.setText(ambulance.getProviders().get(0).getProvider().getAvgRating());
            if(ambulance.getProviders().get(0).getProvider().getAvgRating().equals("0.00")){
                vh.ratingbar.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar.setRating(Float.parseFloat(ambulance.getProviders().get(0).getProvider().getAvgRating()));
            }
        }else{
            row.findViewById(R.id.p1_n).setVisibility(View.GONE);
            row.findViewById(R.id.p2_n).setVisibility(View.GONE);
            row.findViewById(R.id.desc1).setVisibility(View.VISIBLE);
            row.findViewById(R.id.more_details).setVisibility(View.GONE);

        }
    /*   vh.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("rating : ",ambulance.getAvgRating().get(0) );
                context.startActivity(new Intent(context, AmbulanceproviderDetail.class)
                        .putExtra("ambulance_name", ambulance.getVehicleType())
                        .putExtra("provider_id", ambulance.getProviderId().get(0))
                        .putExtra("provider_name", ambulance.getProviderName().get(0))
                        .putExtra("provider_desc", ambulance.getVehicleDescription())
                        .putExtra("ratingbar",ambulance.getAvgRating().get(0))
                        .putExtra("cost",ambulance.getCost().get(0))
                        .putExtra("ambulance_provider_id", ambulance.getProviderId().get(0))
                        .putExtra("ambulance_id", ambulance.getId()));

                //  .putExtra("homeService", homeService));
            }
        });
        vh.relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("rating : ",ambulance.getAvgRating().get(1) );
                context.startActivity(new Intent(context, AmbulanceproviderDetail.class)
                        .putExtra("ambulance_name", ambulance.getVehicleType())
                        .putExtra("provider_id", ambulance.getProviderId().get(1))
                        .putExtra("provider_name", ambulance.getProviderName().get(1))
                        .putExtra("ambulance_provider_id", ambulance.getProviderId().get(1))
                        .putExtra("provider_desc", ambulance.getVehicleDescription())
                        .putExtra("ratingbar",ambulance.getAvgRating().get(1))
                        .putExtra("cost",ambulance.getCost().get(1))
                        .putExtra("ambulance_id", ambulance.getId()));
                //  .putExtra("homeService", homeService));
            }
        });
*/


       // row.findViewById(R.id.p1_n).findViewById(R.id.provider_price).setOnClickListener(new View.OnClickListener() {
        row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logViewedContentEvent();
                context.startActivity(new Intent(context, AmbulanceProvider.class)
                        .putExtra("ambulance_name", ambulance.getVehicle().getVehicleType())
                        .putExtra("provider_id", ambulance.getProviders().get(0).getProviderVehicle().getProviderId())
                        .putExtra("provider_name", ambulance.getProviders().get(0).getProvider().getProviderName())
                        .putExtra("ambulance_provider_id", ambulance.getProviders().get(0).getProviderVehicle().getProviderId())
                        .putExtra("ambulance_id", ambulance.getVehicle().getId()));
            }
        });

       // row.findViewById(R.id.p2_n).findViewById(R.id.provider_price).setOnClickListener(new View.OnClickListener() {
        row.findViewById(R.id.p2_n).findViewById(R.id.provider_book_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logViewedContentEvent();
                context.startActivity(new Intent(context, AmbulanceProvider.class)
                        .putExtra("ambulance_name", ambulance.getVehicle().getVehicleType())
                        .putExtra("provider_id", ambulance.getProviders().get(1).getProviderVehicle().getProviderId())
                        .putExtra("provider_name", ambulance.getProviders().get(1).getProvider().getProviderName())
                        .putExtra("ambulance_provider_id", ambulance.getProviders().get(1).getProviderVehicle().getProviderId())
                        .putExtra("ambulance_id", ambulance.getVehicle().getId()));
            }
        });




      /*  if (!checkPermissionForPhone())
            requestPermissionForPhone();

        row.findViewById(R.id.p1_n).findViewById(R.id.call_us).setOnClickListener(new View.OnClickListener(){
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

        row.findViewById(R.id.p2_n).findViewById(R.id.call_us).setOnClickListener(new View.OnClickListener(){
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

        row.findViewById(R.id.list_item_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AmbulanceDetails.class)
                        .putExtra("ambulance", ambulance));
            }
        });*/

        row.findViewById(R.id.more_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AmbulanceDetails.class)
                        .putExtra("ambulance", ambulance));
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


    public class ViewHolder {
        TextView list_item_name, p1_name, p2_name, p1_price, p2_price,rating,rating1;
        RatingBar ratingbar,ratingbar1;
        public RelativeLayout relative,relative1,relative2;
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


}
