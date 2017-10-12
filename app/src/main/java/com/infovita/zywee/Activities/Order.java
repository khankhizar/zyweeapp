package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.infovita.zywee.Fragments.DeliveryDateTime;
import com.infovita.zywee.Fragments.DeliveryDetail;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Supports.ApplicationPreferences;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Supports.ManualItem;
import com.infovita.zywee.Supports.SharedData;
import com.infovita.zywee.Supports.constant;
import com.infovita.zywee.stepper.mobileStepperSimple;
import com.infovita.zywee.stepper.stepperFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khizarkhan on 03-02-2017.
 */
public class Order extends mobileStepperSimple {
    public static final String ORDER = "OrderData";
    private static final String TAG = "DeliveryDetail";


    // private static final String TAG = "DeliveryDetail";

    List<stepperFragment> stepperFragmentList = new ArrayList<>();
    stepperFragment obj = new DeliveryDetail();
    //stepperFragment obj1 = new SelectStore();
    stepperFragment obj1 = new DeliveryDateTime();
    //stepperFragment obj3 = new Payment();


   // public static String price = "";
    @Override
    public void initApp(Bundle bundle) {
      /*  Intent i = getIntent();
        price = i.getExtras().getString("price");
*/
      //  SharedData.storeSelector = 1;

        stepperFragmentList.add(obj);
        stepperFragmentList.add(obj1);
//        stepperFragmentList.add(obj2);
//        stepperFragmentList.add(obj3);
        setFragment(stepperFragmentList);
        init();
        setActionBar();
    }

