package com.infovita.zywee.stepper;

import android.support.v4.app.Fragment;

/**
 * Created by Khizarkhan on 03-02-2017.
 */

public abstract class stepperFragment extends Fragment {
    public abstract boolean onNextButtonHandler();
    public abstract boolean onBackButtonHandler();
}
