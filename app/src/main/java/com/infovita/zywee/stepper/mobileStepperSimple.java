package com.infovita.zywee.stepper;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.infovita.zywee.R;

import java.util.List;

/**
 * Created by Khizarkhan on 03-02-2017.
 */

public abstract class mobileStepperSimple extends AppCompatActivity {
    private android.support.v4.app.FragmentManager fm;
    private android.support.v4.app.FragmentTransaction ft;
    private int fragmentNumber, CURRENT_FRAGMENT_STATE = -1;
    private List<stepperFragment> fragments;
    private Button nextButton, backButton;
    public static final String ORDER = "OrderData";

    ProgressBar progress;
    private int progressStatus = 1;


    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobilesteppersimple_main);

        nextButton = (Button) findViewById(R.id.next);
        backButton = (Button) findViewById(R.id.back);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonClicked();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragments.get(CURRENT_FRAGMENT_STATE).onBackButtonHandler()) {
                    onBackPressed();
                    progressStatus--;
                    setProgressBar();
                    progress.setProgress(progressStatus);
                }
            }
        });
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setProgress(progressStatus);
        initApp(savedInstanceState);
        setProgressBar();
    }


    private void setProgressBar() {
//        progress.setProgress(progressStatus);
        if (progressStatus == 1) {
            nextButton.setEnabled(true);
            nextButton.setText("NEXT");
            nextButton.setTextColor(getResources().getColor(R.color.b1));
            nextButton.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.blue_fwd_arrow_thin), null);
        }
        if (progressStatus > 1) {
            backButton.setVisibility(View.VISIBLE);
            backButton.setTextColor(getResources().getColor(R.color.b1));
            if (progressStatus == 4) {
                nextButton.setTextColor(getResources().getColor(R.color.y1));
                nextButton.setText("Place Order");
                nextButton.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.blue_fwd_arrow_thin), null);
            } else if (progressStatus < 4) {
                nextButton.setEnabled(true);
                nextButton.setText("NEXT");
                nextButton.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.blue_fwd_arrow_thin), null);
                nextButton.setTextColor(getResources().getColor(R.color.b1));
            }
        } else {
            backButton.setVisibility(View.GONE);
        }
    }

    protected void onNextButtonClicked() {
        if (fragments.get(CURRENT_FRAGMENT_STATE).onNextButtonHandler()) {
            nextFragment();
            progressStatus++;
            setProgressBar();
            progress.setProgress(progressStatus);
        }
    }

    public abstract void initApp(Bundle bundle);

    public abstract void onStepperCompleted();

    public void setFragment(List<stepperFragment> fragment) {
        this.fragments = fragment;
        fragmentNumber = this.fragments.size();
    }

    public void init() {
        nextFragment();
    }

    private boolean addFragmentLayout(Fragment fragment) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();
        setProgressBar();
        ScrollView scrollView = (ScrollView) findViewById(R.id.mobilescroll);
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        return true;
    }


    protected void backFragment() {
        if (CURRENT_FRAGMENT_STATE > 0) {
            CURRENT_FRAGMENT_STATE = CURRENT_FRAGMENT_STATE - 1;
            addFragmentLayout(this.fragments.get(CURRENT_FRAGMENT_STATE));
//            setProgressBar();
//            progress.setProgress(progressStatus);
        } else {
            //      Log.d(getClass().getName(),"reached first fragment");
        }
    }

    public void nextFragment() {
        if (CURRENT_FRAGMENT_STATE < fragments.size() - 1) {
            CURRENT_FRAGMENT_STATE = CURRENT_FRAGMENT_STATE + 1;
            addFragmentLayout(this.fragments.get(CURRENT_FRAGMENT_STATE));
            //    Log.d("added","fragment");
        } else {
            //  Log.d(getClass().getName(),"Completed all Fragments");
            //it means we have completed all fragments
            onStepperCompleted();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("CURRENT_FRAGMENT_STATE", CURRENT_FRAGMENT_STATE);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CURRENT_FRAGMENT_STATE = savedInstanceState.getInt("CURRENT_FRAGMENT_STATE");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public final void onBackPressed() {
        if (CURRENT_FRAGMENT_STATE > 0) {
            backFragment();
        } else
            super.onBackPressed();
    }

}

