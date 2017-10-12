package com.infovita.zywee.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infovita.zywee.Activities.MaincategoriesActivity;
import com.infovita.zywee.Activities.PackageResultsActivity;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

@SuppressLint("ValidFragment")
public class PackagesFragment extends Fragment {
    //private final Context context;
    Context context;
    Serverdatas sd = Serverdatas.getSingletonObject();
    View packageview;
    public PackagesFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        packageview = inflater.inflate(R.layout.fragment_tests, container, false);

        try {
            if (sd.getMain_category_list_data() != null) {
                String jsonStr = sd.getMain_category_list_data();

                if (jsonStr != null) {
                    JSONObject Data = new JSONObject(jsonStr);

                    JSONObject jObject=Data.getJSONObject("MasterTreatementPackageType");
                   // Log.d("Responsef: ", "> " + Data);

                    Iterator<String> iter = jObject.keys();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        try {
                            Object value = jObject.get(key);
                            int drawableResourceId = 0;
                           // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                drawableResourceId = this.getResources().getIdentifier("selectfromstore", "drawable",getContext().getPackageName());
                           // }

                            addCustomButtonView(Integer.parseInt(key),drawableResourceId, value+"");
                            Log.d("Responsevalue: ", "> " + key + value);
                        } catch (JSONException e) {
                            // Something went wrong!
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d("Responsevalue: ", "> " + e);

        }
        return packageview;

    }
    private void addCustomButtonView(final int i,int icon, String text) {
        final String package_type_name=text;
        View view = null;
       // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.basic_list_design, null);
       // }
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(text);

        ImageView iconImage = (ImageView) view.findViewById(R.id.icon);
        //iconImage.setImageResource(R.drawable.healthcheck_yellow_72min);
        if(package_type_name.equals("Cosmetic package")) {
            iconImage.setImageResource(R.drawable.cosmetic_yellow_72min);
        }else if(package_type_name.equals("Dental packages")) {
            iconImage.setImageResource(R.drawable.dental_yellow_72min);
        }else if(package_type_name.equals("Cardiac package")) {
            iconImage.setImageResource(R.drawable.cardiactests_yellow_72min);
        }else if(package_type_name.equals("Eye packages")) {
            iconImage.setImageResource(R.drawable.eye_yellow_72min);
        }else if(package_type_name.equals("Diabetes packages")) {
            iconImage.setImageResource(R.drawable.diabetes_yellow_72min);
        }else if(package_type_name.equals("Cancer packages")) {
            iconImage.setImageResource(R.drawable.cancer_yellow_72min);
        }else if(package_type_name.equals("Children")) {
            iconImage.setImageResource(R.drawable.children_yellow_72min);
        }else if(package_type_name.equals("Maternity packages")) {
            iconImage.setImageResource(R.drawable.maternity_yellow_72);
        }else if(package_type_name.equals("Infertility package")) {
            iconImage.setImageResource(R.drawable.fertility_yellow_72);
        }else if(package_type_name.equals("Ortho packages")) {
            iconImage.setImageResource(R.drawable.ortho_yellow_72);
        } else {
            iconImage.setImageResource(R.drawable.healthcheck_yellow_72min);
        }

        LinearLayout prescription_layout = (LinearLayout) packageview.findViewById(R.id.testfragment);
        prescription_layout.addView(view);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAction(i,package_type_name);
            }
        });

    }
    public void onClickAction(int i,String package_type_name){
        try {
            Log.d("clicked value: ", "> " +i);
            Intent intent = new Intent(getActivity(), PackageResultsActivity.class);
            intent.putExtra("Packagetype",i+"");
            intent.putExtra("PackagetypeName",package_type_name);
            intent.putExtra("health_institute_id","");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            System.out.println("VISIBLE Package");
            MaincategoriesActivity activity = (MaincategoriesActivity) context;
            activity.setTitle("Packages");
        } else {
            System.out.println("GONE");
        }
    }

}
