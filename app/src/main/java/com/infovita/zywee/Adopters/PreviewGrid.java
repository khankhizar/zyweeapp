package com.infovita.zywee.Adopters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.infovita.zywee.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Khizarkhan on 02-02-2017.
 */

public class PreviewGrid extends BaseAdapter {
    private Context mContext;
//    private final ArrayList<Bitmap> Imageid;
//    public Bitmap bmp = new Bitmap();

    public static ArrayList<Uri> Imageid = new ArrayList<>();


    public PreviewGrid(Context c, ArrayList<Uri> Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        Log.d("GridSize", String.valueOf(Imageid.size()));
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Imageid.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    View row;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        row = convertView;

        CustomChildHolder holder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.custom_layout_for_image_grid, null);

            holder = new CustomChildHolder();
            holder.prescription_image = (ImageView) row.findViewById(R.id.prescription_preview);
            holder.cancel_button = (ImageButton) row.findViewById(R.id.delete_prescription_button);


            final Uri uri = Imageid.get(position);
//            Glide.with(mContext).load(uri).into(holder.prescription_image);

            Glide.with(mContext)
                    .load(uri)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.prescription_image);


            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "image/*");
                        mContext.startActivity(intent);
                    } catch (Exception e) {
                        Log.e("ImagePreview", "Error in showing image: " + e.toString());
                    }
                }
            });


            holder.cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Imageid.remove(position);//Remove item from grid
                    Log.d("GridView", "Deleting item at : " + position);
                    File fdelete = new File(uri.getPath());
                    if (fdelete.exists()) {
                        if (fdelete.delete()) {
                            System.out.println("file Deleted :" + uri.getPath());
                        } else {
                            System.out.println("file not Deleted :" + uri.getPath());
                        }
                    }
                    notifyDataSetChanged();//Refresh the grid


                }
            });

        } else {
            row = (View) convertView;
        }

        row.invalidate();
        return row;
    }


    public static class CustomChildHolder {
        ImageView prescription_image;
        ImageButton cancel_button;
    }
}
