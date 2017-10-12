package com.infovita.zywee.Adopters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infovita.zywee.Activities.BookDetailView;
import com.infovita.zywee.Pojo.Appointment;
import com.infovita.zywee.Pojo.AppointmentUser;
import com.infovita.zywee.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Khizarkhan on 24-10-2016.
 */

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.Holder> {

    private static String LOG_TAG = "BookRecyclerAdapter";
   /* Context context;
    List<Appointments> appointmentsList;*/
    private static ArrayList<Appointment> mDataset;
    private static ArrayList<AppointmentUser> Dataset;
    //private static ArrayList<Appointment> Data;
    private static MyClickListener myClickListener;

    Context context;
    private static List<Appointment> appointmentsList;
    View row;
    String dates;
    int rename_flag;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 5;

   /* public BookRecyclerAdapter(ArrayList<Appointments> dataSet, ArrayList<AppointmentUser> data) {
        mDataset = dataSet;
        Dataset = data;
        //Data = data1;
    }*/

    public BookRecyclerAdapter(Context context, List<Appointment> appointmentsList, int rename_flag) {
        this.context = context;
        this.appointmentsList = appointmentsList;
        this.rename_flag = rename_flag;
    }


    public static class Holder extends RecyclerView.ViewHolder implements View .OnClickListener {

        TextView Appointment_id,date;
       // TextView Appointment_id,date,time,inst_name,price;
        Context context;


        public Holder(View itemView) {
            super(itemView);
            Appointment_id = (TextView) itemView.findViewById(R.id.booking_id);
            date = (TextView) itemView.findViewById(R.id.booking_date);
          /*time = (TextView) itemView.findViewById(R.id.time);
          order_id = (TextView) itemView.findViewById(R.id.order_id);
          price = (TextView) itemView.findViewById(R.id.price);*/
         /* date = (TextView) itemView.findViewById(R.id.date);
          time = (TextView) itemView.findViewById(R.id.time);
          inst_name = (TextView) itemView.findViewById(R.id.order_id);
          price = (TextView) itemView.findViewById(R.id.price);*/
            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View v) {
            Intent detailView = new Intent(context, BookDetailView.class);
            detailView.putExtra("customer_name",  appointmentsList.get(getAdapterPosition()).getName());
            detailView.putExtra("appointment_title",appointmentsList.get(getAdapterPosition()).getAppointmentTitle());
           // Log.d("appointment_title : ",appointmentsList.get(getAdapterPosition()).getAppointmentTitle()+"");
            detailView.putExtra("appointment_id", (CharSequence) appointmentsList.get(getAdapterPosition()).getAppointmentId());
            //detailView.putExtra("order_id", (CharSequence) appointmentsList.get(getAdapterPosition()).getId());
           // detailView.putExtra("notes", (CharSequence) mDataset.get(getAdapterPosition()).getNotes());
            detailView.putExtra("end_datetime", (CharSequence) appointmentsList.get(getAdapterPosition()).getEndDatetime());
            detailView.putExtra("collection_cost", (CharSequence) appointmentsList.get(getAdapterPosition()).getCollectionCost());
            detailView.putExtra("m3", (CharSequence) appointmentsList.get(getAdapterPosition()).getStatus());
            detailView.putExtra("start_datetime", (CharSequence) appointmentsList.get(getAdapterPosition()).getEndDatetime());
            detailView.putExtra("customer",appointmentsList.get(getAdapterPosition()).getPhone());
            detailView.putExtra("institute_name",appointmentsList.get(getAdapterPosition()).getProviderName());
            detailView.putExtra("institute_address",appointmentsList.get(getAdapterPosition()).getAddress());
            detailView.putExtra("appointment_title1",(CharSequence) appointmentsList.get(getAdapterPosition()).getAppointmentTitle());
            detailView.putExtra("type",appointmentsList.get(getAdapterPosition()).getType());
            /* detailView.putExtra("customer_name1", Data.get(getAdapterPosition()).getName());
            detailView.putExtra("appointment_title2",Data.get(getAdapterPosition()).getAppointmentTitle());
            detailView.putExtra("appointment_id1", (CharSequence) Data.get(getAdapterPosition()).getAppointmentId());
            detailView.putExtra("end_datetime1", (CharSequence) Data.get(getAdapterPosition()).getEndDatetime());
            detailView.putExtra("collection_cost1", (CharSequence) Data.get(getAdapterPosition()).getCollectionCost());
            detailView.putExtra("m4", (CharSequence) Data.get(getAdapterPosition()).getStatus());
            detailView.putExtra("customer1",Data.get(getAdapterPosition()).getPhone());
*/
//            detailView.putExtra("phone_number"),mDataset.get()

            context.startActivity(detailView);


        }
    }



    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }


        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cancel_page, parent, false);
            Holder dataObjectHolder = new Holder(view);
            return dataObjectHolder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
          //  Log.d("data1 : ",appointmentsList.get(position).getAppointmentTitle()+"");
           // AppointmentUser appointments = Dataset.get(position);
            Appointment appointmentsDetails=appointmentsList.get(position);
            //Appointment appointment = Data.get(position);


            String title1 = appointmentsDetails.getAppointmentTitle();
            holder.Appointment_id.setText(title1);
          /* StringBuffer stringbf1 = new StringBuffer();
            Matcher m1 = Pattern.compile("([a-z])([a-z]*)",
                    Pattern.CASE_INSENSITIVE).matcher(title1);
            while (m1.find()) {
                m1.appendReplacement(stringbf1,
                        m1.group(1).toUpperCase() + m1.group(2).toLowerCase());
            }*/

           /* String title2 = appointment.getAppointmentTitle();
            StringBuffer stringbf2 = new StringBuffer();
            Matcher m2 = Pattern.compile("([a-z])([a-z]*)",
                    Pattern.CASE_INSENSITIVE).matcher(title2);
            while (m2.find()) {
                m2.appendReplacement(stringbf2,
                        m2.group(1).toUpperCase() + m2.group(2).toLowerCase());
            }
*/

          /*  String title = appointments.getAppointmentTitle();
            Appointments appoint = mDataset.get(position);
            title = appointments.getAppointmentTitle();
            StringBuffer stringbf = new StringBuffer();
            Matcher m = Pattern.compile("([a-z])([a-z]*)",
                    Pattern.CASE_INSENSITIVE).matcher(title);
            while (m.find()) {
                m.appendReplacement(stringbf,
                        m.group(1).toUpperCase() + m.group(2).toLowerCase());
            }*/
            /*String upperString = title.substring(0,1).toUpperCase() + title.substring(1);
            holder.Appointment_id.setText(upperString);*/
           // holder.Appointment_id.setText( appointments.getAppointmentTitle());

            // if (title.equals("")) {
                   // holder.Appointment_id.setText(m1.appendTail(stringbf1).toString());
              /*  } else {
                    holder.Appointment_id.setText(m.appendTail(stringbf).toString());

            }*//*else{
                holder.Appointment_id.setText(m2.appendTail(stringbf2).toString());
            }*/


           /* holder.Appointment_id.setText(m.appendTail(stringbf).toString());*/
            String strDate=appointmentsList.get(position).getEndDatetime()+"";
            Log.d("date",strDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    Date varDate=dateFormat.parse(strDate);
                    dateFormat=new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                    /*dateFormat=new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                    Date varDate=dateFormat.parse(strDate);*/
                    holder.date.setText(dateFormat.format(varDate));
                    Log.d("Date :",dateFormat.format(varDate));
                    //System.out.println("Date :"+dateFormat.format(varDate));
                }catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            //holder.date.setText(mDataset.get(position).getBookDatetime()+"");

          /*  dates = appoint.getEndDatetime().toString();
            String[] parts = dates.split(" ");
            holder.date.setText(parts[0]);
            holder.time.setText(parts[1]);
           // holder.inst_name.setText(ins_name);
            holder.price.setText("₹ "+ appoint.getCollectionCost().toString().replaceAll("\\.0*$", ""));
*/
        }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }

     /*   @Override
        public int getItemCount() {
            return mDataset.size();
        }*/


    public MyClickListener getItemClick() {
        return myClickListener;
    }

    public void setItemClick(MyClickListener itemClick) {
        this.myClickListener = itemClick;
    }




    public interface MyClickListener {
    public void onItemClick(int position, View v);
}
}
