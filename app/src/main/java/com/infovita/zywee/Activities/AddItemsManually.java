package com.infovita.zywee.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infovita.zywee.R;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Supports.InternetStatus;
import com.infovita.zywee.Supports.ManualItem;
import com.infovita.zywee.Supports.ZyweeData;

import java.util.ArrayList;

/**
 * Created by Khizarkhan on 03-02-2017.
 */

public class AddItemsManually extends AppCompatActivity implements View.OnClickListener {

    ImageButton remove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_manually);

        setActionBar();
        initialise();

    }


   // ProgressBar progress;


    //Manual medicine layout containing 3 edit text
    LinearLayout manualMedicineLayout;

    //Layout which represent entire layout
    RelativeLayout manualEntryScreenLayout;

    //Buttons used in the layout
    Button addMore, review, enterAnotherItemButton;

    //Variable to track the medicine count
    private int medicineCount = 0;

    EditText[] descriptionEditText, quantityEditText, notesEditText;
    ImageButton[] removeButton;
    String[] tag;
    private final String TAG = "ManualEntry";


    DatabaseHelper helper;



    private void initialise() {

        //Gets the manual medicine layout having edit texts
        manualMedicineLayout = (LinearLayout) findViewById(R.id.manual_medicine_layout);

        //Gets the entire screen
        manualEntryScreenLayout = (RelativeLayout) findViewById(R.id.manual_entry_screen);

        //Gets the enter another item button
        enterAnotherItemButton = (Button) findViewById(R.id.enter_another_item_button);
        //Gets the addItemsButton
       // addMore = (Button) findViewById(R.id.add_more);
        review = (Button) findViewById(R.id.review);
        //progress = (ProgressBar) findViewById(R.id.progress_bar);


        descriptionEditText = new EditText[5];
        quantityEditText = new EditText[5];
        notesEditText = new EditText[5];
        removeButton = new ImageButton[5];

        tag = new String[5];


        for (int i = 0; i < 5; i++) {
            removeButton[i] = new ImageButton(this);
        }
        for (int i = 0; i < 5; i++) {
            descriptionEditText[i] = new EditText(this);
        }
        for (int i = 0; i < 5; i++) {
            quantityEditText[i] = new EditText(this);
        }
        for (int i = 0; i < 5; i++) {
            notesEditText[i] = new EditText(this);
        }


        //Listener for buttons
        enterAnotherItemButton.setOnClickListener(this);
//        addMore.setOnClickListener(this);
        review.setOnClickListener(this);

        //Initially add the one layout to enter prescription
        addManualMedicineLayout(medicineCount);


    }


    //    Initialise and sets the action bar
    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + "Manual Medicine" + "</small>"));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

       /* ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<small>Non Prescriptive Items</small>"));*/
    }

    @Override
    public void onBackPressed() {
        AddItemsManually.this.finish();
       /* new AlertDialog.Builder(AddItemsManually.this)
                .setMessage("If you go back, the data wil be cleared.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // clearDatabase();*/
                        AddItemsManually.this.finish();
               /*     }
                })
                .setNegativeButton("No", null)
                .show();*/

    }


    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);

        DatabaseHelper helper = new DatabaseHelper(this);
        int mCartCount = (int) (helper.fetchManualCartCount() + helper.fetchPrescriptionCartCount());
       /* //you can add some logic (hide it if the count == 0)
        if (true) {
            ActionItemBadge.update(this, menu.findItem(R.id.action_cart), getResources().getDrawable(R.drawable.order_24), ActionItemBadge.BadgeStyles.YELLOW, mCartCount);
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.action_cart));
        }

*/
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           /* case R.id.action_cart:
                Intent cart = new Intent(getApplication(), MyCart.class);
                startActivity(cart);
                break;*/
            case R.id.action_home:
                call_home();
                break;

            /*case R.id.about_us:
                Uri uri = Uri.parse("https://www.zywee.com/aboutUs");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    private void call_home(){

        startActivity(new Intent(AddItemsManually.this, LandingActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }


    @Override
    public void onClick(View v) {
        helper = new DatabaseHelper(this);
        int cartCount = (int) helper.fetchManualCartCount();
        switch (v.getId()) {
            case R.id.enter_another_item_button:
                if ((medicineCount + cartCount) < 4) {
                    addManualMedicineLayout(medicineCount);
                }
                else if (medicineCount == 4) {
                    hideSoftKeyboard();
                    snakbarMessage(findViewById(android.R.id.content).getRootView(), "Sorry! Only 4 medicines can be entered!");
                } else {
                    hideSoftKeyboard();
                    snakbarMessage(findViewById(android.R.id.content).getRootView(), "Sorry! Only 4 medicines can be entered. There are " + cartCount + " medicines in the cart");
                }
                break;

           /* case R.id.add_more:
                addItem(v.getId());
                break;*/
            case R.id.review:
                addItem(v.getId());
                break;
        }
    }

    private void addItem(final int id) {
       // progress.setVisibility(View.VISIBLE);
        final ArrayList<ManualItem> items = fetchData(10); //Any number more than 4 since it remove the data w.r.t index passed
        final DatabaseHelper helper = new DatabaseHelper(this);
        boolean result = false;
        int cart = (int) helper.fetchManualCartCount();
        boolean emptyCheck = false;
        int count = 0;
        if (cart < 4) {
            if ((cart + items.size()) <= 4) {
                for (int i = 0; i < items.size(); i++) {
                    if (!items.get(i).getDescrription().equals("") && !items.get(i).getQuantity().equals("")) {
                        /*zd = new ZyweeData(items.get(i).getTag(), items.get(i).getDescrription(), items.get(i).getQuantity(), items.get(i).getNotes());
                        result = helper.addManualEntryDetail(zd);*/
                        Log.d(TAG, "Manual entry" + items.get(i).getTag() + " " + items.get(i).getDescrription() + " " + items.get(i).getQuantity() + " " + items.get(i).getNotes());
                    } else {
                        hideSoftKeyboard();
                        emptyCheck = true;
                        count++;
                        //sharedData.snakbarMessage(findViewById(android.R.id.content).getRootView(), "Medicine without description and quantity are not added");
                    }
                }
            } else {
                hideSoftKeyboard();
                snakbarMessage(findViewById(android.R.id.content).getRootView(), "You cannot more than 4 medicines! Already there are " + cart + " medicines in the cart");
            }
        } else {
            hideSoftKeyboard();
            snakbarMessage(findViewById(android.R.id.content).getRootView(), "Already there are 4 medicines");
        }
        if (count != items.size()) {
            if (emptyCheck) {
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure to Continue?")
                        .setMessage("Medicine without description and quantity are not added")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            boolean result = false;

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < items.size(); i++) {
                                    if (!items.get(i).getDescrription().equals("") && !items.get(i).getQuantity().equals("")) {
                                        ZyweeData zd = new ZyweeData(items.get(i).getTag(), items.get(i).getDescrription(), items.get(i).getQuantity(), items.get(i).getNotes());
                                        result = helper.addManualEntryDetail(zd);
                                    }
                                }

                                /*If button clicked is add more it will redirect to product page
                                * else if it is review button it will redirect to cart page*/
                               /* if (result && id == R.id.add_more) {
                                    Intent start = new Intent(AddItemsManually.this, AddItemsFromStoreList.class);
                                    startActivity(start);
                                    AddItemsManually.this.finish();
                                } else*/
                                if(result && id == R.id.review){
                                    Intent start = new Intent(AddItemsManually.this, MyCart.class);
                                    startActivity(start);
                                    AddItemsManually.this.finish();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            } else

            {
                for (int i = 0; i < items.size(); i++) {
                    if (!items.get(i).getDescrription().equals("") && !items.get(i).getQuantity().equals("")) {
                        ZyweeData zd = new ZyweeData(items.get(i).getTag(), items.get(i).getDescrription(), items.get(i).getQuantity(), items.get(i).getNotes());
                        result = helper.addManualEntryDetail(zd);
                    }
                }
               /* if (result && id == R.id.add_more) {
                    Intent start = new Intent(AddItemsManually.this, AddItemsFromStoreList.class);
                    startActivity(start);
                    AddItemsManually.this.finish();
                } else*/ if(result && id == R.id.review){
                Intent start = new Intent(AddItemsManually.this, MyCart.class);
                startActivity(start);
                AddItemsManually.this.finish();
            }

            }
        } else {
            snakbarMessage(findViewById(android.R.id.content).getRootView(), "Please add both Description and specify Quantity");
        }

        //progress.setVisibility(View.INVISIBLE);
    }



    //Method to add the extra layout to add medicines

    private void addManualMedicineLayout(int i) {
        tag[i] = String.valueOf(System.currentTimeMillis());
        medicineCount++;
        if (i != 0) {
            drawHorizontalLine();
        }

        View view = LayoutInflater.from(this).inflate(R.layout.manual_medicine_layout, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView medicine = (TextView) view.findViewById(R.id.medicineCount);
        medicine.setText("Medicine" + String.valueOf(medicineCount));

        descriptionEditText[i] = (EditText) view.findViewById(R.id.add_description);
        quantityEditText[i] = (EditText) view.findViewById(R.id.specify_quantity);
        notesEditText[i] = (EditText) view.findViewById(R.id.addNotesManual);
        removeButton[i] = (ImageButton) view.findViewById(R.id.remove_medicine_button);

        final int state = i;
        removeButton[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AddItemsManually.this)
                        .setMessage("Are you sure to remove medicine?")
                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ArrayList<ManualItem> items = fetchData(state);//Get the all data
                                refreshLayout(items);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });


        manualMedicineLayout.addView(view);
        descriptionEditText[i].setFocusable(true);

        if (medicineCount == 1) {
            removeButton[0].setVisibility(View.GONE);
        } else {
            removeButton[0].setVisibility(View.VISIBLE);
        }
    }//end

    //Method to draw horizontal line
    private void drawHorizontalLine() {
        View view2 = new View(this);
        view2.setBackgroundColor(0xFFC2BEBF);
        manualMedicineLayout.addView(view2, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
    }


    //Fetch all the editText field data to the corresponding string
    private ArrayList<ManualItem> fetchData(int position) {
        //progress.setVisibility(View.VISIBLE);
        ArrayList<ManualItem> items = new ArrayList<>();
        for (int i = 0; i < medicineCount; i++) {
            if (i != position) {
                ManualItem item = new ManualItem();
                item.setTag(tag[i]);
                item.setDescrription(descriptionEditText[i].getText().toString().trim());
                item.setQuantity(quantityEditText[i].getText().toString().trim());
                item.setNotes(notesEditText[i].getText().toString().trim());

                items.add(item);
                Log.d(TAG, "Manual medicines:" + item.getDescrription() + " ; " + item.getQuantity() + " ; " + item.getNotes());
            }
        }
        return items;
    }


    private void refreshLayout(ArrayList<ManualItem> items) {
        medicineCount = 0;
        manualMedicineLayout.removeAllViews();
        for (int i = 0; i < items.size(); i++) {
            addManualMedicineLayout(i);
            tag[i] = items.get(i).getTag();
            descriptionEditText[i].setText(items.get(i).getDescrription());
            quantityEditText[i].setText(items.get(i).getQuantity());
            notesEditText[i].setText(items.get(i).getNotes());
        }
       // progress.setVisibility(View.INVISIBLE);
    }


    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static boolean isOnline(Context context, View view) {
        boolean status = InternetStatus.getInstance(context).isOnline();
        if (status)
            return status;
        else {
            snakbarMessage(view, "Please connect to Internet");
            return status;
        }
    }

    public static void snakbarMessage(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }
}
