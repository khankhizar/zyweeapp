package com.infovita.zywee.Fragments;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.infovita.zywee.Activities.PersonalDetailActivity;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Sharedvalues.ServiceHandler;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Supports.InternetStatus;
import com.infovita.zywee.Utility.AvenuesParams;
import com.infovita.zywee.Utility.ServiceUtility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorProcessing extends Fragment {
    public Serverdatas sd;
    private DatabaseHelper DBhelper;

    public String TableName= "doctors_cart";
    String name, mobile, email, address, appt_date, appt_time;
    private String TAG = "DoctorProcessing";
    String accessCode,merchantId,orderId,currency,amount,redirectUrl,cancelUrl,rsaKeyUrl,pay;
    String jsonStr,coupon_code,cost;
    public int value = 0;

    public DoctorProcessing() {
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
        this.DBhelper= DBhelper;
        this.coupon_code = coupon_code;
        this.cost = cost;
        //Log.d(TAG, "call_booking called");
        if (pay.equals("Pay now")) {
            if (isOnline())
                new paybook().execute();
        }else{
            if (isOnline())
                new book().execute();

        }
    }

    //Making http call

    private class book extends AsyncTask<Void, Void, Integer> {


        protected void onPreExecute() {
            super.onPreExecute();//
            showProgressDiaalog();
        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

            JSONArray arrayListtest_data = new JSONArray();// /ItemDetail jsonArray
            try {
                final Cursor resultSet = DBhelper.getTableData(TableName);
                //Log.d("c_data", resultSet + "");
                if (resultSet.moveToFirst()) {
                    do {
                        JSONObject appt_data = new JSONObject();// main object
                        appt_data.put("id", resultSet.getString(1));
                        appt_data.put("name", resultSet.getString(2));
                       /* if (Float.parseFloat(resultSet.getString(4)) != 0) {
                            Float total_value = Float.parseFloat(resultSet.getString(3));
                            Float discount_val = Float.parseFloat(resultSet.getString(4));
                            total_value = total_value - (total_value * (discount_val / 100));
                            appt_data.put("price", Math.round(total_value));
                            Log.d("t_value :", Math.round(total_value)+"");
                        } else {*/
                           /* appt_data.put("price",Float.parseFloat(resultSet.getString(3)));
                            //Log.d("total_value :",resultSet.getString(3));
                            Float price = Float.parseFloat(resultSet.getString(3));*/
                        appt_data.put("price",cost);
                        //Log.d("total_value :",resultSet.getString(3));
                        Float price = Float.parseFloat(cost);
                            value = Math.round(price);
                       // }

                        appt_data.put("health_institute_name", resultSet.getString(5));
                        appt_data.put("health_institute_id", resultSet.getString(4));
                       // appt_data.put("discount", resultSet.getString(4));
                        appt_data.put("appt_type", "package");
                      //  appt_data.put("specialization_id", resultSet.getString(7));
                        arrayListtest_data.put(appt_data);
                    } while (resultSet.moveToNext());
                }
                resultSet.close();
            } catch (Exception e) {
                //Log.d("test_err", e + "");
            }

            String json_appt = arrayListtest_data.toString();


            //Log.d("test_json", json_appt);
            nameValuePair.add(new BasicNameValuePair("appt_json_data", json_appt));
            nameValuePair.add(new BasicNameValuePair("appt_date", appt_date));
            nameValuePair.add(new BasicNameValuePair("appt_time", appt_time));
            nameValuePair.add(new BasicNameValuePair("patient_name", name));
            nameValuePair.add(new BasicNameValuePair("patient_mobile", mobile));
            nameValuePair.add(new BasicNameValuePair("patient_email", email));
            nameValuePair.add(new BasicNameValuePair("patient_address", address));
            nameValuePair.add(new BasicNameValuePair("coupon_code",coupon_code));
            //nameValuePair.add(new BasicNameValuePair("cost", cost));
            //Log.d("test_data_server", nameValuePair + "");

            try {
                jsonStr = sh.makeServiceCall(sd.url + "zywee_app/webservices/confirm_booking", ServiceHandler.POST, nameValuePair);
                jsonStr = jsonStr.replaceAll("\"", "");
                Log.d("Response: ", ">" + jsonStr);
                if (jsonStr != "") {
                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(context);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(jsonStr).toString().trim());
                    edit.putString(AvenuesParams.AMOUNT,ServiceUtility.chkNull(value).toString().trim());
                    edit.commit();
                    hideProgressDiaalog();
                    return 1;
                } else {
                    hideProgressDiaalog();
                    return -1;
                }
            } catch (Exception e) {
                //Log.d("test_data", e + "");
                hideProgressDiaalog();
                return -1;
            }
            // Making a request to url and getting response

        }

        public void onPostExecute(Integer arg) {
            if (arg == 1) {
                hideProgressDiaalog();
              //  Toast.makeText(context, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                clearDatabase();
                context.booking_confirmed = true;
               // context.setpayment();
                //context.setConfirmationPage();
               /* if (pay.equals("Pay now")) {
                    context.setpayment();
                }else{*/
                    context.setConfirmationPage();
               // }
            } else {
                context.snakbarMessage("Please try again later!");
            }
        }
    }

    private class paybook extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            super.onPreExecute();//
            showProgressDiaalog();
        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

            JSONArray arrayListtest_data = new JSONArray();// /ItemDetail jsonArray
            try {
                final Cursor resultSet = DBhelper.getTableData(TableName);
                //Log.d("c_data", resultSet + "");
                if (resultSet.moveToFirst()) {
                    do {
                        JSONObject appt_data = new JSONObject();// main object
                        appt_data.put("id", resultSet.getString(1));
                        appt_data.put("name", resultSet.getString(2));
                       /* if (Float.parseFloat(resultSet.getString(4)) != 0) {
                            Float total_value = Float.parseFloat(resultSet.getString(3));
                            Float discount_val = Float.parseFloat(resultSet.getString(4));
                            total_value = total_value - (total_value * (discount_val / 100));
                            appt_data.put("price", Math.round(total_value));
                            Log.d("t_value :", Math.round(total_value)+"");
                        } else {*/
                       /* appt_data.put("price",Float.parseFloat(resultSet.getString(3)));
                        //Log.d("total_value :",resultSet.getString(3));
                        Float price = Float.parseFloat(resultSet.getString(3));*/
                        appt_data.put("price",cost);
                        //Log.d("total_value :",resultSet.getString(3));
                        Float price = Float.parseFloat(cost);
                        value = Math.round(price);
                        // }

                        appt_data.put("health_institute_name", resultSet.getString(5));
                        appt_data.put("health_institute_id", resultSet.getString(4));
                        // appt_data.put("discount", resultSet.getString(4));
                        appt_data.put("appt_type", "package");
                        //  appt_data.put("specialization_id", resultSet.getString(7));
                        arrayListtest_data.put(appt_data);
                    } while (resultSet.moveToNext());
                }
                resultSet.close();
            } catch (Exception e) {
                //Log.d("test_err", e + "");
            }
            String json_appt = arrayListtest_data.toString();
            Log.d("test_json", json_appt);
            nameValuePair.add(new BasicNameValuePair("appt_json_data", json_appt));
            nameValuePair.add(new BasicNameValuePair("appt_date", appt_date));
            nameValuePair.add(new BasicNameValuePair("appt_time", appt_time));
            nameValuePair.add(new BasicNameValuePair("patient_name", name));
            nameValuePair.add(new BasicNameValuePair("patient_mobile", mobile));
            nameValuePair.add(new BasicNameValuePair("patient_email", email));
            nameValuePair.add(new BasicNameValuePair("patient_address", address));
            nameValuePair.add(new BasicNameValuePair("coupon_code",coupon_code));
          //  nameValuePair.add(new BasicNameValuePair("cost", cost));
           /* if (pay.equals("pay later")) {
                nameValuePair.add(new BasicNameValuePair("payment", "N"));
            }*//*else{
                nameValuePair.add(new BasicNameValuePair("payment", "N"));
            }*/
            //Log.d("test_data_server", nameValuePair + "");

            try {
                jsonStr = sh.makeServiceCall(sd.url + "zywee_app/webservices/packdoctest_payment", ServiceHandler.POST, nameValuePair);
                jsonStr = jsonStr.replaceAll("\"", "");
                Log.d("Response:", ">" + jsonStr);

                if (jsonStr != "") {
                    hideProgressDiaalog();
                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(context);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(jsonStr).toString().trim());
                    edit.putString(AvenuesParams.AMOUNT,ServiceUtility.chkNull(value).toString().trim());
                    edit.commit();
                    Log.d("order_id1",ServiceUtility.chkNull(jsonStr).toString().trim());
                    return 1;
                } else {
                    hideProgressDiaalog();
                    return -1;
                }
            } catch (Exception e) {
                Log.d("except_data", e + "");
                hideProgressDiaalog();
                return -1;
            }
            // Making a request to url and getting response

        }



        public void onPostExecute(Integer arg) {
            Log.d("arg","> " + arg);
            if (arg == 1) {

                hideProgressDiaalog();
                Log.d("Response_list_lnt: ", ">" + arg);
                //Toast.makeText(context, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                // clearDatabase();
                Log.d("id :",jsonStr);
                context.booking_confirmed = true;
                context.setpayment();
               /* if (pay.equals("Pay now")) {
                    context.setpayment();
                }else{
                    context.setConfirmationPage();
                }
*/
            } else {
                context.snakbarMessage("Please try again later!");
            }
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
