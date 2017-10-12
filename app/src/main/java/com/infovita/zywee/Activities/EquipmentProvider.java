package com.infovita.zywee.Activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infovita.zywee.Network.ServerCall1;
import com.infovita.zywee.Pojo.Coupon;
import com.infovita.zywee.R;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Supports.InternetStatus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipmentProvider extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    String equipment_name, provider_name, equipment_provider_id, provider_equipment_cost_id, equipment_id, monthly_cost,
            daily,week,month,daily_cost, weekly_cost;
    List<String> rent_type;

    Spinner rentSpinner;
    TextView priceTextView;
    CheckBox deliveryCheckBox;

    String selectedPrice = "";

    public static final String TABLE_EQUIPMENT_CART = "equipment_cart";

    final DatabaseHelper DBhelper = new DatabaseHelper(this);

    String code;
    EditText coupon;
    private Coupon res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_provider);

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
                        selectedPrice = String.valueOf(Float.parseFloat(selectedPrice) - Integer.parseInt(coupon));
                        ((TextView) findViewById(R.id.price)).setText(String.valueOf(selectedPrice));
                        ((TextView) findViewById(R.id.text)).setText("Thank you. Your discount has been applied");
                        Log.d("discount :", selectedPrice + "");
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
        ((TextView) findViewById(R.id.cart_equipment_name)).setText(equipment_name);

        deliveryCheckBox = (CheckBox) findViewById(R.id.delivery_checkbox);
        priceTextView = (TextView) findViewById(R.id.price);
        rentSpinner = (Spinner) findViewById(R.id.detail_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner, rent_type);
        rentSpinner.setAdapter(adapter);

        rentSpinner.setOnItemSelectedListener(this);
        findViewById(R.id.remove).setOnClickListener(this);
        findViewById(R.id.cancel_action).setOnClickListener(this);
        findViewById(R.id.submit_button).setOnClickListener(this);
    }

    private void fetchIntentData() {
        Bundle bundle = getIntent().getExtras();
        provider_name = bundle.getString("provider_name");
        equipment_provider_id = bundle.getString("equipment_provider_id");
        provider_equipment_cost_id = bundle.getString("provider_equipment_cost_id");
        equipment_id = bundle.getString("equipment_id");
        monthly_cost = bundle.getString("monthly_cost");
        daily_cost = bundle.getString("daily_cost");
        weekly_cost = bundle.getString("weekly_cost");
        equipment_name = bundle.getString("equipment_name");

        rent_type = new ArrayList<>();

        if (!daily_cost.startsWith("0"))
            rent_type.add("Daily");
        if (!weekly_cost.startsWith("0"))
            rent_type.add("Weekly");
        if (!monthly_cost.startsWith("0"))
            rent_type.add("Monthly");
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
        String str = parent.getItemAtPosition(position).toString();
        if (str.startsWith("D")) {
            priceTextView.setText("₹" + daily_cost + "/-");
            selectedPrice = daily_cost;
        } else if (str.startsWith("W")) {
            selectedPrice = weekly_cost;
            priceTextView.setText("₹" + weekly_cost + "/-");
        } else {
            selectedPrice = monthly_cost;
            priceTextView.setText("₹" + monthly_cost + "/-");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(EquipmentProvider.this)
                .setMessage("If you go back, the data wil be cleared.")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearDatabase();
                        EquipmentProvider.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void alertDialog(String alert) {
        new AlertDialog.Builder(EquipmentProvider.this)
                .setMessage(alert)
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeAppointment(equipment_id);
                        EquipmentProvider.this.finish();
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

    private void submit() {
//        if (addToDB()) {
            boolean bool = addToDB();
            startActivity(new Intent(this, PersonalDetailActivity.class)
                    .putExtra("booking_type", "equipment")
                    .putExtra("payment_option", "")
                    .putExtra("Transport","E")
                    .putExtra("cart_total",selectedPrice)
                    .putExtra("coupon_code",code));

//        }
    }

    private boolean addToDB() {
        ContentValues equipment_data = new ContentValues();

        equipment_data.put("item_id", equipment_id);
        equipment_data.put("item_name", equipment_name);
        equipment_data.put("item_price", selectedPrice);
        equipment_data.put("health_institute_id", equipment_provider_id);
        equipment_data.put("health_institute_name", provider_name);
        equipment_data.put("equipment_cost_id", provider_equipment_cost_id);
        if (selectedPrice.equals(daily_cost)) {
            equipment_data.put("duration","day");
        } else if (selectedPrice.equals(weekly_cost)) {
            equipment_data.put("duration","week");
        } else {
            equipment_data.put("duration","month");
        }
        Log.d("rent :", selectedPrice);

        if (deliveryCheckBox.isChecked())
            equipment_data.put("transport", "E");
        else equipment_data.put("transport", "N");

        boolean result = false;
        try {
            result = DBhelper.insertTableData(TABLE_EQUIPMENT_CART, equipment_data);
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
        DBhelper.removeTableData(TABLE_EQUIPMENT_CART, "item_id", id);
    }

    protected void clearDatabase() {
        DBhelper.clearTableData(TABLE_EQUIPMENT_CART);
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
                        startActivity(new Intent(EquipmentProvider.this, LandingActivity.class)
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