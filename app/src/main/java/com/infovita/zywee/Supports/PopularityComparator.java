package com.infovita.zywee.Supports;

import com.infovita.zywee.Pojo.Test1;

import java.util.Comparator;

/**
 * Created by Khizarkhan on 03-08-2017.
 */
public class PopularityComparator implements Comparator<Test1> {
    @Override
    public int compare(Test1 emp1, Test1 emp2) {
        return emp2.getList().get(0).getTestInstituteSpecialization().getTestPopularity().compareTo(emp1.getList().get(1).getTestInstituteSpecialization().getTestPopularity().toString()) ;
    }
}
