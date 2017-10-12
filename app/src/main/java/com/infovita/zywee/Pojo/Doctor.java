package com.infovita.zywee.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Khizarkhan on 26-12-2016.
 */

public class Doctor implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("doctor_name")
    @Expose
    private String doctorName;
    @SerializedName("health_institute_id")
    @Expose
    private String healthInstituteId;
    @SerializedName("specialization_id")
    @Expose
    private String specializationId;
    @SerializedName("doctor_gender")
    @Expose
    private String doctorGender;
    @SerializedName("doctor_qualification")
    @Expose
    private String doctorQualification;
    @SerializedName("doctor_fee")
    @Expose
    private String doctorFee;
    @SerializedName("doctor_experience_years")
    @Expose
    private String doctorExperienceYears;
    @SerializedName("doctor_specialty")
    @Expose
    private String doctorSpecialty;
    @SerializedName("master_specialization_id")
    @Expose
    private String masterSpecializationId;
    @SerializedName("new_specialization_id")
    @Expose
    private String newSpecializationId;
    @SerializedName("new_specialization_name")
    @Expose
    private String newSpecializationName;
    @SerializedName("health_institute_type_id")
    @Expose
    private String healthInstituteTypeId;
    @SerializedName("has_appointment_booking")
    @Expose
    private String hasAppointmentBooking;
    @SerializedName("health_institute_name")
    @Expose
    private String healthInstituteName;
    @SerializedName("health_institute_descrption")
    @Expose
    private String healthInstituteDescrption;
    @SerializedName("health_institute_logo_file")
    @Expose
    private String healthInstituteLogoFile;
    @SerializedName("health_institute_bed_count")
    @Expose
    private String healthInstituteBedCount;
    @SerializedName("health_institute_avg_rating")
    @Expose
    private String healthInstituteAvgRating;
    @SerializedName("health_institute_vote_count")
    @Expose
    private String healthInstituteVoteCount;
    @SerializedName("health_institute_min_ward_charge")
    @Expose
    private String healthInstituteMinWardCharge;
    @SerializedName("health_institute_diag_charges")
    @Expose
    private String healthInstituteDiagCharges;
    @SerializedName("health_institute_diag_packages")
    @Expose
    private String healthInstituteDiagPackages;
    @SerializedName("health_institute_diag_timing")
    @Expose
    private String healthInstituteDiagTiming;
    @SerializedName("health_institute_modern_facilities")
    @Expose
    private String healthInstituteModernFacilities;
    @SerializedName("health_institute_nurse_count")
    @Expose
    private String healthInstituteNurseCount;
    @SerializedName("main_institute_type")
    @Expose
    private String mainInstituteType;
    @SerializedName("health_institute_consult_fee")
    @Expose
    private String healthInstituteConsultFee;
    @SerializedName("institute_address_cityid")
    @Expose
    private String instituteAddressCityid;
    @SerializedName("institute_address_stateid")
    @Expose
    private String instituteAddressStateid;
    @SerializedName("institute_address_countryid")
    @Expose
    private String instituteAddressCountryid;
    @SerializedName("institute_address_id")
    @Expose
    private String instituteAddressId;
    @SerializedName("institute_address_line1")
    @Expose
    private String instituteAddressLine1;
    @SerializedName("institute_address_line2")
    @Expose
    private String instituteAddressLine2;
    @SerializedName("institute_address_pincode")
    @Expose
    private String instituteAddressPincode;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("institute_address_phone_appointment")
    @Expose
    private String instituteAddressPhoneAppointment;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("locality_id")
    @Expose
    private String localityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHealthInstituteId() {
        return healthInstituteId;
    }

    public void setHealthInstituteId(String healthInstituteId) {
        this.healthInstituteId = healthInstituteId;
    }

    public String getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(String specializationId) {
        this.specializationId = specializationId;
    }

    public String getDoctorGender() {
        return doctorGender;
    }

    public void setDoctorGender(String doctorGender) {
        this.doctorGender = doctorGender;
    }

    public String getDoctorQualification() {
        return doctorQualification;
    }

    public void setDoctorQualification(String doctorQualification) {
        this.doctorQualification = doctorQualification;
    }

    public String getDoctorFee() {
        return doctorFee;
    }

    public void setDoctorFee(String doctorFee) {
        this.doctorFee = doctorFee;
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

    public String getMasterSpecializationId() {
        return masterSpecializationId;
    }

    public void setMasterSpecializationId(String masterSpecializationId) {
        this.masterSpecializationId = masterSpecializationId;
    }

    public String getNewSpecializationId() {
        return newSpecializationId;
    }

    public void setNewSpecializationId(String newSpecializationId) {
        this.newSpecializationId = newSpecializationId;
    }

    public String getNewSpecializationName() {
        return newSpecializationName;
    }

    public void setNewSpecializationName(String newSpecializationName) {
        this.newSpecializationName = newSpecializationName;
    }

    public String getHealthInstituteTypeId() {
        return healthInstituteTypeId;
    }

    public void setHealthInstituteTypeId(String healthInstituteTypeId) {
        this.healthInstituteTypeId = healthInstituteTypeId;
    }

    public String getHasAppointmentBooking() {
        return hasAppointmentBooking;
    }

    public void setHasAppointmentBooking(String hasAppointmentBooking) {
        this.hasAppointmentBooking = hasAppointmentBooking;
    }

    public String getHealthInstituteName() {
        return healthInstituteName;
    }

    public void setHealthInstituteName(String healthInstituteName) {
        this.healthInstituteName = healthInstituteName;
    }

    public String getHealthInstituteDescrption() {
        return healthInstituteDescrption;
    }

    public void setHealthInstituteDescrption(String healthInstituteDescrption) {
        this.healthInstituteDescrption = healthInstituteDescrption;
    }

    public String getHealthInstituteLogoFile() {
        return healthInstituteLogoFile;
    }

    public void setHealthInstituteLogoFile(String healthInstituteLogoFile) {
        this.healthInstituteLogoFile = healthInstituteLogoFile;
    }

    public String getHealthInstituteBedCount() {
        return healthInstituteBedCount;
    }

    public void setHealthInstituteBedCount(String healthInstituteBedCount) {
        this.healthInstituteBedCount = healthInstituteBedCount;
    }

    public String getHealthInstituteAvgRating() {
        return healthInstituteAvgRating;
    }

    public void setHealthInstituteAvgRating(String healthInstituteAvgRating) {
        this.healthInstituteAvgRating = healthInstituteAvgRating;
    }

    public String getHealthInstituteVoteCount() {
        return healthInstituteVoteCount;
    }

    public void setHealthInstituteVoteCount(String healthInstituteVoteCount) {
        this.healthInstituteVoteCount = healthInstituteVoteCount;
    }

    public String getHealthInstituteMinWardCharge() {
        return healthInstituteMinWardCharge;
    }

    public void setHealthInstituteMinWardCharge(String healthInstituteMinWardCharge) {
        this.healthInstituteMinWardCharge = healthInstituteMinWardCharge;
    }

    public String getHealthInstituteDiagCharges() {
        return healthInstituteDiagCharges;
    }

    public void setHealthInstituteDiagCharges(String healthInstituteDiagCharges) {
        this.healthInstituteDiagCharges = healthInstituteDiagCharges;
    }

    public String getHealthInstituteDiagPackages() {
        return healthInstituteDiagPackages;
    }

    public void setHealthInstituteDiagPackages(String healthInstituteDiagPackages) {
        this.healthInstituteDiagPackages = healthInstituteDiagPackages;
    }

    public String getHealthInstituteDiagTiming() {
        return healthInstituteDiagTiming;
    }

    public void setHealthInstituteDiagTiming(String healthInstituteDiagTiming) {
        this.healthInstituteDiagTiming = healthInstituteDiagTiming;
    }

    public String getHealthInstituteModernFacilities() {
        return healthInstituteModernFacilities;
    }

    public void setHealthInstituteModernFacilities(String healthInstituteModernFacilities) {
        this.healthInstituteModernFacilities = healthInstituteModernFacilities;
    }

    public String getHealthInstituteNurseCount() {
        return healthInstituteNurseCount;
    }

    public void setHealthInstituteNurseCount(String healthInstituteNurseCount) {
        this.healthInstituteNurseCount = healthInstituteNurseCount;
    }

    public String getMainInstituteType() {
        return mainInstituteType;
    }

    public void setMainInstituteType(String mainInstituteType) {
        this.mainInstituteType = mainInstituteType;
    }

    public String getHealthInstituteConsultFee() {
        return healthInstituteConsultFee;
    }

    public void setHealthInstituteConsultFee(String healthInstituteConsultFee) {
        this.healthInstituteConsultFee = healthInstituteConsultFee;
    }

    public String getInstituteAddressCityid() {
        return instituteAddressCityid;
    }

    public void setInstituteAddressCityid(String instituteAddressCityid) {
        this.instituteAddressCityid = instituteAddressCityid;
    }

    public String getInstituteAddressStateid() {
        return instituteAddressStateid;
    }

    public void setInstituteAddressStateid(String instituteAddressStateid) {
        this.instituteAddressStateid = instituteAddressStateid;
    }

    public String getInstituteAddressCountryid() {
        return instituteAddressCountryid;
    }

    public void setInstituteAddressCountryid(String instituteAddressCountryid) {
        this.instituteAddressCountryid = instituteAddressCountryid;
    }

    public String getInstituteAddressId() {
        return instituteAddressId;
    }

    public void setInstituteAddressId(String instituteAddressId) {
        this.instituteAddressId = instituteAddressId;
    }

    public String getInstituteAddressLine1() {
        return instituteAddressLine1;
    }

    public void setInstituteAddressLine1(String instituteAddressLine1) {
        this.instituteAddressLine1 = instituteAddressLine1;
    }

    public String getInstituteAddressLine2() {
        return instituteAddressLine2;
    }

    public void setInstituteAddressLine2(String instituteAddressLine2) {
        this.instituteAddressLine2 = instituteAddressLine2;
    }

    public String getInstituteAddressPincode() {
        return instituteAddressPincode;
    }

    public void setInstituteAddressPincode(String instituteAddressPincode) {
        this.instituteAddressPincode = instituteAddressPincode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getInstituteAddressPhoneAppointment() {
        return instituteAddressPhoneAppointment;
    }

    public void setInstituteAddressPhoneAppointment(String instituteAddressPhoneAppointment) {
        this.instituteAddressPhoneAppointment = instituteAddressPhoneAppointment;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getLocalityId() {
        return localityId;
    }

    public void setLocalityId(String localityId) {
        this.localityId = localityId;
    }



}

