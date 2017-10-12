package com.infovita.zywee.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Sharedvalues.ServiceHandler;
import com.infovita.zywee.Utility.AvenuesParams;
import com.infovita.zywee.Utility.Constants;
import com.infovita.zywee.Utility.RSAUtility;
import com.infovita.zywee.Utility.ServiceUtility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Khizarkhan on 06-06-2017.
 */

public class WebViewActivity1 extends Activity {
    private ProgressDialog dialog;
    Intent mainIntent;
    String html, encVal,start,destination,provider_id,id,distance_id,accessories,title;
    String orderid,amount,status,result,name,email,mobile,country,cost;
    String jsonStr,trans,patient_name,patient_mobile,patient_email,patient_address,
            appt_date,appt_time,appt_json_data,provider_home_service_id,provider_service_charge_id,service_provider_id,collection_cost;
    Serverdatas sd = Serverdatas.getSingletonObject();
    String server_url = sd.url;
    String address, provider_equipment_cost_id, equipment_provider_id, equipment_id, total_cost, transport, coupon_code,equipment_name,duration;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_web_view);
        mainIntent = getIntent();

        /*SharedPreferences preferences = getSharedPreferences(AvenuesParams.ORDER_ID, MODE_PRIVATE);
        orderid = preferences.getString(AvenuesParams.ORDER_ID, "order");
        Log.d("order_id",orderid);*/

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        orderid = sharedPreferences.getString(AvenuesParams.ORDER_ID, "default value");
        amount = sharedPreferences.getString(AvenuesParams.AMOUNT,"default value");
        Log.d("order_id",orderid);
        trans = mainIntent.getStringExtra("trans");
        Log.d("trans",trans);
        patient_name = mainIntent.getStringExtra("billing_name");
        patient_mobile = mainIntent.getStringExtra("billing_tel");
        patient_email = mainIntent.getStringExtra("billing_email");
        patient_address = mainIntent.getStringExtra("patient_address");
        appt_date = mainIntent.getStringExtra("appt_date");
        appt_time = mainIntent.getStringExtra("appt_time");
        appt_json_data = mainIntent.getStringExtra("json_test");
        provider_equipment_cost_id = mainIntent.getStringExtra("provider_equipment_cost_id");
        equipment_provider_id = mainIntent.getStringExtra("equipment_provider_id");
        equipment_id = mainIntent.getStringExtra("equipment_id");
        equipment_name = mainIntent.getStringExtra("equipment_name");
        total_cost = mainIntent.getStringExtra("total_cost");
        transport = mainIntent.getStringExtra("transport");
        duration = mainIntent.getStringExtra("duration");
        start = mainIntent.getStringExtra("start");
        destination = mainIntent.getStringExtra("destination");
        provider_id = mainIntent.getStringExtra("provider_id");
        id = mainIntent.getStringExtra("id");
        distance_id = mainIntent.getStringExtra("distance_id");
        accessories = mainIntent.getStringExtra("accessories");
        title = mainIntent.getStringExtra("title");
        provider_home_service_id = mainIntent.getStringExtra("provider_home_service_id");
        provider_service_charge_id = mainIntent.getStringExtra("provider_service_charge_id");
        service_provider_id = mainIntent.getStringExtra("service_provider_id");
        collection_cost = mainIntent.getStringExtra("collection_cost");
        cost = mainIntent.getStringExtra("cost");
        coupon_code = mainIntent.getStringExtra("coupon_code");

       // appt_json_datas = mainIntent.getStringExtra("json_appt");
        Log.d("booking_data",
                provider_equipment_cost_id+ "\n" +
                        equipment_provider_id+ "\n" +
                        equipment_id+ "\n" +
                        total_cost+ "\n" +
                        transport+ "\n" +
                        equipment_name+"\n"+
                        duration+ "\n"+
                        coupon_code);

       // name = mainIntent.getStringExtra("Billing_name"");

        // Calling async task to get display content
        new RenderView().execute();

    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class RenderView extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            dialog = new ProgressDialog(WebViewActivity1.this);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(AvenuesParams.ACCESS_CODE, mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE)));
            params.add(new BasicNameValuePair(AvenuesParams.ORDER_ID, orderid));
            Log.d("order",orderid);
            String vResponse = sh.makeServiceCall(server_url + "zywee_app/webservices/getRSA", ServiceHandler.POST, params);
            //Log.d("Response", vResponse);
            System.out.println(vResponse);
            if(!ServiceUtility.chkNull(vResponse).equals("")
                    && ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR")==-1){
                StringBuffer vEncVal = new StringBuffer("");
                if(cost != ""||cost!="null") {
                    vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, cost));
                }else{
                    vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, amount));
                }
              //  vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, "1"));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, mainIntent.getStringExtra(AvenuesParams.CURRENCY)));
                encVal = RSAUtility.encrypt(vEncVal.substring(0,vEncVal.length()-1), vResponse);
                Log.d("encval", encVal);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (dialog.isShowing())
                dialog.dismiss();

            @SuppressWarnings("unused")
            class MyJavaScriptInterface
            {
                @JavascriptInterface
                public void processHTML(String html)
                {
                    // process the html as needed by the app
                    status = null;
                    if(html.indexOf("Failure")!=-1){
                        status = "Transaction Declined";
                    }else if(html.indexOf("Success")!=-1){
                        status = "Transaction Successful";
                    }else if(html.indexOf("Aborted")!=-1){
                        status = "Transaction Cancelled";
                    }else{
                        status = "Status Not Known";
                    }
                    Log.d("status",status);
                    if(trans.equals("D")) {
                        updated();
                    }else if(trans.equals("E")) {
                        updates();
                    }else if(trans.equals("T")){
                        updateable();
                    }else{
                        update();
                    }

                   // new book().execute();
                    //Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                   /* Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
                    intent.putExtra("transStatus", status);
                    startActivity(intent);*/
                }
            }

            final WebView webview = (WebView) findViewById(R.id.webview);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
            webview.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(webview, url);
                    Log.d("url", url);
                   // new book().execute();
                    if(url.indexOf("/ccavResponseHandler")!=-1){
                        Log.d("index", "webview");
                        webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");

                    }
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                }
            });

			/* An instance of this class will be registered as a JavaScript interface */
            StringBuffer params = new StringBuffer();
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ACCESS_CODE,mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_ID,mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ORDER_ID,orderid));
            //params.append(ServiceUtility.addToPostParams(AvenuesParams.ORDER_ID,mainIntent.getStringExtra(AvenuesParams.ORDER_ID)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.REDIRECT_URL,mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.CANCEL_URL,mainIntent.getStringExtra(AvenuesParams.CANCEL_URL)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ENC_VAL, URLEncoder.encode(encVal)));
            params.append(ServiceUtility.addToPostParams("billing_name",mainIntent.getStringExtra("billing_name")));
            params.append(ServiceUtility.addToPostParams("billing_tel",mainIntent.getStringExtra("billing_tel")));
            params.append(ServiceUtility.addToPostParams("billing_email",mainIntent.getStringExtra("billing_email")));
            params.append(ServiceUtility.addToPostParams("billing_country",mainIntent.getStringExtra("billing_country")));


            String vPostParams = params.substring(0,params.length()-1);
            Log.d("params :" , vPostParams);
            try {
                webview.postUrl(Constants.TRANS_URL, EncodingUtils.getBytes(vPostParams, "UTF-8"));
            } catch (Exception e) {
                showToast("Exception occured while opening webview.");
            }

        }
    }

    private void updateable() {
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<String> call = serverCall.ambulancepayBooking(orderid, status,trans,patient_name,patient_mobile,patient_email,patient_address,start,destination,provider_id,
                id,distance_id,total_cost,accessories,title,appt_date,appt_time,coupon_code);

        try {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    result = response.body();
                    Log.d("response : " , result);
                    if (result != "") {
                        Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
                        intent.putExtra("transStatus", status);
                        startActivity(intent);
                    }
                    hideProgressDiaalog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d( "response : error " , t.getMessage());
                    // Toast.makeText(context, "Something went wrong.. Please try again..", Toast.LENGTH_LONG).show();
                    hideProgressDiaalog();
                }
            });
        } catch (Exception e) {
            hideProgressDiaalog();
            e.printStackTrace();
        }

    }

    private void update() {
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<String> call = serverCall.homeServicepayupdate(orderid, status,trans,patient_name,patient_mobile,patient_email,patient_address,provider_home_service_id,
                provider_service_charge_id,service_provider_id,total_cost,collection_cost,appt_date,appt_time,title,duration,coupon_code);

        try {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    result = response.body();
                    Log.d("response : " , result);
                    if (result != "") {
                        Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
                        intent.putExtra("transStatus", status);
                        startActivity(intent);
                    }
                    hideProgressDiaalog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d( "response : error " , t.getMessage());
                    // Toast.makeText(context, "Something went wrong.. Please try again..", Toast.LENGTH_LONG).show();
                    hideProgressDiaalog();
                }
            });
        } catch (Exception e) {
            hideProgressDiaalog();
            e.printStackTrace();
        }

    }

    private void updated() {
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<String> call = serverCall.updatedBooking(orderid, status,trans,patient_name,patient_mobile,patient_email,patient_address,
                appt_date,appt_time,appt_json_data,coupon_code,cost);
        Log.d("id2 :",orderid);
        Log.d("status2 :", status);
        Log.d("trans :", trans);
        Log.d("cost",cost);
        try {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    result = response.body();
                    Log.d("response : " , result);
                    if (result != "") {
                        Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
                        intent.putExtra("transStatus", status);
                        startActivity(intent);
                    }
                    hideProgressDiaalog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d( "response : error " , t.getMessage());
                    // Toast.makeText(context, "Something went wrong.. Please try again..", Toast.LENGTH_LONG).show();
                    hideProgressDiaalog();
                }
            });
        } catch (Exception e) {
            hideProgressDiaalog();
            e.printStackTrace();
        }

    }

    private void updates() {
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<String> call = serverCall.equipmentpaystatus(orderid, status,trans,patient_name,patient_mobile,patient_email,patient_address, provider_equipment_cost_id, equipment_provider_id, equipment_id, total_cost, transport, coupon_code,equipment_name,duration,appt_date,appt_time);
        Log.d("booking_data",patient_name + "\n" +
                patient_email + "\n" +
                patient_mobile+ "\n" +
                patient_address+ "\n" +
                provider_equipment_cost_id+ "\n" +
                equipment_provider_id+ "\n" +
                equipment_id+ "\n" +
                total_cost+ "\n" +
                transport+ "\n" +
                equipment_name+"\n"+
                duration+ "\n"+
                appt_date+ "\n"+
                appt_time+ "\n"+
                orderid+ "\n"+
                status+ "\n"+
                coupon_code);
        try {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    result = response.body();
                    Log.d("response : " , result);
                    if (result != "") {
                        Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
                        intent.putExtra("transStatus", status);
                        startActivity(intent);
                    }
                    hideProgressDiaalog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d( "response : error " , t.getMessage());
                    // Toast.makeText(context, "Something went wrong.. Please try again..", Toast.LENGTH_LONG).show();
                    hideProgressDiaalog();
                }
            });
        } catch (Exception e) {
            hideProgressDiaalog();
            e.printStackTrace();
        }

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WebViewActivity1.this, LandingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void showProgressDiaalog() {
        dialog.show();
    }

    private void hideProgressDiaalog() {
        if (dialog != null && dialog.isShowing())
            dialog.cancel();
    }

    public void showToast(String msg) {
        Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
    }
}
