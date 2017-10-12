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
 * A simple {@link Fragment} subclass.
 */
public class EquipmentProcessing extends Fragment {
    public Serverdatas sd;
    private DatabaseHelper DBhelper;

    public String TableName = "equipment_cart";
    String name, mobile, email, address, appt_date, appt_time;
    private String TAG = "EquipmentProcessing";
    String res,cost;
    String accessCode,merchantId,orderId,currency,amount,redirectUrl,cancelUrl,rsaKeyUrl,pay;
    //String jsonStr;
    public int value = 0;

    public EquipmentProcessing() {
        // Required empty public constructor
    }

    PersonalDetailActivity context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sd = Serverdatas.getSingletonObject();

        //test ccavenue detail
       /* accessCode = "AVLM05CE20BN32MLNB";
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
            initialiseBookEquipment();
    }

    String provider_equipment_cost_id, equipment_provider_id, equipment_id, total_cost, transport, coupon_code,equipment_name,duration;

    private void initialiseBookEquipment() {
        showProgressDiaalog();
        final Cursor resultSet = DBhelper.getTableData(TableName);
        if (resultSet.moveToFirst()) {
            do {
                provider_equipment_cost_id = resultSet.getString(6);
                equipment_provider_id = resultSet.getString(4);
                equipment_id = resultSet.getString(1);
                equipment_name = resultSet.getString(2);
                //total_cost = resultSet.getString(3);
                total_cost = cost;
                transport = resultSet.getString(7);
                duration = resultSet.getString(8);

                if (pay.equals("Pay now")){
                    bookpayEquipment();
                }else{
                    bookEquipment();
                }

            } while (resultSet.moveToNext());
        } else {
            hideProgressDiaalog();
        }
    }


    private void bookEquipment() {
        coupon_code = "";
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Log.d("time",appt_time);
        Call<String> call = serverCall.equipmentBooking(name, email, mobile, address, provider_equipment_cost_id, equipment_provider_id, equipment_id, total_cost, transport, coupon_code,equipment_name,duration,appt_date,appt_time);
        Log.d("booking_data",name + "\n" +
                email + "\n" +
                mobile+ "\n" +
        address+ "\n" +
        provider_equipment_cost_id+ "\n" +
        equipment_provider_id+ "\n" +
        equipment_id+ "\n" +
        total_cost+ "\n" +
        transport+ "\n" +
        equipment_name+"\n"+
        duration+ "\n"+
        coupon_code);
        Log.d("Equipment_name",equipment_name);
        try {
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
                    Log.d("equipment_booking", "response : " + res);
                    if (res != "-1"){
                        clearDatabase();
                        context.booking_confirmed = true;
                        context.setConfirmationPage();
                       // context.setpayment();
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
                    Log.d("equipment_booking", "response : error " + t.getMessage());
                    /*Toast.makeText(context, "Something went wrong.. Please try again..", Toast.LENGTH_LONG).show();*/
                    clearDatabase();
                    context.booking_confirmed = true;
                    //context.setConfirmationPage();
                    //context.setpayment();
                   /* if (pay.equals("Pay now")) {
                        context.setpayment();
                    }else{*/
                        context.setConfirmationPage();
                   // }
                    hideProgressDiaalog();
                }
            });
        } catch (Exception e) {
            hideProgressDiaalog();
            e.printStackTrace();
        }
    }

    private void bookpayEquipment() {
        coupon_code = "";
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Log.d("time",appt_time);
        Call<String> call = serverCall.equipmentpayBooking(name, email, mobile, address, provider_equipment_cost_id, equipment_provider_id, equipment_id, total_cost, transport, coupon_code,equipment_name,duration,appt_date,appt_time);
        Log.d("booking_data",name + "\n" +
                email + "\n" +
                mobile+ "\n" +
        address+ "\n" +
        provider_equipment_cost_id+ "\n" +
        equipment_provider_id+ "\n" +
        equipment_id+ "\n" +
        total_cost+ "\n" +
        transport+ "\n" +
        equipment_name+"\n"+
        duration+ "\n"+
        coupon_code);
        Log.d("Equipment_name",equipment_name);
        try {
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
                    Log.d("equipment_booking", "response : " + res);
                    if (res != "-1"){
                       // clearDatabase();
                        context.booking_confirmed = true;
                        //context.setConfirmationPage();
                         context.setpayment();
                       /* if (pay.equals("Pay now")) {
                            context.setpayment();
                        }else{
                            context.setConfirmationPage();
                        }*/
                    }
                    hideProgressDiaalog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("equipment_booking", "response : error " + t.getMessage());
                    /*Toast.makeText(context, "Something went wrong.. Please try again..", Toast.LENGTH_LONG).show();*/
                    //clearDatabase();
                    context.booking_confirmed = true;
                    //context.setConfirmationPage();
                    //context.setpayment();
                   // if (pay.equals("Pay now")) {
                        context.setpayment();
                    /*}else{
                        context.setConfirmationPage();
                    }*/
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
