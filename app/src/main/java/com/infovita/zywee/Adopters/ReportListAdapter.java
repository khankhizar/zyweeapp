package com.infovita.zywee.Adopters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infovita.zywee.Pojo.Image;
import com.infovita.zywee.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Khizarkhan on 09-11-2016.
 */

public class ReportListAdapter extends BaseAdapter {
    Context context;
    List<Image> appointmentsList;
    View row;
    int rename_flag;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    public ReportListAdapter(Context context, List<Image> appointmentsList,  int rename_flag) {
        this.context = context;
        this.appointmentsList = appointmentsList;
        this.rename_flag = rename_flag;
    }



    @Override
    public int getCount() {
        return appointmentsList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return appointmentsList.get(position) ;
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
            row = layoutInflater.inflate(R.layout.report_child, null);
            row.setTag(vh);
        } else {
            vh = (ReportListAdapter.ViewHolder) row.getTag();
        }

        vh.list_item_name = (TextView) row.findViewById(R.id.report_title);
        vh.date = (TextView) row.findViewById(R.id.report_date);

        vh.download = (ImageView) row.findViewById(R.id.report_download);

        vh.view = (LinearLayout) row.findViewById(R.id.view);

        final Image appointments = appointmentsList.get(position);
        vh.list_item_name.setText(appointments.getBucketImage().getFileName());


        String strDate=appointments.getBucketImage().getCreated();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date varDate=dateFormat.parse(strDate);
            dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
            vh.date.setText(dateFormat.format(varDate));
            //System.out.println("Date :"+dateFormat.format(varDate));
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


       /* vh.download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Intent.ACTION_VIEW);
                String image = appointments.getScanImage().getMobile();

                String id = appointments.getScanImage().getTestName();
                String pdfLink = "http://54.152.88.70/zywee/scanimages" + "/" + image + "/" + id;
               // String pdfLink = "https://www.zywee.com/scanreport/"+id;
                Log.d("info_pdf",pdfLink);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfLink));
                context.startActivity(browserIntent);
                *//**//*intent.setDataAndType(Uri.parse( "http://docs.google.com/viewer?url=" + pdfLink + id), "text/html");

                context.startActivity(intent);*//**//*
            }
        });
*/

        return row;
    }
    public class ViewHolder{
        TextView list_item_name,date;
        ImageView download;
        LinearLayout view;
    }


}
