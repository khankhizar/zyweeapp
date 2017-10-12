package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infovita.zywee.Network.ServerCall1;
import com.infovita.zywee.Pojo.Coupon;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Supports.InternetStatus;
import com.infovita.zywee.Supports.MarshMallowPermission;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestCartActivity extends AppCompatActivity {
    Serverdatas sd = Serverdatas.getSingletonObject();
    private ProgressDialog pDialog;
    public String test_name = "";
    public String specialization_id = "";
    public String test_description = "";
    public String test_price = "";
    public String health_institute_name = "";
    public String test_id = "";
    public String user_phone = "";
    public String health_institute_id = "";
    public String discount = "0";
    final DatabaseHelper DBhelper = new DatabaseHelper(this);
    private SQLiteDatabase db;
    public int cart_total = 0;
    public String payment_option = "";
    String code;
    EditText coupon;
    private Coupon res;
    Coupon coupons;
    LinearLayout layout,layout1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_booking);

        setup_toolbar();

       // Log.d("test_name :",test_name);

        //  createDatabase();
        //  createDatabase();
        test_name = getIntent().getExtras().getString("item_name");
        test_description = getIntent().getExtras().getString("item_description");
        test_price = getIntent().getExtras().getString("item_price");
        Log.d("price :", test_price + "");
        health_institute_name = getIntent().getExtras().getString("health_institute_name");
        test_id = getIntent().getExtras().getString("item_id");
        health_institute_id = getIntent().getExtras().getString("health_institute_id");
        discount = getIntent().getExtras().getString("discount");
        specialization_id = getIntent().getExtras().getString("specialization_id");
        payment_option = getIntent().getExtras().getString("payment_option");
        Log.d("pay",payment_option + "" );

        TextView health_institute_name_setter = (TextView) findViewById(R.id.cart_health_institute_name);
        health_institute_name_setter.setText(health_institute_name);

        TextView type = (TextView) findViewById(R.id.cartTitle);
        type.setText(" Medical Test");

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



        //Log.d("disc", discount);
        SharedPreferences prefs = getSharedPreferences(sd.user_phone, MODE_PRIVATE);
        user_phone = prefs.getString("user_phone", null);
        // call_booking();

        insertIntoDB();
        refreshTable();

        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);

        if (!marshMallowPermission.checkPermissionForPhone())
            marshMallowPermission.requestPermissionForPhone();

        Button more_tests_button = (Button) findViewById(R.id.more_tests);
        more_tests_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MoreTestActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                //intent.putExtra("Testtype", 3 + "");
                intent.putExtra("health_institute_name", health_institute_name);
                intent.putExtra("health_institute_id", health_institute_id);
                startActivity(intent);
