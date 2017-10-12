package com.infovita.zywee.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.infovita.zywee.Pojo.Ambulance;
import com.infovita.zywee.Pojo.AmbulancePrice;
import com.infovita.zywee.Pojo.Appointment;
import com.infovita.zywee.Pojo.City;
import com.infovita.zywee.Pojo.Doctor;
import com.infovita.zywee.Pojo.EntityInstituteAddress;
import com.infovita.zywee.Pojo.Equipment;
import com.infovita.zywee.Pojo.GetBookingDetail;
import com.infovita.zywee.Pojo.GetReport;
import com.infovita.zywee.Pojo.HomeService;
import com.infovita.zywee.Pojo.HomeServicePrice;
import com.infovita.zywee.Pojo.Image;
import com.infovita.zywee.Pojo.MorePackage;
import com.infovita.zywee.Pojo.MoreTest;
import com.infovita.zywee.Pojo.Package;
import com.infovita.zywee.Pojo.Test1;
import com.infovita.zywee.Sharedvalues.Serverdatas;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by praveen on 30/6/16.
 */
public interface ServerCall {

    // public static String url="http://192.168.2.7/zywee_app/webservices/";
    public static Serverdatas sd = Serverdatas.getSingletonObject();
    String url = sd.url;
    // String url = "http://52.3.216.145/zywee_app/webservices/";
    // String url = "http://54.152.88.70/zywee_app/webservices/";

    @FormUrlEncoded
    @POST("zywee/Packages/getPackageslist")
    Call<List<MorePackage>> getMorePackage(@Field("health_package_id") String health_package_id);

    @FormUrlEncoded
    @POST("zywee/Tests/getSpeclist")
    Call<List<MoreTest>> getMoreTests(@Field("diagnostic_id") String diagnostic_id);

    @FormUrlEncoded
    @POST("zywee_app/webservices/ambulancecancel")
    Call<String> getambulancecancel(@Field("AID") String AID);


    @FormUrlEncoded
    @POST("zywee_app/webservices/listcity")
    Call<List<City>> getCityname(@Field("city_id") String id);

    @FormUrlEncoded
    @POST("zywee_app/webservices/packdoctest_status")
    Call<String> updatedBooking(@Field("order_id") String orderid, @Field("status") String status, @Field("category") String trans,
                                @Field("patient_name") String name, @Field("patient_mobile") String mobile, @Field("patient_email") String email,
                                @Field("patient_address") String address, @Field("appt_date") String date, @Field("appt_time") String time,
                                @Field("appt_json_data") String data, @Field("coupon_code") String coupon_code, @Field("collection_cost") String cost);

    @FormUrlEncoded
    @POST("zywee_app/webservices/paymentstatus")
    Call<String> updateBooking(@Field("order_id") String orderid,@Field("status") String status, @Field("category") String trans );

    /**
     * web services used in packages
     */
    @FormUrlEncoded
    @POST("zywee_app/webservices/getScanImages")
    Call<List<Image>> getImage(@Field("phone") String phone);


    /**
     * web services used in packages
     */
    @FormUrlEncoded
    @POST("zywee_app/webservices/getReports")
    Call<List<GetReport>> getReport(@Field("phone") String phone);

    /**
     * web services used in packages
     */
    @FormUrlEncoded
    @POST("zywee_app/webservices/getAppointmentDetails")
    Call<GetBookingDetail> getDetail(@Field("appointment_id") String appointment_id);


    /**
     * web services used in packages
     */
    @FormUrlEncoded
    @POST("zywee_app/webservices/getAppointmentDetails")
    Call<EntityInstituteAddress> getAddress(@Field("appointment_id") String appointment_id);

    /**
     * web services used in doctors
     */
    @FormUrlEncoded
    @POST("zywee_app/webservices/getdoctors")
    Call<List<Doctor>> getDoctor(@Field("main_type") String main_type, @Field("search_query") String query, @Field("cityid") String cityid);


    /**
     * web services used in packages
     */
    @FormUrlEncoded
    @POST("zywee_app/webservices/getpackage")
    Call<List<Package>> getPackage(@Field("main_type") String main_type, @Field("search_query") String query, @Field("cityid") String cityid);

    /**
     * web services used in test
     */
    @FormUrlEncoded
    @POST("zywee_app/webservices/gettest")
    Call<List<Test1>> getTests(@Field("main_type") String main_type, @Field("search_query") String query, @Field("city_id") String cityid
    );

    @FormUrlEncoded
    @POST("zywee_app/webservices/getequipments")
    Call<List<Equipment>> getEquipments(@Field("main_type") String main_type, @Field("search_query") String search_query, @Field("cityid") String cityid);

