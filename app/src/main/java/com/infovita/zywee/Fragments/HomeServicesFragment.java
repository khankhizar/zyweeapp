package com.infovita.zywee.Fragments;

//import android.app.Fragment;

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

import com.infovita.zywee.Activities.HomeServicesResultsActivity;
import com.infovita.zywee.Activities.MaincategoriesActivity;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

@SuppressLint("ValidFragment")
public class HomeServicesFragment extends Fragment {
    //private final Context context;
    Context context;
    Serverdatas sd = Serverdatas.getSingletonObject();
    View EqupmentView;

    public HomeServicesFragment(Context context) {
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
        EqupmentView = inflater.inflate(R.layout.fragment_tests, container, false);

        try {
            if (sd.getMain_category_list_data() != null) {
                String jsonStr = sd.getMain_category_list_data();

                if (jsonStr != null) {
                    JSONObject Data = new JSONObject(jsonStr);

                    JSONObject jObject = Data.getJSONObject("HomeServiceCategory");
                    // Log.d("Responsef: ", "> " + Data);

                    Iterator<String> iter = jObject.keys();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        try {
                            Object value = jObject.get(key);
                            int drawableResourceId = 0;
                            // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            drawableResourceId = this.getResources().getIdentifier("selectfromstore", "drawable", getContext().getPackageName());
                            // }

                            addCustomButtonView(Integer.parseInt(key), drawableResourceId, value + "");
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
        return EqupmentView;

    }

    private void addCustomButtonView(final int i, int icon, String text) {
        final String doctor_type_name = text;
        View view = null;
        // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.basic_list_design, null);
        // }
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(text);
        ((ImageView) view.findViewById(R.id.icon)).setImageResource(R.drawable.homehealth_yellow_72min);

//        ImageView iconImage = (ImageView) view.findViewById(R.id.icon);
//        iconImage.setImageResource(icon);

        LinearLayout prescription_layout = (LinearLayout) EqupmentView.findViewById(R.id.testfragment);
        prescription_layout.addView(view);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAction(i, doctor_type_name);
            }
        });

    }

    public void onClickAction(int i, String service_type_name) {
        try {
            Log.d("clicked value: ", "> " + i + " " + service_type_name);
            startActivity(new Intent(getActivity(), HomeServicesResultsActivity.class)
                    .putExtra("service_type", i + "")
                    .putExtra("service_type_name", service_type_name)
                    .putExtra("health_institute_id", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            System.out.println("VISIBLE Home Services");
            MaincategoriesActivity activity = (MaincategoriesActivity) context;
            activity.setTitle("Home Services");
        } else {
            System.out.println("GONE");
        }
    }
}
