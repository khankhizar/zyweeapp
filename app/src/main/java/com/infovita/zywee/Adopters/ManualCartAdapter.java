package com.infovita.zywee.Adopters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.infovita.zywee.Activities.MyCart;
import com.infovita.zywee.R;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Supports.ManualItem;

import java.util.ArrayList;

/**
 * Created by Khizarkhan on 03-02-2017.
 */
public class ManualCartAdapter extends ArrayAdapter<ManualItem> {

    private final LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<ManualItem> list = new ArrayList<>();
    private MyCart.Listener listener;
    int resource;


    public ManualCartAdapter(Context context, int resource, ArrayList<ManualItem> list, MyCart.Listener listener) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
        this.listener = listener;

       // Log.d("List", String.valueOf(list.size()));

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ManualItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ManualItem item = this.getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(resource, null);
            holder = new ViewHolder();
            holder.description = (TextView) convertView.findViewById(R.id.description_text_view);
            holder.quantity = (TextView) convertView.findViewById(R.id.quantity_text_view);
            holder.notes = (TextView) convertView.findViewById(R.id.notes_text_view);
            holder.modify = (Button) convertView.findViewById(R.id.modify_button);
            holder.remove = (Button) convertView.findViewById(R.id.remove_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.description.setText(item.getDescrription());
        holder.quantity.setText(item.getQuantity());
        holder.notes.setText(item.getNotes());

        holder.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onModifyClick(position);
                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper helper = new DatabaseHelper(context);
                boolean result = helper.removeManualItem(item.getTag());//Remove the item from the database
                if (listener != null) {
//                    remove(item);
                    listener.onRemoveClick(position);
                }
            }
        });

        return convertView;
    }

    @Override
    public void remove(ManualItem object) {
        super.remove(object);
//        Toast.makeText(context, "Item has been removed", Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder {
        TextView description, quantity, notes;
        Button modify, remove;
    }
}

