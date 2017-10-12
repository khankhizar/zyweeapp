package com.infovita.zywee.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Supports.OrderData;

import java.io.File;

/**
 * Created by Khizarkhan on 10-02-2017.
 */
public class OrderDetailView extends AppCompatActivity {

    TextView customer_name, customer_number, address, order_id, notes, price, status, description, quantity, manual_notes;

    Serverdatas webServiceUserHandler;

    OrderData data;
    private static final String TAG = "OrderDetailView";

    private String server_url;

    String parent;


    boolean flag = true;



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_view);

        /*Initialising fb sdk*/
        FacebookSdk.sdkInitialize(this);
        fbEventLogger();
        setActionBar();

        Intent i = getIntent();
        i.getStringExtra("customer_name");
        String s = i.getExtras().getString("product");
        String str = i.getExtras().getString("product_order");
        parent = i.getExtras().getString("parent");

        if (parent.equals("new_order")) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            if (str != null) {
               // Log.d("data", str);
              //  new SetProductOrder(str).execute();
                TextView order = (TextView) findViewById(R.id.order);
//                order.setText("Order placed Successfully!");
            }
        }

        if (s != null) {
           // Log.d("data" ,s);
            //new SetProductData(s).execute();
        }
        webServiceUserHandler = new Serverdatas();
        server_url = webServiceUserHandler.endPointPrescription + "prescriptions/";
        customer_name = (TextView) findViewById(R.id.customer_name);
        customer_number = (TextView) findViewById(R.id.customer_phone);
        notes = (TextView) findViewById(R.id.notes);
       // price = (TextView) findViewById(R.id.price_textview);
        order_id = (TextView) findViewById(R.id.order_id);
        address = (TextView) findViewById(R.id.address_textview);


        if (parent.equals("new_order")) {
//            getActionBar().setDisplayHomeAsUpEnabled(false);
            Button ok = (Button) findViewById(R.id.ok);
            ok.setVisibility(View.VISIBLE);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent open = new Intent(getApplicationContext(), LandingActivity.class);
                    open.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(open);
                }
            });
        }


        customer_name.setText(i.getExtras().getString("customer_name"));
        customer_number.setText(i.getExtras().getString("customer_number"));
        order_id.setText(i.getExtras().getString("order_id"));
        String note = i.getExtras().getString("notes");
        if (note.isEmpty()) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.notes_layout);
            layout.setVisibility(View.GONE);
        } else {
            notes.setText(note);
        }

        final String image = i.getExtras().getString("image");
        ImageView prescription = (ImageView) findViewById(R.id.prescription);
        TextView text = (TextView) findViewById(R.id.prescription_text);
        View view = findViewById(R.id.prescription_line);
        if (image != null) {
            flag = false;
            if (image.isEmpty()) {
                view.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                prescription.setVisibility(View.GONE);
            } else {
//                Glide.with(getApplicationContext()).load(server_url + image).into(prescription);
                Glide.with(getApplicationContext())
                        .load(server_url + image)
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        .into(prescription);
            }
        } else {
            view.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
            prescription.setVisibility(View.GONE);
        }


        /*Shows the preview image in the separate activity*/
        prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("IMAGE name", image);
                File f = new File(Environment
                        .getExternalStorageDirectory().getAbsolutePath() + "/Buzz/" + image);
                /*Intent open = new Intent(getApplicationContext(),ShowImageActivity.class);
                open.putExtra("prescriptionImage",image);
                startActivity(open);*/
                try {
                    Uri uri = Uri.fromFile(f);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "image/*");
                    startActivity(intent);
                } catch (Exception e) {
                    //Log.e("ImagePreview", "Error in showing image: " + e.toString());
                }
            }
        });


        /*
        * Populates the price if the Vendor updated the price */
       /* String priceStr = i.getExtras().getString("price");
        if (priceStr.equals("0")) {
            price.setText("₹" + "N/A");
        } else {
            price.setText("₹" + priceStr);
        }*/


        String m1 = i.getExtras().getString("m1");
        String m2 = i.getExtras().getString("m2");
        String m3 = i.getExtras().getString("m3");
        String m4 = i.getExtras().getString("m4");
        if (m1.contains("-") || m2.contains("-") || m3.contains("-") || m4.contains("-")) {
            flag = false;
            TextView v = addTextView();
            v.setText("Manual medicine :");
            if (m1.contains("-")) {
                addManualMedicineLayout(m1);
            }
            if (m2.contains("-")) {
                addManualMedicineLayout(m2);
            }
            if (m3.contains("-")) {
                addManualMedicineLayout(m3);
            }
            if (m4.contains("-")) {
                addManualMedicineLayout(m4);
            }
            addHorizontalLine();
        }

        address.setText(i.getExtras().getString("address"));

        String order_status = i.getExtras().getString("order_status");
        status = (TextView) findViewById(R.id.status_textview);
        setStatus(order_status);
