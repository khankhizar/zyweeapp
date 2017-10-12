package com.infovita.zywee.Supports;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Khizarkhan on 03-02-2017.
 */

public class SharedData extends Activity {

    public static final String LOGIN_FLAG = "0";
    public static SharedData singletonObject;


    public static boolean storeResume = true;
    public static String delivery_mode = "1";
    public static int storeSelector = 1;
    public static String pharma_type = "0";


    public String selected_vendor_id = "";
    public String customer_name = "";
    public String customer_phone = "";
    public String email = "";
    public String delivery_address = "";
    public String delivery_date = "";
    public String delivery_start_time = "";


    public static String getMain_category_list_data() {
        return main_category_list_data;
    }

    public static void setMain_category_list_data(String main_category_list_data) {
        SharedData.main_category_list_data = main_category_list_data;
    }

    public static String main_category_list_data;

    public static synchronized SharedData getSingletonObject() {
        if (singletonObject == null) {
            singletonObject = new SharedData();
        }
        return singletonObject;
    }

    public void clear() {
        singletonObject = null;
    }


    public boolean isOnline(Context context, View view) {
        boolean status = InternetStatus.getInstance(context).isOnline();
        if (status)
            return status;
        else {
            snakbarMessage(view, "Please connect to Internet");
            return status;
        }
    }

    public boolean isOnline(Context context) {
        boolean status = InternetStatus.getInstance(context).isOnline();

        if (status) {
            return status;
        } else {
            Toast.makeText(context, "Please connect to Internet", Toast.LENGTH_LONG).show();
            return status;
        }

    }


    /**
     * Update Cart count
     *
     * @param context
     * @param menu Menu
     */
    //    Keep track of cart count
    static long mCartCount = 0;

    public static class UpdateCart extends AsyncTask<Void, Void, Void> {

        DatabaseHelper helper;
        RelativeLayout notifCount;

        Context mContext;
        Menu menu;

        public UpdateCart(Context context, Menu menu) {
            this.menu = menu;
            mContext = context;
        }


        @Override
        protected void onPreExecute() {
          /*  MenuItem item = menu.findItem(R.id.action_cart);
            MenuItemCompat.setActionView(item, R.layout.cart_icon);
            notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);*/
        }

        @Override
        protected Void doInBackground(Void... params) {
//            helper = new DatabaseHelper(mContext);
//            mCartCount = helper.fetchManualCartCount() + helper.fetchNonPrescriptionCartCount() + helper.fetchPrescriptionCartCount();
//            Log.d("CartCount" , "Manual : " + helper.fetchManualCartCount() + ": NonPresc : " + helper.fetchNonPrescriptionCartCount() + " : Presc : " + helper.fetchPrescriptionCartCount() );
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           /* TextView tv = (TextView) notifCount.findViewById(R.id.cartCount);
            tv.setText(String.valueOf(mCartCount));*/
        }
    }//END


    public void snakbarMessage(View view, String message) {

        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

}

