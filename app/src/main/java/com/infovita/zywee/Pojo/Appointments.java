
package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointments {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("appointment_id")
    @Expose
    private String appointmentId;
    @SerializedName("appointment_title")
    @Expose
    private String appointmentTitle;
    @SerializedName("book_datetime")
    @Expose
    private String bookDatetime;
    @SerializedName("start_datetime")
    @Expose
    private String startDatetime;
    @SerializedName("end_datetime")
    @Expose
    private String endDatetime;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("hash")
    @Expose
    private Object hash;
    @SerializedName("health_institute_id")
    @Expose
    private String healthInstituteId;
    @SerializedName("is_unavailable")
    @Expose
    private String isUnavailable;
    @SerializedName("id_users_provider")
    @Expose
    private Object idUsersProvider;
    @SerializedName("id_users_customer")
    @Expose
    private Object idUsersCustomer;
    @SerializedName("id_user_diagnostics")
    @Expose
    private Object idUserDiagnostics;
    @SerializedName("id_user_tp")
    @Expose
    private String idUserTp;
    @SerializedName("id_services")
    @Expose
    private Object idServices;
    @SerializedName("id_google_calendar")
    @Expose
    private Object idGoogleCalendar;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("followup_status")
    @Expose
    private String followupStatus;
    @SerializedName("visit_type")
    @Expose
    private String visitType;
    @SerializedName("random_code")
    @Expose
    private String randomCode;
    @SerializedName("payment")
    @Expose
    private String payment;
    @SerializedName("whether_home")
    @Expose
    private String whetherHome;
    @SerializedName("house_no")
    @Expose
    private Object houseNo;
    @SerializedName("street")
    @Expose
    private Object street;
    @SerializedName("locality")
    @Expose
    private Object locality;
    @SerializedName("sub_locality")
    @Expose
    private Object subLocality;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("pin")
    @Expose
    private Object pin;
    @SerializedName("distance")
    @Expose
    private Object distance;
    @SerializedName("collection_cost")
    @Expose
    private String collectionCost;
    @SerializedName("zcash_used")
    @Expose
    private String zcashUsed;
    @SerializedName("registration_id")
    @Expose
    private String registrationId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getBookDatetime() {
        return bookDatetime;
    }

    public void setBookDatetime(String bookDatetime) {
        this.bookDatetime = bookDatetime;
    }

    public String getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public Object getHash() {
        return hash;
    }

    public void setHash(Object hash) {
        this.hash = hash;
    }

    public String getHealthInstituteId() {
        return healthInstituteId;
    }

    public void setHealthInstituteId(String healthInstituteId) {
        this.healthInstituteId = healthInstituteId;
    }

    public String getIsUnavailable() {
        return isUnavailable;
    }

    public void setIsUnavailable(String isUnavailable) {
        this.isUnavailable = isUnavailable;
    }

    public Object getIdUsersProvider() {
        return idUsersProvider;
    }

    public void setIdUsersProvider(Object idUsersProvider) {
        this.idUsersProvider = idUsersProvider;
    }

    public Object getIdUsersCustomer() {
        return idUsersCustomer;
    }

    public void setIdUsersCustomer(Object idUsersCustomer) {
        this.idUsersCustomer = idUsersCustomer;
    }

    public Object getIdUserDiagnostics() {
        return idUserDiagnostics;
    }

    public void setIdUserDiagnostics(Object idUserDiagnostics) {
        this.idUserDiagnostics = idUserDiagnostics;
    }

    public String getIdUserTp() {
        return idUserTp;
    }

    public void setIdUserTp(String idUserTp) {
        this.idUserTp = idUserTp;
    }

    public Object getIdServices() {
        return idServices;
    }

    public void setIdServices(Object idServices) {
        this.idServices = idServices;
    }

    public Object getIdGoogleCalendar() {
        return idGoogleCalendar;
    }

    public void setIdGoogleCalendar(Object idGoogleCalendar) {
        this.idGoogleCalendar = idGoogleCalendar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFollowupStatus() {
        return followupStatus;
    }

    public void setFollowupStatus(String followupStatus) {
        this.followupStatus = followupStatus;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getWhetherHome() {
        return whetherHome;
    }

    public void setWhetherHome(String whetherHome) {
        this.whetherHome = whetherHome;
    }

    public Object getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(Object houseNo) {
        this.houseNo = houseNo;
    }

    public Object getStreet() {
        return street;
    }

    public void setStreet(Object street) {
        this.street = street;
    }

    public Object getLocality() {
        return locality;
    }

    public void setLocality(Object locality) {
        this.locality = locality;
    }

    public Object getSubLocality() {
        return subLocality;
    }

    public void setSubLocality(Object subLocality) {
        this.subLocality = subLocality;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getPin() {
        return pin;
    }

    public void setPin(Object pin) {
        this.pin = pin;
    }

    public Object getDistance() {
        return distance;
    }

    public void setDistance(Object distance) {
        this.distance = distance;
    }

    public String getCollectionCost() {
        return collectionCost;
    }

    public void setCollectionCost(String collectionCost) {
        this.collectionCost = collectionCost;
    }

    public String getZcashUsed() {
        return zcashUsed;
    }

    public void setZcashUsed(String zcashUsed) {
        this.zcashUsed = zcashUsed;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