    @FormUrlEncoded
    @POST("zywee_app/webservices/equipment_book")
    Call<String> equipmentBooking(@Field("patient_name") String patient_name,
                                  @Field("patient_email") String patient_email,
                                  @Field("patient_mobile") String patient_mobile,
                                  @Field("patient_address") String patient_address,
                                  @Field("provider_equipment_cost_id") String provider_equipment_cost_id,
                                  @Field("equipment_provider_id") String equipment_provider_id,
                                  @Field("equipment_id") String equipment_id,
                                  @Field("total_cost") String total_cost,
                                  @Field("transport") String transport,
                                  @Field("coupon_code") String coupon_code,
                                  @Field("equipment_name") String equipment_name,
                                  @Field("duration") String duration,
                                  @Field("appt_date") String appt_date,
                                  @Field("appt_time") String appt_time);

    @FormUrlEncoded
    @POST("zywee_app/webservices/equipment_payment")
    Call<String> equipmentpayBooking(@Field("patient_name") String patient_name,
                                  @Field("patient_email") String patient_email,
                                  @Field("patient_mobile") String patient_mobile,
                                  @Field("patient_address") String patient_address,
                                  @Field("provider_equipment_cost_id") String provider_equipment_cost_id,
                                  @Field("equipment_provider_id") String equipment_provider_id,
                                  @Field("equipment_id") String equipment_id,
                                  @Field("total_cost") String total_cost,
                                  @Field("transport") String transport,
                                  @Field("coupon_code") String coupon_code,
                                  @Field("equipment_name") String equipment_name,
                                  @Field("duration") String duration,
                                  @Field("appt_date") String appt_date,
                                  @Field("appt_time") String appt_time);
                                    /* @Field("order_id") String orderid,
                                     @Field("status") String status,
                                     @Field("category") String category);*/

    @FormUrlEncoded
    @POST("zywee_app/webservices/equipment_paystatus")
    Call<String> equipmentpaystatus( @Field("order_id") String orderid,
                                     @Field("status") String status,
                                     @Field("category") String category,
                                     @Field("patient_name") String patient_name,
                                     @Field("patient_mobile") String patient_mobile,
                                     @Field("patient_email") String patient_email,
                                     @Field("patient_address") String patient_address,
                                     @Field("provider_equipment_cost_id") String provider_equipment_cost_id,
                                     @Field("equipment_provider_id") String equipment_provider_id,
                                     @Field("equipment_id") String equipment_id,
                                     @Field("total_cost") String total_cost,
                                     @Field("transport") String transport,
                                     @Field("coupon_code") String coupon_code,
                                     @Field("equipment_name") String equipment_name,
                                     @Field("duration") String duration,
                                     @Field("appt_date") String appt_date,
                                     @Field("appt_time") String appt_time);


    /**
     * web services used in ambulances
     */
    @FormUrlEncoded
    @POST("zywee_app/webservices/getambulance")
    Call<List<Ambulance>> getAmbulances(@Field("main_type") String main_type, @Field("search_query") String query, @Field("cityid") String cityid);

    @FormUrlEncoded
    @POST("zywee_app/webservices/getambulance_price")
    Call<AmbulancePrice> getAmbulanceCost(@Field("provider_id") String provider_id,
                                          @Field("id") String id);

    @FormUrlEncoded
    @POST("zywee_app/webservices/ambulance_book")
    Call<String> ambulanceBooking(@Field("patient_name") String patient_name,
                                  @Field("patient_email") String patient_email,
                                  @Field("patient_mobile") String patient_mobile,
                                  @Field("patient_address") String patient_address,
                                  @Field("start") String start,
                                  @Field("destination") String destination,
                                  @Field("provider_id") String provider_id,
                                  @Field("id") String id,
                                  @Field("distance_id") String distance_id,
                                  @Field("total_cost") String total_cost,
                                  @Field("accessories") String accessories,
                                  @Field("title") String title,
                                  @Field("appt_date") String appt_date,
                                  @Field("appt_time") String appt_time,
                                  @Field("coupon_code") String coupon_code);

    @FormUrlEncoded
    @POST("zywee_app/webservices/transport_payment")
    Call<String> ambulancepayBook(@Field("patient_name") String patient_name,
                                  @Field("patient_email") String patient_email,
                                  @Field("patient_mobile") String patient_mobile,
                                  @Field("patient_address") String patient_address,
                                  @Field("start") String start,
                                  @Field("destination") String destination,
                                  @Field("provider_id") String provider_id,
                                  @Field("id") String id,
                                  @Field("distance_id") String distance_id,
                                  @Field("total_cost") String total_cost,
                                  @Field("accessories") String accessories,
                                  @Field("title") String title,
                                  @Field("appt_date") String appt_date,
                                  @Field("appt_time") String appt_time,
                                  @Field("coupon_code") String coupon_code);

