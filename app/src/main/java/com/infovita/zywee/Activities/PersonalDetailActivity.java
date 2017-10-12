package com.infovita.zywee.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.infovita.zywee.Fragments.AmbulanceProcessing;
import com.infovita.zywee.Fragments.DoctorProcessing;
import com.infovita.zywee.Fragments.EquipmentProcessing;
import com.infovita.zywee.Fragments.HomeServiceProcessing;
import com.infovita.zywee.Fragments.PackageProcessing;
import com.infovita.zywee.Fragments.TestProcessing;
import com.infovita.zywee.R;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Utility.AvenuesParams;
import com.infovita.zywee.Utility.ServiceUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PersonalDetailActivity extends AppCompatActivity {

    public static boolean booking_confirmed = false;

    Calendar c;
    private Date dt;
    static Date dateSelected;
    static int selectedMonth, selectedDay, selectedYear;
    static Date currentDate;
    static String time_str,provider_home_service_id,provider_service_charge_id,service_provider_id,collection_cost;
    int mYear, mMonth, mDay, mHour, mMinute;
    private TextView date, time;
    final DatabaseHelper DBhelper = new DatabaseHelper(this);
    public ProgressDialog pDialog;
    public String booking_type = "";
    private AlertDialog dialog;
    private Calendar mCalendarOpeningTime, mCalendarClosingTime;
    String accessCode,merchantId,orderId,currency,amount,redirectUrl,cancelUrl,rsaKeyUrl;
    private SharedPreferences preferences;
    private RadioGroup radioPay;
    private RadioButton radioPayButton;
    String paymentopt,Transport;
    String name,email,mobile,address,dateStr,timeStr,start,destination,provider_id,id,distance_id,accessories,title;
    public String TableName= "tests_cart";
    public int value = 0;
    String provider_equipment_cost_id, equipment_provider_id, equipment_id, total_cost, transport, coupon_code,equipment_name,duration,cost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);
       // Log.d("payment_option",getIntent().getExtras().getString("payment_option"));
        booking_type =getIntent().getExtras().getString("booking_type");
        paymentopt = getIntent().getExtras().getString("payment_option");
        Transport = getIntent().getExtras().getString("Transport");
        cost = getIntent().getExtras().getString("cart_total");
        coupon_code = getIntent().getExtras().getString("coupon");
        Log.d("cost :", cost +"");


        setup_toolbar(getResources().getString(R.string.prompt_personal_detail));

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);


        initUI();

        //ContinueButton click event
        findViewById(R.id.continue_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    EditText nameEditText, emailEditText, mobileEditText, addressEditText, codeEditText;

    private void initUI() {
        nameEditText = (EditText) findViewById(R.id.name);
        emailEditText = (EditText) findViewById(R.id.email);
        mobileEditText = (EditText) findViewById(R.id.mobile);
        addressEditText = (EditText) findViewById(R.id.address);
        codeEditText = (EditText) findViewById(R.id.verification_code);
        radioPay = (RadioGroup) findViewById(R.id.radio);
        dateTime();
        //test ccavenue detail
        /*accessCode = "AVLM05CE20BN32MLNB";
        merchantId = "51793";
       // orderId = "54";
        amount = "1";
        currency = "INR";
        redirectUrl = "http://54.152.88.70/zywee_app/Webservices/ccavResponseHandler";
        cancelUrl = "http://54.152.88.70/zywee_app/Webservices/ccavResponseHandler";
        rsaKeyUrl = "http://54.152.88.70/zywee_app/Webservices/getRSA";*/

        //live ccavenue detail
        accessCode = "AVIB06CI27AD68BIDA";
        merchantId = "51793";
        // orderId = jsonStr;
        amount = "1";
        currency = "INR";
        redirectUrl = "https://52.3.216.145/zywee_app/Webservices/ccavResponseHandler";
        cancelUrl = "https://52.3.216.145/zywee_app/Webservices/ccavResponseHandler";
        rsaKeyUrl = "https://52.3.216.145/zywee_app/Webservices/getRSA";

        if(paymentopt.equals("E") ){
            //((RadioButton) findViewById(R.id.radiopaynot)).setChecked(true);
            ((RadioButton) findViewById(R.id.radiopaynot)).setVisibility(View.GONE);
            radioPay.check(R.id.radiopay);

        } else if(paymentopt.equals("D")){
           // ((RadioButton) findViewById(R.id.radiopay)).setChecked(true);
            ((RadioButton) findViewById(R.id.radiopay)).setVisibility(View.GONE);
            radioPay.check(R.id.radiopaynot);
            //((RadioButton) findViewById(R.id.radiopaynot)).setChecked(false);

        }else{
           // ((RadioButton) findViewById(R.id.radiopaynot)).setChecked(true);
            ((RadioButton) findViewById(R.id.radiopaynot)).setVisibility(View.VISIBLE);
            ((RadioButton) findViewById(R.id.radiopay)).setVisibility(View.VISIBLE);
            radioPay.check(R.id.radiopay);

        }

       // setOpeningAndClosingTimes();
    }



    private void dateTime() {
        c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 1);
        currentDate = c.getTime();
        SimpleDateFormat start_time = new SimpleDateFormat("hh:mm a");
        time_str = start_time.format(c.getTime());


        date = (TextView) findViewById(R.id.dated);
        date.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Get Current Date
                                        Calendar c = Calendar.getInstance();
                                        c.add(Calendar.DAY_OF_YEAR, 1);
                                        mYear = c.get(Calendar.YEAR);
                                        mMonth = c.get(Calendar.MONTH);
                                        mDay = c.get(Calendar.DAY_OF_MONTH);


                                        DatePickerDialog datePickerDialog = new DatePickerDialog(PersonalDetailActivity.this,
                                                new DatePickerDialog.OnDateSetListener() {

                                                    @Override
                                                    public void onDateSet(DatePicker view, int year,
                                                                          int monthOfYear, int dayOfMonth) {

                                                        Calendar newDate = Calendar.getInstance();
                                                        newDate.set(year, monthOfYear, dayOfMonth);
                                                        dateSelected = newDate.getTime();

                                                        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");

                                                        if (dateSelected.compareTo(currentDate) == -1) {
                                                            Toast.makeText(PersonalDetailActivity.this, "Please select valid date", Toast.LENGTH_SHORT).show();
                                                        }else if (newDate.get(Calendar.DAY_OF_WEEK) ==
                                                                Calendar.SUNDAY){
                                                            Toast.makeText(PersonalDetailActivity.this, "Please select date other than Sunday", Toast.LENGTH_LONG).show();
                                                        } else {
                                                            selectedDay = dayOfMonth;
                                                            selectedMonth = monthOfYear;
                                                            selectedYear = year;
                                                            date.setError(null);
                                                            time.setError(null);
                                                            date.setText(dateFormat.format(newDate.getTime()));
                                                           // time.setText(time_str);
                                                        }  /*if (newDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ) {
                                                            date.setTextColor(Color.parseColor("#ff0000"));
                                                            ((Button) dialog.getButton(AlertDialog.BUTTON_POSITIVE)).setEnabled(false);
                                                        } else {
                                                            date.setTextColor(Color.parseColor("#000000"));
                                                            ((Button) dialog.getButton(AlertDialog.BUTTON_POSITIVE)).setEnabled(true);
                                                        }*/

                                                      
                                                    }
                                                }

                                                , mYear, mMonth, mDay);
                                        datePickerDialog.show();
                                    }
                                }


        );
        time = (TextView)
                findViewById(R.id.time);

        time.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {                                     // Get Current Time
                                        final Calendar c = Calendar.getInstance();
                                        mHour = c.get(Calendar.HOUR_OF_DAY);
                                        mMinute = c.get(Calendar.MINUTE);


                                        // Launch Time Picker Dialog
                                        TimePickerDialog timePickerDialog = new TimePickerDialog(PersonalDetailActivity.this,
                                                new TimePickerDialog.OnTimeSetListener() {

                                                    @Override
                                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                                          int minute) {
//
                                                        c.set(Calendar.DAY_OF_MONTH,
                                                                selectedDay);
                                                        c.set(Calendar.MONTH, selectedMonth);
                                                        c.set(Calendar.YEAR, selectedYear);
                                                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                                        c.set(Calendar.MINUTE, minute);

                                                        Date timeSelected = c.getTime();

                                                        mCalendarOpeningTime = Calendar.getInstance();
                                                        mCalendarOpeningTime.set(Calendar.DAY_OF_MONTH,
                                                                selectedDay);
                                                        mCalendarOpeningTime.set(Calendar.MONTH, selectedMonth);
                                                        mCalendarOpeningTime.set(Calendar.YEAR, selectedYear);
                                                        mCalendarOpeningTime.set(Calendar.HOUR_OF_DAY, 9);
                                                        mCalendarOpeningTime.set(Calendar.MINUTE, 00);
                                                       // mCalendarOpeningTime.set(Calendar.AM_PM, Calendar.AM);

                                                        mCalendarClosingTime = Calendar.getInstance();
                                                        mCalendarClosingTime.set(Calendar.DAY_OF_MONTH,
                                                                selectedDay);
                                                        mCalendarClosingTime.set(Calendar.MONTH, selectedMonth);
                                                        mCalendarClosingTime.set(Calendar.YEAR, selectedYear);
                                                        mCalendarClosingTime.set(Calendar.HOUR_OF_DAY, 18);
                                                        mCalendarClosingTime.set(Calendar.MINUTE, 00);
                                                       // mCalendarClosingTime.set(Calendar.AM_PM, Calendar.PM);

                                                        if(timeSelected.after(mCalendarOpeningTime.getTime())&& timeSelected.before(mCalendarClosingTime.getTime())) {
                                                            try {
                                                                final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                                                                final Date dateObj = sdf.parse(time_str);
                                                                time.setError(null);
                                                                Log.d("time",new SimpleDateFormat("hh:mm a").format(c.getTime()));
                                                                time.setText(new SimpleDateFormat("hh:mm a").format(c.getTime()));
                                                            } catch (final ParseException e) {
                                                                e.printStackTrace();
                                                            }

                                                           // Toast.makeText(PersonalDetailActivity.this, "Please select valid time", Toast.LENGTH_SHORT).show();

                                                        /*}else if (timeSelected.compareTo(currentDate) == -1) {
                                                            Toast.makeText(PersonalDetailActivity.this, "Please select valid time", Toast.LENGTH_SHORT).show();*/
                                                        } else {
                                                            Toast.makeText(PersonalDetailActivity.this, "Please select time between 9:00 am to 6:00 pm", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }, mHour, mMinute, false);
                                        timePickerDialog.show();
                                    }
                                }


        );

    }

    private void submit() {
        //Getting the string from all the EditText
        name = nameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        mobile = mobileEditText.getText().toString().trim();
        address = addressEditText.getText().toString().trim();
        dateStr = date.getText().toString().trim();
        timeStr = time.getText().toString().trim();

        int selectedId = radioPay.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioPayButton = (RadioButton) findViewById(selectedId);
        String pay = radioPayButton.getText().toString();
        Log.d("pay",pay);

        //Checking whether the details are entered or not
        boolean flag = true;
        if (name.equals("")) {
            flag = false;
            nameEditText.setError("Please enter name");
        }
        if (!email.equals("") && !isValidEmail(email)) {
            flag = false;
            emailEditText.setError("Please enter valid email");
        }

        if (mobile.length() != 10) {
            flag = false;
            mobileEditText.setError("Please enter mobile number");
        }
        if (address.equals("")) {
            flag = false;
            addressEditText.setError("Please enter address");
        }
        if (dateStr.equals("")) {
            flag = false;
            date.setError("Please select date");
        }
        if (timeStr.equals("")) {
            flag = false;
            time.setError("Please select time");
        }


        //Moving to next activity if all the data entered
        if (flag) {
            switch (booking_type){
                case "test":{
                    TestProcessing testProcessing = new TestProcessing();
                    testProcessing.call_booking(PersonalDetailActivity.this, name, email, mobile, address, dateStr, timeStr,pay,DBhelper,coupon_code,cost);
                }
                break;
                case "package":{
                    PackageProcessing packagefragment = new PackageProcessing();
                    packagefragment.call_booking(PersonalDetailActivity.this, name, email, mobile, address, dateStr, timeStr,pay,DBhelper,coupon_code,cost);
                }
                break;
                case "doctor":{
                    DoctorProcessing doctorfragment = new DoctorProcessing();
                    doctorfragment.call_booking(PersonalDetailActivity.this, name, email, mobile, address, dateStr, timeStr,pay,DBhelper,coupon_code,cost);
                }
                break;
                case "equipment":
                    EquipmentProcessing equipmentFragmnet = new EquipmentProcessing();
                    equipmentFragmnet.call_booking(PersonalDetailActivity.this, name, email, mobile, address, dateStr, timeStr,pay,DBhelper,coupon_code,cost);
                    break;
                case "ambulance":
                    AmbulanceProcessing ambulanceProcessing = new AmbulanceProcessing();
                    ambulanceProcessing.call_booking(PersonalDetailActivity.this, name, email, mobile, address, dateStr, timeStr,pay,DBhelper,coupon_code,cost);
                    break;
                case "homeService":
                    HomeServiceProcessing homeServiceProcessing = new HomeServiceProcessing();
                    homeServiceProcessing.call_booking(PersonalDetailActivity.this, name, email, mobile, address, dateStr, timeStr,pay,DBhelper,coupon_code,cost);
                    break;
            }
        }
    }

    public final static boolean isValidEmail(CharSequence mail) {
        return !TextUtils.isEmpty(mail) && android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
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


    public void snakbarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submit();
                    }
                });
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.rgb(0, 111, 192));//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

    @Override
    public void onBackPressed() {
        if (booking_confirmed) {
            Intent intent = new Intent(PersonalDetailActivity.this, LandingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("If you go back, the data wil be cleared.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            PersonalDetailActivity.this.finish();
                            /*startActivity(new Intent(PersonalDetailActivity.this, LandingActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));*/
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
           // PersonalDetailActivity.this.finish();
        }
    }




    public void setConfirmationPage(){
        setContentView(R.layout.test_booking_confirmation);
        setup_toolbar("Booking confirmation");
        TextView tv4 = (TextView) findViewById(R.id.textView1);
        tv4.setText("Thank you,You will get confirmation SMS soon.");
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalDetailActivity.this, LandingActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    public void setpayment(){
        Log.d("booking_type",booking_type);
        JSONArray arrayListtest_data = new JSONArray();// /ItemDetail jsonArray
        try {
        if(booking_type.equals("test")) {
            final Cursor resultSet = DBhelper.getTableData(TableName);
            Log.d("c_data", resultSet + "");
            if (resultSet.moveToFirst()) {
                do {
                    JSONObject appt_data = new JSONObject();// main object
                    appt_data.put("id", resultSet.getString(1));
                    appt_data.put("name", resultSet.getString(2));
                    if (Float.parseFloat(resultSet.getString(4)) != 0) {
                        Float total_value = Float.parseFloat(resultSet.getString(3));
                        Float discount_val = Float.parseFloat(resultSet.getString(4));
                        total_value = total_value - (total_value * (discount_val / 100));
                        appt_data.put("price", Math.round(total_value));
                        value = Math.round(total_value);
                        //Log.d("t_value :", Math.round(total_value)+"");
                    } else {
                        appt_data.put("price", Float.parseFloat(resultSet.getString(3)));
                        //Log.d("total_value :",resultSet.getString(3));
                        Float price = Float.parseFloat(resultSet.getString(3));
                        value = Math.round(price);
                    }

                    appt_data.put("health_institute_name", resultSet.getString(6));
                    appt_data.put("health_institute_id", resultSet.getString(5));
                    appt_data.put("discount", resultSet.getString(4));
                    appt_data.put("appt_type", "package");
                    appt_data.put("specialization_id", resultSet.getString(7));
                    arrayListtest_data.put(appt_data);
                } while (resultSet.moveToNext());
            }
            resultSet.close();
        }else if(booking_type.equals("package")){
            final Cursor resultSet = DBhelper.getTableData("packages_cart");
            Log.d("c_data", resultSet + "");
            if (resultSet.moveToFirst()) {
                do {
                    JSONObject appt_data = new JSONObject();// main object
                    appt_data.put("id", resultSet.getString(1));
                    appt_data.put("name", resultSet.getString(2));
                    if (Float.parseFloat(resultSet.getString(4)) != 0) {
                        Float total_value = Float.parseFloat(resultSet.getString(3));
                        Float discount_val = Float.parseFloat(resultSet.getString(4));
                        total_value = total_value - (total_value * (discount_val / 100));
                        appt_data.put("price", Math.round(total_value));
                        value = Math.round(total_value);
                    } else {
                        appt_data.put("price", Float.parseFloat(resultSet.getString(3)));
                        Float price = Float.parseFloat(resultSet.getString(3));
                        value = Math.round(price);
                    }

                    appt_data.put("health_institute_name", resultSet.getString(6));
                    appt_data.put("health_institute_id", resultSet.getString(5));
                    appt_data.put("discount", Float.parseFloat(resultSet.getString(4)));
                    appt_data.put("appt_type", "package");
                    arrayListtest_data.put(appt_data);
                } while (resultSet.moveToNext());
            }
            resultSet.close();
        }else if(booking_type.equals("doctor")){
            final Cursor resultSet = DBhelper.getTableData("doctors_cart");
            //Log.d("c_data", resultSet + "");
            if (resultSet.moveToFirst()) {
                do {
                    JSONObject appt_data = new JSONObject();// main object
                    appt_data.put("id", resultSet.getString(1));
                    appt_data.put("name", resultSet.getString(2));
                       /* if (Float.parseFloat(resultSet.getString(4)) != 0) {
                            Float total_value = Float.parseFloat(resultSet.getString(3));
                            Float discount_val = Float.parseFloat(resultSet.getString(4));
                            total_value = total_value - (total_value * (discount_val / 100));
                            appt_data.put("price", Math.round(total_value));
                            Log.d("t_value :", Math.round(total_value)+"");
                        } else {*/
                    appt_data.put("price",Float.parseFloat(resultSet.getString(3)));
                    //Log.d("total_value :",resultSet.getString(3));
                    Float price = Float.parseFloat(resultSet.getString(3));
                    value = Math.round(price);
                    // }

                    appt_data.put("health_institute_name", resultSet.getString(5));
                    appt_data.put("health_institute_id", resultSet.getString(4));
                    // appt_data.put("discount", resultSet.getString(4));
                    appt_data.put("appt_type", "package");
                    //  appt_data.put("specialization_id", resultSet.getString(7));
                    arrayListtest_data.put(appt_data);
                } while (resultSet.moveToNext());
            }
            resultSet.close();
        }else if(booking_type.equals("equipment")) {
            final Cursor resultSet = DBhelper.getTableData("equipment_cart");
            if (resultSet.moveToFirst()) {
                do {
                    provider_equipment_cost_id = resultSet.getString(6);
                    equipment_provider_id = resultSet.getString(4);
                    equipment_id = resultSet.getString(1);
                    equipment_name = resultSet.getString(2);
                    total_cost = resultSet.getString(3);
                    transport = resultSet.getString(7);
                    duration = resultSet.getString(8);
                    Log.d("booking_data",
                            provider_equipment_cost_id+ "\n" +
                            equipment_provider_id+ "\n" +
                            equipment_id+ "\n" +
                            total_cost+ "\n" +
                            transport+ "\n" +
                            equipment_name+"\n"+
                            duration+ "\n"+
                            coupon_code);
                   /* JSONObject appt_data = new JSONObject();
                    appt_data.put("provider_equipment_cost_id", resultSet.getString(6));
                    appt_data.put("equipment_provider_id", resultSet.getString(4));
                    appt_data.put("equipment_id", resultSet.getString(1));
                    appt_data.put("equipment_name", resultSet.getString(2));
                    appt_data.put("total_cost", resultSet.getString(3));
                    appt_data.put("transport", resultSet.getString(7));
                    appt_data.put("duration", resultSet.getString(8));
                    arrayListtest_data.put(appt_data);
             */   } while (resultSet.moveToNext());
            }
            resultSet.close();
        }else if(booking_type.equals("ambulance")){
            final Cursor resultSet = DBhelper.getTableData("ambulance_cart");
            if (resultSet.moveToFirst()) {
                do {
                    start = resultSet.getString(3);
                    destination = resultSet.getString(4);
                    provider_id = resultSet.getString(2);
                    id = resultSet.getString(1);
                    distance_id = resultSet.getString(5);
                    total_cost = resultSet.getString(6);
                    accessories = resultSet.getString(7);
                    title = resultSet.getString(8);
                } while (resultSet.moveToNext());
            }
            resultSet.close();
        }else if(booking_type.equals("homeService")) {
            final Cursor resultSet = DBhelper.getTableData("home_service_cart");
            if (resultSet.moveToFirst()) {
                do {
                    provider_home_service_id = resultSet.getString(2);
                    provider_service_charge_id = resultSet.getString(3);
                    service_provider_id = resultSet.getString(4);
                    total_cost = resultSet.getString(5);
                    collection_cost = resultSet.getString(6);
                    title = resultSet.getString(7);
                    duration = resultSet.getString(8);
//                bookHomeService();
                } while (resultSet.moveToNext());
            }
            resultSet.close();
        }

        } catch (Exception e) {
            Log.d("test_err", e + "");
        }



        String json_tests = arrayListtest_data.toString();



        Intent intent = new Intent(this,WebViewActivity1.class);

        intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(accessCode).toString());
        intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(merchantId).toString().trim());
       // intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(orderId).toString().trim());
        intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(currency).toString().trim());
       // intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(amount).toString().trim());
        intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull(redirectUrl).toString().trim());
        intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull(cancelUrl).toString().trim());
        intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull(rsaKeyUrl).toString().trim());
        intent.putExtra("billing_name", name);
        intent.putExtra("billing_tel", mobile);
        intent.putExtra("billing_email", email);
        intent.putExtra("billing_country", "India");
        intent.putExtra("trans",Transport);
        intent.putExtra("appt_date",dateStr);
        intent.putExtra("appt_time",timeStr);
        intent.putExtra("patient_address",address);
        intent.putExtra("json_test",json_tests);
        intent.putExtra("provider_equipment_cost_id",provider_equipment_cost_id);
        intent.putExtra("equipment_provider_id",equipment_provider_id);
        intent.putExtra("equipment_id",equipment_id);
        intent.putExtra("equipment_name",equipment_name);
        intent.putExtra("total_cost",total_cost);
        intent.putExtra("transport",transport);
        intent.putExtra("duration",duration);
        intent.putExtra("start",start);
        intent.putExtra("destination",destination);
        intent.putExtra("provider_id",provider_id);
        intent.putExtra("id",id);
        intent.putExtra("distance_id",distance_id);
       // intent.putExtra("total_cost",total_cost);
        intent.putExtra("accessories",accessories);
        intent.putExtra("title",title);
        intent.putExtra("provider_home_service_id",provider_home_service_id);
        intent.putExtra("provider_service_charge_id",provider_service_charge_id);
        intent.putExtra("service_provider_id",service_provider_id);
        intent.putExtra("collection_cost",collection_cost);
        intent.putExtra("cost",cost);
        intent.putExtra("coupon_code",coupon_code);
//        Log.d("cost_ids :",provider_equipment_cost_id);


        startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        clearDatabase();
            }

    protected void clearDatabase() {
        DBhelper.clearTableData(TableName);
        DBhelper.clearTableData("packages_cart");
        DBhelper.clearTableData("doctors_cart");
        DBhelper.clearTableData("equipment_cart");
        DBhelper.clearTableData("ambulance_cart");
        DBhelper.clearTableData("home_service_cart");

    }

}