//        customer_name.setText(i.getExtras().getString("customer_name"));*/

    }

    private void fbEventLogger() {
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ACHIEVED_LEVEL);
    }

    private void setStatus(String order_status) {
        switch (order_status) {
            case "0":
                status.setText("Processing");
                break;
            case "1":
                status.setText("Accepted");
                break;
            case "2":
                status.setText("Rejected");
                break;
            case "3":
                status.setText("Shipped");
                break;
            case "4":
                status.setText("Delivered");
                break;
        }
    }


    //Method to add the extra layout to add medicines
    private void addManualMedicineLayout(String s) {

        View view = LayoutInflater.from(this).inflate(R.layout.manual_medicine_detail, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView description, quantity, notes;
        description = (TextView) view.findViewById(R.id.description_textview);
        quantity = (TextView) view.findViewById(R.id.quantity_textview);
        notes = (TextView) view.findViewById(R.id.notes_textview);


        LinearLayout layout = (LinearLayout) findViewById(R.id.medicine_layout);
        layout.addView(view);

        LinearLayout manual_notes = (LinearLayout) view.findViewById(R.id.manual_notes);
        String[] string = s.split("-");
        for (int i = 0; i < string.length; i++)
            switch (i) {
                case 0:
                    if (string[0] != null)
                        description.setText(string[0]);
                    break;
                case 1:
                    if (string[1] != null)
                        quantity.setText("Qty : " + string[1]);
                    break;
                case 2:
                    if (string[2] != null) {
                        if (!TextUtils.isEmpty(string[2].trim())) {
                            manual_notes.setVisibility(View.VISIBLE);
                            notes.setText(string[2]);
                        } else manual_notes.setVisibility(View.GONE);
                    }
                    break;
            }

    }//end


    //Add TextView
    private TextView addTextView() {
        TextView v = new TextView(this);
        v.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        v.setPadding(5, 5, 5, 5);
        v.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        v.setTypeface(null, Typeface.BOLD);
        v.setTextColor(getResources().getColor(R.color.b2));
        LinearLayout layout = (LinearLayout) findViewById(R.id.medicine_layout);
        layout.addView(v);
        return v;
    }


    //Add horizontal line
    private void addHorizontalLine() {
        View v = new View(this);
        v.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                1
        ));
        v.setPadding(0, 10, 0, 10);
        v.setBackgroundColor(getResources().getColor(R.color.g2));
        LinearLayout layout = (LinearLayout) findViewById(R.id.medicine_layout);
        layout.addView(v);
    }

 /*   //Method to add the extra layout to add medicines
    private void addProductLayout(String name, String brand, String size, String quantity, String price, String image) {

        WebServiceUserHandler webServiceUserHandler = new WebServiceUserHandler();
        String image_url = webServiceUserHandler.endPointPrescription + "orthopedic_products/" + image + ".jpg";

        View view = LayoutInflater.from(this).inflate(R.layout.product_order_detail, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView product_name, product_brand, product_quantity, product_size, product_price;
        ImageView product_image = (ImageView) view.findViewById(R.id.image);
        product_name = (TextView) view.findViewById(R.id.product_title);
        product_quantity = (TextView) view.findViewById(R.id.quantity_text_view);
        product_brand = (TextView) view.findViewById(R.id.product_brand);
        product_size = (TextView) view.findViewById(R.id.product_size);
        product_price = (TextView) view.findViewById(R.id.product_cost);


        LinearLayout layout = (LinearLayout) findViewById(R.id.medicine_layout);
        layout.addView(view);

        Picasso.with(getApplicationContext())
                .load(image_url)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(product_image);

        product_name.setText(name);
        if (brand != null) {
            product_brand.setText("Brand : " + brand);
        } else {
            product_brand.setVisibility(View.GONE);
        }
        product_size.setText("Size : " + size);
        product_quantity.setText("Quantity : " + quantity);
        product_price.setText("Price : ₹" + price);

    }//end
*/

    public class Product {
        String name, brand, size, quantity, price, image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

       /* public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }*/
    }

    @Override
    public void onBackPressed() {
        if (parent.equals("new_order")) {
            finish();
            Intent open = new Intent(getApplicationContext(), LandingActivity.class);
            open.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(open);
        } else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      /*  switch (item.getItemId()) {
            *//*case R.id.action_cart:
                Intent cart = new Intent(getApplication(), MyCart.class);
                startActivity(cart);
                break;*//*
            case R.id.home:
                startActivity(new Intent(OrderDetailView.this, LandingActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;

           *//* case R.id.about_us:
                Uri uri = Uri.parse("https://www.zywee.com/aboutUs");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;*//*
        }*/
        return super.onOptionsItemSelected(item);
    }

    public void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        // actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<small> Order Detail </small>"));
        // actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);

    }



}