    @Override
    public void onStepperCompleted() {
//        survey data = new survey();

        try {
            new SendOrderToVendorAsync().execute();
        } catch (Exception e) {
           // Log.e("SHARED PREFERENCE", e.toString());
            e.printStackTrace();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Error in Database! Saving");

            alertDialogBuilder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
//                    Toast.makeText(medicalActivity.this, "RESTART APP! DB ERROR", Toast.LENGTH_LONG).show();
                }
            });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } finally {
//            obj5 = null;
            obj = null;
           // obj2 = null;
            obj1 = null;
           // obj3 = null;
//            obj4 = null;
            stepperFragmentList = null;


            /*detailView.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);*/

        }

    }


    Intent detailView;
    Handler handler;
    Runnable runnable;

    Context context = this;


    public class SendOrderToVendorAsync extends AsyncTask<Void, Void, String> implements DialogInterface.OnCancelListener {
        ApplicationPreferences preferences;
        String total,zywee_credits_used,coupon, userId, latitude, longitude, vendorId, image, customer_name, customer_phone, address, date, time, email, delivery_mode, notes;
        //        String pharma_id, pharma_name, pharma_number;
        Serverdatas webServiceUserHandler;
        ArrayList<ManualItem> cart = new ArrayList<>();

        ProgressBar image_progress;

        SharedPreferences sharedpreferences = getSharedPreferences(ORDER, Context.MODE_PRIVATE);

        ProgressDialog dialog;
        DatabaseHelper helper;


        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            webServiceUserHandler = new Serverdatas();
            preferences = new ApplicationPreferences();
           // userId = Serverdatas.get(getApplicationContext());
            Serverdatas sd=Serverdatas.getSingletonObject();

            SharedPreferences preferences = getSharedPreferences(sd.user_id, MODE_PRIVATE);
            // Reading from SharedPreferences
            userId = preferences.getString("user_id", "user_id");
            helper = new DatabaseHelper(context);
         //   cartList = new ArrayList<>();
            //Log.d("User id", userId);
            dialog = new ProgressDialog(context);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Placing Order..");
            dialog.show();
            dialog.setOnCancelListener(this);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            latitude = sharedpreferences.getString(constant.latitude, null);
            longitude = sharedpreferences.getString(constant.longitude, null);
            customer_name = sharedpreferences.getString(constant.contact_person, null);
            customer_phone = sharedpreferences.getString(constant.contact_number, null);
            email = sharedpreferences.getString(constant.contact_email, null);
            date = sharedpreferences.getString(constant.date, null);
            time = sharedpreferences.getString(constant.time, null);
            address = sharedpreferences.getString(constant.address, null);
            vendorId = sharedpreferences.getString(constant.pharma_id, "1");
         //   delivery_mode = sharedpreferences.getString(constant.delivery_type, null);
            notes = sharedpreferences.getString(constant.notes, null);
           /* coupon = sharedpreferences.getString(constant.coupon, null);
            zywee_credits_used = sharedpreferences.getString(constant.zywee_credits_used, "0");
            total = sharedpreferences.getString(constant.total, null);*/

           // cartList = helper.getNonPrescriptionCartDetail();
           // image = sharedpreferences.getString(constant.image,null);


            image = preferences.getPrescriptionName(context);
            cart = helper.getManualEntryDetails();
            //Log.d(TAG, "Cart size: " + cart.size());
            return sendOrder();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "Response : " + result);
            dialog.cancel();
            if (result != null)
                if (result.matches("[0-9]+") && result.length() > 2) {
//                if (!result.contains("wrong")) {
                    handler = new Handler();

                    handler.postDelayed(runnable = new Runnable() {
                        @Override
                        public void run() {
                            /*Log.d(TAG, "Order Data : User id" + userId + "\nlatitude : " + latitude + "\nlongitude : " + longitude
                                    + "\nvendor_id : " + vendorId
                                    + "\ncustomer_name : " + customer_name
                                    + "\ncustomer_phone : " + customer_phone
                                    + "\ncustomer_address : " + preferences.getContactAddress(context)
                            + "\ncustomer_address : " + address );
*/                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            helper.removeCartData();
                            preferences.storePrescriptionName(context, "");
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.clear();
                            editor.commit();
                            SharedData sd = new SharedData();
                            sd.snakbarMessage(findViewById(android.R.id.content).getRootView(), "Order Successfully Placed");
                        }
                    }, 3000);

//                    showOrderDetail();
                    List<String> m = new ArrayList<String>();
                    m.add(0, "");
                    m.add(1, "");




                    m.add(2, "");
                    m.add(3, "");
                    for (int i = 0; i < cart.size(); i++) {
                        ManualItem item = cart.get(i);
                        int k = i + 1;
                        String j = "m" + k;
                        j = item.getDescrription() + "-" + item.getQuantity() + "-" + item.getNotes();
                        m.add(i, j);
                    }
                    detailView = new Intent(context, OrderDetailView.class);
                    detailView.putExtra("customer_name", customer_name);
                    detailView.putExtra("customer_number", customer_phone);
                    detailView.putExtra("order_id", result);
                    detailView.putExtra("notes", notes);
                    detailView.putExtra("m1", m.get(0));
                    detailView.putExtra("m2", m.get(1));
                    detailView.putExtra("m3", m.get(2));
                    detailView.putExtra("m4", m.get(3));
                    detailView.putExtra("image", image);
                    detailView.putExtra("address", address);
                   // detailView.putExtra("price", "0");
                    detailView.putExtra("order_status", "0");
                    detailView.putExtra("parent", "new_order");
                   // detailView.putExtra("product_order" , jsonStr);
                    context.startActivity(detailView);
                } else {
                    dialog.cancel();
                    SharedData sd = new SharedData();
                    sd.snakbarMessage(findViewById(android.R.id.content).getRootView(), "Failed to place an order, please try again");
                }
            /* {
                SharedData sd = new SharedData();
                sd.snakbarMessage(findViewById(android.R.id.content).getRootView(), "Failed to place an order, please try again");
            }*/

                Log.d(TAG, "Response: " + result);
        }


        //Method to add the extra layout to add medicines
        private void addManualMedicineLayout(String s) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.manual_medicine_detail, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView description, quantity, notes;
            description = (TextView) view.findViewById(R.id.description_textview);
            quantity = (TextView) view.findViewById(R.id.quantity_textview);
            notes = (TextView) view.findViewById(R.id.notes_textview);


            LinearLayout layout = (LinearLayout) findViewById(R.id.manual_medicine_layout);
            layout.addView(view);

            String[] string = s.split("-");
            for (int i = 0; i < string.length; i++)
                switch (i) {
                    case 0:
                        if (string[0] != null)
                            description.setText(string[0]);
                        break;
                    case 1:
                        if (string[1] != null)
                            quantity.setText("Qty : " + string[1]);
                        break;
                    case 2:
                        if (string[2] != null)
                            notes.setText(string[2]);
                        break;
                }

        }//end

        @Override
        public void onCancel(DialogInterface dialog) {
            running = false;
        }



        String jsonStr = "";
        private String sendOrder() {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<>(12);
            //Log.d("SendOrder", "UserId " + userId + "\n" + "VenodrId" + vendorId + "\n" + customer_name + "\n" + customer_phone + "\n" + address + "\n" + date + "\n" + time + "\n" + image + "\n" + latitude + "\n" + longitude);

            nameValuePairs.add(new BasicNameValuePair("user_id", userId));
            nameValuePairs.add(new BasicNameValuePair("vendor_id", vendorId));
            nameValuePairs.add(new BasicNameValuePair("customer_name", customer_name));
            nameValuePairs.add(new BasicNameValuePair("customer_phone", customer_phone));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("delivery_address", address));
            nameValuePairs.add(new BasicNameValuePair("delivery_date", date));
            nameValuePairs.add(new BasicNameValuePair("delivery_time_from", time));
