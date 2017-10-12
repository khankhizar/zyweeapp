package com.infovita.zywee.Activities;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.infovita.zywee.Fragments.AmbulanceFragment;
import com.infovita.zywee.Fragments.DoctorsFragment;
import com.infovita.zywee.Fragments.EquipmentsFragment;
import com.infovita.zywee.Fragments.HomeServicesFragment;
import com.infovita.zywee.Fragments.PackagesFragment;
import com.infovita.zywee.Fragments.TestsFragment;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Sharedvalues.ServiceHandler;
import com.infovita.zywee.Supports.InternetStatus;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MaincategoriesActivity extends AppCompatActivity {
    Serverdatas sd=Serverdatas.getSingletonObject();
    public String server_url=sd.url;
    public int default_view=0;
    private ProgressDialog pDialog;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.tests_white_96,
            R.drawable.healthcheck_white_96,
            R.drawable.doctor_white_96,
            R.drawable.equipment_white_96,
            R.drawable.ambulance_white_96,
            R.drawable.homehealth_white_96min
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // try {
            setContentView(R.layout.activity_maincategorieslist);
            default_view= getIntent().getExtras().getInt("default_view");
            getlistserver();

            //toolbar = (Toolbar) findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      /*  } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    private void getlistserver() {
        if(isOnline())
        new getlist().execute();
    }

    //Making http call

    private class getlist extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute(){
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MaincategoriesActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Integer doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
//            nameValuePair.add(new BasicNameValuePair("phone",phone_number));
            // Log.d("Response_url: ", "> " + server_url+"gettypes");

            // Making a request to url and getting response
            try {
                String jsonStr = sh.makeServiceCall(server_url+"zywee_app/webservices/gettypes", ServiceHandler.POST, nameValuePair);
              //  Log.d("Response: ", "> " + jsonStr);
                sd.setMain_category_list_data(jsonStr);
                pDialog.cancel();
                return 1;
            }catch (Exception e){
              //  Log.d("Response: ", "> " + e);
                pDialog.cancel();
                return -1;
            }
        }

        public void onPostExecute(Integer arg){
            if(arg == 1){

                viewPager = (ViewPager) findViewById(R.id.viewpager);
                setupViewPager(viewPager);

                tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);
                setupTabIcons();
            }
        }
    }

    public void snakbarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getlistserver();
                    }
                });
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.rgb(0, 111, 192));//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

    private boolean isOnline() {
        boolean status = InternetStatus.getInstance(getApplicationContext()).isOnline();
        if (status)
            return status;
        else {
            /*SharedData sharedData = new SharedData();
            sharedData.*/
            snakbarMessage("Please connect to Internet");
            return status;
        }
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TestsFragment(this), "Tests");
        adapter.addFragment(new PackagesFragment(this),"Packages");
        adapter.addFragment(new DoctorsFragment(this), "Doctors");
        adapter.addFragment(new EquipmentsFragment(this), "Equipments");
        adapter.addFragment(new AmbulanceFragment(this), "Ambulance");
        adapter.addFragment(new HomeServicesFragment(this), "Home Services");
        viewPager.setAdapter(adapter);
       //Add this to show default viewable fragment
        viewPager.setCurrentItem(default_view);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }


}
