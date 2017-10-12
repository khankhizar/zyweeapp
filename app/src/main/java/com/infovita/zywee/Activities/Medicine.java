package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infovita.zywee.Adopters.PreviewGrid;
import com.infovita.zywee.R;
import com.infovita.zywee.Sharedvalues.Serverdatas;
import com.infovita.zywee.Supports.DatabaseHelper;
import com.infovita.zywee.Supports.ZyweeData;

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
import java.util.ArrayList;

/**
 * Created by Khizarkhan on 01-02-2017.
 */
public class Medicine extends AppCompatActivity {

    private static final int GET_IMAGE_FROM_HISTORY = 0;
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_FILE = 3;
    private static final int KITKAT_GALLARY = 4;

    private final String TAG = getClass().getName();

    GridView zPreviewGrid;
    public static ArrayList<Uri> mImageCaptureUri;
    ArrayList<Bitmap> bitmapArray;
    private Uri imageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_medicine);
        setupToolbar();
        initialise();
        zReviewOrderButton.setVisibility(View.GONE);

    }

    private void setupToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + "Prescription Medicine" + "</small>"));
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
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(Html.fromHtml("<small>Prescription Medicine</small>"));
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);*/

    }


    private void initialise() {
        PreviewGrid.Imageid = new ArrayList<>();
        mImageCaptureUri = new ArrayList<>();
        zPreviewGrid = (GridView) findViewById(R.id.image_preview_grid);

        //Fetch the button icon and title from xml
        String[] title = getResources().getStringArray(R.array.prescription_text_array);
        TypedArray drawableIconArray = getResources().obtainTypedArray(R.array.prescription_drawable_array);

        //Add button view to the layout
        for (int i = 0; i < title.length; i++) {
            addCustomButtonView(i, drawableIconArray.getResourceId(i, -1), title[i]);
        }

       // sd = new SharedData();
        zReviewOrderButton = (Button) findViewById(R.id.review_order_button);

        zReviewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mImageCaptureUri.size() != 0) {
//                    sendAction("PrescriptionReview");
                    Intent open = new Intent(getApplication(), PrescriptionReview.class);
                    open.putExtra("image", String.valueOf(mImageCaptureUri.get(0)));
                    startActivity(open);
                } else {
                    snakbarMessage("Please add prescription");
                }
//                if (zPreviewGrid!=null) {
               /* try {
                    int i = PreviewGrid.Imageid.size();
                    Log.d(TAG, "Gridsize : " + i);
                    if (i != 0) {
                        if (sd.isOnline(getApplicationContext(), findViewById(android.R.id.content).getRootView())) {
                            saveToCart(i);
                        }
                    } else {
                        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                        cartCount = (int) helper.fetchPrescriptionCartCount();
                        if (cartCount != 0) {
                            Intent open = new Intent(getApplication(), MyCart.class);
                            startActivity(open);
                        } else
                            snakbarMessage("Please add prescription");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    snakbarMessage("Please add prescription");
                }*/
