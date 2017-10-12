package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infovita.zywee.Network.ServerCall;
import com.infovita.zywee.Network.ServerCall1;
import com.infovita.zywee.Pojo.AmbulancePrice;
import com.infovita.zywee.Pojo.Coupon;
import com.infovita.zywee.R;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Supports.InternetStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmbulanceProvider extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    String ambulance_name, provider_id, provider_name, ambulance_provider_id, ambulance_id;
    List<String> distance_type, price, accessory_type, accessory_price, accessory_id, distance_id;

    Spinner rentSpinner;
    TextView priceTextView;
    String selectedPrice = "0";

    String totalPrice = "0";
    String selected_distance_id;

    HashMap<String, String> accessories;

    public static final String TABLE_AMBULANCE_CART = "ambulance_cart";

    final DatabaseHelper DBhelper = new DatabaseHelper(this);
    private ProgressDialog pDialog;
    public String payment_option = "";
    String code;
    EditText coupon;
    private Coupon res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_provider);

        setup_toolbar();
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fetchIntentData();
        initUI();
        coupon = (EditText) findViewById(R.id.coupon);

        findViewById(R.id.apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = coupon.getText().toString().trim();
                if (isOnline())
                applycoupon();
                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
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
                        totalPrice = String.valueOf(Integer.parseInt(totalPrice) - Integer.parseInt(coupon));
                        ((TextView) findViewById(R.id.price)).setText(String.valueOf(totalPrice));
                        ((TextView) findViewById(R.id.text)).setText("Thank you. Your discount has been applied");
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
        ((TextView) findViewById(R.id.cart_equipment_name)).setText(ambulance_name);

        priceTextView = (TextView) findViewById(R.id.price);
        rentSpinner = (Spinner) findViewById(R.id.detail_spinner);

        rentSpinner.setOnItemSelectedListener(this);
        findViewById(R.id.remove).setOnClickListener(this);
        findViewById(R.id.cancel_action).setOnClickListener(this);
        findViewById(R.id.submit_button).setOnClickListener(this);
    }

    private void fetchIntentData() {
        accessories = new HashMap<>();
        pDialog = new ProgressDialog(AmbulanceProvider.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCanceledOnTouchOutside(false);

        Bundle bundle = getIntent().getExtras();
        provider_name = bundle.getString("provider_name");
        provider_id = bundle.getString("provider_id");
        ambulance_name = bundle.getString("ambulance_name");
        ambulance_provider_id = bundle.getString("ambulance_provider_id");
        ambulance_id = bundle.getString("ambulance_id");

        Log.d("post data", ambulance_provider_id + " \n" + ambulance_id);

        distance_type = new ArrayList<>();
        price = new ArrayList<>();
        accessory_id = new ArrayList<>();
        accessory_type = new ArrayList<>();
        accessory_price = new ArrayList<>();
        distance_id = new ArrayList<>();

        showProgressDiaalog();
        ServerCall serverCall = ServerCall.retrofit.create(ServerCall.class);
        Call<AmbulancePrice> call = serverCall.getAmbulanceCost(provider_id, ambulance_id);
        Log.d("Ambulance id" , " > " + provider_id + " " + ambulance_id);
        call.enqueue(new Callback<AmbulancePrice>() {
            @Override
            public void onResponse(Call<AmbulancePrice> call, Response<AmbulancePrice> response) {
                System.out.println(response.raw().body());
                hideProgressDiaalog();
                distance_type = response.body().getDistanceType();
                price = response.body().getCost();
                accessory_type = response.body().getAccessoryType();
                accessory_id = response.body().getAccessoryId();
                accessory_price = response.body().getAmount();
                distance_id = response.body().getDistanceId();

                for (int i = 0; i < accessory_id.size(); i++)
                    addAccessories(accessory_id.get(i), accessory_type.get(i), accessory_price.get(i));

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AmbulanceProvider.this, R.layout.spinner, distance_type);
                rentSpinner.setAdapter(adapter);

                Log.d("response", "ambulance_cost : > " + response.body() + "\n" + distance_type + " \n" + price + "\n" + accessory_type);
            }

            @Override
            public void onFailure(Call<AmbulancePrice> call, Throwable t) {
                hideProgressDiaalog();
                Log.e("Response", "ambulance_cost : error > " + t.getMessage());
            }
        });
    }

    private void addAccessories(final String accessory_id, final String accessory_type, final String accessory_price) {
        LinearLayout list = (LinearLayout) findViewById(R.id.layout_accessories);
        final View child_view = LayoutInflater.from(this).inflate(R.layout.accessory_layout, null);
        child_view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        list.addView(child_view);
        ((CheckBox) child_view.findViewById(R.id.accessoryCheckBox)).setText(accessory_type);
        ((TextView) child_view.findViewById(R.id.accessoryPrice)).setText("₹" + accessory_price);

        ((CheckBox) child_view.findViewById(R.id.accessoryCheckBox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    accessories.put(accessory_id, accessory_price);
                    Log.d("hashmap", "accessories" + accessories.toString());
                    setTotalPrice(String.valueOf(Integer.parseInt(totalPrice) + Integer.parseInt(accessory_price)));
                } else {
                    accessories.remove(accessory_id);
                    setTotalPrice(String.valueOf(Integer.parseInt(totalPrice) - Integer.parseInt(accessory_price)));
                }
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

    private void setup_toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + "Provider Detail" + "</small>"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

       /* getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
//        toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

  /*  *//*Setup the toolbar*//*
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
    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String distancePrice = String.valueOf(Integer.parseInt(totalPrice) - Integer.parseInt(selectedPrice)
                + Integer.parseInt(price.get(position)));
        selectedPrice = price.get(position);
        setTotalPrice(distancePrice);
        selected_distance_id = distance_id.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(AmbulanceProvider.this)
                .setMessage("If you go back, the data wil be cleared.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearDatabase();
                        AmbulanceProvider.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void alertDialog(String alert) {
        new AlertDialog.Builder(AmbulanceProvider.this)
                .setMessage(alert)
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeAppointment(ambulance_id);
                        AmbulanceProvider.this.finish();
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
        start = ((EditText) findViewById(R.id.starting_point)).getText().toString().trim();
        destination = ((EditText) findViewById(R.id.end_point)).getText().toString().trim();
        if (start.equals("") || destination.equals(""))
            Toast.makeText(this, "Please mention starting and ending point..", Toast.LENGTH_SHORT).show();
        else {
            boolean bool = addToDB();

            startActivity(new Intent(AmbulanceProvider.this, PersonalDetailActivity.class)
                    .putExtra("booking_type", "ambulance")
                    .putExtra("payment_option","")
                    .putExtra("Transport","T")
                    .putExtra("cart_total",totalPrice)
                    .putExtra("coupon",code)
                    );
        }
    }

    private boolean addToDB() {
        JSONArray array = new JSONArray();
        Iterator iterator = accessories.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = accessories.get(key);
            JSONObject object = new JSONObject();
            try {
                object.put("accessory_id", key);
                object.put("amount", value);
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
       }
        ContentValues ambulance_data = new ContentValues();

        ambulance_data.put("item_id", ambulance_id);
        ambulance_data.put("provider_id", provider_id);
        ambulance_data.put("start", start);
        ambulance_data.put("destination", destination);
        ambulance_data.put("distance_id", selected_distance_id);
        ambulance_data.put("total_cost", totalPrice);
        ambulance_data.put("ambulance_name",ambulance_name);
        ambulance_data.put("accessories_list", array.toString());

        boolean result = false;
        try {
            result = DBhelper.insertTableData(TABLE_AMBULANCE_CART, ambulance_data);
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
        DBhelper.removeTableData(TABLE_AMBULANCE_CART, "item_id", id);
    }

    protected void clearDatabase() {
        DBhelper.clearTableData(TABLE_AMBULANCE_CART);
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
                        startActivity(new Intent(AmbulanceProvider.this, LandingActivity.class)
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
