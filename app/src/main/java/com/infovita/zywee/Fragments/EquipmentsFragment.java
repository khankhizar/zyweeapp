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

import com.infovita.zywee.Activities.EquipmentResultsActivity;
import com.infovita.zywee.Activities.MaincategoriesActivity;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

@SuppressLint("ValidFragment")
public class EquipmentsFragment extends Fragment {
    //private final Context context;
    Context context;
    Serverdatas sd = Serverdatas.getSingletonObject();
    View EqupmentView;
    public EquipmentsFragment(Context context) {
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
        EqupmentView = inflater.inflate(R.layout.fragment_tests, container, false);

        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run(){
                // Do whatever you want
                // Inflate the layout for this fragment
                try {
                    if (sd.getMain_category_list_data() != null) {
                        String jsonStr = sd.getMain_category_list_data();

                        if (jsonStr != null) {
                            JSONObject Data = new JSONObject(jsonStr);

                            JSONObject jObject=Data.getJSONObject("EquipmentSpeciality");
                            // Log.d("Responsef: ", "> " + Data);

                            Iterator<String> iter = jObject.keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    Object value = jObject.get(key);
                                    int drawableResourceId = 0;
                                    // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                    drawableResourceId = getActivity().getResources().getIdentifier("selectfromstore", "drawable",getContext().getPackageName());
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
            }
        });
        return EqupmentView;
    }

    private void addCustomButtonView(final int i,int icon, String text) {
        final String doctor_type_name=text;
        View view = null;
       // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.basic_list_design, null);
       // }
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(text);

        ImageView iconImage = (ImageView) view.findViewById(R.id.icon);
        iconImage.setImageResource(R.drawable.equipment_yellow_72min);

        LinearLayout prescription_layout = (LinearLayout) EqupmentView.findViewById(R.id.testfragment);
        prescription_layout.addView(view);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAction(i,doctor_type_name);
            }
        });

    }
    public void onClickAction(int i,String equipment_type){
        try {
            Log.d("clicked value: ", "> " +i + " " + equipment_type);
            Intent intent = new Intent(getActivity(), EquipmentResultsActivity.class);
            intent.putExtra("equipment_type",i+"");
            intent.putExtra("Equipment_type_name",equipment_type);
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
            System.out.println("VISIBLE Doctors");
            MaincategoriesActivity activity = (MaincategoriesActivity) context;
            activity.setTitle("Equipments");
        } else {
            System.out.println("GONE");
        }
    }

}
