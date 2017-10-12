package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Supports.ApplicationPreferences;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Supports.InternetStatus;
import com.infovita.zywee.Supports.ZyweeData;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Khizarkhan on 02-02-2017.
 */
public class PrescriptionReview extends AppCompatActivity implements View.OnClickListener {


    ImageView imageView;
    ImageButton remove;
    Button addMore, review;

    static String TAG = "PrescriptionReview";
    Uri uri;
    DatabaseHelper helper;
    ProgressDialog dialog;

    //SharedData sd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_review);

       // setActionBar();
        setup_toolbar();
        Intent i = getIntent();
        uri = Uri.parse(i.getExtras().getString("image"));

       // Log.d(TAG, uri + " ");

        initialise();
       // sd = new SharedData();

        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "image/*");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    //Log.e("ImagePreview", "Error in showing image: " + e.toString());
                }
            }
        });
    }

   /* //Manual medicine layout containing 3 edit text
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
*/
    //ProgressBar progress;



    /*Set the action bar*/
   /* public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>Prescription Medicine</small>"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     new AlertDialog.Builder(PrescriptionReview.this)
                                                             .setMessage("Your prescription is not uploaded, if u go back it will be removed.")
                                                             .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                                                 public void onClick(DialogInterface dialog, int id) {
                                                                     Medicine.mImageCaptureUri.remove(0);
                                                                     PrescriptionReview.this.finish();
                                                                 }
                                                             })
                                                             .setNegativeButton("Cancel", null)
                                                             .show();
                                                 }
                                             }

        );
    }*/

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

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("If you go back the data wil be cleared.")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Medicine.mImageCaptureUri.remove(0);
                        PrescriptionReview.this.finish();

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*Setting screen name to track in localytics*/
       // Localytics.tagScreen("PrescriptionReview");
    }

    private void removePrescription() {
        new AlertDialog.Builder(PrescriptionReview.this)
                .setMessage("Are you sure to remove prescription?")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Medicine.mImageCaptureUri.remove(0);
                        PrescriptionReview.this.finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
//        PrescriptionReview.this.finish();
    }

    private void initialise() {
        imageView = (ImageView) findViewById(R.id.image);
        remove = (ImageButton) findViewById(R.id.remove);
        addMore = (Button) findViewById(R.id.add_more);
        review = (Button) findViewById(R.id.review);

        addMore.setOnClickListener(this);
        review.setOnClickListener(this);
        remove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_more:
                saveToCart(v.getId());
                break;
            case R.id.review:
                saveToCart(v.getId());
                break;
            case R.id.remove:
                removePrescription();
                break;
        }
    }
    private void saveToCart(int id) {
        DatabaseHelper helper = new DatabaseHelper(this);
        int cartCount = (int) helper.fetchPrescriptionCartCount();
        //Log.d(TAG, cartCount + "");
        if (cartCount == 0) {
            try {
                moveToBuzzFolder(uri);
                if (PrescriptionReview.isOnline(getApplicationContext(), findViewById(android.R.id.content).getRootView())) {
                    UploadFileToServer upload = new UploadFileToServer(id);
                    upload.execute();
                }
            } catch (Exception e) {
                //Log.e(TAG, "Error moving Image to the Buzz folder" + e.toString());
            }
        } else {
            snakbarMessage(findViewById(android.R.id.content).getRootView(), "Already there are " + cartCount + " prescriptions in the cart. Please remove the prescription in this page");
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


    // move the selected photo from its current folder to application Buzz
    // folder
    public void moveToBuzzFolder(Uri mImageUri) throws Exception {

        Bitmap storeIntoDestFolder = null;

        try {
            storeIntoDestFolder = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
        } catch (OutOfMemoryError e) {
            //Log.v(TAG, "Out of memory: " + e.toString());
            //Log.v(TAG, "Running garbage collection");
            System.gc();
            try {
                storeIntoDestFolder = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
            } catch (OutOfMemoryError exc) {
            }
        } catch (Exception e) {
            //Log.v(TAG, "Generinc exception caught in moveToBuzzFolder()");
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        // Log.i(TAG, "Before compress:
        // "+storeIntoDestFolder.getByteCount()+"");
        if (storeIntoDestFolder != null)
            storeIntoDestFolder.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

        File f = new File(Environment.getExternalStorageDirectory() + "/Buzz/" + "Buzz_"
                + String.valueOf(System.currentTimeMillis()) + ".jpg");
        //Log.v("BUZZ", "Path for the Buzz directory is: " + f.getAbsolutePath());

        uri = Uri.fromFile(f);

        ApplicationPreferences prefs = new ApplicationPreferences();
        prefs.storePrescriptionName(getApplicationContext(), f.getName());


        f.createNewFile();
        // write the bytes in file
        FileOutputStream fo = new FileOutputStream(f);
        try {
            fo.write(bytes.toByteArray());
        } catch (Exception e) {
            //Log.e(TAG, "Error writing to the image file");
        }
        // close FileOutput
        fo.close();
    }


    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        ProgressDialog dialog;
        Serverdatas webServiceUserHandler;
        String url;
        int id;
        ZyweeData zd;
        DatabaseHelper helper;


        public UploadFileToServer(int id) {
            this.id = id;
        }


        @Override
        protected void onPreExecute() {
            zd = new ZyweeData();
            helper = new DatabaseHelper(getApplicationContext());
            dialog = new ProgressDialog(PrescriptionReview.this);
            dialog.setMessage("Uploading Image..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            webServiceUserHandler = new Serverdatas();
            url = webServiceUserHandler.endPoint1 + "upload_file";
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = uploadFile();
            return result;

        }


        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            // http://192.168.0.114/AndroidFileUpload/fileUpload.php

            HttpPost httppost = new HttpPost(url);
            try {

                MultipartEntity en = new MultipartEntity();
                File sourceFile = new File(uri.getPath());

                SharedPreferences sharedpreferences = getSharedPreferences("image_uri", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("image_uri", uri.getPath());
                editor.commit();


                en.addPart("image", new FileBody(sourceFile));

                //Log.d(TAG, "File : " + new FileBody(sourceFile));

                long totalSize = en.getContentLength();
                //Log.v(TAG, "total entity size is: " + totalSize);
                httppost.setEntity(en);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                    //Log.v(TAG, "Status Code: " + statusCode);
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }
            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
                return "error";
            } catch (Exception e) {
                snakbarMessage(findViewById(android.R.id.content).getRootView(), "Please connect to internet");
                return "error";
            }
            return responseString;
        }


        @Override
        protected void onPostExecute(String result) {

            /*Log.e(TAG, "Response from server after uploading image: " +
                    result);*/

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            if (!result.equals("error")) {
                zd.setPrescriptionImageUri(uri);
                helper.addZyweePrescriptionCartDetail(zd);
                //Log.v(TAG, "Added to cart : " + uri.toString());

                if (id == R.id.add_more) {
                    Intent open = new Intent(getApplicationContext(), AddItemsManually.class);
                    startActivity(open);
                } else
                if (id == R.id.review) {
                    Intent open = new Intent(getApplicationContext(), MyCart.class);
                    startActivity(open);
                }
                PrescriptionReview.this.finish();
            } else {
                snakbarMessage(findViewById(android.R.id.content).getRootView(), "Internet might be slow to upload prescription");
            }

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

  /*  @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart_main, menu);

        DatabaseHelper helper = new DatabaseHelper(this);
        int mCartCount = (int) (helper.fetchManualCartCount() + helper.fetchPrescriptionCartCount());

        //you can add some logic (hide it if the count == 0)
        if (true) {
            ActionItemBadge.update(this, menu.findItem(R.id.action_cart), getResources().getDrawable(R.drawable.order_24), ActionItemBadge.BadgeStyles.YELLOW, mCartCount);
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.action_cart));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent cart = new Intent(getApplication(), MyCart.class);
                startActivity(cart);
                break;

            case R.id.action_home:
                call_home();
                break;
        }
        return true;
    }

    private void call_home(){
       /* new AlertDialog.Builder(this)
                .setMessage("If you go back, the data wil be cleared.")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearDatabase();
                        finish();*/
                        startActivity(new Intent(PrescriptionReview.this, LandingActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
             /*   })
               // .setNegativeButton("No", null)
                .show();
    }*/



}
