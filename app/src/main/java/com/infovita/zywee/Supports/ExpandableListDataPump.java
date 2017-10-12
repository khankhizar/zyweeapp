package com.infovita.zywee.Supports;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Khizarkhan on 04-05-2016.
 */
public class ExpandableListDataPump {
    public static LinkedHashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> test = new ArrayList();
        test.add("MRI");
        test.add("CT");
        test.add("PET CT");
        test.add("BLOOD TESTS");
        test.add("ENDOSCOPY");
        test.add("ECHO");
        test.add("ULTRASOUND SCAN USG");
        test.add("BMD");
        test.add("Nerve Conduction");
        test.add("Mammography");
        test.add("Open MRI");
        test.add("Dental OPG");


        List<String> healthpackages = new ArrayList();
        healthpackages.add("Health check up packages");
        healthpackages.add("Dental packages");
        healthpackages.add("Eye packages");
        healthpackages.add("Maternity packages");
        healthpackages.add("Infertility packages");
        healthpackages.add("Cosmetic packages");
        healthpackages.add("Cardiac packages");
        healthpackages.add("Ortho packages");

        List<String> Equipment = new ArrayList();
        Equipment.add("Beds");
        Equipment.add("ICU Equipments");
        Equipment.add("Respiratory Aids");
        Equipment.add("Walking Aids");
        Equipment.add("Wheel Chairs");


        List<String> doctors = new ArrayList();
       /* doctors.add("Dental");
        doctors.add("Dermatology");
        doctors.add("Dietitian");
        doctors.add("Obstetrics and Gynecologist OBG");
        doctors.add("Pediatrician");
        doctors.add("Cardiac");
        doctors.add("General Surgery");
        doctors.add("Gastroenterologist");
        doctors.add("Oncology");
        doctors.add("Endocrinology");
        doctors.add("Radiology");
        doctors.add("Urology");
        doctors.add("Nephorlogy");
        doctors.add("Neurology");
        doctors.add("Ortho");
        //16
        doctors.add("Psychiatry");
        doctors.add("ENT");
        doctors.add("Internal Medicine");
        doctors.add("Rheumatology");
        doctors.add("Opthalmology");
        doctors.add("Plastic surgeon");
        //23,24
        doctors.add("Physiothearpy");
        doctors.add("Physician");
        doctors.add("Ayurveda");*/

        List<String> Ambulance = new ArrayList();
        Ambulance.add("Omni");
        Ambulance.add("Tempo Traveller");
        Ambulance.add("Turn Out Wheelchair Cab");
        Ambulance.add("Wheelchair Ramp Cab");

        List<String> HomeServices = new ArrayList();
        HomeServices.add("Doctor");
        HomeServices.add("Nurse");
        HomeServices.add("Nurse Attender Care Giver");
        HomeServices.add("Physiotherapist");
        HomeServices.add("Home Sample Collection");
        HomeServices.add("Eye Check Up");
        HomeServices.add("Dental Check Up");


        List<String> Bookings = new ArrayList();

        List<String> Share = new ArrayList();

        List<String> Rate = new ArrayList();

        List<String> Reports = new ArrayList();



        expandableListDetail.put("Medical Tests",test);

        expandableListDetail.put("Health Checks", healthpackages);

        expandableListDetail.put("Equipment Rental",Equipment);

       // expandableListDetail.put("Doctors",doctors);
        expandableListDetail.put("Doctors",null);

        expandableListDetail.put("Ambulance", Ambulance);

        expandableListDetail.put("HomeServices", HomeServices);

        expandableListDetail.put("My Bookings", null);

        expandableListDetail.put("My Reports", null);

        expandableListDetail.put("Share Zywee App",null);

        expandableListDetail.put("Rate Us", null);



        return expandableListDetail;

    }

}