//            nameValuePairs.add(new BasicNameValuePair("delivery_time_to", bd.getBTime()));
            nameValuePairs.add(new BasicNameValuePair("image", image));
            nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
            nameValuePairs.add(new BasicNameValuePair("longitude", longitude));
            nameValuePairs.add(new BasicNameValuePair("notes", notes));
           // nameValuePairs.add(new BasicNameValuePair("zywee_credits_used" , zywee_credits_used));
            for (int i = 0; i < cart.size(); i++) {
                ManualItem item = cart.get(i);
                int k = i + 1;
                String j = "m" + k;
                //Log.d("manual :", item.getDescrription() + "-" + item.getQuantity() + "-" + item.getNotes());
                nameValuePairs.add(new BasicNameValuePair(j, item.getDescrription() + "-" + item.getQuantity() + "-" + item.getNotes()));
            }

            //nameValuePairs.add(new BasicNameValuePair("order_type", delivery_mode));
           // nameValuePairs.add(new BasicNameValuePair("coupan_code", coupon));
           // String[][] item = new String[product.size()][4];

           /* JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < product.size(); i++) {
                String id = product.get(i).getProduct_id();
                String size = product.get(i).getSize();
                String quantity = product.get(i).getQuantity();
                String price = product.get(i).getProduct_price();
                JSONObject object = new JSONObject();
                try {
                    object.put("master_product_type" , product.get(i).getMaster_product_type());
                    object.put("product_name" ,product.get(i).getProduct_name());
                    object.put("product_id", id);
                    object.put("product_size", size);
                    object.put("product_quantity", quantity);
                    object.put("product_price", price.replace("₹",""));
                    jsonArray.put(object);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }*/

            /*jsonStr = jsonArray.toString();
            nameValuePairs.add(new BasicNameValuePair("product", jsonStr));
            Log.d("jsonStr", jsonStr);*/
           // Log.d("Link : ",webServiceUserHandler.sendOrderDetail(nameValuePairs));
            return webServiceUserHandler.sendOrderDetail(nameValuePairs);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* switch (item.getItemId()) {
            case R.id.home:
                call_home();
                finish();
                break;

          *//*  case R.id.about_us:
                Uri uri = Uri.parse("https://www.zywee.com/aboutUs");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;*//*
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void call_home(){
       /* new AlertDialog.Builder(this)
                .setMessage("If you go back, the data wil be cleared.")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearDatabase();
                        finish();*/
        startActivity(new Intent(Order.this, LandingActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    public void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
       // actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<small>Delivery Detail</small>"));
       // actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);

    }

}
