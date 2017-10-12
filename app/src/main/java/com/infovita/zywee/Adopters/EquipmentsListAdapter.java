package com.infovita.zywee.Adopters;

import android.content.Context;
import android.content.Intent;
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
import com.infovita.zywee.Activities.EquipmentDetails;
import com.infovita.zywee.Activities.EquipmentProvider;
import com.infovita.zywee.Pojo.Equipment;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

import java.util.List;

/**
 * Created by praveen on 30/6/16.
 */
public class EquipmentsListAdapter extends BaseAdapter {

    Context context;
    List<Equipment> equipmentsList;
    View row;
    int rename_flag;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    public EquipmentsListAdapter(Context context, List<Equipment> equipmentsList, int rename_flag) {
        this.context = context;
        this.equipmentsList = equipmentsList;
        this.rename_flag = rename_flag;
    }

    @Override
    public int getCount() {
        return equipmentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return equipmentsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        ViewHolder vh = new ViewHolder();
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
        // vh.list_locality_name = (TextView)row.findViewById(R.id.list_locality_name);
        // vh.distance = (TextView) row.findViewById(R.id.distance);
        vh.rating = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.rating);
        vh.ratingbar = (RatingBar) row.findViewById(R.id.p1_n).findViewById(R.id.ratingBar);
        // vh.test_cost_full = (TextView) row.findViewById(R.id.test_cost_full);
        vh.p2_name = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.provider_name);
        vh.p2_price = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.provider_price);
        //  vh.list_locality_name1 = (TextView)row.findViewById(R.id.list_locality_name1);
        //vh.distance1 = (TextView) row.findViewById(R.id.distance1);
        vh.rating1 = (TextView) row.findViewById(R.id.p2_n).findViewById(R.id.rating);
        vh.ratingbar1 = (RatingBar) row.findViewById(R.id.p2_n).findViewById(R.id.ratingBar);
        // vh.test_cost_full1 = (TextView) row.findViewById(R.id.test_cost_full1);
