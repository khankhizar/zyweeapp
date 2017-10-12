package com.infovita.zywee.Adopters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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
import com.infovita.zywee.Activities.HomeServiceDetails;
import com.infovita.zywee.Activities.HomeServieProvider;
import com.infovita.zywee.Pojo.HomeService;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

import java.util.List;

/**
 * Created by praveen on 13/7/16.
 */
public class HomeServiceListAdapter extends BaseAdapter {

    Context context;
    List<HomeService> homeServiceList;
    View row;
    int rename_flag;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    public HomeServiceListAdapter(Context context, List<HomeService> homeServiceList, int rename_flag) {
        this.context = context;
        this.homeServiceList = homeServiceList;
        this.rename_flag = rename_flag;
    }

    @Override
    public int getCount() {
        return homeServiceList.size();
    }

    @Override
    public Object getItem(int position) {
        return homeServiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        row = convertView;
        ViewHolder vh = new ViewHolder();
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.home_service, null);
            row.setTag(vh);
        } else {
            vh = (ViewHolder) row.getTag();
        }


        vh.list_item_name = (TextView) row.findViewById(R.id.name);
       // vh.p1_n  = (CardView) row.findViewById(R.id.p1_n);
       // vh.p2_n  = (CardView) row.findViewById(R.id.p2_n);
        vh.p1_name = (TextView)  row.findViewById(R.id.p1_n).findViewById(R.id.p1_name);
        vh.p1_price = (TextView) row.findViewById(R.id.p1_n).findViewById(R.id.p1_price);
        vh.p2_name = (TextView)  row.findViewById(R.id.p2_n).findViewById(R.id.p1_name);
        vh.p2_price = (TextView)  row.findViewById(R.id.p2_n).findViewById(R.id.p1_price);
        vh.p1_rating = (RatingBar)  row.findViewById(R.id.p1_n).findViewById(R.id.ratingBar);
        vh.p2_rating = (RatingBar) row.findViewById(R.id.p2_n).findViewById(R.id.ratingBar);
