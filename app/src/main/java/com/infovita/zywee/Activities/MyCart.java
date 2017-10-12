package com.infovita.zywee.Activities;

/**
 * Created by Khizarkhan on 02-02-2017.
 */

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.Tracker;
import com.infovita.zywee.Adopters.ManualCartAdapter;
import com.infovita.zywee.Adopters.PrescriptionCartAdapter;
import com.infovita.zywee.R;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Supports.InternetStatus;
import com.infovita.zywee.Supports.ManualItem;
import com.infovita.zywee.Supports.PrescriptionCart;
import com.infovita.zywee.Supports.ZyweeData;
import com.infovita.zywee.Supports.constant;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Praveen on 23-12-2015.
 */
public class MyCart extends AppCompatActivity implements View.OnClickListener {

    private static final int GET_IMAGE_FROM_HISTORY = 0;
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_FILE = 3;
    private static final int KITKAT_GALLARY = 4;


    static ArrayList<Uri> mImageCaptureUri;

    List<String> priceArray;

    //    TAG with activity name
    private static final String TAG = "MyCart";

    final DatabaseHelper DBhelper = new DatabaseHelper(this);


    ArrayList<PrescriptionCart> prescriptionUri;

    //    Gets all the layout
    RelativeLayout prescriptionLayout, nonPrescriptionLayout, manualMedicineLayout;

    LinearLayout product_list, medicine_list, prescription_list;

    //      EditText for entering notes
    EditText notesEditText;

    //    Gets the enter_delivery_address_button
    Button enterDeliveryAddressButton, add_more;

    //    ListView for prescription and non_prescription products
    ListView prescriptionListView, nonPrescriptionListView;

    //    DatabaseHelper to fetch the database
    DatabaseHelper db;
    public String TableName = "PrescriptionCartTable";
    public String TableName1 = "ManualEntryTable";
    private int prescriptionPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);


//        Call the method to set the action bar
       // setActionBar();
        setup_toolbar();
