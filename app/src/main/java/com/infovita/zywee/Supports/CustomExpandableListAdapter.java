package com.infovita.zywee.Supports;

/**
 * Created by Khizarkhan on 04-05-2016.
 */

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.infovita.zywee.R;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private List<Integer> drawable;
    //private ImageSwitcher indicator;
    private ImageView indicator;
    private ImageView image;


    public CustomExpandableListAdapter(Context context, List<Integer> drawable, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.drawable = drawable;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int groupPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int groupPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(groupPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (expandableListDetail.get(this.expandableListTitle.get(groupPosition)) != null)
            return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
                    .size();
        else return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String listTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_view_item_row, null);
        }

        indicator = (ImageView)convertView.findViewById(R.id.imageSwitcher);
        if ( getChildrenCount( groupPosition ) == 0 ) {
            indicator.setVisibility( View.INVISIBLE );

        } else {
            indicator.setVisibility( View.VISIBLE );
        }

        TextView listTextView = (TextView) convertView
                .findViewById(R.id.textViewName);
        image = (ImageView)convertView.findViewById(R.id.imageViewIcon);
        if(groupPosition==6 || groupPosition==7 || groupPosition==8 || groupPosition==9){
            listTextView.setTypeface(null, Typeface.BOLD);
            listTextView.setText(listTitle);
            listTextView.setPadding(20,0,0,0);
            image.setVisibility(View.GONE);


        }else{

            image.setVisibility(View.VISIBLE);

        }


        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.textViewName);
        if (isExpanded) {
            listTitleTextView.setTypeface(null, Typeface.BOLD);
            listTitleTextView.setText(listTitle);
            ImageView image = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            image.setImageDrawable(context.getResources().getDrawable(drawable.get(groupPosition)));


            //indicator.getResources().getDrawable(R.drawable.plus_gray);
            indicator.setImageResource(R.drawable.minus_gray);

        }else {
            listTitleTextView.setTypeface(null, Typeface.NORMAL);
            listTitleTextView.setText(listTitle);
            ImageView image = (ImageView) convertView.findViewById(R.id.imageViewIcon);

                image.setImageDrawable(context.getResources().getDrawable(drawable.get(groupPosition)));


         //   indicator.getResources().getDrawable(R.drawable.minus_gray);
            indicator.setImageResource(R.drawable.plus_gray);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }



}
