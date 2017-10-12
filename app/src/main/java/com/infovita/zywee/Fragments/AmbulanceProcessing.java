package com.infovita.zywee.Fragments;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.infovita.zywee.Activities.PersonalDetailActivity;
import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Supports.InternetStatus;
import com.infovita.zywee.Utility.AvenuesParams;
import com.infovita.zywee.Utility.ServiceUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by praveen on 19/7/16.
 */
public class AmbulanceProcessing extends Fragment {
    public Serverdatas sd;
    private DatabaseHelper DBhelper;

    public String TableName = "ambulance_cart";
    String name, mobile, email, address, appt_date, appt_time;
    private String TAG = "EquipmentProcessing";
    String res,cost;
    String accessCode,merchantId,orderId,currency,amount,redirectUrl,cancelUrl,rsaKeyUrl,pay,trans;
    //String jsonStr;
    public int value = 0;

    public AmbulanceProcessing() {
        // Required empty public constructor
    }

    PersonalDetailActivity context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sd = Serverdatas.getSingletonObject();

        //test ccavenue detail
        /*accessCode = "AVLM05CE20BN32MLNB";
        merchantId = "51793";
        // orderId = jsonStr;
        amount = "1";
        currency = "INR";
        redirectUrl = "http://54.152.88.70/zywee_app/Webservices/ccavResponseHandler";
        cancelUrl = "http://54.152.88.70/zywee_app/Webservices/ccavResponseHandler";
        rsaKeyUrl = "http://54.152.88.70/zywee_app/Webservices/getRSA";*/

