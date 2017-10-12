package com.infovita.zywee.Activities;


import com.infovita.zywee.Pojo.Appointment;

import java.util.Comparator;


/**
 * Created by Khizarkhan on 19-04-2017.
 */
public class DateComparator implements Comparator<Appointment> {


    @Override
    public int compare(Appointment emp1, Appointment emp2) {
        return emp2.getEndDatetime().compareTo(emp1.getEndDatetime().toString()) ;
    }
}
