package com.infovita.zywee.Supports;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by Khizarkhan on 03-02-2017.
 */
public class ApplicationPreferences extends Activity {

    private static final String TAG = "ApplicationPreferences";

    public static final String PROPERTY_LOGIN_STATUS = "loginStatus";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    public static final String EXTRA_MESSAGE = "message";

    private static final String PROPERTY_USER_REGISTERED_PHONE ="userRegisteredPhone";

    private static final String PROPERTY_USER_ID = "userId";

    private static final String PROPERTY_READ = "agreementRead";

    private static final String PROPERTY_LATITUDE = "latitude";

    private static final String PROPERTY_LONGITUDE = "longitude";


    private static final String DELIVERY_MODE = "deliveryMode";
    private static final String CONTACT_PERSON = "contactPerson";
    private static final String CONTACT_PHONE = "contactPhone";
    private static final String CONTACT_EMAIL = "contactEmail";
    private static final String CONTACT_HOUSE_NUMBER = "contactHouseNumber";
    private static final String CONTACT_ADDRESS = "contactAddress";

    private static final String VENDOR_ID = "vendorID";
    private static final String PHARMACY_NAME = "pharmacyName";
    private static final String PHARMACY_PHONE = "pharmacyPhone";

    private static final String DELIVERY_START_DATE = "startDate";
    private static final String DELIVERY_START_TIME = "startTime";