    @FormUrlEncoded
    @POST("zywee_app/webservices/transport_paystatus")
    Call<String> ambulancepayBooking(@Field("order_id") String orderid,
                                     @Field("status") String status,
                                     @Field("category") String category,
                                     @Field("patient_name") String patient_name,
                                     @Field("patient_email") String patient_email,
                                     @Field("patient_mobile") String patient_mobile,
                                     @Field("patient_address") String patient_address,
                                     @Field("start") String start,
                                     @Field("destination") String destination,
                                     @Field("provider_id") String provider_id,
                                     @Field("id") String id,
                                     @Field("distance_id") String distance_id,
                                     @Field("total_cost") String total_cost,
                                     @Field("accessories") String accessories,
                                     @Field("title") String title,
                                     @Field("appt_date") String appt_date,
                                     @Field("appt_time") String appt_time,
                                     @Field("coupon_code") String coupon_code);


   /* @FormUrlEncoded
    @POST("gethomeservices")
    Call<List<HomeService>>  getHomeService(@Field("main_type") String main_type, @Field("search_query") String query, @Field("cityid") String cityid);
*/
    /**
     * web services used in home services
     */
    @FormUrlEncoded
    @POST("zywee_app/webservices/gethomeservices")
    Call<List<HomeService>> getHomeService(@Field("main_type") String main_type, @Field("search_query") String query, @Field("cityid") String cityid);

    @FormUrlEncoded
    @POST("zywee_app/webservices/getservice_price")
    Call<List<HomeServicePrice>> getHomeServiceCost(@Field("service_id") String service_id,
                                                    @Field("provider_id") String provider_id);

    @FormUrlEncoded
    @POST("zywee_app/webservices/book_services")
    Call<String> homeServiceBooking(@Field("patient_name") String patient_name,
                                    @Field("patient_email") String patient_email,
                                    @Field("patient_mobile") String patient_mobile,
                                    @Field("patient_address") String patient_address,
                                    @Field("provider_home_service_id") String provider_home_service_id,
                                    @Field("provider_service_charge_id") String provider_service_charge_id,
                                    @Field("service_provider_id") String service_provider_id,
                                    @Field("total_cost") String total_cost,
                                    @Field("collection_cost") String collection_cost,
                                    @Field("select_date") String select_date,
                                    @Field("appt_time") String appt_time,
                                    @Field("title") String title,
                                    @Field("duration") String duration,
                                    @Field("coupon_code") String coupon_code);

    @FormUrlEncoded
    @POST("zywee_app/webservices/homeservice_payment")
    Call<String> homeServicepayBooking(@Field("patient_name") String patient_name,
                                       @Field("patient_email") String patient_email,
                                       @Field("patient_mobile") String patient_mobile,
                                       @Field("patient_address") String patient_address,
                                       @Field("provider_home_service_id") String provider_home_service_id,
                                       @Field("provider_service_charge_id") String provider_service_charge_id,
                                       @Field("service_provider_id") String service_provider_id,
                                       @Field("total_cost") String total_cost,
                                       @Field("collection_cost") String collection_cost,
                                       @Field("select_date") String select_date,
                                       @Field("appt_time") String appt_time,
                                       @Field("title") String title,
                                       @Field("duration") String duration,
                                       @Field("coupon_code") String coupon_code);

    @FormUrlEncoded
    @POST("zywee_app/webservices/homeservice_paystatus")
    Call<String> homeServicepayupdate(@Field("order_id") String orderid,
                                      @Field("status") String status,
                                      @Field("category") String category,
                                      @Field("patient_name") String patient_name,
                                      @Field("patient_email") String patient_email,
                                      @Field("patient_mobile") String patient_mobile,
                                      @Field("patient_address") String patient_address,
                                      @Field("provider_home_service_id") String provider_home_service_id,
                                      @Field("provider_service_charge_id") String provider_service_charge_id,
                                      @Field("service_provider_id") String service_provider_id,
                                      @Field("total_cost") String total_cost,
                                      @Field("collection_cost") String collection_cost,
                                      @Field("select_date") String select_date,
                                      @Field("appt_time") String appt_time,
                                      @Field("title") String title,
                                      @Field("duration") String duration,
                                      @Field("coupon_code") String coupon_code);

    @FormUrlEncoded
    @POST("zywee_app/webservices/getAppointments")
    Call<List<Appointment>> getAppointments(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("zywee_app/webservices/cancelAppointment")
    Call<String> cancel(@Field("appointment_id") String appointment_id);

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    OkHttpClient client = new OkHttpClient();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//            .callbackExecutor(Executors.newFixedThreadPool(1))
            .build();

}

