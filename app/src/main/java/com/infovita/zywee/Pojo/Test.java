package com.infovita.zywee.Pojo;

/**
 * Created by Khizarkhan on 30-09-2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sub_test_type")
    @Expose
    private String subTestType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("test_category_id")
    @Expose
    private String testCategoryId;
    @SerializedName("how_is_test_done")
    @Expose
    private String howIsTestDone;
    @SerializedName("test_result_mean")
    @Expose
    private String testResultMean;
    @SerializedName("when_to_take_test")
    @Expose
    private String whenToTakeTest;
    @SerializedName("diagnostics_specialization_id")
    @Expose
    private List<String> diagnosticsSpecializationId = null;
    @SerializedName("test_type_discount")
    @Expose
    private List<String> testTypeDiscount = null;
    @SerializedName("mapping_institute_diagnostic_id")
    @Expose
    private List<String> mappingInstituteDiagnosticId = null;
    @SerializedName("sub_test_id")
    @Expose
    private String subTestId;
    @SerializedName("charge")
    @Expose
    private List<String> charge = null;
    @SerializedName("institute_id")
    @Expose
    private List<String> instituteId = null;
    @SerializedName("health_institute_vote_count")
    @Expose
    private String healthInstituteVoteCount;
    @SerializedName("health_institute_modern_facilities")
    @Expose
    private String healthInstituteModernFacilities;
    @SerializedName("health_institute_nurse_count")
    @Expose
    private String healthInstituteNurseCount;
    @SerializedName("health_institute_logo_file")
    @Expose
    private String healthInstituteLogoFile;
    @SerializedName("health_institute_consult_fee")
    @Expose
    private String healthInstituteConsultFee;
    @SerializedName("health_institute_diag_charges")
    @Expose
    private String healthInstituteDiagCharges;
    @SerializedName("health_institute_diag_packages")
    @Expose
    private String healthInstituteDiagPackages;
    @SerializedName("health_institute_avg_rating")
    @Expose
    private List<String> healthInstituteAvgRating = null;
    @SerializedName("health_institute_min_ward_charge")
    @Expose
    private String healthInstituteMinWardCharge;
    @SerializedName("health_institute_diag_timing")
    @Expose
    private String healthInstituteDiagTiming;
    @SerializedName("health_institute_type_id")
    @Expose
    private List<String> healthInstituteTypeId = null;
    @SerializedName("health_institute_name")
    @Expose
    private List<String> healthInstituteName = null;
    @SerializedName("has_appointment_booking")
    @Expose
    private String hasAppointmentBooking;
    @SerializedName("main_institute_type")
    @Expose
    private String mainInstituteType;
    @SerializedName("health_institute_descrption")
    @Expose
    private List<String> healthInstituteDescrption = null;
    @SerializedName("institute_address_countryid")
    @Expose
    private List<String> instituteAddressCountryid = null;
    @SerializedName("geo")
    @Expose
    private List<String> geo = null;
    @SerializedName("institute_address_line2")
    @Expose
    private List<String> instituteAddressLine2 = null;
    @SerializedName("institute_address_phone_appointment")
    @Expose
    private String instituteAddressPhoneAppointment;
    @SerializedName("institute_address_line1")
    @Expose
    private List<String> instituteAddressLine1 = null;
    @SerializedName("institute_address_cityid")
    @Expose
    private List<String> instituteAddressCityid = null;
    @SerializedName("institute_address_stateid")
    @Expose
    private List<String> instituteAddressStateid = null;
    @SerializedName("institute_address_pincode")
    @Expose
    private String instituteAddressPincode;
    @SerializedName("institute_address_id")
    @Expose
    private List<String> instituteAddressId = null;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("locality_id")
    @Expose
    private List<String> localityId = null;
    @SerializedName("locality_name")
    @Expose
    private List<String> localityName = null;
    @SerializedName("_version_")
    @Expose
    private String version;
    @SerializedName("package_type_id")
    @Expose
    private List<String> packageTypeId = null;
    @SerializedName("institute_speciality_id")
    @Expose
    private List<String> instituteSpecialityId = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubTestType() {
        return subTestType;
    }

    public void setSubTestType(String subTestType) {
        this.subTestType = subTestType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTestCategoryId() {
        return testCategoryId;
    }

    public void setTestCategoryId(String testCategoryId) {
        this.testCategoryId = testCategoryId;
    }

    public String getHowIsTestDone() {
        return howIsTestDone;
    }

    public void setHowIsTestDone(String howIsTestDone) {
        this.howIsTestDone = howIsTestDone;
    }

    public String getTestResultMean() {
        return testResultMean;
    }

    public void setTestResultMean(String testResultMean) {
        this.testResultMean = testResultMean;
    }

    public String getWhenToTakeTest() {
        return whenToTakeTest;
    }

    public void setWhenToTakeTest(String whenToTakeTest) {
        this.whenToTakeTest = whenToTakeTest;
    }

    public List<String> getDiagnosticsSpecializationId() {
        return diagnosticsSpecializationId;
    }

    public void setDiagnosticsSpecializationId(List<String> diagnosticsSpecializationId) {
        this.diagnosticsSpecializationId = diagnosticsSpecializationId;
    }

    public List<String> getTestTypeDiscount() {
        return testTypeDiscount;
    }

    public void setTestTypeDiscount(List<String> testTypeDiscount) {
        this.testTypeDiscount = testTypeDiscount;
    }

    public List<String> getMappingInstituteDiagnosticId() {
        return mappingInstituteDiagnosticId;
    }

    public void setMappingInstituteDiagnosticId(List<String> mappingInstituteDiagnosticId) {
        this.mappingInstituteDiagnosticId = mappingInstituteDiagnosticId;
    }

    public String getSubTestId() {
        return subTestId;
    }

    public void setSubTestId(String subTestId) {
        this.subTestId = subTestId;
    }

    public List<String> getCharge() {
        return charge;
    }

    public void setCharge(List<String> charge) {
        this.charge = charge;
    }

    public List<String> getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(List<String> instituteId) {
        this.instituteId = instituteId;
    }

    public String getHealthInstituteVoteCount() {
        return healthInstituteVoteCount;
    }

    public void setHealthInstituteVoteCount(String healthInstituteVoteCount) {
        this.healthInstituteVoteCount = healthInstituteVoteCount;
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

    public String getHealthInstituteLogoFile() {
        return healthInstituteLogoFile;
    }

    public void setHealthInstituteLogoFile(String healthInstituteLogoFile) {
        this.healthInstituteLogoFile = healthInstituteLogoFile;
    }

    public String getHealthInstituteConsultFee() {
        return healthInstituteConsultFee;
    }

    public void setHealthInstituteConsultFee(String healthInstituteConsultFee) {
        this.healthInstituteConsultFee = healthInstituteConsultFee;
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

    public List<String> getHealthInstituteAvgRating() {
        return healthInstituteAvgRating;
    }

    public void setHealthInstituteAvgRating(List<String> healthInstituteAvgRating) {
        this.healthInstituteAvgRating = healthInstituteAvgRating;
    }

    public String getHealthInstituteMinWardCharge() {
        return healthInstituteMinWardCharge;
    }

    public void setHealthInstituteMinWardCharge(String healthInstituteMinWardCharge) {
        this.healthInstituteMinWardCharge = healthInstituteMinWardCharge;
    }

    public String getHealthInstituteDiagTiming() {
        return healthInstituteDiagTiming;
    }

    public void setHealthInstituteDiagTiming(String healthInstituteDiagTiming) {
        this.healthInstituteDiagTiming = healthInstituteDiagTiming;
    }

    public List<String> getHealthInstituteTypeId() {
        return healthInstituteTypeId;
    }

    public void setHealthInstituteTypeId(List<String> healthInstituteTypeId) {
        this.healthInstituteTypeId = healthInstituteTypeId;
    }

    public List<String> getHealthInstituteName() {
        return healthInstituteName;
    }

    public void setHealthInstituteName(List<String> healthInstituteName) {
        this.healthInstituteName = healthInstituteName;
    }

    public String getHasAppointmentBooking() {
        return hasAppointmentBooking;
    }

    public void setHasAppointmentBooking(String hasAppointmentBooking) {
        this.hasAppointmentBooking = hasAppointmentBooking;
    }

    public String getMainInstituteType() {
        return mainInstituteType;
    }

    public void setMainInstituteType(String mainInstituteType) {
        this.mainInstituteType = mainInstituteType;
    }

    public List<String> getHealthInstituteDescrption() {
        return healthInstituteDescrption;
    }

    public void setHealthInstituteDescrption(List<String> healthInstituteDescrption) {
        this.healthInstituteDescrption = healthInstituteDescrption;
    }

    public List<String> getInstituteAddressCountryid() {
        return instituteAddressCountryid;
    }

    public void setInstituteAddressCountryid(List<String> instituteAddressCountryid) {
        this.instituteAddressCountryid = instituteAddressCountryid;
    }

    public List<String> getGeo() {
        return geo;
    }

    public void setGeo(List<String> geo) {
        this.geo = geo;
    }

    public List<String> getInstituteAddressLine2() {
        return instituteAddressLine2;
    }

    public void setInstituteAddressLine2(List<String> instituteAddressLine2) {
        this.instituteAddressLine2 = instituteAddressLine2;
    }

    public String getInstituteAddressPhoneAppointment() {
        return instituteAddressPhoneAppointment;
    }

    public void setInstituteAddressPhoneAppointment(String instituteAddressPhoneAppointment) {
        this.instituteAddressPhoneAppointment = instituteAddressPhoneAppointment;
    }

    public List<String> getInstituteAddressLine1() {
        return instituteAddressLine1;
    }

    public void setInstituteAddressLine1(List<String> instituteAddressLine1) {
        this.instituteAddressLine1 = instituteAddressLine1;
    }

    public List<String> getInstituteAddressCityid() {
        return instituteAddressCityid;
    }

    public void setInstituteAddressCityid(List<String> instituteAddressCityid) {
        this.instituteAddressCityid = instituteAddressCityid;
    }

    public List<String> getInstituteAddressStateid() {
        return instituteAddressStateid;
    }

    public void setInstituteAddressStateid(List<String> instituteAddressStateid) {
        this.instituteAddressStateid = instituteAddressStateid;
    }

    public String getInstituteAddressPincode() {
        return instituteAddressPincode;
    }

    public void setInstituteAddressPincode(String instituteAddressPincode) {
        this.instituteAddressPincode = instituteAddressPincode;
    }

    public List<String> getInstituteAddressId() {
        return instituteAddressId;
    }

    public void setInstituteAddressId(List<String> instituteAddressId) {
        this.instituteAddressId = instituteAddressId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<String> getLocalityId() {
        return localityId;
    }

    public void setLocalityId(List<String> localityId) {
        this.localityId = localityId;
    }

    public List<String> getLocalityName() {
        return localityName;
    }

    public void setLocalityName(List<String> localityName) {
        this.localityName = localityName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getPackageTypeId() {
        return packageTypeId;
    }

    public void setPackageTypeId(List<String> packageTypeId) {
        this.packageTypeId = packageTypeId;
    }

    public List<String> getInstituteSpecialityId() {
        return instituteSpecialityId;
    }

    public void setInstituteSpecialityId(List<String> instituteSpecialityId) {
        this.instituteSpecialityId = instituteSpecialityId;
    }

}