/*                } else {
                    snakbarMessage("Please add prescription");
                }*/
            }
        });
    }

    private void reviewOrder(int k) {
        try {
            moveToBuzzFolder(mImageCaptureUri.get(0));
        } catch (Exception e) {
            Log.e(TAG, "Error moving Image to the Buzz folder" + e.toString());
        }

        UploadFileToServer upload = new UploadFileToServer(k);
        upload.execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(mImageCaptureUri.size()==0){
            zReviewOrderButton.setVisibility(View.GONE);
        } else {
            zReviewOrderButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Medicine.this.finish();
       /* AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent(AppEventsConstants.EVENT_NAME_SESSION_INTERRUPTIONS);
        logger.logEvent(AppEventsConstants.EVENT_NAME_TIME_BETWEEN_SESSIONS);*/
    }

    private void addCustomButtonView(final int i, int icon, String text) {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_button_layout, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView title = (TextView) view.findViewById(R.id.text);
        title.setText(text);

        ImageView iconImage = (ImageView) view.findViewById(R.id.icon);
        iconImage.setImageResource(icon);

        LinearLayout prescription_layout = (LinearLayout) findViewById(R.id.prescription_layout);
        prescription_layout.addView(view);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (sd.isOnline(getApplicationContext(),
//                        findViewById(android.R.id.content).getRootView())) {
                onClickAction(i);
//                }
            }
        });
    }//END

    private void onClickAction(int i) {
        if (mImageCaptureUri.size() == 0) {
            switch (i) {
                //Open the camera
                case 0:
                    Log.d(TAG, "Opened : Camera");
                    //sendAction("Camera");
                    getImageFromCamera();
                    break;
                //Browse the gallery
                case 1:
                   // sendAction("BrowsePrescription");
                    browsePrescription();
                    break;

               /* case 2:
                    Intent start = new Intent(Medicine.this, AddItemsManually.class);
                    startActivity(start);
                    break;*/
            }
        } else {
            snakbarMessage("You can add only 1 prescription!");
        }
    }


    /**
     * Show Dilog to choose from 2 options
     * Gallery
     * History
     */
    private void browsePrescription() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new CharSequence[]{"Browse Local Files", "Pick from History"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // GET IMAGE FROM THE GALLERY
                                getPrescriptionImageFromGallery();
                                break;

                            case 1:
                                getPrescriptionImageFromHistory();
                                break;

                            default:
                                break;
                        }
                    }
                });

        builder.show();
    }


    /**
     * for selecting the prescription image from the gallery
     */
    public void getPrescriptionImageFromGallery() {
        if (mImageCaptureUri.size() <= 1) {
            Intent intent;
            try {
                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PICK_FROM_FILE);
                } else {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, KITKAT_GALLARY);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * *for selecting the prescription image from previous prescriptions
     */
    public void getPrescriptionImageFromHistory() {

        Intent intent = new Intent(this, MyPrescriptions.class);
        intent.putExtra("parent", "PrescriptionMedicine");
        startActivityForResult(intent, GET_IMAGE_FROM_HISTORY);
//        startActivity(intent);
    }


    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            File mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory("Buzz"), TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                Log.d(TAG, "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }


    public Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        // Read EXIF Data
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        // Return result
        return rotatedBitmap;
    }

    private void getImageFromCamera() {
        int i = mImageCaptureUri.size();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        long time = System.currentTimeMillis();

        imageName = getPhotoFileUri(time + ".jpg");
        Log.d(TAG, mImageCaptureUri.toString());

        try {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageName);
            intent.putExtra("result-data", true);
            startActivityForResult(intent, CAMERA_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int size = mImageCaptureUri.size();
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CAMERA_REQUEST:
                Log.d(TAG, " Image data : " + imageName);
//                Log.d(TAG, " Image data : " + data.getData());
                if (imageName != null) {
                    mImageCaptureUri.add(size, imageName);
                    setImageViews();
                } else {
                    snakbarMessage(findViewById(android.R.id.content).getRootView(), "Something went wrong, image cannot be fetched");
                }
                break;

            case KITKAT_GALLARY:
                mImageCaptureUri.add(size, data.getData());
                Log.v("Buzz", "received Image path: " + mImageCaptureUri);
//                globalImageUriStr = mImageCaptureUri.toString();
                setImageViews();
                break;

            case PICK_FROM_FILE:
                try {

                    mImageCaptureUri.add(size, data.getData());
//                    globalImageUriStr = mImageCaptureUri.toString();
                    setImageViews();

                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                break;

            case GET_IMAGE_FROM_HISTORY:
                try {
                    String receivedImage = data.getStringExtra("ImageName");
                    mImageCaptureUri.add(size, Uri.parse(receivedImage));
                    Log.v(TAG, "Image received from history is: "
                            + mImageCaptureUri);
                    Log.d("images :",receivedImage);
                    //if (receivedImage != "com.infovita.zywee.Activities.Medicine"){
                   // if (receivedImage.endsWith(".jpg")){
                    setImageViews();
                  //  }
                    //setImageViews();

                } catch (SecurityException e) {
                    Log.e("Buzz",
                            "Security Error in image history intent: "
                                    + e.toString());
                    e.printStackTrace();
                }
                break;
        }
    }

    public void snakbarMessage(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }

    /*public void snakbarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getAmbulanceList();
                    }
                });
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.rgb(0, 111, 192));//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }*/


    private void setImageViews() {

        /*If there is an image uri button will be enabled
        * else disabled*/
        if (mImageCaptureUri.get(0)!=null){
            zReviewOrderButton.setVisibility(View.VISIBLE);
        } else {
            zReviewOrderButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        File checkFile = new File(Environment.getExternalStoragePublicDirectory("Buzz"), TAG);
        //getting the control of sdcard files
//        deleteDir(checkFile);
    }

    //Deleting the temperary folder and the file created in the sdcard
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }


    public void snakbarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);//change Snackbar's background color;
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);//change Snackbar's text color;
        snackbar.show(); // Don’t forget to show!
    }


    // move the selected photo from its current folder to application Buzz
    // folder
    public void moveToBuzzFolder(Uri mImageUri) throws Exception {

        Bitmap storeIntoDestFolder = null;

        try {
            storeIntoDestFolder = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
        } catch (OutOfMemoryError e) {
            Log.v(TAG, "Out of memory: " + e.toString());
            Log.v(TAG, "Running garbage collection");
            System.gc();
            try {
                storeIntoDestFolder = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
            } catch (OutOfMemoryError exc) {
            }
        } catch (Exception e) {
            Log.v(TAG, "Generinc exception caught in moveToBuzzFolder()");
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        // Log.i(TAG, "Before compress:
        // "+storeIntoDestFolder.getByteCount()+"");
        if (storeIntoDestFolder != null)
            storeIntoDestFolder.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

        File f = new File(Environment.getExternalStorageDirectory() + "/Buzz/" + "Buzz_"
                + String.valueOf(System.currentTimeMillis()) + ".jpg");
        Log.v("BUZZ", "Path for the Buzz directory is: " + f.getAbsolutePath());

        mImageCaptureUri.set(0, Uri.fromFile(f));
/*
        ApplicationPreferences prefs = new ApplicationPreferences();
        prefs.storePrescriptionName(getApplicationContext(), f.getName());

        // new code

        // if(test== false)
        // {
        // f.delete();
        // }

        f.createNewFile();*/
        // write the bytes in file
        FileOutputStream fo = new FileOutputStream(f);
        try {
            fo.write(bytes.toByteArray());
        } catch (Exception e) {
            Log.e(TAG, "Error writing to the image file");
        }
        // close FileOutput
        fo.close();
    }


    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
    }


    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        ProgressDialog dialog;
        Serverdatas sd;
        String url;
        int k;
        ZyweeData zdi;
        DatabaseHelper helper;

        ArrayList<Uri> cartPrescriptionUri;

        public UploadFileToServer(int k) {
            this.k = k;
        }


        @Override
        protected void onPreExecute() {
            cartPrescriptionUri = new ArrayList<>();
            cartPrescriptionUri = PreviewGrid.Imageid;
            zdi = new ZyweeData();
            helper = new DatabaseHelper(getApplicationContext());
            dialog = new ProgressDialog(Medicine.this);
            dialog.setMessage("Uploading Image..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            sd = new Serverdatas();
            url = sd.endPoint1 + "upload_file";
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
                File sourceFile = new File(mImageCaptureUri.get(0).getPath());

                SharedPreferences sharedpreferences = getSharedPreferences("image_uri", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("image_uri", mImageCaptureUri.get(0).getPath());
                editor.commit();


                en.addPart("image", new FileBody(sourceFile));

                Log.d(TAG, "File : " + new FileBody(sourceFile));

                long totalSize = en.getContentLength();
                Log.v(TAG, "total entity size is: " + totalSize);
                httppost.setEntity(en);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                    Log.v(TAG, "Status Code: " + statusCode);
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

            Log.e(TAG, "Response from server after uploading image: " +
                    result);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            /*SharedPreferences sharedpreferences = getSharedPreferences("image_upload", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();*/
            // showing the server response in an alert dialog
            // showAlert(result);
            if (!result.equals("error")) {
                zdi.setPrescriptionImageUri(cartPrescriptionUri.get(k));
                helper.addZyweePrescriptionCartDetail(zdi);
                Log.v(TAG, "Added to cart : " + cartPrescriptionUri.get(k).toString());
                Medicine.this.finish();
                Intent open = new Intent(getApplicationContext(), MyCart.class);
                startActivity(open);
                /*editor.putString("image_upload", "succesxs");
                editor.commit();*/
            } else {
               snakbarMessage(findViewById(android.R.id.content).getRootView(), "Internet might be slow to upload prescription");
                /*editor.putString("image_upload", "error");
                editor.commit();*/
//                snakbarMessage("Sorry image cannot be uploaded!");
            }
        }
    }

    Button zReviewOrderButton;

}