//                TestCartActivity.this.finish();

               /* Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(Serverdatas.contact_number));
                if (ActivityCompat.checkSelfPermission(TestCartActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
*/
            }
        });

        Button continue_tests_button = (Button) findViewById(R.id.continue_booking_button);
        continue_tests_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getTestsCount() != 0) {
                    Intent intent = new Intent(getApplicationContext(), PersonalDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.putExtra("cart_total", cart_total+".00");
                    intent.putExtra("payment_option",payment_option);
                    intent.putExtra("booking_type", "test");
                    intent.putExtra("Transport","D");
                    intent.putExtra("coupon",code);
                    Log.d("total :",cart_total +"");
                    startActivity(intent);
                } else snakbarMessage("There is no test added");
            }
        });
        //Close opened db
    }

    private void applycoupon() {
            ServerCall1 serverCall = ServerCall1.retrofit.create(ServerCall1.class);
            Call<Coupon> call = serverCall.couponcheck(code);

            try {
                call.enqueue(new Callback<Coupon>() {
                    @Override
                    public void onResponse(Call<Coupon> call, Response<Coupon> response) {
                        res = response.body();
                        Log.d("rep1:", response.body().toString());
                        Log.d("rep:", response.body().getZyweeCoupon().getDiscount());
                       // if (res .equals("1")) {
                        if (res != null) {
                            ((LinearLayout) findViewById(R.id.coup)).setVisibility(View.GONE);
                            ((LinearLayout) findViewById(R.id.coup1)).setVisibility(View.VISIBLE);
                            //  coupons.getZyweeCoupon().getDiscount();
                            String coupon = response.body().getZyweeCoupon().getDiscount();
                            cart_total = cart_total - Integer.parseInt(coupon);
                            if(cart_total <= 0.00 ){
                                cart_total = cart_total + Integer.parseInt(coupon);
                                ((TextView) findViewById(R.id.cart_test_total_price)).setText(String.valueOf(cart_total));
                                ((TextView) findViewById(R.id.text)).setText("invalid transaction amount - Payable amount be bigger then 0");
                            }else {
                                ((TextView) findViewById(R.id.cart_test_total_price)).setText(String.valueOf(cart_total));
                                ((TextView) findViewById(R.id.text)).setText("Thank you. Your discount has been applied");
                                Log.d("discount :", cart_total + "");
                            }

                        }else{
                            ((LinearLayout) findViewById(R.id.coup1)).setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.text)).setText("You entered an invalid coupon code");
                            //  coupons.getZyweeCoupon().getDiscount();
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


    public int getTestsCount() {
        return DBhelper.getRecordCount("tests_cart");
    }


    /*Setup the toolbar*/

    private void setup_toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + "Book Appointment" + "</small>"));
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


    void refreshTable() {
        try {
            final Cursor resultSet = DBhelper.getTableData("tests_cart");
            //Log.d("c_data", resultSet + "");
            resultSet.moveToFirst();
            final TextView cart_total_setter = (TextView) findViewById(R.id.cart_test_total_price);

            do {
                final LinearLayout cart_parent = (LinearLayout) findViewById(R.id.parent_test_cart);
                final View child_view = LayoutInflater.from(this).inflate(R.layout.cart_data, null);
                child_view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ((TextView) child_view.findViewById(R.id.cart_test_name)).setText(resultSet.getString(2));
                //Log.d("dbdata", "item_id : " + resultSet.getString(1));

                final String test_id = resultSet.getString(1);

                final float test_price = calculatePrice(resultSet);

                (child_view.findViewById(R.id.remove)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(TestCartActivity.this)
                                .setMessage("Are you sure to remove test?")
                                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        removeAppointment(test_id);
                                        cart_total -= test_price;
                                        cart_total_setter.setText("₹" + cart_total);
                                        cart_parent.removeView(child_view);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                });
                //Check if test has discount

                if (Float.parseFloat(resultSet.getString(4)) != 0) {
                    //Log.d("price : ",resultSet.getString(3));
                    //Log.d("dbdata", resultSet.getString(4));
                    Float total_value = Float.parseFloat(resultSet.getString(3));
                    Float discount_val = Float.parseFloat(resultSet.getString(4));
                    total_value = total_value - (total_value * (discount_val / 100));
                    ((TextView) child_view.findViewById(R.id.cart_test_price)).setText("₹" + Math.round(total_value));
                    cart_total += Math.round(total_value);
                    Log.d("total:",cart_total+"");
                } else {
                    Float price = Float.parseFloat(resultSet.getString(3));
                    ((TextView) child_view.findViewById(R.id.cart_test_price)).setText("₹" + Math.round(price));
                    cart_total += Math.round(price);
                    Log.d("total:",cart_total+"");
                }
                cart_parent.addView(child_view);
            } while (resultSet.moveToNext());
            cart_total_setter.setText("₹" + cart_total);
            Log.d("cart_total :", cart_total+"");
        } catch (Exception e) {
            //Log.d("madroid_log", e + "");
        }
    }

    float calculatePrice(Cursor resultSet) {
        if (Float.parseFloat(resultSet.getString(4)) != 0) {
            //Log.d("dbdata1", resultSet.getString(4));
            Float total_value = Float.parseFloat(resultSet.getString(3));
            Float discount_val = Float.parseFloat(resultSet.getString(4));
            total_value = total_value - (total_value * (discount_val / 100));
            return Math.round(total_value);
        } else {
            return Float.parseFloat(resultSet.getString(3));
        }
    }


    //Removing data from database

    protected void clearDatabase() {

        DBhelper.clearTableData("tests_cart");
    }

    protected void removeAppointment(String id) {
        DBhelper.removeTableData("tests_cart", "item_id", id);
    }

    protected void insertIntoDB() {

        if (test_name.equals("") || test_id.equals("") || health_institute_name.equals("") || health_institute_id.equals("")) {
            Toast.makeText(getApplicationContext(), "Data not saved.", Toast.LENGTH_LONG).show();
            return;
        }
        ContentValues Test_datas = new ContentValues();

        Test_datas.put("item_id", test_id);
        Test_datas.put("item_name", test_name);
        Test_datas.put("item_price", test_price);
        Test_datas.put("discount", discount);
        Test_datas.put("health_institute_id", health_institute_id);
        Test_datas.put("health_institute_name", health_institute_name);
        Test_datas.put("specialization_id", specialization_id);
        boolean result = false;
        try {
            result = DBhelper.insertTableData("tests_cart", Test_datas);
            Log.d("sqldata_exp", result + "");
            if (result) {
              //  Toast.makeText(getApplication(), "", Toast.LENGTH_LONG).show();
            } else {
             //   Toast.makeText(getApplication(), "Item already exists", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            //Log.d("sqldata_exp", e + "");
            Toast.makeText(getApplication(), "Something went wrong.", Toast.LENGTH_LONG).show();
        }

    }


    public void snakbarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG);
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
            return status;
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(TestCartActivity.this)
                .setMessage("If you go back, the data wil be cleared.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearDatabase();
                       // TestCartActivity.this.finish();
                        finish();
                        startActivity(new Intent(TestCartActivity.this, LandingActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                    }
                })
                .setNegativeButton("No", null)
                .show();
      /*  if (getTestsCount() != 0) {
            *//*new AlertDialog.Builder(this)
                    .setMessage("If you go back the data wil be cleared.")
                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clearDatabase();
                            finish();
                            startActivity(new Intent(TestCartActivity.this, MaincategoriesActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    .putExtra("default_view", 0));
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();*//*
            call_home();
        } else {
            super.onBackPressed();
        }*/
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
                        startActivity(new Intent(TestCartActivity.this, LandingActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
