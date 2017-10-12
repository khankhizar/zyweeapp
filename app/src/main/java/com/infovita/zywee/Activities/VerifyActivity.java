package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Sharedvalues.ServiceHandler;
import com.infovita.zywee.Supports.InternetStatus;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class VerifyActivity extends AppCompatActivity {
    Serverdatas sd = Serverdatas.getSingletonObject();
    public String server_url = sd.url;
    public String phone="",otp="";
    private ProgressDialog pDialog;
    private EditText vcode;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(sd.user_phone, MODE_PRIVATE);
        phone = prefs.getString("user_phone", null);
        setContentView(R.layout.activity_verify);
        Button verify = (Button) findViewById(R.id.verify_button);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vcode = (EditText)findViewById(R.id.verification_code);
                otp=vcode.getText().toString();
                if(otp.length() != 6){
                    snakbarMessage("Please enter a valid verification code");
                }else{
                    call_verification_server();
                }
            }
        });

        findViewById(R.id.retry_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences(sd.LOGIN_FLAG, MODE_PRIVATE).edit();
                editor.putInt("LOGIN_FLAG", 0);
                editor.commit();
                startActivity(new Intent(VerifyActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    private void call_verification_server() {
        if (isOnline())
            new verify().execute();
    }

    //Making http call

    private class verify extends AsyncTask<Void, Void, Integer> {

        String user_id = "";
        protected void onPreExecute(){
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(VerifyActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected Integer doInBackground(Void... arg0) {


            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("otp",otp));
            nameValuePair.add(new BasicNameValuePair("phone",phone));

            // Making a request to url and getting response
            try {
                String jsonStr = sh.makeServiceCall(server_url + "zywee_app/webservices/verify_otp", ServiceHandler.POST, nameValuePair);
                jsonStr = jsonStr.replaceAll("\\s","");
                Log.d("Response_url",server_url+"verify_otp");
//                    Log.d("Response_srvr",jsonStr);
                Log.d("Response: verify ", "> " + Integer.parseInt(jsonStr));

                if(Integer.parseInt(jsonStr) != -1){
                    user_id = jsonStr;
                    pDialog.cancel();
                    return 1;
                }else{
                    pDialog.cancel();
                    return -1;
                }
            }catch (Exception e){
                Log.d("Response: ", "> " + e);
                pDialog.cancel();
                return -1;
            }


        }

        public void onPostExecute(Integer arg){
            if(arg == 1){
                SharedPreferences.Editor editor = getSharedPreferences(sd.LOGIN_FLAG, MODE_PRIVATE).edit();
                editor.putInt("LOGIN_FLAG", 2);
                editor.commit();
                editor = getSharedPreferences(sd.user_id, MODE_PRIVATE).edit();
                editor.putString("user_id", user_id);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(),LandingActivity.class);
                startActivity(intent);
                finish();
            }else{
                snakbarMessage("Please enter a valid verification code");
                //Toast.makeText(VerifyActivity.this, "Please enter a valid verification code", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void snakbarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        call_verification_server();
                    }
                });
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.rgb(0, 111, 192));//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }



    private boolean isOnline() {
        boolean status = InternetStatus.getInstance(getApplicationContext()).isOnline();
        if (status)
            return status;
        else {
            /*SharedData sharedData = new SharedData();
            sharedData.*/
            snakbarMessage("Please connect to Internet");
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            return status;
        }
    }
}
