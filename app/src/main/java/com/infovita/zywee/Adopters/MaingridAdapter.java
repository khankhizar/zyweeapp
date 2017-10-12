package com.infovita.zywee.Adopters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.infovita.zywee.Activities.LandingActivity;
import com.infovita.zywee.Activities.MaincategoriesActivity;
import com.infovita.zywee.Activities.Medicine;
import com.infovita.zywee.Activities.MyReports;
import com.infovita.zywee.R;

public class MaingridAdapter extends BaseAdapter{

    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;

    private FirebaseAnalytics mFirebaseAnalytics;

    public MaingridAdapter(LandingActivity mainActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        }catch (Exception e){
           // Log.d("err","firebase");
        }
        // Obtain the FirebaseAnalytics instance.


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.mainlists, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String type = result[position];
                Intent intent = new Intent(context,MaincategoriesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("category_clicked",type);
                mFirebaseAnalytics.logEvent("category_clicked",bundle);
                mFirebaseAnalytics.setUserProperty("category_clicked",type);
                switch(type){
                    case "Medical Tests":
                        intent.putExtra("default_view",0);
                        context.startActivity(intent);
                        break;
                    case "Health Packages":
                        intent.putExtra("default_view",1);
                        context.startActivity(intent);
                        break;
                    case "Doctors":
                        intent.putExtra("default_view",2);
                        context.startActivity(intent);
                        break;
                    case "Medical Equipments":
                        intent.putExtra("default_view",3);
                        context.startActivity(intent);
                        break;
                    case "Ambulance":
                        intent.putExtra("default_view",4);
                        context.startActivity(intent);
                        break;
                    case "Home Services":
                        intent.putExtra("default_view",5);
                        context.startActivity(intent);
                        break;
                    case "My Reports":
                        Intent intent1 = new Intent(context,MyReports.class);
                        context.startActivity(intent1);
                        break;
                    case "Medicine":
                        Intent intent2 = new Intent(context,Medicine.class);
                        context.startActivity(intent2);
                    default:
                        //snakbarMessage(v,"Coming Soon");
                        break;
                }
                /*if (result[position] == "Medical Tests") {
                    context.startActivity(intent);
                }else{
                    snakbarMessage(v,"Coming Soon");
                }*/
            }
        });

        return rowView;
    }

    public void snakbarMessage(View view,String message){
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.rgb(0, 111, 192));//change Snackbar's background color;
        TextView textView = (TextView)snackbarView .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

}