//        vh.relative = (RelativeLayout) row.findViewById(R.id.p1_n).findViewById(R.id.re);
//        vh.relative1 = (RelativeLayout) row.findViewById(R.id.p2_n).findViewById(R.id.re);
//

        final Equipment equipments = equipmentsList.get(position);
        vh.list_item_name.setText(equipments.getEquipment().getEquipmentName());


        Glide.with(context)
                .load(Serverdatas.image_url+"equipments/"+ equipments.getEquipment().getId()+".jpg")
                //.load(Serverdatas.image_url + "equipments/" + equipments.getList().get(0).getProviderEquipmentCost().getEquipmentProviderId() + ".jpg")
                .fitCenter()
                /*.centerCrop()*/
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.zywee_logo)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // do something
                       // Log.e("info_img",e+"");
                        Glide.with(context)
                                .load(Serverdatas.image_url + "equipments/" + equipments.getEquipment().getId() + ".jpg")
                                //.load(Serverdatas.image_url+"equipments/"+equipments.getList().get(0).getProviderEquipmentCost().getEquipmentProviderId()+".jpg")
                                .fitCenter()
                                /*.centerCrop()*/
                                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .placeholder(R.drawable.zywee_logo)
                                .into((ImageView) row.findViewById(R.id.image));
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // do something
                       // Log.e("info_img",isFromMemoryCache+""+isFirstResource);
                        return false;
                    }
                })
                .into((ImageView) row.findViewById(R.id.image));
        int no_of_providers = equipments.getList().size();
        if (no_of_providers>1){
            row.findViewById(R.id.p2_n).setVisibility(View.VISIBLE);
            vh.p1_name.setText(equipments.getList().get(0).getEquipmentProvider().getProviderName());
            vh.p1_price.setText("₹" +equipments.getList().get(0).getProviderEquipmentCost().getMonthlyCost() + "/-");
           // Log.d("price_cost",equipments.getMonthlyCost().get(0));
            //  vh.list_locality_name.setText(equipments.getAddress(0).getSubLocality());
            // vh.distance.setText(sd_local.getDistance());
           // Log.d("rating: " ,equipments.getAvgRating().get(0));
            vh.rating.setText(equipments.getList().get(0).getEquipmentProvider().getAvgRating());
            if(equipments.getList().get(0).getEquipmentProvider().getAvgRating().equals("0.00")){
                vh.ratingbar.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar.setRating(Float.parseFloat(equipments.getList().get(0).getEquipmentProvider().getAvgRating()));
            }
            vh.p2_name.setText(equipments.getList().get(1).getEquipmentProvider().getProviderName());
            vh.p2_price.setText("₹" +equipments.getList().get(1).getProviderEquipmentCost().getMonthlyCost() + "/-");
            vh.rating1.setText(equipments.getList().get(1).getEquipmentProvider().getAvgRating());
            if(equipments.getList().get(1).getEquipmentProvider().getAvgRating().equals("0.00")){
                vh.ratingbar1.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar1.setRating(Float.parseFloat(equipments.getList().get(1).getEquipmentProvider().getAvgRating()));
            }

            //   vh.list_locality_name1.setText(sd_local.getList_locality_name());
            // vh.distance1.setText(sd_local.getDistance());
        } else if (no_of_providers == 1){
            row.findViewById(R.id.p2_n).setVisibility(View.GONE);
            vh.p1_name.setText(equipments.getList().get(0).getEquipmentProvider().getProviderName());
            vh.p1_price.setText("₹" +equipments.getList().get(0).getProviderEquipmentCost().getMonthlyCost() + "/-");
            vh.rating.setText(equipments.getList().get(0).getEquipmentProvider().getAvgRating());
            if(equipments.getList().get(0).getEquipmentProvider().getAvgRating().equals("0.00")){
                vh.ratingbar.setRating(Float.parseFloat("4.00"));
            }else{
                vh.ratingbar.setRating(Float.parseFloat(equipments.getList().get(0).getEquipmentProvider().getAvgRating()));
            }
           // vh.ratingbar.setRating(Float.parseFloat(equipments.getAvgRating().get(0)));
            // vh.list_locality_name.setText(sd_local.getList_locality_name());
            // vh.distance.setText(sd_local.getDistance());
        }else{
            row.findViewById(R.id.p1_n).setVisibility(View.GONE);
            row.findViewById(R.id.p2_n).setVisibility(View.GONE);
            row.findViewById(R.id.more_details).setVisibility(View.GONE);
            row.findViewById(R.id.desc1).setVisibility(View.VISIBLE);
        }
       /* vh.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, EquipmentsproviderDetail.class)
                        .putExtra("equipment_name", equipments.getEquipmentName())
                        .putExtra("provider_name", equipments.getProviderName().get(0))
                        .putExtra("equipment_provider_id", equipments.getPId().get(0))
                        .putExtra("provider_equipment_cost_id", equipments.getProviderEquipmentCostId().get(0))
                        .putExtra("equipment_id", equipments.getEquipmentId().get(0))
                        .putExtra("monthly_cost", equipments.getMonthlyCost().get(0))
                        .putExtra("daily_cost", equipments.getDailyCost().get(0))
                        .putExtra("weekly_cost", equipments.getWeeklyCost().get(0))
                        .putExtra("equip_desc",equipments.getDescription())
                        .putExtra("provider_desc", equipments.getPDescription().get(0))
                        .putExtra("ratingbar",equipments.getAvgRating().get(0)));

                //  .putExtra("homeService", homeService));
            }
        });
        vh.relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, EquipmentsproviderDetail.class)
                        .putExtra("equipment_name", equipments.getEquipmentName())
                        .putExtra("provider_name", equipments.getProviderName().get(1))
                        .putExtra("equipment_provider_id", equipments.getPId().get(01))
                        .putExtra("provider_equipment_cost_id", equipments.getProviderEquipmentCostId().get(1))
                        .putExtra("equipment_id", equipments.getEquipmentId().get(1))
                        .putExtra("monthly_cost", equipments.getMonthlyCost().get(1))
                        .putExtra("daily_cost", equipments.getDailyCost().get(1))
                        .putExtra("weekly_cost", equipments.getWeeklyCost().get(1))
                        .putExtra("equip_desc",equipments.getDescription())
                        .putExtra("provider_desc", equipments.getPDescription().get(1))
                        .putExtra("ratingbar",equipments.getAvgRating().get(1)));
                //  .putExtra("homeService", homeService));
            }
        });*/


       // row.findViewById(R.id.p1_n).findViewById(R.id.provider_price).setOnClickListener(new View.OnClickListener() {
        row.findViewById(R.id.p1_n).findViewById(R.id.provider_book_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, EquipmentProvider.class)
                        .putExtra("equipment_name", equipments.getEquipment().getEquipmentName())
                        .putExtra("provider_name", equipments.getList().get(0).getEquipmentProvider().getProviderName())
                        .putExtra("equipment_provider_id", equipments.getList().get(0).getProviderEquipmentCost().getEquipmentProviderId())
                        .putExtra("provider_equipment_cost_id", equipments.getList().get(0).getProviderEquipmentCost().getProviderEquipmentCostId())
                        .putExtra("equipment_id", equipments.getList().get(0).getProviderEquipmentCost().getEquipmentId())
                        .putExtra("monthly_cost", equipments.getList().get(0).getProviderEquipmentCost().getMonthlyCost())
                        .putExtra("daily_cost", equipments.getList().get(0).getProviderEquipmentCost().getDailyCost())
                        .putExtra("weekly_cost", equipments.getList().get(0).getProviderEquipmentCost().getWeeklyCost()));
            }
        });
       // row.findViewById(R.id.p2_n).findViewById(R.id.provider_price).setOnClickListener(new View.OnClickListener() {
        row.findViewById(R.id.p2_n).findViewById(R.id.provider_book_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, EquipmentProvider.class)
                        .putExtra("equipment_name", equipments.getEquipment().getEquipmentName())
                        .putExtra("provider_name", equipments.getList().get(1).getEquipmentProvider().getProviderName())
                        .putExtra("equipment_provider_id", equipments.getList().get(1).getProviderEquipmentCost().getEquipmentProviderId())
                        .putExtra("provider_equipment_cost_id", equipments.getList().get(1).getProviderEquipmentCost().getProviderEquipmentCostId())
                        .putExtra("equipment_id", equipments.getList().get(1).getProviderEquipmentCost().getEquipmentId())
                        .putExtra("monthly_cost", equipments.getList().get(1).getProviderEquipmentCost().getMonthlyCost())
                        .putExtra("daily_cost", equipments.getList().get(1).getProviderEquipmentCost().getDailyCost())
                        .putExtra("weekly_cost", equipments.getList().get(1).getProviderEquipmentCost().getWeeklyCost()));
            }
        });

        row.findViewById(R.id.more_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, EquipmentDetails.class)
                        .putExtra("equipment", equipments));
            }
        });

        return row;
    }

       /* if (!checkPermissionForPhone())
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
                context.startActivity(new Intent(context, EquipmentDetails.class)
                        .putExtra("equipment", equipments));
            }
        });*/

      /*  row.findViewById(R.id.more_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, EquipmentDetails.class)
                        .putExtra("equipment", equipments));
            }
        });

        return row;
    }*/

    public class ViewHolder {
        TextView list_item_name;
        TextView p1_name,rating,rating1,rating2;
        TextView p2_name;
        TextView p1_price;
        TextView p2_price;
        TextView list_locality_name;
        TextView distance;
        RatingBar ratingbar;
        TextView test_cost_full;
        TextView list_locality_name1;
        TextView distance1;
        RatingBar ratingbar1;
        TextView test_cost_full1;

        public RelativeLayout relative,relative1;

    }

  /*  public boolean checkPermissionForPhone(){
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
    }*/
}
