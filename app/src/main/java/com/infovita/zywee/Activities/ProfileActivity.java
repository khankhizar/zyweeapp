package com.infovita.zywee.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.infovita.zywee.R;

import com.infovita.zywee.Sharedvalues.Serverdatas;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setup_toolbar();
        initUI();
    }

    private void initUI() {
        Serverdatas sd=Serverdatas.getSingletonObject();

        SharedPreferences preferences = getSharedPreferences(sd.user_phone, MODE_PRIVATE);
        // Reading from SharedPreferences
        String mobile = preferences.getString("user_phone", "phone");
        preferences = getSharedPreferences(sd.user_email, MODE_PRIVATE);
        String email = preferences.getString("user_email", "email");

        ((TextView) findViewById(R.id.mobile)).setText(mobile);
        ((TextView) findViewById(R.id.email)).setText(email);
    }

    /*Setup the toolbar*/
    private void setup_toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + getResources().getString(R.string.profile) + "</small>"));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.this.finish();
            }
        });
    }
}