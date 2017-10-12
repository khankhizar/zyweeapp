package com.infovita.zywee.Adopters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.infovita.zywee.Pojo.Appointment;
import com.infovita.zywee.R;

import java.util.List;

import static com.infovita.zywee.R.id.date;


/**
 * Created by Khizarkhan on 21-10-2016.
 */
public class BookListAdapter extends BaseAdapter {
    Context context;
    List<Appointment> appointmentsList;
    View row;
    String dates;
    int rename_flag;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

    public BookListAdapter(Context context, List<Appointment> appointmentsList, int rename_flag) {
        this.context = context;
        this.appointmentsList = appointmentsList;
        this.rename_flag = rename_flag;
    }

    @Override
    public int getCount() {
        return appointmentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return appointmentsList.get(position);
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
            row = layoutInflater.inflate(R.layout.cancel_page, null);
            row.setTag(vh);
        } else {
            vh = (BookListAdapter.ViewHolder) row.getTag();
        }

        vh.list_item_name = (TextView) row.findViewById(R.id.booking_id);
        vh.date = (TextView) row.findViewById(date);
       // vh.time = (TextView) row.findViewById(R.id.time);

        final Appointment appointments = appointmentsList.get(position);
        vh.list_item_name.setText(appointments.getAppointmentTitle().toString());
        vh.date.setText(appointments.getEndDatetime().toString());
     //   vh.price.setText(appointments.getCollectionCost().toString());
       /* vh.inst_name.setText(appointments.getCity().toString());
        Log.d("ins:", appointments.getAppointmentId().toString());
        vh.price.setText( appointments.getCollectionCost().toString());
        dates = appointments.getStartDatetime().toString();
        String[] parts = dates.split(" ");
        vh.date.setText(parts[0]);
        vh.time.setText(parts[1]);*/
       // vh.date.setText(appointments.getAppointmentId().toString());

        return row;
    }
    public class ViewHolder{
        TextView list_item_name,date,inst_name,price,time;
    }
}