        //live ccavenue detail
        accessCode = "AVIB06CI27AD68BIDA";
        merchantId = "51793";
        // orderId = jsonStr;
        amount = "1";
        currency = "INR";
        redirectUrl = "https://52.3.216.145/zywee_app/Webservices/ccavResponseHandler";
        cancelUrl = "https://52.3.216.145/zywee_app/Webservices/ccavResponseHandler";
        rsaKeyUrl = "https://52.3.216.145/zywee_app/Webservices/getRSA";
    }

    private void showProgressDiaalog() {
        context.pDialog.show();
    }

    private void hideProgressDiaalog() {
        if (context.pDialog != null && context.pDialog.isShowing())
            context.pDialog.cancel();
    }

    public void call_booking(PersonalDetailActivity context, String name, String email, String mobile, String address, String appt_date, String appt_time, String pay, DatabaseHelper DBhelper, String coupon_code, String cost) {
        this.context = context;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.appt_date = appt_date;
        this.appt_time = appt_time;
        this.pay = pay;
        this.DBhelper = DBhelper;
        this.coupon_code = coupon_code;
        this.cost = cost;
        //Log.d(TAG, "call_booking called");
        if (isOnline())
            initialiseBookAmbulance();
    }

    String provider_equipment_cost_id, equipment_provider_id, equipment_id, transport, coupon_code;

    String start, destination, provider_id, id, distance_id, total_cost, accessories,title;

    private void initialiseBookAmbulance() {
        showProgressDiaalog();
        final Cursor resultSet = DBhelper.getTableData(TableName);
        if (resultSet.moveToFirst()) {
            do {
                start = resultSet.getString(3);
                destination = resultSet.getString(4);
                provider_id = resultSet.getString(2);
                id = resultSet.getString(1);
                distance_id = resultSet.getString(5);
                //total_cost = resultSet.getString(6);
                total_cost = cost;
                accessories = resultSet.getString(7);
                title = resultSet.getString(8);

                if (pay.equals("Pay now")){
                    bookpayAmbulance();
                }else{
                    bookAmbulance();
                }


            } while (resultSet.moveToNext());
        } else {
            hideProgressDiaalog();
        }
    }
    private void bookAmbulance() {
        coupon_code = "";
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<String> call = serverCall.ambulanceBooking(name, email, mobile, address, start, destination, provider_id, id, distance_id, total_cost, accessories,title,appt_date,appt_time,coupon_code);
        /*Log.d("booking_data", name + "\n" +
                email + "\n" +
                mobile + "\n" +
                address + "\n" +
                start + "\n" +
                destination + "\n" +
                provider_id + "\n" +
                id + "\n" +
                distance_id + "\n" +
                total_cost + "\n" +
                accessories);
        */try {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    res = response.body();
                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(context);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(res).toString().trim());
                    edit.putString(AvenuesParams.AMOUNT,ServiceUtility.chkNull(total_cost).toString().trim());
                    edit.commit();
                   // Log.d("ambulance_booking", "response : " + res);
                    if (res != "-1") {
                        clearDatabase();
                        context.booking_confirmed = true;
                        context.setConfirmationPage();
                        //context.setpayment();
                        /*if (pay.equals("Pay now")) {
                            context.setpayment();
                        }else{
                            context.setConfirmationPage();
                        }*/
                    }
                    hideProgressDiaalog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                   /* Log.d("ambulance_booking", "response : error " + t.getMessage());
                    Toast.makeText(context, "Something went wrong.. Please try again..", Toast.LENGTH_LONG).show();
                    hideProgressDiaalog();*/
                    clearDatabase();
                    context.booking_confirmed = true;
                    context.setConfirmationPage();
                    /*if (pay.equals("Pay now")) {
                        context.setpayment();
                    }else{
                        context.setConfirmationPage();
                    }*/
                    //context.setConfirmationPage();
                    hideProgressDiaalog();
                }
            });
        } catch (Exception e) {
            hideProgressDiaalog();
            e.printStackTrace();
        }
    }

    private void bookpayAmbulance() {
        coupon_code = "";
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<String> call = serverCall.ambulancepayBook(name, email, mobile, address, start, destination, provider_id, id, distance_id, total_cost, accessories,title,appt_date,appt_time,coupon_code);
        /*Log.d("booking_data", name + "\n" +
                email + "\n" +
                mobile + "\n" +
                address + "\n" +
                start + "\n" +
                destination + "\n" +
                provider_id + "\n" +
                id + "\n" +
                distance_id + "\n" +
                total_cost + "\n" +
                accessories);
        */try {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    res = response.body();
                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(context);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(res).toString().trim());
                    edit.putString(AvenuesParams.AMOUNT,ServiceUtility.chkNull(total_cost).toString().trim());
                    edit.commit();
                     Log.d("ambulance_booking", "response : " + res);
                    if (res != "-1") {
                       // clearDatabase();
                        context.booking_confirmed = true;
                        //context.setConfirmationPage();
                        context.setpayment();
                        /*if (pay.equals("Pay now")) {
                            context.setpayment();
                        }else{
                            context.setConfirmationPage();
                        }*/
                    }
                    hideProgressDiaalog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                   /* Log.d("ambulance_booking", "response : error " + t.getMessage());
                    Toast.makeText(context, "Something went wrong.. Please try again..", Toast.LENGTH_LONG).show();
                    hideProgressDiaalog();*/
                   // clearDatabase();
                    context.booking_confirmed = true;
                    context.setpayment();
                    /*if (pay.equals("Pay now")) {
                        context.setpayment();
                    }else{
                        context.setConfirmationPage();
                    }*/
                    //context.setConfirmationPage();
                    hideProgressDiaalog();
                }
            });
        } catch (Exception e) {
            hideProgressDiaalog();
            e.printStackTrace();
        }
    }





    private boolean isOnline() {
        boolean status = InternetStatus.getInstance(context.getApplicationContext()).isOnline();
        if (status)
            return status;
        else {
            context.snakbarMessage("Please connect to Internet");
            return status;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(context, "pause called", Toast.LENGTH_LONG);
    }

    protected void clearDatabase() {
        DBhelper.clearTableData(TableName);
    }
}