//        Call the initialise() to get the all fields using id
        initialise();


        /*Initialising fb sdk*/
        FacebookSdk.sdkInitialize(this);
        fbEventLogger();
    }

    private void fbEventLogger() {
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ACHIEVED_LEVEL);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART);
    }


    private Tracker mTracker;


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*Initialise all the fields*/
    @SuppressLint("NewApi")
    private void initialise() {

//        Gets the prescription and nonPrescription layout
        prescriptionLayout = (RelativeLayout) findViewById(R.id.prescription_product_cart_layout);
        //nonPrescriptionLayout = (RelativeLayout) findViewById(R.id.non_prescription_product_cart_layout);
        manualMedicineLayout = (RelativeLayout) findViewById(R.id.manual_cart_layout);

        //product_list = (LinearLayout) findViewById(R.id.product_list);
        medicine_list = (LinearLayout) findViewById(R.id.medicine_list);
        prescription_list = (LinearLayout) findViewById(R.id.prescription_list);

//        Gets the enterNotesEditText
        notesEditText = (EditText) findViewById(R.id.notes_edittext);


//        Gets the Enter_Delivery_Button
        enterDeliveryAddressButton = (Button) findViewById(R.id.review);
        //Listener for enterDeliveryAddressButton
        enterDeliveryAddressButton.setOnClickListener(this);

       // add_more = (Button) findViewById(R.id.add_more);
        //Listener for enterDeliveryAddressButton
       // add_more.setOnClickListener(this);

//        Gets the prescription and non_prescription listView
        prescriptionListView = (ListView) findViewById(R.id.prescription_product__list_view);
       // nonPrescriptionListView = (ListView) findViewById(R.id.non_prescription_product__list_view);


        DatabaseHelper helper = new DatabaseHelper(this);
        long manualcount = helper.fetchManualCartCount();
       // long non_prescription_count = helper.fetchNonPrescriptionCartCount();
        long prescription_count = helper.fetchPrescriptionCartCount();
        long cartCount = manualcount +  prescription_count;


        ManualMedicineAsyncTask asyncTask = new ManualMedicineAsyncTask(); // First
        PrescriptionListAsyncTask asyncTask1 = new PrescriptionListAsyncTask();
       // NonPrescriptionListAsyncTask asyncTask2 = new NonPrescriptionListAsyncTask();
        if (isOnline()) {
            if (manualcount != 0) {
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                manualMedicineLayout.setVisibility(View.GONE);
            }
            if (prescription_count != 0) {
                asyncTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                prescriptionLayout.setVisibility(View.GONE);
            }
           /* if (non_prescription_count != 0) {
                asyncTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                nonPrescriptionLayout.setVisibility(View.GONE);
            }*/
        }


        if (cartCount == 0) {
            notesEditText.setVisibility(View.GONE);
            enterDeliveryAddressButton.setVisibility(View.GONE);
            setEmptyPage();

        }
    }//End


    /**
     * Method to add empty page to the layout in case of no data
     * Alsoadding some action to the button
     */
    private void setEmptyPage() {
        setContentView(R.layout.no_data_without_toolbar);
        TextView prompt_message = (TextView) findViewById(R.id.prompt_message);
        prompt_message.setText(getResources().getString(R.string.prompt_no_prescriptions));

        Button action_button = (Button) findViewById(R.id.action_button);
        action_button.setVisibility(View.VISIBLE);
        action_button.setText(getResources().getString(R.string.action_add_prescription));
        action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent open = new Intent(getApplicationContext(), Medicine.class);
                open.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(open);
            }
        });
    }


    /*Initialise and set the actionBar title*/
   /* public void setActionBar() {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(Html.fromHtml("<small>" + "Cart" + "</small>"));
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }*/
       /* ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<small>Cart</small>"));
    }//End
*/

    private void setup_toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + "Cart" + "</small>"));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public static final String ORDER = "OrderData";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.review:
                SharedPreferences sharedpreferences = getSharedPreferences(ORDER, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(constant.notes, notesEditText.getText().toString().trim());
                editor.apply();
                Intent open = new Intent(this, Order.class);
               /* if (price_text != null) {
                    open.putExtra("price", price_text.getText().toString().trim().replace("₹", ""));
                } else {
                    open.putExtra("price", "0");
                }*/
                startActivity(open);
                break;

           /* case R.id.add_more:
                Intent add = new Intent(MyCart.this, AddItemsFromStoreList.class);
                startActivity(add);
                MyCart.this.finish();
                break;*/
        }
    }


    public interface Listener {
        void onModifyClick(int position);

        void onRemoveClick(int position);
    }


    /**
     * AsyncTask to populate prescription_list_view with the prescription_products
     */
    public class PrescriptionListAsyncTask extends AsyncTask<Void, Void, Void> implements Listener {

        PrescriptionCartAdapter prescriptionCartAdapter;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MyCart.this);
            dialog.setMessage("Loading..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            //        ArrayList to hold the prescription products which will be passed to the adapter PrescriptionCartAdapter
            prescriptionUri = new ArrayList<>();
            //Gets the database
            db = new DatabaseHelper(getApplicationContext());
        }

        @Override
        protected Void doInBackground(Void... params) {
            prescriptionUri = db.getPrescriptionCartDetail();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            db.close(); //Close the database

            /*
            * If there are products then it will be added to the listview
            * or else the layout will be removed
            * */
            /*if (prescriptionUri.size() > 0) {
                prescriptionCartAdapter = new PrescriptionCartAdapter(getApplication(), R.layout.prescription_cart_layout, prescriptionUri, this);
                prescriptionListView.setAdapter(prescriptionCartAdapter);
            } else {
                prescriptionLayout.removeAllViews();
            }*/
            refreshPrescriptionList(prescriptionUri);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }


            //Set the listview height based on item count
            setListViewHeightBasedOnItems(prescriptionListView);
        }


        @Override
        public void onModifyClick(int position) {
//            new ModifyPrescription(prescriptionCartAdapter, position).execute();
        }

        @Override
        public void onRemoveClick(int position) {
            Log.d(TAG, "Removed item at : " + position);
//            sendAction("Cart~RemovePrescription");
            /*prescriptionUri = db.getPrescriptionCartDetail();
            prescriptionCartAdapter.notifyDataSetChanged();*/
            invalidateOptionsMenu();
//            new PrescriptionListAsyncTask().execute();

            checkCart();
        }
    }//END

    public void checkCart() {
        db = new DatabaseHelper(getApplicationContext());
        long cartCount = db.fetchManualCartCount();
        db = new DatabaseHelper(getApplicationContext());
        cartCount += db.fetchPrescriptionCartCount();
        Log.d(TAG, "Cart count : " + cartCount);
       /* if (cartCount < 1) {
            setEmptyPage();
        }*/
    }


    /**
     * AsyncTask to populate non_prescription_list_view with the non_prescription_products
     */



    private void refreshManualMedicineList(List<ManualItem> product) {
        for (int i = 0; i < product.size(); i++) {
            addManualMedicineLayout(i, product);
        }
    }

    private void refreshPrescriptionList(List<PrescriptionCart> product) {
        for (int i = 0; i < product.size(); i++) {
            addPrescriptionLayout(i, product);
        }
    }


    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + 30; //30 is just to use some extra length(not necessary)
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }//END


    public class ModifyPrescription extends AsyncTask<Void, Void, Boolean> {
        PrescriptionCartAdapter adapter;
        int position;

        public ModifyPrescription(PrescriptionCartAdapter adapter, int position) {
            this.adapter = adapter;
            this.position = position;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        List<Integer> positionArray = new ArrayList<>();

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            /*browsePrescription();
            for (int i = 0; i < prescriptionUri.size(); i++) {
                positionArray.add(i, prescriptionUri.get(i).getRow_id());
            }
            DatabaseHelper helper = new DatabaseHelper(MyCart.this);
            mImageCaptureUri = helper.getAllUri();
            Log.d(TAG, "All uri" + Uri.parse(mImageCaptureUri.toString()));
            browsePrescription();
            modify(positionArray.get(position), position);
            adapter.notifyDataSetChanged();*/
        }
    }


    /**
     * AsyncTask to populate manual_entry_list_view with the data
     */
    public class ManualMedicineAsyncTask extends AsyncTask<Void, Void, Void> implements Listener {

        //ArrayList to hold the manual products which will be passed to the adapter ManualCartAdapter
        ArrayList<ManualItem> cart = new ArrayList<>();

        ListView manual_list;
        ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MyCart.this);
            dialog.setMessage("Loading..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            manual_list = (ListView) findViewById(R.id.manual_list_view);
            db = new DatabaseHelper(getApplicationContext());
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "Fetching manual medicine cart products");
            cart = db.getManualEntryDetails();
            return null;
        }

        ManualCartAdapter adapter;

        @Override
        protected void onPostExecute(Void aVoid) {
            /*
            * If there are products then it will be added to the listview
            * or else the layout will be removed
            * */
            if (cart.size() > 0) {
                refreshManualMedicineList(cart);
                /*adapter = new ManualCartAdapter(getApplication(), R.layout.manual_list_layout, cart, this);
                manual_list.setAdapter(adapter);
                Log.d(TAG, "NonPrescription products are sent to the adapter");*/
            } else {
                manualMedicineLayout.removeAllViews();
                Log.d(TAG, "No non_prescription products");
            }

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            //Set the listview height based on item count
            setListViewHeightBasedOnItems(manual_list);
        }

        @Override
        public void onModifyClick(int position) {
            invalidateOptionsMenu();
        }

        @Override
        public void onRemoveClick(int position) {
            invalidateOptionsMenu();
//            new ManualMedicineAsyncTask().execute();
            adapter.remove(adapter.getItem(position));
            adapter.notifyDataSetChanged();
            checkCart();
        }
    }


    /**
     * Modify method of prescription uri
     *
     * @param id       Row_id in the database
     * @param position index value
     */
    private void modify(int id, int position) {
        Log.d(TAG, "position :" + position);
        Log.d(TAG, "new Uri :" + Uri.parse(mImageCaptureUri.get(position).toString()));
        DatabaseHelper helper = new DatabaseHelper(this);
        ZyweeData zd = new ZyweeData();
        zd.setPrescriptionImageUri(mImageCaptureUri.get(position));
        helper.updatePrescriptionUri(zd, id);
    }

    @Override
    public void onBackPressed() {

            new AlertDialog.Builder(this)
                    .setMessage("If you go back the data wil be cleared.")
                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clearDatabase();
                            MyCart.this.finish();

                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);

        DatabaseHelper helper = new DatabaseHelper(this);
        int mCartCount = (int) (helper.fetchManualCartCount() + helper.fetchPrescriptionCartCount());

        //you can add some logic (hide it if the count == 0)
       /* if (true) {
            ActionItemBadge.update(this, menu.findItem(R.id.action_cart), getResources().getDrawable(R.drawable.order_24), ActionItemBadge.BadgeStyles.YELLOW, mCartCount);
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.action_cart));
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                call_home();
                break;
        }
        return true;
    }

    private void call_home(){
        new AlertDialog.Builder(this)
                .setMessage("If you go back the data wil be cleared.")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearDatabase();
                        finish();
                        startActivity(new Intent(MyCart.this, LandingActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    protected void clearDatabase() {

        DBhelper.clearTableData(TableName);
        DBhelper.clearTableData(TableName1);
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

    public void snakbarMessage(String message) {

        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content).getRootView(), message, Snackbar.LENGTH_INDEFINITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

    /**
     * Method to add product layout
     *
     * @param i           index
     * @param productList list of data
     */
    private void addPrescriptionLayout(final int i, final List<PrescriptionCart> productList) {
        ImageView image;
        ImageButton remove;

        final PrescriptionCart product = productList.get(i);


        final View view = LayoutInflater.from(this).inflate(R.layout.prescription_cart_layout, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        prescription_list.addView(view);

        /*View line = view.findViewById(R.id.line);
        if (i == 0) {
            line.setVisibility(View.GONE);
        }*/
        image = (ImageView) view.findViewById(R.id.image);
        remove = (ImageButton) view.findViewById(R.id.remove);


        Glide.with(getApplicationContext())
                .load(product.getPrescriptionImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(image);

        /*Prompting the user to remove the item
        * If he wish to proceed item will be removed from the local database
        * and the list will be refreshed*/
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean res = alert("Proceed to remove product?");
                new AlertDialog.Builder(MyCart.this)
                        .setMessage("Proceed to remove product?")
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Remove product and update cart
                                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                                db.deletePrescriptionCartItem(product.getRow_id());
                                List<PrescriptionCart> list = productList;
                                list.remove(i);
                                prescription_list.removeAllViews();
//                                PrescriptionMedicine.mImageCaptureUri.remove(0);
                                if (list.size() == 0) {
                                    prescription_list.setVisibility(View.GONE);
                                } else {
                                    refreshPrescriptionList(list);
                                }
                                Toast.makeText(getApplicationContext(), "Prescription removed", Toast.LENGTH_SHORT).show();
                                invalidateOptionsMenu();
                                checkCart();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(product.getPrescriptionImage()), "image/*");
                    Log.d("images :",product.getPrescriptionImage()+ "image/*");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("ImagePreview", "Error in showing image: " + e.toString());
                }
            }
        });
    }//end


    /**
     * Method to add product layout
     *
     * @param i           index
     * @param productList list of data
     */
    private void addManualMedicineLayout(final int i, final List<ManualItem> productList) {
        TextView description, quantity, notes;
        Button modify;
        ImageButton remove;

        final ManualItem item = productList.get(i);


        final View view = LayoutInflater.from(this).inflate(R.layout.manual_list_layout, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        medicine_list.addView(view);

        View line = view.findViewById(R.id.line);
        if (i == 0) {
            line.setVisibility(View.GONE);
        }
        description = (TextView) view.findViewById(R.id.description_text_view);
        quantity = (TextView) view.findViewById(R.id.quantity_text_view);
        notes = (TextView) view.findViewById(R.id.notes_text_view);
        modify = (Button) view.findViewById(R.id.modify_button);
        remove = (ImageButton) view.findViewById(R.id.clear_product);


        description.setText(item.getDescrription());
        quantity.setText("Quantity : " + item.getQuantity());
        notes.setText("Notes : " + item.getNotes());

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


         /*Prompting the user to remove the item
        * If he wish to proceed item will be removed from the local database
        * and the list will be refreshed*/
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                boolean result = helper.removeManualItem(item.getTag());//Remove the item from the database

                new AlertDialog.Builder(MyCart.this)
                        .setMessage("Proceed to remove medicine?")
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Remove product and update cart
                                DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                                boolean result = helper.removeManualItem(item.getTag());//Remove the item from the database
                                List<ManualItem> list = productList;
                                list.remove(i);
                                medicine_list.removeAllViews();
                                if (list.size() == 0) {
                                    medicine_list.setVisibility(View.GONE);
                                } else {
                                    refreshManualMedicineList(list);
                                }
                                Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_SHORT).show();
                                invalidateOptionsMenu();
                                checkCart();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });


    }//end

}
