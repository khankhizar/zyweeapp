package com.infovita.zywee.Sharedvalues;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by madroid on 22-12-2015.
 */
public class Serverdatas {
    // public static String url="http://192.168.2.7/zywee_app/webservices/";
    //public static String url = "http://54.152.88.70/zywee_app/webservices/";
    // TODO Change url link to live server

    // public static String url = "http://52.3.216.145/zywee_app/webservices/";
    public static String url = "http://52.3.216.145/";

    // public static String url = "http://54.152.88.70/";
    public static String url1 = "https://www.zywee.com/";


    /**New Live Server for pharma*/
    public String endPoint1 = "http://52.3.216.145/cake_pharma/webservices/";
    public String endPointPrescription = "http://52.3.216.145/cake_pharma/";

    /**Test server*/
   /* public String endPoint1 = "http://54.152.88.70/cake_pharma/webservices/";
    public String endPointPrescription = "http://54.152.88.70/cake_pharma/";*/


    public static final String LOGIN_FLAG = "0";
    public static String user_phone = "";
    public static String user_id = "";
    public static String user_email = "";
    public static Serverdatas singletonObject;
    //public static String cityid = "1";
    public static String cityid = "";
    private String TAG = "WebServiceHandler";

    /*public static String image_url = "http://52.3.216.145/img/";
    public static String image_url_base = "http://52.3.216.145/images/mainimages/";*/

    /*public static String image_url = "http://54.152.88.70/zywee/img/";
    public static String image_url_base = "http://54.152.88.70/zywee/images/mainimages/";*/

    public static String image_url = "https://www.zywee.com/img/";
    public static String image_url_base = "https://www.zywee.com/images/mainimages/";


    public static final String contact_number = "tel:+917676100666";
   // private String appointment_id="";
    private String id = "";




    public String getList_type() {
        return list_type;
    }

    public void setList_type(String list_type) {
        this.list_type = list_type;
    }

    private String list_type;
    private String appointment_id;
    private String list_item_name;
    private String list_item_details;
    private String list_item_price;
    private String list_item_id;
    private String list_center_name;
    private String list_locality_name;
    private String list_item_discount;
    private String health_institute_avg_rating;
    private String health_institute_id;
    private String specialization_id;
    private String distance;
    public static String location = "location";
    public static String latitude = "latitude";
    public static String longitude = "longitude";
    private String geo = "12.9131844,77.6323384";

    public String contact_person = "name";
    public String address = "address";
    public String date = "date";
    public String time = "time";
    private String specializaation;
    private String doctorQualification;
    private String doctorExperienceYears;
    private String doctorSpecialty;
    private String hasAppointmentBooking;
    private String doctorFee;


    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }



    public String getSpecialization_id() {
        return specialization_id;
    }

    public void setSpecialization_id(String specialization_id) {
        this.specialization_id = specialization_id;
    }


    public String getHealth_institute_id() {
        return health_institute_id;
    }

    public void setHealth_institute_id(String health_institute_id) {
        this.health_institute_id = health_institute_id;
    }


    public String getHealth_institute_avg_rating() {
        return health_institute_avg_rating;
    }

    public void setHealth_institute_avg_rating(String health_institute_avg_rating) {
        this.health_institute_avg_rating = health_institute_avg_rating;
    }


    public String getList_item_discount() {
        return list_item_discount;
    }

    public void setList_item_discount(String list_item_discount) {
        this.list_item_discount = list_item_discount;
    }


    public String getList_locality_name() {
        return list_locality_name;
    }

    public void setList_locality_name(String list_locality_name) {
        this.list_locality_name = list_locality_name;
    }


    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String city_name;

    public String getList_item_name() {
        return list_item_name;
    }

    public void setList_item_name(String list_item_name) {
        this.list_item_name = list_item_name;
    }

    public String getList_item_details() {
        return list_item_details;
    }

    public void setList_item_details(String list_item_details) {
        this.list_item_details = list_item_details;
    }

    public String getList_item_price() {
        return list_item_price;
    }

    public void setList_item_price(String list_item_price) {
        this.list_item_price = list_item_price;
    }

    public String getList_item_id() {
        return list_item_id;
    }

    public void setList_item_id(String list_item_id) {
        this.list_item_id = list_item_id;
    }

    public String getList_center_name() {
        return list_center_name;
    }

    public void setList_center_name(String list_center_name) {
        this.list_center_name = list_center_name;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorFee() {
        return doctorFee;
    }

    public void setDoctorFee(String doctorFee) {
        this.doctorFee = doctorFee;
    }




    public static String getMain_category_list_data() {
        return main_category_list_data;
    }

    public static void setMain_category_list_data(String main_category_list_data) {
        Serverdatas.main_category_list_data = main_category_list_data;
    }

    public static String main_category_list_data;

    public static synchronized Serverdatas getSingletonObject() {
        if (singletonObject == null) {
            singletonObject = new Serverdatas();
        }
        return singletonObject;
    }

    public void clear() {
        singletonObject = null;
    }


    public void setAppointment_id(String appointment_id) {
        //Log.d("appointment_id",appointment_id);
        this.appointment_id = appointment_id;
    }
    public String getAppointment_id() {
        return appointment_id;
    }

    public String getSpecializaation() {
        return specializaation;
    }

    public void setSpecializaation(String specializaation) {
        this.specializaation = specializaation;
    }

    public String getDoctorQualification() {
        return doctorQualification;
    }

    public void setDoctorQualification(String doctorQualification) {
        this.doctorQualification = doctorQualification;
    }

    public String getDoctorExperienceYears() {
        return doctorExperienceYears;
    }

    public void setDoctorExperienceYears(String doctorExperienceYears) {
        this.doctorExperienceYears = doctorExperienceYears;
    }

    public String getDoctorSpecialty() {
        return doctorSpecialty;
    }

    public void setDoctorSpecialty(String doctorSpecialty) {
        this.doctorSpecialty = doctorSpecialty;
    }

    public String getHasAppointmentBooking() {
        return hasAppointmentBooking;
    }

    public void setHasAppointmentBooking(String hasAppointmentBooking) {
        this.hasAppointmentBooking = hasAppointmentBooking;
    }

    public String sendOrderDetail(List<NameValuePair> nameValuePairs) {
        String methodName = "order";
        //Log.v(TAG, "Sending order web service called");
        return webserviceCall(nameValuePairs, methodName);
    }

    private String webserviceCall(List<NameValuePair> nameValuePairs, String methodName) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        HttpPost httppost = new HttpPost(endPoint1 + methodName);
        //Log.d("Linkactive :",endPoint1 + methodName);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);

            //Log.i("Tag", "Response" + response);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
                return out.toString();
            } else {
                // Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //Log.e(TAG, "Error while getting response from the server(ClientProtocolException): " + e.toString());
        } catch (IOException e) {
            //Log.e(TAG, "Error while getting response from the server(IOException): " + e.toString());
        }
        //Log.v(TAG, "Returning Null Response string from background to main thread");
        return null;
    }





}
