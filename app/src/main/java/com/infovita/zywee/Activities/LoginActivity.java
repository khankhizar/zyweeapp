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
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {

    Serverdatas sd = Serverdatas.getSingletonObject();
    public String server_url = sd.url;
    private ProgressDialog pDialog;
    private EditText phone, email;
    private String phone_number, email_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login_submit_button = (Button) findViewById(R.id.login_submit_button);
        login_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = (EditText) findViewById(R.id.login_phone);
                phone_number = phone.getText().toString();
                email = (EditText) findViewById(R.id.login_email);
                email_id = email.getText().toString();
                boolean login = true;
                if (phone_number.length() != 10) {
//                    snakbarMessage("Please enter a valid Phone number.");
                    phone.setError("Please enter a valid Phone number");
                    login = false;
                }
                if (!isValidEmail(email_id)) {
                    email.setError("Please enter a valid email id");
                    login = false;
                }
                if (login) {
                    callotpserver();
                }
            }
        });

    }

    public final static boolean isValidEmail(CharSequence mail) {
        return !TextUtils.isEmpty(mail) && android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }

    private void callotpserver() {
        if(isOnline())
        new login().execute();
    }

    //Making http call

    private class login extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("phone", phone_number));
            nameValuePair.add(new BasicNameValuePair("email", email_id));
           // Log.d("Response: ", "> " + phone_number + "\n" + email_id);

            // Making a request to url and getting response
            try {
                String jsonStr = sh.makeServiceCall(server_url + "zywee_app/webservices/otp_generator", ServiceHandler.POST, nameValuePair);

               // Log.d("Response: ", "> " + jsonStr);
                pDialog.cancel();
                return 1;
            } catch (Exception e) {
                //Log.d("Response: ", "> " + e);
                pDialog.cancel();
                return -1;
            }


        }

        public void onPostExecute(Integer arg) {
            if (arg == 1) {
                SharedPreferences.Editor editor = getSharedPreferences(sd.LOGIN_FLAG, MODE_PRIVATE).edit();
                editor.putInt("LOGIN_FLAG", 1);
                editor.commit();
                editor = getSharedPreferences(sd.user_phone, MODE_PRIVATE).edit();
                editor.putString("user_phone", phone_number);
                editor.commit();
                editor = getSharedPreferences(sd.user_email, MODE_PRIVATE).edit();
                editor.putString("user_email", email_id);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), VerifyActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void snakbarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callotpserver();
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
