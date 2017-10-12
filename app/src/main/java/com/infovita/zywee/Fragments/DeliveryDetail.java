package com.infovita.zywee.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infovita.zywee.R;
import com.infovita.zywee.Supports.ApplicationPreferences;
import com.infovita.zywee.Supports.InternetStatus;
import com.infovita.zywee.Supports.SharedData;
import com.infovita.zywee.Supports.constant;
import com.infovita.zywee.stepper.stepperFragment;

/**
 * Created by Khizarkhan on 06-02-2017.
 */

public class DeliveryDetail extends stepperFragment implements View.OnClickListener {

    public static final String ORDER = "OrderData";

    private final String TAG = "Delivery Details";
    String normal_d = "1";
    String express_d = "2";

    Button normalDeliveryButton, expressDeliveryButton;
    ProgressBar progressBar;

    Button getCurrentAddressButton;
    EditText contactPersonEditText, phoneNumberEditText, emailEditText, houseNumberEditText, streetAddressEditText;

    String delivery_mode = "";
    String contact_name,phone_number,email,house_number,street_address;

    View view;


    public DeliveryDetail() {
        // Required empty public constructor
    }

    private void initialise() {
//        normalDeliveryButton = (Button) view.findViewById(R.id.normal_delivery);
//        expressDeliveryButton = (Button) view.findViewById(R.id.express_delivery);
        progressBar = (ProgressBar) view.findViewById(R.id.deliver_page_progress_bar);
        getCurrentAddressButton = (Button) view.findViewById(R.id.get_current_address);

        contactPersonEditText = (EditText) view.findViewById(R.id.contact_person);
        phoneNumberEditText = (EditText) view.findViewById(R.id.phone_number);
        emailEditText = (EditText) view.findViewById(R.id.email);
        houseNumberEditText = (EditText) view.findViewById(R.id.house_number);
        streetAddressEditText = (EditText) view.findViewById(R.id.street_address);
        //clearErrors();


//        normalDeliveryButton.setOnClickListener(this);
//        expressDeliveryButton.setOnClickListener(this);
        getCurrentAddressButton.setOnClickListener(this);

        sharedData = SharedData.getSingletonObject();

        ApplicationPreferences preferences = new ApplicationPreferences();
        String phone_number = preferences.getUserRegisterPhone(getContext());

        phoneNumberEditText.setText(phone_number);
        hideSoftKeyboard();
    }



    @Override
    public boolean onNextButtonHandler() {

        hideSoftKeyboard();

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(ORDER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        contact_name = contactPersonEditText.getText().toString().trim();
        phone_number = phoneNumberEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        house_number = houseNumberEditText.getText().toString().trim();
        street_address = streetAddressEditText.getText().toString().trim();

//        Variable to represent error fields
        boolean status = true;

        if (contact_name.equals("")) {
            status = false;
            contactPersonEditText.requestFocus();
            contactPersonEditText.setError("Please enter contact person name");

        }
        if (phone_number.equals("") && phone_number.length() != 10) {
            status = false;
            phoneNumberEditText.requestFocus();
            phoneNumberEditText.setError("Contact number required");

        } else if (!phone_number.matches("^[7-9][0-9]{9}$")) {
            status = false;
            phoneNumberEditText.requestFocus();
            phoneNumberEditText.setError("Enter valid Mobile Number");
        }
        if (!email.equals("") &&  !isValidEmail(email)) {
            status = false;
            emailEditText.requestFocus();
            emailEditText.setError("Email id invalid");
        }
        if (house_number.equals("")) {
            status = false;
            houseNumberEditText.requestFocus();
            houseNumberEditText.setError("Please enter House number");
        }
        if (street_address.equals("")) {
            status = false;
            streetAddressEditText.requestFocus();
            streetAddressEditText.setError("Please enter address");
        }

        if (status && isOnline()) {
            try {
                String latitude, longitude;
                GPSTracker gps = new GPSTracker(getContext());
                if (gps.canGetLocation()) {
                    latitude = String.valueOf(gps.getLatitude());
                    longitude = String.valueOf(gps.getLongitude());
                    editor.putString(constant.delivery_type, delivery_mode);
                    editor.putString(constant.contact_person, contact_name);
                    editor.putString(constant.contact_number, phone_number);
                    editor.putString(constant.contact_email, email);
                    editor.putString(constant.address, house_number + ", " + street_address);
                    editor.putString(constant.latitude, latitude);
                    editor.putString(constant.longitude, longitude);
                    editor.apply();
                    return true;
                } else {
                    gps.showSettingsAlert();
                }
            } catch (Exception e) {
                return false;
            }
        }


        return false;
    }

    public final static boolean isValidEmail(CharSequence mail) {
        return !TextUtils.isEmpty(mail) && android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }


    @Override
    public boolean onBackButtonHandler() {
        return true;
    }


    private boolean isOnline() {
        boolean status = InternetStatus.getInstance(getContext()).isOnline();
        if (status)
            return status;
        else {
            /*SharedData sharedData = new SharedData();
            sharedData.*/
            snakbarMessage("Please connect to Internet");
            return status;
        }
    }


    TextView normal_delivery_text, express_delivery_text;
    RelativeLayout normal_delivery_layout, express_delivery_layout;
    ImageView normal, express;



    SharedData sharedData;

    private void clearErrors() {
        contactPersonEditText.setError(null);
        phoneNumberEditText.setError(null);
        houseNumberEditText.setError(null);
        streetAddressEditText.setError(null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delivery_detail, container, false);
        initialise();



        return view;
    }

    public void snakbarMessage(String message) {

        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.get_current_address:
                if (isOnline()) {
                    try {
                        GPSTracker gps = new GPSTracker(getContext());
                        if (gps.canGetLocation()) {
                            new GetCurrentAddress().execute();
                        } else {
                            gps.showSettingsAlert();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

    private class GetCurrentAddress extends AsyncTask<Void, Void, Void> {
        GPSTracker gps = new GPSTracker(getContext());
        double latitude, longitude;
        ApplicationPreferences preferences = new ApplicationPreferences();
        boolean gps_check = false;

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Fetching Address..");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            preferences.storeLatitude(getContext(), String.valueOf(latitude));
            preferences.storeLongitude(getContext(), String.valueOf(longitude));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            progressBar.setVisibility(View.INVISIBLE);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                streetAddressEditText.setText(gps.getAddress(getContext(), latitude, longitude));
            } catch (Exception e) {
                e.printStackTrace();
            }
            streetAddressEditText.setError(null);
            super.onPostExecute(aVoid);
        }
    }



    @Override
    public void onPause() {
        super.onPause();
    }



    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

}

