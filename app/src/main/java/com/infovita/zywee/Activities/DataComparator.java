package com.infovita.zywee.Activities;

import com.infovita.zywee.Pojo.Test1;

import java.util.Comparator;

/**
 * Created by Khizarkhan on 17-05-2017.
 */
public class DataComparator implements Comparator<Test1> {
    @Override
    public int compare(Test1 emp1, Test1 emp2) {
        return emp2.getList().get(0).getEntityHealthInstitute().getHasAppointmentBooking().compareTo(emp1.getList().get(0).getEntityHealthInstitute().getHasAppointmentBooking().toString()) ;
    }
}
