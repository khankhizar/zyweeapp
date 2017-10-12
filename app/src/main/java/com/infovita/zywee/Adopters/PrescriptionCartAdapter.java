package com.infovita.zywee.Adopters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.infovita.zywee.Activities.MyCart;
import com.infovita.zywee.R;
import com.infovita.zywee.Supports.PrescriptionCart;

import java.util.ArrayList;

/**
 * Created by Khizarkhan on 03-02-2017.
 */
public class PrescriptionCartAdapter extends ArrayAdapter<PrescriptionCart> {

    private final LayoutInflater vi;
    Context context;
    int resource;
    ArrayList<PrescriptionCart> prescriptionArrayList = new ArrayList<>();

    MyCart.Listener listener;

    public PrescriptionCartAdapter(Context context, int resource, ArrayList<PrescriptionCart> objects, MyCart.Listener listener) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listener = listener;
        prescriptionArrayList = objects;
        Log.d("List", String.valueOf(objects.size()));

        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PrescriptionCart getItem(int position) {
        return prescriptionArrayList.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final PrescriptionCart prescriptionCart = this.getItem(position);

        ViewHolder holder;

        if (convertView == null) {
            convertView = vi.inflate(resource, null);
            holder = new ViewHolder();
            holder.prescriptionImage = (ImageView) convertView.findViewById(R.id.image);
 /*           holder.modifyButton = (Button) convertView.findViewById(R.id.modifyButtonForPrescriptionImage);
            holder.removeButton = (Button) convertView.findViewById(R.id.removeButtonForPrescriptionImage);*/
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       /* holder.prescriptionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(prescriptionCart.getPrescriptionImage()), "image*//*");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (Exception e) {
                    Log.e("ImagePreview", "Error in showing image: " + e.toString());
                }
            }
        });

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                remove(prescriptionCart);

                DatabaseHelper db = new DatabaseHelper(getContext());
                db.deletePrescriptionCartItem(prescriptionCart.getRow_id());

                if (listener != null)
                    listener.onRemoveClick(position);

            }
        });

        holder.modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onModifyClick(position);
            }
        });

        Log.d("ImagePathUri", Uri.parse(prescriptionCart.getPrescriptionImage()).toString());

//        Log.d("DownloadTask", "Starting");
//        new DownloadImageTask(holder.prescriptionImage, holder.progressBar).execute(prescriptionCart.getPrescriptionImage());

//        Glide.with(context).load(prescriptionCart.getPrescriptionImage()).into(holder.prescriptionImage);
        Picasso.with(context)
                .load(prescriptionCart.getPrescriptionImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.prescriptionImage);
*/
        return convertView;
    }


    @Override
    public void remove(PrescriptionCart object) {
        super.remove(object);
//        Toast.makeText(context, "Item has been removed", Toast.LENGTH_SHORT).show();
    }


    static class ViewHolder {
        public ImageView prescriptionImage;
        public Button modifyButton;
        public Button removeButton;
        public ProgressBar progressBar;
    }

}