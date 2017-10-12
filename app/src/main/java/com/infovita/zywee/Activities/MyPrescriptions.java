package com.infovita.zywee.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.infovita.zywee.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Khizarkhan on 02-02-2017.
 */
public class MyPrescriptions extends AppCompatActivity {

    private final String TAG = "MyPrescription";

    public static String parentOfThisActivity = "";
    ImageAdapter myImageAdapter;

    ArrayList<Uri> uri = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prescriptions);

        Intent i = getIntent();
        parentOfThisActivity = i.getStringExtra("parent");
        Log.v(TAG, "Parent of this activity is: " + parentOfThisActivity);

        /*Initialising fb sdk*/
        FacebookSdk.sdkInitialize(this);
        fbEventLogger();
        setActionBar();
        initializeComponents();
    }

    private void fbEventLogger() {
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ACHIEVED_LEVEL);
    }


    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent(AppEventsConstants.EVENT_NAME_TIME_BETWEEN_SESSIONS);
        logger.logEvent(AppEventsConstants.EVENT_NAME_SESSION_INTERRUPTIONS);
    }

    //    Initialise and sets the action bar
    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<small>" + "My Prescriptions" + "</small>"));
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

        MyPrescriptions.this.finish();
    }


    private void initializeComponents() {
        GridView gridview = (GridView) findViewById(R.id.my_prescriptions_grid_view);
        myImageAdapter = new ImageAdapter(this);
        int i = myImageAdapter.getCount();


        gridview.setAdapter(myImageAdapter);

        String ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory().getAbsolutePath();

        String targetPath = ExternalStorageDirectoryPath + "/BUZZ/com.infovita.zywee.Activities.Medicine/";


			/*Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG)
                .show();
			*/
        File targetDirector = new File(targetPath);
        int a = 0;
        if (targetDirector != null) {
            File[] files = targetDirector.listFiles();
            if (files != null) {
//                for (File file : files)
                for (int j = 0; j < files.length; j++) {
                    if(files[j].getAbsolutePath().endsWith(".jpg")){
                        myImageAdapter.add(files[j].getAbsolutePath());
                    }
                    uri.add(j, Uri.fromFile(files[j]));
                   /* if(Uri.fromFile(files[j]).equals(".jpg")) {
                        uri.add(j, Uri.fromFile(files[j]));
                    }*/
                    Log.d("uri :", uri + "");
                    gridview.setOnItemClickListener(myOnItemClickListener);
                    a++;
                }
            }
        }
        if (a == 0) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("No Previous Prescriptions");
            adb.setMessage("No Prescriptions available");
            adb.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog ad = adb.create();
            ad.show();
        }
    }


   /* AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if (MyPrescriptions.parentOfThisActivity.equals("MainActivity")) {
                Uri prescriptionUri = uri.get(position);
                try {
                    File file = new File(prescriptionUri.getPath());
                    if (file.exists()){
                        file.delete();
                    }
                } catch (Exception e) {
                    Log.e("ImagePreview", "Error in showing image: " + e.toString());
                }
            }
            return false;
        }
    };*/

    AdapterView.OnItemClickListener myOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            String prompt = (String) parent.getItemAtPosition(position);
            Uri prescriptionUri = uri.get(position);

            if (MyPrescriptions.parentOfThisActivity.equals("PrescriptionMedicine")) {
                Intent output = new Intent();
                String nameOfSelectedImage = "TempPhoto.jpg";
                //nameOfSelectedImage = prompt.substring(prompt.indexOf("Buzz_"));

                try {
                    //putting it in try catch in case the image is not previously added to database
                    nameOfSelectedImage = prompt.substring(prompt.indexOf("Buzz_"));
                    Log.v(TAG, nameOfSelectedImage);
                } catch (Exception e) {
                    Log.v(TAG, "Temporary image selected");
                }

                output.putExtra("ImageName", prescriptionUri.toString());

                Log.v("ImageName", id +"");

                setResult(RESULT_OK, output);
                finish();
            } else if (MyPrescriptions.parentOfThisActivity.equals("LandingActivity")) {

                Log.d(TAG, "Showing Image");
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri.get(position), "image/*");
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("ImagePreview", "Error in showing image: " + e.toString());
                }
            }
        }
    };


    /**
     * Adapter
     * Add the images to the grid view
     */
    public class ImageAdapter extends BaseAdapter {

        private Context mContext;
        ArrayList<String> itemList = new ArrayList<>();

        public ImageAdapter(Context c) {
            mContext = c;
        }

        void add(String path) {
            itemList.add(path);
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                //imageView.setLayoutParams(new GridView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(5, 5, 5, 5);
            } else {
                imageView = (ImageView) convertView;

            }


            Bitmap bm = decodeSampledBitmapFromUri(itemList.get(position), 200, 200);

            imageView.setImageBitmap(bm);
            return imageView;
        }

        public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
            Bitmap bm = null;
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            try {
                BitmapFactory.decodeFile(path, options);
            } catch (OutOfMemoryError err) {
                Log.v(TAG, "Out of Memory error: " + err.toString());
                System.gc();
                BitmapFactory.decodeFile(path, options);
            }
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            try {
                bm = BitmapFactory.decodeFile(path, options);
            } catch (OutOfMemoryError err) {
                Log.v(TAG, "Out of Memory error: " + err.toString());
                System.gc();
                bm = BitmapFactory.decodeFile(path, options);
            }
            return bm;
        }

        public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;
            if (height > reqHeight || width > reqWidth) {
                if (width > height) {
                    inSampleSize = Math.round((float) height / (float) reqHeight);
                } else {
                    inSampleSize = Math.round((float) width / (float) reqWidth);
                }
            }
            return inSampleSize;
        }

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
          *//*  case R.id.action_cart:
                Intent cart = new Intent(getApplication(), MyCart.class);
                startActivity(cart);
                break;*//*
            case R.id.home:
                //Intent home = new Intent(getApplication(), LandingActivity.class);
               // call_home();
                startActivity(new Intent(getApplication(), LandingActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                *//*home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);*//*
                break;

            *//*case R.id.about_us:
                Uri uri = Uri.parse("https://www.zywee.com/aboutUs");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;*//*
        }
        return super.onOptionsItemSelected(item);
    }*/

    /*private void call_home(){

        startActivity(new Intent(getApplicationContext(), LandingActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }*/

}
