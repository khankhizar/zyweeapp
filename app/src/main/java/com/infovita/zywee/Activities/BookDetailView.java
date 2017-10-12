package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.Pojo.EntityHealthInstitute;
import com.infovita.zywee.Pojo.GetBookingDetail;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Sharedvalues.ServiceHandler;
import com.infovita.zywee.Supports.InternetStatus;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.infovita.zywee.Network.ServerCall.sd;


/**
 * Created by Khizarkhan on 24-10-2016.
 */
public class BookDetailView extends AppCompatActivity {

    String parent;
    TextView customer_name, status;
    TextView customer_number;
    TextView price;
    TextView order_id;
    TextView address,institute_name,institute_address;
    TextView Time;
    TextView appointment_title,status_textview;
    Context context;
   private static String appointment_id ;
    String id;
    private ProgressDialog pDialog;
    Intent i = getIntent();
    String date;
    private String TAG;

    ProgressDialog dialog;

    byte[] data;
    String AID;
    String type;
    TextView Cancel;

    private static ArrayList<EntityHealthInstitute> Dataset;

    String currentDateTime;
   // private String appointment_id =  i.getExtras().getString("appointment_id");

//    String date, time;
    LinearLayout layout;
    Calendar c;
    static Date currentDate;
    Date varDate;

    public BookDetailView() throws ParseException {
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_view);


        setup_toolbar();
        Intent i = getIntent();
        i.getStringExtra("customer_name");
        //new getInstituteName().execute();


        layout = (LinearLayout) findViewById(R.id.li);


        customer_name = (TextView) findViewById(R.id.customer_name);
        customer_number = (TextView) findViewById(R.id.customer_phone);
        price = (TextView) findViewById(R.id.price);
        status_textview = (TextView) findViewById(R.id.status_textview);
        order_id = (TextView) findViewById(R.id.order_id);
        address = (TextView) findViewById(R.id.dated);
        Time = (TextView) findViewById(R.id.time);
        appointment_title = (TextView) findViewById(R.id.appointment_title);
        institute_name =(TextView) findViewById(R.id.institute_name);
        institute_address = (TextView) findViewById(R.id.institute_add);
        Cancel = (TextView)findViewById(R.id.cancel);
        type = i.getExtras().getString("type");
        appointment_id = i.getExtras().getString("appointment_id");
       // Log.d("main_type1", " > " + appointment_id);
        id = i.getExtras().getString("order_id");
       // Log.d("order_id :",">" + order_id);

        String status =i.getExtras().getString("m3");
        if(status.equals("B")){
            status_textview.setText("Booked");
            layout.setVisibility(View.VISIBLE);
        }else if(status.equals("C")){
            status_textview.setText("Cancelled");
            layout.setVisibility(View.GONE);
        }


        customer_name.setText(i.getExtras().getString("customer_name"));
        customer_number.setText(i.getExtras().getString("customer"));
        institute_name.setText(i.getExtras().getString("institute_name"));
        String addresses = i.getExtras().getString("institute_address");
        if(addresses.equals("")){
            institute_address.setText("N/A");
        }else {
            institute_address.setText(addresses);
        }
        String title =i.getExtras().getString("appointment_title");
        appointment_title.setText(title);
       /* StringBuffer stringbf = new StringBuffer();
        Matcher m = Pattern.compile("([a-z])([a-z]*)",
                Pattern.CASE_INSENSITIVE).matcher(title);
        while (m.find()) {
            m.appendReplacement(stringbf,
                    m.group(1).toUpperCase() + m.group(2).toLowerCase());
        }

        String title1 =i.getExtras().getString("appointment_title1");
        StringBuffer stringbf1 = new StringBuffer();
        Matcher m1= Pattern.compile("([a-z])([a-z]*)",
                Pattern.CASE_INSENSITIVE).matcher(title1);
        while (m1.find()) {
            m1.appendReplacement(stringbf1,
                    m1.group(1).toUpperCase() + m1.group(2).toLowerCase());
        }
        if (title.equals("")){
            appointment_title.setText(m1.appendTail(stringbf1).toString());
        }else{
            appointment_title.setText(m.appendTail(stringbf).toString());
        }*/
       // appointment_title.setText(m.appendTail(stringbf).toString());
       // appointment_title.setText((StringUtils.capitalize(title.toLowerCase().trim())));
       // appointment_title.setText(i.getExtras().getString("appointment_title"));
        price.setText("₹ " + i.getExtras().getString("collection_cost").replaceAll("\\.0*$", ""));
        //institute_name.setText(i.getExtras().getString("health_institute_name"));
        order_id.setText(appointment_id);

