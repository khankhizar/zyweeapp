package com.infovita.zywee.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

/**
 * Created by Khizarkhan on 17-01-2017.
 */

public class DetailsActivity extends ActionBarActivity {
    private TextView titleTextView;
    private ImageView imageView;

    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

  //      ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        Serverdatas sd=Serverdatas.getSingletonObject();
        SharedPreferences preferences = getSharedPreferences(sd.user_phone, MODE_PRIVATE);
        // Reading from SharedPreferences
        phone = preferences.getString("user_phone", "phone");



       // String images = imagesList.get().getScanImage().getMobile();


        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        //Log.d("image : ",image);
       // titleTextView = (TextView) findViewById(R.id.title);
        imageView = (ImageView) findViewById(R.id.grid_item_image);
      //  titleTextView.setText(Html.fromHtml(title));
       // String imgLink = "http://54.152.88.70/zywee/scanimages" + "/" + phone + "/" + image;
        String imgLink = "https://s3.amazonaws.com/scanimages-70/"  + phone + "/" + image;

        Glide.with(this).load(imgLink).fitCenter().into(imageView);
    }
}
