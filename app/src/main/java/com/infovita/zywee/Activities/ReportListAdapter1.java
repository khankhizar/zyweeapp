package com.infovita.zywee.Activities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infovita.zywee.Pojo.Image;
import com.infovita.zywee.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khizarkhan on 11-01-2017.
 */
public class ReportListAdapter1 extends BaseAdapter {
    Context context;
    List<Image> imagesList;
    View row;
    int rename_flag;

    private ArrayList mData = new ArrayList();

    private int layoutResourceId;

    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    public ReportListAdapter1(Context context, int layoutResourceId, List<Image> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
        this.rename_flag = rename_flag;
        this.layoutResourceId = layoutResourceId;
    }




    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public Object getItem(int position) {

        return imagesList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        row = convertView;

        final Image ambulance = (Image) getItem(i);

        ReportListAdapter1.ViewHolder vh = new ReportListAdapter1.ViewHolder();
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            vh.titleTextView = (TextView) row.findViewById(R.id.grid_item_title);
            vh.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
           /* LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.report_child, null);*/
            row.setTag(vh);
        } else {
            vh = (ReportListAdapter1.ViewHolder) row.getTag();
        }


        Image img = imagesList.get(i);

        String image = img.getBucketImage().getMobile();

        Log.d("kkkjkl :", img.getBucketImage().getFileName());
        String imgLink = "http://54.152.88.70/zywee/scanimages" + "/" + image + "/" + img.getBucketImage().getFileName();
        Log.d("link :", imgLink);
        if(img.getBucketImage().getFileName().endsWith("jpg")) {

            // vh.imageView.setImageURI(Uri.parse(imgLink));
            Glide.with(context).load(imgLink).into(vh.imageView);
        }else{
            Glide.with(context).load(R.drawable.download).into(vh.imageView);
        }
        vh.titleTextView.setVisibility(View.VISIBLE);
        vh.titleTextView.setText(img.getBucketImage().getFileName().replace(".jpg","").replace(".pdf",""));



        return row;
    }





    public class ViewHolder{
        TextView titleTextView;
        ImageView imageView;
       /* TextView list_item_name,date;
        ImageView download;*/
    }

}