//        vh.relative = (RelativeLayout) row.findViewById(R.id.p1_n).findViewById(R.id.lay4);
//        vh.relative1 = (RelativeLayout) row.findViewById(R.id.p2_n).findViewById(R.id.lay4);


        final HomeService homeService = (HomeService) getItem(position);
        vh.list_item_name.setText(homeService.getHomeServiceCategory().getServiceName());


        Glide.with(context)
                .load(Serverdatas.image_url + "homeService/" + homeService.getHomeServiceCategory().getId() + ".jpg")
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.zywee_logo)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // do something
                       /* Log.e("info_img",e+"");
                        Glide.with(context)
                                .load(Serverdatas.image_url + "homeService/" + homeService.getId() + ".jpg")
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
        int no_of_providers = homeService.getList().size();
        if (no_of_providers > 1) {
            row.findViewById(R.id.p2_n).setVisibility(View.VISIBLE);
            vh.p1_name.setText(homeService.getList().get(0).getServiceProvider().getProviderName());
            vh.p1_price.setText("₹" + homeService.getList().get(0).getProviderServiceCharge().getCost() + "/-");
           // Log.d("rating :",homeService.getAvgRating().get(0));
            if(homeService.getList().get(0).getServiceProvider().getAvgRating().equals("0.00")){
                vh.p1_rating.setRating(Float.parseFloat("4.00"));
            }else{
                vh.p1_rating.setRating(Float.parseFloat(homeService.getList().get(0).getServiceProvider().getAvgRating()));
            }
            //vh.p1_rating.setRating(Float.parseFloat(homeService.getAvgRating().get(0)));
            vh.p2_name.setText(homeService.getList().get(1).getServiceProvider().getProviderName());
            if (homeService.getList().size() > 1)
                vh.p2_price.setText("₹" + homeService.getList().get(1).getProviderServiceCharge().getCost() + "/-");
            else vh.p2_price.setText("N/A");
            if(homeService.getList().get(1).getServiceProvider().getAvgRating().equals("0.00")){
                vh.p2_rating.setRating(Float.parseFloat("4.00"));
            }else{
                vh.p2_rating.setRating(Float.parseFloat(homeService.getList().get(1).getServiceProvider().getAvgRating()));
            }
           // vh.p2_rating.setRating(Float.parseFloat(homeService.getAvgRating().get(1)));
        } else if (no_of_providers == 1) {
            row.findViewById(R.id.p2_n).setVisibility(View.GONE);
            vh.p1_name.setText(homeService.getList().get(0).getServiceProvider().getProviderName());
            vh.p1_price.setText("₹" + homeService.getList().get(0).getProviderServiceCharge().getCost() + "/-");
            //vh.p1_rating.setRating(Float.parseFloat(homeService.getAvgRating().get(0)));
            if(homeService.getList().get(0).getServiceProvider().getAvgRating().equals("0.00")){
                vh.p1_rating.setRating(Float.parseFloat("4.00"));
            }else{
                vh.p1_rating.setRating(Float.parseFloat(homeService.getList().get(0).getServiceProvider().getAvgRating()));
            }
        } else{
            row.findViewById(R.id.p1_n).setVisibility(View.GONE);
            row.findViewById(R.id.p2_n).setVisibility(View.GONE);
            row.findViewById(R.id.more_details).setVisibility(View.GONE);
            row.findViewById(R.id.desc1).setVisibility(View.VISIBLE);
        }
       /* vh.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, HomeProviderDetails.class)
                        .putExtra("provider_id", homeService.getServiceProviderId().get(0))
                        .putExtra("service_name", homeService.getServiceName())
                        .putExtra("provider_name", homeService.getProviderName().get(0))
                        .putExtra("provider_desc", homeService.getServiceProvidersDescription().get(0))
                        .putExtra("item_desc",homeService.getDescription())
                        .putExtra("cost",homeService.getCost().get(0))
                        .putExtra("service_id", homeService.getId())
                        .putExtra("ratingbar", homeService.getAvgRating().get(0))
                        .putExtra("provider_home_services_id", homeService.getProviderHomeServiceId().get(0))
                        .putExtra("provider_service_charge_id", homeService.getHomeServiceChargeId().get(0)));
                        //  .putExtra("homeService", homeService));
            }
        });
        vh.relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, HomeProviderDetails.class)
                        .putExtra("provider_id", homeService.getServiceProviderId().get(1))
                        .putExtra("service_name", homeService.getServiceName())
                        .putExtra("provider_name", homeService.getProviderName().get(1))
                        .putExtra("provider_desc", homeService.getServiceProvidersDescription().get(1))
                        .putExtra("item_desc",homeService.getDescription())
                        .putExtra("cost",homeService.getCost().get(1))
                        .putExtra("service_id", homeService.getId())
                        .putExtra("ratingbar", homeService.getAvgRating().get(1))
                        .putExtra("home_service_charges_name",homeService.getHomeServiceChargesName().get(1))
                        .putExtra("provider_home_services_id", homeService.getProviderHomeServiceId().get(1))
                        .putExtra("provider_service_charge_id", homeService.getHomeServiceChargeId().get(1)));
                //  .putExtra("homeService", homeService));
            }
        });
*/


        row.findViewById(R.id.p1_n).findViewById(R.id.p1_book_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, HomeServieProvider.class)
                        .putExtra("provider_id", homeService.getList().get(0).getProviderHomeService().getServiceProviderId())
                        .putExtra("service_name", homeService.getHomeServiceCategory().getServiceName())
                        .putExtra("provider_name", homeService.getList().get(0).getServiceProvider().getProviderName())
                        .putExtra("service_id", homeService.getHomeServiceCategory().getId())
                        .putExtra("provider_home_services_id", homeService.getList().get(0).getProviderHomeService().getServiceProviderId())
                        .putExtra("provider_service_charge_id", homeService.getList().get(0).getProviderServiceCharge().getHomeServiceChargeId()));
            }
        });

        row.findViewById(R.id.p2_n).findViewById(R.id.p1_book_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, HomeServieProvider.class)
                        .putExtra("provider_id", homeService.getList().get(1).getProviderHomeService().getServiceProviderId())
                        .putExtra("service_name", homeService.getHomeServiceCategory().getServiceName())
                        .putExtra("provider_name", homeService.getList().get(1).getServiceProvider().getProviderName())
                        .putExtra("service_id", homeService.getHomeServiceCategory().getId())
                        .putExtra("provider_home_services_id", homeService.getList().get(1).getProviderHomeService().getServiceProviderId())
                        .putExtra("provider_service_charge_id", homeService.getList().get(1).getProviderServiceCharge().getHomeServiceChargeId()));
            }
        });
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

        row.findViewById(R.id.name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, HomeServiceDetails.class)
                        .putExtra("homeService", homeService));
            }
        });

*/
        row.findViewById(R.id.more_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, HomeServiceDetails.class)
                        .putExtra("homeService", homeService));
            }
        });


        return row;
    }

    public class ViewHolder {
        TextView list_item_name, p1_name, p2_name, p1_price, p2_price;
        RatingBar p1_rating,p2_rating;
        CardView p1_n,p2_n;
        public RelativeLayout relative,relative1;
    }

    /*public boolean checkPermissionForPhone(){
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
    }*/
}