    /**
     *
     *  Stores the status of the user login after installation
     *  into the shared Preferences for the application
     *
     * @param context: for current context of the activity
     * @param status: for login status whether logged in or not
     */
    public void storeLoginStatus(Context context, String status)
    {

        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_LOGIN_STATUS,0);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving login status on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_LOGIN_STATUS, status);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();

    }

    /**
     *  get the login status of the application from the stored shared preferences
     * @return
     */
    public boolean getLoginStatus(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_LOGIN_STATUS, 0);
        String loginStatus = prefs.getString(PROPERTY_LOGIN_STATUS, "");
        Log.v(TAG, "Login Status: " + loginStatus);
        if (loginStatus.equals("") || loginStatus.equals("false")) {
            Log.i(TAG, "Login status: "+loginStatus);
            return false;
        }else if(loginStatus.equals("true")){
            return true;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return false;
        }
        return false;
    }

    public void storeUserRegisteredPhone(Context context,String phone)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_USER_REGISTERED_PHONE, 0);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_USER_REGISTERED_PHONE, phone);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public String getUserRegisterPhone(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_USER_REGISTERED_PHONE,0);
        String userPhone = prefs.getString(PROPERTY_USER_REGISTERED_PHONE, "");
        Log.v(TAG, "User Phone: " + userPhone);
        if (userPhone.equals("")) {
            return null;
        }else if(!userPhone.equals("")){
            return userPhone;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }
    /**
     * store the user_id of the patient
     */

    public void storeUserId(Context context,String userId)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_USER_ID, 0);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving user app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_USER_ID, userId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public String getUserID(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_USER_ID,0);
        String userId = prefs.getString(PROPERTY_USER_ID, "");
        Log.v(TAG, "User Id: " + userId);
        if (userId.equals("")) {
            return null;
        }else if(!userId.equals("")){
            return userId;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }


    public void storeLatitude(Context context, String latitude) {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_LATITUDE, 0);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving latitude: " + latitude + " on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_LATITUDE, latitude);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }


    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    public String getLatitude(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_LATITUDE, 0);
        String latitude = prefs.getString(PROPERTY_LATITUDE, "");
        if (latitude.equals("")) {
            Log.i(TAG, "Latitude not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        Log.v(TAG, "Prefs Lat: " + latitude + " app version: " + currentVersion);
        return latitude;
    }
    public void storeLongitude(Context context, String longitude) {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_LONGITUDE, 0);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving longitude: "+longitude+" on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_LONGITUDE, longitude);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }


    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    public String getLongitude(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_LONGITUDE, 0);
        String longitude = prefs.getString(PROPERTY_LONGITUDE, "");
        if (longitude.equals("")) {
            Log.i(TAG, "Longitude not found.");
            return "";
        }

        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        Log.i(TAG, "Prefs Long: " + longitude + " app version: " + currentVersion);
        return longitude;
    }

    public void setAgreementReadIndicator(Context context,String indicator)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_READ, 0);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving user app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_READ, indicator);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public String getAgreementReadIndicator(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_READ,0);
        String read = prefs.getString(PROPERTY_READ, "");
        Log.v(TAG, "Read?: " + read);
        if (read.equals("")) {
            return null;
        }else if(!read.equals("")){
            return read;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }



    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public void storeDeliveryDetails(Context context, String delivery_mode, String contact_person, String contact_phone, String contact_email, String contact_house_number, String contact_address) {
        final SharedPreferences prefs = context.getSharedPreferences(CONTACT_PERSON, 0);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving details of the person " + contact_person + " on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(DELIVERY_MODE, delivery_mode);
        editor.putString(CONTACT_PERSON, contact_person);
        editor.putString(CONTACT_PHONE, contact_phone);
        editor.putString(CONTACT_EMAIL, contact_email);
        editor.putString(CONTACT_HOUSE_NUMBER, contact_house_number);
        editor.putString(CONTACT_ADDRESS, contact_address);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }


    public void storeDeliveryStartDateTime(Context context, String date, String time){
        final SharedPreferences prefs = context.getSharedPreferences(DELIVERY_START_DATE, 0);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving delivery details which starts on " + date + " on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(DELIVERY_START_DATE, date);
        editor.putString(DELIVERY_START_TIME, time);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public String getVendorID(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(CONTACT_PERSON,0);
        String vendorID = prefs.getString(VENDOR_ID, "");
        Log.v(TAG, "Vendor ID: " + vendorID);
        if (vendorID.equals("")) {
            return null;
        }else if(!vendorID.equals("")){
            return vendorID;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }

    public void storePharmacyDetails(Context context, String selectedVendorID, String selectedPharmacyName, String selectedPharmacyPhone ){
        final SharedPreferences prefs = context.getSharedPreferences(VENDOR_ID, 0);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving details of the pharacy " + selectedPharmacyName + " on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(VENDOR_ID, selectedVendorID);
        editor.putString(PHARMACY_NAME, selectedPharmacyName);
        editor.putString(PHARMACY_PHONE, selectedPharmacyPhone);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }


    private static final String VENDOR = "name";
    public void storeVendorId(Context context, String selectedVendorID){
        final SharedPreferences prefs = context.getSharedPreferences(VENDOR, 0);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving details of the pharacy id " + selectedVendorID + " on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(VENDOR, selectedVendorID);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public String getVendor(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(VENDOR,0);
        String vendor = prefs.getString(VENDOR, "");
        Log.v(TAG, "Vendor Id: " + vendor);
        if (vendor.equals("")) {
            return null;
        }else if(!vendor.equals("")){
            return vendor;
        }
        return null;
    }

    public String getPharmacyName(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PHARMACY_NAME,0);
        String pharmacyName = prefs.getString(PHARMACY_NAME, "");
        Log.v(TAG, "Pharmacy Name: " + pharmacyName);
        if (pharmacyName.equals("")) {
            return null;
        }else if(!pharmacyName.equals("")){
            return pharmacyName;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }

    public String getPharmacyPhone(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PHARMACY_PHONE,0);
        String pharmacyPhone = prefs.getString(PHARMACY_PHONE, "");
        Log.v(TAG, "Vendor ID: " + pharmacyPhone);
        if (pharmacyPhone.equals("")) {
            return null;
        }else if(!pharmacyPhone.equals("")){
            return pharmacyPhone;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }

    public String getContactPerson(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(CONTACT_PERSON,0);
        String contactPerson = prefs.getString(CONTACT_PERSON, "");
        Log.v(TAG, "Contact Person: " + contactPerson);
        if (contactPerson.equals("")) {
            return null;
        }else if(!contactPerson.equals("")){
            return contactPerson;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }

    public String getContactPhone(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(CONTACT_PHONE,0);
        String contactPhone = prefs.getString(CONTACT_PHONE, "");
        Log.v(TAG, "Contact Phone: " + contactPhone);
        if (contactPhone.equals("")) {
            return null;
        }else if(!contactPhone.equals("")){
            return contactPhone;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }

    public String getContactEmail(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(CONTACT_EMAIL,0);
        String contactEmail = prefs.getString(CONTACT_EMAIL, "");
        Log.v(TAG, "Contact Email: " + contactEmail);
        if (contactEmail.equals("")) {
            return null;
        }else if(!contactEmail.equals("")){
            return contactEmail;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }

    public String getContactHouseNumber(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(CONTACT_HOUSE_NUMBER,0);
        String contactHouseNumber = prefs.getString(CONTACT_HOUSE_NUMBER, "");
        Log.v(TAG, "Contact House Number: " + contactHouseNumber);
        if (contactHouseNumber.equals("")) {
            return null;
        }else if(!contactHouseNumber.equals("")){
            return contactHouseNumber;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }

    public String getContactAddress(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(CONTACT_ADDRESS,0);
        String contactAddress = prefs.getString(CONTACT_ADDRESS, "");
        Log.v(TAG, "Contact Address: " + contactAddress);
        if (contactAddress.equals("")) {
            return null;
        }else if(!contactAddress.equals("")){
            return contactAddress;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }


    private static final String PRESCRIPTION_NAME = "name";

    public void storePrescriptionName(Context context, String name){
        final SharedPreferences prefs = context.getSharedPreferences(PRESCRIPTION_NAME, 0);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Prescription name: " + name + " on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PRESCRIPTION_NAME, name);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public String getPrescriptionName(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PRESCRIPTION_NAME, 0);
        String name = prefs.getString(PRESCRIPTION_NAME, "");
        Log.v(TAG, "Prescription name: " + name);
        if (name.equals("")) {
            return null;
        }else if(!name.equals("")){
            return name;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }



    public static String PROPERTY_REFER = "refer";

    public void storeRefer(Context context,String refer)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_REFER, 0);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving user app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REFER, refer);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public String getRefer(Context context)
    {
        final SharedPreferences prefs = context.getSharedPreferences(PROPERTY_REFER,0);
        String userId = prefs.getString(PROPERTY_REFER, "");
        Log.v(TAG, "Refer: " + userId);
        if (userId.equals("")) {
            return null;
        }else if(!userId.equals("")){
            return userId;
        }
        // Check if app was updated; if so, it must clear the login status
        // since the existing login status is not true for new app version
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return registeredVersion+"";
        }
        return null;
    }
}