        c = Calendar.getInstance();
        //c.add(Calendar.DAY_OF_YEAR,1);
        currentDate = c.getTime();

        date = i.getExtras().getString("end_datetime");
        String strDate=date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            varDate=dateFormat.parse(strDate);
            dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
            address.setText(dateFormat.format(varDate));
            dateFormat=new SimpleDateFormat("hh:mm a");
            Time.setText(dateFormat.format(varDate));
            //System.out.println("Date :"+dateFormat.format(varDate));
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        /*try{
            String[] parts = date.split(" ");
            address.setText(parts[0]);
            Time.setText(parts[1]);
        }catch (Exception e){

        }*/
        if (varDate.compareTo(currentDate) == -1) {
            Cancel.setVisibility(View.GONE);
        }else{
            Cancel.setVisibility(View.VISIBLE);
        }

       // getAmbulanceList();


        //TextView Cancel = (TextView)findViewById(R.id.cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  new AlertDialog.Builder(BookDetailView.this)
                        .setTitle("Cancel Appointment")
                        .setMessage("Would you like to Cancel the appointment" )
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (isOnline())
                                            new cancel().execute();
                                    }
                                })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                               // cancelDialog1();
                                // do nothing
                            }
                        })
                        .show();*/

                if (isOnline())
                    if(type.equals("D"))
                        new cancel().execute();
                else if(type.equals("H"))
                        new servicecancel().execute();
                else if(type.equals("T"))
                        new ambulancecancel().execute();
                else if(type.equals("E"))
                        new equipmentcancel().execute();

            }


        });
       // getAmbulanceList(+++++);
       // getList();

    }


    private void cancelDialog1() {
        dialog.cancel();
    }


    public void setup_toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + "Detail" + "</small>"));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private static String server_url;
    String jsonData;

    private class cancel extends AsyncTask<Void, Void, Integer> {

//        ProgressDialog dialog;
        Serverdatas preferences;

            protected void onPreExecute() {

                server_url = sd.url + "zywee_app/webservices/cancel";
                super.onPreExecute();
                preferences = new Serverdatas();
                dialog = new ProgressDialog(BookDetailView.this);
                dialog.setMessage("Loading..");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                // Showing progress dialog
            }

            @Override
            protected Integer doInBackground(Void... arg0) {
                Serverdatas sd=Serverdatas.getSingletonObject();

                // Creating service handler class instance
                ServiceHandler sh = new ServiceHandler();
                List<NameValuePair> nameValuePair = new ArrayList<>(2);
                //AID = appointment_id;
                /*try {

                    data = appointment_id.getBytes("UTF-8");

                    AID = Base64.encodeToString(data, Base64.DEFAULT);

                    Log.i("Base 64 ", AID);

                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();

                }
*/
                nameValuePair.add(new BasicNameValuePair("AID", appointment_id));
//            Log.d("Response: ", "> " + phone_number);


                // Making a request to url and getting response
                try {
                    jsonData = sh.makeServiceCall(server_url, ServiceHandler.POST, nameValuePair);
                    jsonData = jsonData.trim();
                    Log.d("Response: ", "> " + jsonData);

                    return 1;
                } catch (Exception e) {
                    Log.d("Response: ", "> " + e);
//                pDialog.cancel();
                    return -1;
                }
            }

            public void onPostExecute(Integer arg) {
                if (arg == 1) {
                    if (jsonData.equals("1")) {
                        /*Toast.makeText(BookDetailView.this, "Appointment cancelled sucessfully",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        dialog.dismiss();*/
                        new AlertDialog.Builder(BookDetailView.this)
                                .setTitle("Alert")
                                .setMessage("Appointment cancelled sucessfully")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                                /*.setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                        cancelDialog();
                                        // do nothing
                                    }
                                })*/
                                .show();
                    } else if (jsonData.equals("2")) {
                        new AlertDialog.Builder(BookDetailView.this)
                                .setTitle("Alert")
                                .setMessage("something went wrong please try again")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                                /*.setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                        cancelDialog();
                                        // do nothing
                                    }
                                })*/
                                .show();

                    }else if (jsonData.equals("3")) {
                        new AlertDialog.Builder(BookDetailView.this)
                                .setTitle("Alert")
                                .setMessage("Appointment does not exist")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                               /* .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                        cancelDialog();
                                        // do nothing
                                    }
                                })*/
                                .show();
                    }else if (jsonData.equals("C")) {
                        new AlertDialog.Builder(BookDetailView.this)
                                .setTitle("Alert")
                                .setMessage("Appointment already cancelled")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                        // continue with delete
                                    }
                                })
                                /*.setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                        cancelDialog();
                                        // do nothing
                                    }
                                })*/
                                .show();



                    } /*else {
                       *//* if (jsonData.equals("C")) {
                            new AlertDialog.Builder(BookDetailView.this)
                                    .setTitle("Cancelled")
                                    .setMessage("Your Appointment has been Cancelled")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    })
                                   *//**//* .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                            cancelDialog();
                                            // do nothing
                                        }
                                    })*//**//*
                                    .show();*//*
                       *//* Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        dialog.dismiss();*//*
                        }*/

                   /* Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/

                    }
                }
            }

        private void cancelDialog() {
            dialog.cancel();
        }


    private class ambulancecancel extends AsyncTask<Void, Void, Integer> {

        //        ProgressDialog dialog;
        Serverdatas preferences;

        protected void onPreExecute() {

            server_url = sd.url + "zywee_app/webservices/ambulancecancel";
            super.onPreExecute();
            preferences = new Serverdatas();
            dialog = new ProgressDialog(BookDetailView.this);
            dialog.setMessage("Loading..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            // Showing progress dialog
        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            Serverdatas sd=Serverdatas.getSingletonObject();

            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePair = new ArrayList<>(2);
           // AID = appointment_id;
           /* try {

                data = appointment_id.getBytes("UTF-8");

                AID = Base64.encodeToString(data, Base64.DEFAULT);

                Log.i("Base 64 ", AID);

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();

            }*/

            nameValuePair.add(new BasicNameValuePair("AID", appointment_id));
