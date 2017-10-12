package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.Network.ServerCall1;
import com.infovita.zywee.Pojo.Coupon;
import com.infovita.zywee.Pojo.HomeServicePrice;
import com.infovita.zywee.R;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Supports.InternetStatus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeServieProvider extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    String service_name, provider_id, provider_name, service_id, provider_service_charge_id, provider_home_services_id,cost,duration;
    List<String> service_type, price, accessory_type;

    Spinner rentSpinner;
    TextView priceTextView;

    String totalPrice = "0";

    public static final String TABLE_HOME_SERVICES_CART = DatabaseHelper.TABLE_HOME_SERVICES_CART;

    final DatabaseHelper DBhelper = new DatabaseHelper(this);
    private ProgressDialog pDialog;
    String code;
    EditText coupon;
    private Coupon res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_service_provider);

        setup_toolbar("Provider Detail");

        fetchIntentData();
        initUI();
        coupon = (EditText) findViewById(R.id.coupon);

        findViewById(R.id.apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = coupon.getText().toString().trim();
                if(isOnline())
                applycoupon();
            }
        });
    }

    private void applycoupon() {
        ServerCall1 serverCall = ServerCall1.retrofit.create(ServerCall1.class);
        Call<Coupon> call = serverCall.couponcheck(code);

        try {
            call.enqueue(new Callback<Coupon>() {
                @Override
                public void onResponse(Call<Coupon> call, Response<Coupon> response) {
                    res = response.body();
                    Log.d("rep:", response.body().getZyweeCoupon().getDiscount());
                    if (res .equals("1")) {
                        //  coupons.getZyweeCoupon().getDiscount();
                    }else{
                        ((LinearLayout) findViewById(R.id.coup)).setVisibility(View.GONE);
                        ((LinearLayout) findViewById(R.id.coup1)).setVisibility(View.VISIBLE);
                        //  coupons.getZyweeCoupon().getDiscount();
                        String coupon = response.body().getZyweeCoupon().getDiscount();
                        totalPrice = String.valueOf(Float.parseFloat(totalPrice) - Integer.parseInt(coupon));
                        if(totalPrice.equals("0.0") || Float.parseFloat(totalPrice) < 0){
                            totalPrice = String.valueOf(Float.parseFloat(totalPrice) + Integer.parseInt(coupon));
                            ((TextView) findViewById(R.id.price)).setText(String.valueOf(totalPrice));;
                            ((TextView) findViewById(R.id.text)).setText("invalid transaction amount - Payable amount be bigger then 0");
                        }else {
                            ((TextView) findViewById(R.id.price)).setText(String.valueOf(totalPrice));
                        }
                        Log.d("discount :", totalPrice + "");
                    }
                    // hideProgressDiaalog();
                }

                @Override
                public void onFailure(Call<Coupon> call, Throwable t) {
                    Log.d("coupon", "response : error " + t.getMessage());
                    ((LinearLayout) findViewById(R.id.coup1)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.text)).setText("You entered an invalid coupon code");
                    // hideProgressDiaalog();
                }
            });
        } catch (Exception e) {
            // hideProgressDiaalog();
            e.printStackTrace();
        }
    }


    private void initUI() {
        ((TextView) findViewById(R.id.provider_name)).setText(provider_name);
        ((TextView) findViewById(R.id.cart_equipment_name)).setText(service_name);

        priceTextView = (TextView) findViewById(R.id.price);
        rentSpinner = (Spinner) findViewById(R.id.detail_spinner);

        rentSpinner.setOnItemSelectedListener(this);
        findViewById(R.id.remove).setOnClickListener(this);
        findViewById(R.id.cancel_action).setOnClickListener(this);
        findViewById(R.id.submit_button).setOnClickListener(this);
    }

    private void fetchIntentData() {
        pDialog = new ProgressDialog(HomeServieProvider.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCanceledOnTouchOutside(false);

        Bundle bundle = getIntent().getExtras();
        provider_name = bundle.getString("provider_name");
        provider_id = bundle.getString("provider_id");
        service_name = bundle.getString("service_name");
        service_id = bundle.getString("service_id");
        provider_home_services_id = bundle.getString("provider_home_services_id");
        provider_service_charge_id = bundle.getString("provider_service_charge_id");
        cost = bundle.getString("cost");

        Log.d("post data", provider_id + " \n" + service_id);

        service_type = new ArrayList<>();
        price = new ArrayList<>();

        showProgressDiaalog();
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<List<HomeServicePrice>> call = serverCall.getHomeServiceCost(service_id, provider_id);
        Log.d("Service id", " > " + provider_id + " " + service_id);
        call.enqueue(new Callback<List<HomeServicePrice>>() {
            @Override
            public void onResponse(Call<List<HomeServicePrice>> call, Response<List<HomeServicePrice>> response) {
                hideProgressDiaalog();

                final List<HomeServicePrice> service = response.body();
                Log.d("service", " > " + service.size());

                final Handler handler = new Handler();
                Thread t = new Thread() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                for (int i = 0; i < service.size(); i++) {
                                    service_type.add(service.get(i).getName());
                                    price.add(service.get(i).getCost());
                                    Log.d("service", " > " + service.get(i).getName());
                                    duration=service.get(i).getName();
                                }
                                Log.d("service", " > " + service_type);
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(HomeServieProvider.this, R.layout.spinner, service_type);
                                rentSpinner.setAdapter(adapter);
                            }
                        });
                    }
                };
                t.start();

                Log.d("response", "service_cost : > " + response.body() + "\n" + service_type + " \n" + price + "\n" + accessory_type);
            }

            @Override
            public void onFailure(Call<List<HomeServicePrice>> call, Throwable t) {
                hideProgressDiaalog();
                Log.e("Response", "ambulance_cost : error > " + t.getMessage());
            }
        });
    }

    private void setTotalPrice(String total) {
        totalPrice = total;
        priceTextView.setText("₹" + totalPrice + "/-");
    }

    private void showProgressDiaalog() {
        pDialog.show();
    }

    private void hideProgressDiaalog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.cancel();
    }

    /*Setup the toolbar*/
    public void setup_toolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + title + "</small>"));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        totalPrice = price.get(position);
        priceTextView.setText("₹" + totalPrice + "/-");
        /*String distancePrice = String.valueOf(Integer.parseInt(totalPrice) - Integer.parseInt(selectedPrice)
                + Integer.parseInt(price.get(position)));
        selectedPrice = price.get(position);
        setTotalPrice(distancePrice);
        selected_distance_id = distance_id.get(position);*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(HomeServieProvider.this)
                .setMessage("If you go back, the data wil be cleared.")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearDatabase();
                        HomeServieProvider.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void alertDialog(String alert) {
        new AlertDialog.Builder(HomeServieProvider.this)
                .setMessage(alert)
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeAppointment(service_id);
                        HomeServieProvider.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remove:
                alertDialog("Are you sure to remove?");
                break;
            case R.id.cancel_action:
                onBackPressed();
                break;
            case R.id.submit_button:
                submit();
                break;
        }
    }

    String start, destination;

    private void submit() {

        boolean bool = addToDB();
        startActivity(new Intent(HomeServieProvider.this, PersonalDetailActivity.class)
                .putExtra("booking_type", "homeService")
                .putExtra("payment_option", "")
                .putExtra("Transport","H")
                .putExtra("cart_total",totalPrice)
                .putExtra("coupon_code",code));

    }

    private boolean addToDB() {
        ContentValues service_data = new ContentValues();

        service_data.put("item_id", service_id);
        service_data.put("provider_home_service_id", provider_home_services_id);
        service_data.put("provider_service_charge_id", provider_service_charge_id);
        service_data.put("service_provider_id", provider_id);
        service_data.put("total_cost", totalPrice);
        service_data.put("collection_cost", totalPrice);
        service_data.put("title",service_name);
        service_data.put("duration",duration);
        Log.d("provider id",provider_home_services_id );
        Log.d("service id",provider_id);


        boolean result = false;
        try {
            result = DBhelper.insertTableData(TABLE_HOME_SERVICES_CART, service_data);
            Log.d("sqldata_exp", result + "");
            if (result) {
                return result;
            } else {
                //Toast.makeText(getApplication(), "Item already exists", Toast.LENGTH_LONG).show();
                return result;
            }
        } catch (Exception e) {
            Log.d("sqldata_exp", e + "");
            Toast.makeText(getApplication(), "Something went wrong.. Please try again..", Toast.LENGTH_LONG).show();
            return result;
        }
    }


    protected void removeAppointment(String id) {
        DBhelper.removeTableData(TABLE_HOME_SERVICES_CART, "item_id", id);
    }

    protected void clearDatabase() {
        DBhelper.clearTableData(TABLE_HOME_SERVICES_CART);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                call_home();
                break;
        }
        return true;
    }

    private void call_home(){
        new AlertDialog.Builder(this)
                .setMessage("If you go back, the data wil be cleared.")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearDatabase();
                        finish();
                        startActivity(new Intent(HomeServieProvider.this, LandingActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private boolean isOnline() {
        boolean status = InternetStatus.getInstance(getApplicationContext()).isOnline();
        if (status)
            return status;

        else {
            /*SharedData sharedData = new SharedData();
            sharedData.*/
            snakbarMessage("Please connect to Internet");
            return status;
        }
    }

    public void snakbarMessage(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

    public void snakbarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        applycoupon();
                    }
                });
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.rgb(0, 111, 192));//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

}