//            Log.d("Response: ", "> " + phone_number);


            // Making a request to url and getting response
            try {
                jsonData = sh.makeServiceCall(server_url, ServiceHandler.POST, nameValuePair);
                jsonData = jsonData.trim();
                Log.d("Response: ", "> " + jsonData);

                return 1;
            } catch (Exception e) {
                 Log.d("Response failure: ", "> " + e);
//                pDialog.cancel();
                return -1;
            }
        }

        public void onPostExecute(Integer arg) {
            if (arg == 1) {
                if (jsonData.equals("1")) {
                    new AlertDialog.Builder(BookDetailView.this)
                            .setTitle("Alert")
                            .setMessage("Appointment cancelled sucessfully")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                           /* .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                    cancelDialog();
                                    // do nothing
                                }
                            })*/
                            .show();
                }else if (jsonData.equals("2")) {
                    new AlertDialog.Builder(BookDetailView.this)
                            .setTitle("Alert")
                            .setMessage("something went wrong please try again")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                            /*.setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                    cancelDialog();
                                    // do nothing
                                }
                            })*/
                            .show();

                }else if (jsonData.equals("3")) {
                    new AlertDialog.Builder(BookDetailView.this)
                            .setTitle("Alert")
                            .setMessage("Appointment does not exist")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                            /*.setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                    cancelDialog();
                                    // do nothing
                                }
                            })*/
                            .show();
                }else if (jsonData.equals("C")) {
                    new AlertDialog.Builder(BookDetailView.this)
                            .setTitle("Alert")
                            .setMessage("Appointment already cancelled")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                           /* .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                    cancelDialog();
                                    // do nothing
                                }
                            })*/
                            .show();



                } /*else {
                       *//* if (jsonData.equals("C")) {
                            new AlertDialog.Builder(BookDetailView.this)
                                    .setTitle("Cancelled")
                                    .setMessage("Your Appointment has been Cancelled")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    })
                                   *//**//* .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                            cancelDialog();
                                            // do nothing
                                        }
                                    })*//**//*
                                    .show();*//*
                    Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    dialog.dismiss();
                }*/

                   /* Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/

            }
        }
    }

    private class equipmentcancel extends AsyncTask<Void, Void, Integer> {

        //        ProgressDialog dialog;
        Serverdatas preferences;

        protected void onPreExecute() {

            server_url = sd.url + "zywee_app/webservices/equipmentcancel";
            super.onPreExecute();
            preferences = new Serverdatas();
            dialog = new ProgressDialog(BookDetailView.this);
            dialog.setMessage("Loading..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            // Showing progress dialog
        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            Serverdatas sd=Serverdatas.getSingletonObject();

            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePair = new ArrayList<>(2);
            //AID = appointment_id;
           /* try {

                data = appointment_id.getBytes("UTF-8");

                AID = Base64.encodeToString(data, Base64.DEFAULT);

                Log.i("Base 64 ", AID);

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();

            }*/

            nameValuePair.add(new BasicNameValuePair("AID", appointment_id));
//            Log.d("Response: ", "> " + phone_number);


            // Making a request to url and getting response
            try {
                jsonData = sh.makeServiceCall(server_url, ServiceHandler.POST, nameValuePair);
                jsonData = jsonData.trim();
                Log.d("Response: ", "> " + jsonData);

                return 1;
            } catch (Exception e) {
                 Log.d("Response: ", "> " + e);
//                pDialog.cancel();
                return -1;
            }
        }

        public void onPostExecute(Integer arg) {
            if (arg == 1) {
                if (jsonData.equals("1")) {
                    new AlertDialog.Builder(BookDetailView.this)
                            .setTitle("Alert")
                            .setMessage("Appointment cancelled sucessfully")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                           /* .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                    cancelDialog();
                                    // do nothing
                                }
                            })*/
                            .show();
                } else if (jsonData.equals("2")) {
                    new AlertDialog.Builder(BookDetailView.this)
                            .setTitle("Alert")
                            .setMessage("something went wrong please try again")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                            /*.setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                    cancelDialog();
                                    // do nothing
                                }
                            })*/
                            .show();

                }else if (jsonData.equals("3")) {
                    new AlertDialog.Builder(BookDetailView.this)
                            .setTitle("Alert")
                            .setMessage("Appointment does not exist")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                           /* .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                    cancelDialog();
                                    // do nothing
                                }
                            })*/
                            .show();
                }else if (jsonData.equals("C")) {
                    new AlertDialog.Builder(BookDetailView.this)
                            .setTitle("Alert")
                            .setMessage("Appointment already cancelled")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                            /*.setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                    cancelDialog();
                                    // do nothing
                                }
                            })*/
                            .show();



                }/* else {
                       *//* if (jsonData.equals("C")) {
                            new AlertDialog.Builder(BookDetailView.this)
                                    .setTitle("Cancelled")
                                    .setMessage("Your Appointment has been Cancelled")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    })
                                   *//**//* .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                            cancelDialog();
                                            // do nothing
                                        }
                                    })*//**//*
                                    .show();*//*
                    Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    dialog.dismiss();
                }*/

                   /* Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/

            }
        }
    }

    private class servicecancel extends AsyncTask<Void, Void, Integer> {

        //        ProgressDialog dialog;
        Serverdatas preferences;

        protected void onPreExecute() {

            server_url = sd.url + "zywee_app/webservices/servicecancel";
            super.onPreExecute();
            preferences = new Serverdatas();
            dialog = new ProgressDialog(BookDetailView.this);
            dialog.setMessage("Loading..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            // Showing progress dialog
        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            Serverdatas sd=Serverdatas.getSingletonObject();

            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePair = new ArrayList<>(2);
            //AID = appointment_id;
            /*try {

                data = appointment_id.getBytes("UTF-8");

                AID = Base64.encodeToString(data, Base64.DEFAULT);

                Log.i("Base 64 ", AID);

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();

            }*/

            nameValuePair.add(new BasicNameValuePair("AID", appointment_id));
//            Log.d("Response: ", "> " + phone_number);


            // Making a request to url and getting response
            try {
                jsonData = sh.makeServiceCall(server_url, ServiceHandler.POST, nameValuePair);
                jsonData = jsonData.trim();
                Log.d("Response: ", "> " + jsonData);

                return 1;
            } catch (Exception e) {
                 Log.d("Response: ", "> " + e);
//                pDialog.cancel();
                return -1;
            }
        }

        public void onPostExecute(Integer arg) {
            if (arg == 1) {
                if (jsonData.equals("1")) {
                    new AlertDialog.Builder(BookDetailView.this)
                            .setTitle("Alert")
                            .setMessage("Appointment cancelled sucessfully")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                           /* .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                    cancelDialog();
                                    // do nothing
                                }
                            })*/
                            .show();
                } else if (jsonData.equals("2")) {
                    new AlertDialog.Builder(BookDetailView.this)
                            .setTitle("Alert")
                            .setMessage("something went wrong please try again")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                           /* .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                    cancelDialog();
                                    // do nothing
                                }
                            })*/
                            .show();

                }else if (jsonData.equals("3")) {
                    new AlertDialog.Builder(BookDetailView.this)
                            .setTitle("Alert")
                            .setMessage("Appointment does not exist")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                })
                           /* .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                    cancelDialog();
                                    // do nothing
                                }
                            })*/
                            .show();
                }else if (jsonData.equals("C")) {
                        new AlertDialog.Builder(BookDetailView.this)
                                .setTitle("Alert")
                                .setMessage("Appointment already cancelled")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                               /* .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                        cancelDialog();
                                        // do nothing
                                    }
                                })*/
                                .show();



                    } /*else {
                       *//* if (jsonData.equals("C")) {
                            new AlertDialog.Builder(BookDetailView.this)
                                    .setTitle("Cancelled")
                                    .setMessage("Your Appointment has been Cancelled")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    })
                                   *//**//* .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
                                            cancelDialog();
                                            // do nothing
                                        }
                                    })*//**//*
                                    .show();*//*
                    Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    dialog.dismiss();
                }*/

                   /* Intent intent = new Intent(BookDetailView.this, LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/

            }
        }
    }






    private void getAmbulanceList() {
       // showProgressDiaalog();

        pDialog = new ProgressDialog(BookDetailView.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<GetBookingDetail> call = serverCall.getDetail(appointment_id);

        //Log.d("main_type1", " > " + appointment_id);

        call.enqueue(new Callback<GetBookingDetail>() {
            @Override
            public void onResponse(Call<GetBookingDetail> call, retrofit2.Response<GetBookingDetail> response) {
                if (response.isSuccessful()) {
                  // EntityHealthInstitute res = response.body().getEntityHealthInstitute();
                   // Log.d("retro", "response : " + res.getHealthInstituteName());
                    pDialog.cancel();
                    institute_name.setText(response.body().getEntityHealthInstitute().getHealthInstituteName());
                    if(response.body().getEntityInstituteAddress().getInstituteAddressLine1()!= null) {
                        institute_address.setText(response.body().getEntityInstituteAddress().getInstituteAddressLine1() + response.body().getEntityInstituteAddress().getInstituteAddressLine2());
                    }else if(response.body().getEntityInstituteAddress().getInstituteAddressLine2()== null){
                        institute_address.setText(response.body().getEntityInstituteAddress().getInstituteAddressLine1());
                    }else{
                        institute_address.setText(response.body().getEntityInstituteAddress().getInstituteAddressLine2());
                    }


                }
            }

            @Override
            public void onFailure(Call<GetBookingDetail> call, Throwable t) {
                //Log.d("retro", "response : " + t.getMessage());
                pDialog.cancel();
                //  hideProgressDiaalog();
            }
        });
    }




    private void showProgressDiaalog() {
        pDialog.show();
    }

    private void hideProgressDiaalog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.cancel();
    }




    private boolean isOnline() {
        boolean status = InternetStatus.getInstance(getApplicationContext()).isOnline();
        if (status)
            return status;
        else {
            snakbarMessage("Please connect to Internet");
            return status;
        }
    }

    public void snakbarMessage(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

    public void snakbarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new cancel();
                    }
                });
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.rgb(0, 111, 192));//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

    // String current_date = DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_12HOUR);




    @Override
    public void onBackPressed() {
        BookDetailView.this.finish();
    }

}
