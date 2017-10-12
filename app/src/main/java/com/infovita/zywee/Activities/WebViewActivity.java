package com.infovita.zywee.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.infovita.zywee.R;
import com.infovita.zywee.Utility.AvenuesParams;
import com.infovita.zywee.Utility.Constants;
import com.infovita.zywee.Utility.RSAUtility;
import com.infovita.zywee.Utility.ServiceHandlers;
import com.infovita.zywee.Utility.ServiceUtility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khizarkhan on 29-05-2017.
 */

public class WebViewActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    Intent mainIntent;
    String html, encVal;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_web_view);
        mainIntent = getIntent();

        // Calling async task to get display content
        new RenderView().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class RenderView extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            dialog = new ProgressDialog(WebViewActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandlers sh = new ServiceHandlers();

           // List<NameValuePair> params = new ArrayList<NameValuePair>();
           /* params.add(new BasicNameValuePair("access_code", ACCESS_CODE));

            params.add(new BasicNameValuePair("order_id", ORDER_ID));

            Log.d("accesscode",ACCESS_CODE);
            Log.d("ORDER_ID",ORDER_ID);

            // Making a request to url and getting response
           *//* ContentValues params = new ContentValues();
            params.put(AvenuesParams.ACCESS_CODE, mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE));
            params.put(AvenuesParams.ORDER_ID, mainIntent.getStringExtra(AvenuesParams.ORDER_ID));*//*

            String vResponse = sh.makeServiceCall(AvenuesParams.RSA_KEY_URL, ServiceHandler.POST, params);
            Log.d("url",AvenuesParams.RSA_KEY_URL);
            //System.out.println(vResponse);
            if(!ServiceUtility.chkNull(vResponse).equals("")
                    && ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR")==-1){
                StringBuffer vEncVal = new StringBuffer("");
                vEncVal.append(ServiceUtility.addToPostParams("amount", AvenuesParams.AMOUNT));
                vEncVal.append(ServiceUtility.addToPostParams("currency", AvenuesParams.CURRENCY));
                encVal = RSAUtility.encrypt(vEncVal.substring(0,vEncVal.length()-1), vResponse);
                Log.d("encVal",encVal);
                Log.d("amount",AvenuesParams.AMOUNT);
                Log.d("currency",AvenuesParams.CURRENCY);
            }
*/
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(AvenuesParams.ACCESS_CODE, mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE)));
            params.add(new BasicNameValuePair(AvenuesParams.ORDER_ID, mainIntent.getStringExtra(AvenuesParams.ORDER_ID)));

            String vResponse = sh.makeServiceCall(mainIntent.getStringExtra(AvenuesParams.RSA_KEY_URL), ServiceHandlers.POST, params);
            System.out.println(vResponse);
            if(!ServiceUtility.chkNull(vResponse).equals("")
                    && ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR")==-1){
                StringBuffer vEncVal = new StringBuffer("");
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, mainIntent.getStringExtra(AvenuesParams.AMOUNT)));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, mainIntent.getStringExtra(AvenuesParams.CURRENCY)));
                encVal = RSAUtility.encrypt(vEncVal.substring(0,vEncVal.length()-1), vResponse);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (dialog.isShowing())
                dialog.dismiss();

            @SuppressWarnings("unused")
            class MyJavaScriptInterface
            {
                @JavascriptInterface
                public void processHTML(String html)
                {
                    // process the html as needed by the app
                    String status = null;
                    if(html.indexOf("Failure")!=-1){
                        status = "Transaction Declined!";
                    }else if(html.indexOf("Success")!=-1){
                        status = "Transaction Successful!";
                    }else if(html.indexOf("Aborted")!=-1){
                        status = "Transaction Cancelled!";
                    }else{
                        status = "Status Not Known!";
                    }
                    //Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
                    intent.putExtra("transStatus", status);
                    startActivity(intent);
                }
            }

            final WebView webview = (WebView) findViewById(R.id.webview);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
            webview.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(webview, url);
                    Log.d("url", url);
                    if(url.indexOf("/ccavResponseHandler.php")!=-1){
                        Log.d("index", "webview");
                        webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                    }
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                }
            });
            /* An instance of this class will be registered as a JavaScript interface */
            StringBuffer params = new StringBuffer();
           /* params.append(ServiceUtility.addToPostParams("access_code",AvenuesParams.ACCESS_CODE));
            params.append(ServiceUtility.addToPostParams("merchant_id",AvenuesParams.MERCHANT_ID));
            params.append(ServiceUtility.addToPostParams("order_id",AvenuesParams.ORDER_ID));
            params.append(ServiceUtility.addToPostParams("redirect_url",AvenuesParams.REDIRECT_URL));
            params.append(ServiceUtility.addToPostParams("cancel_url",AvenuesParams.CANCEL_URL));
            params.append(ServiceUtility.addToPostParams("enc_val",URLEncoder.encode(encVal)));*/

            params.append(ServiceUtility.addToPostParams(AvenuesParams.ACCESS_CODE,mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_ID,mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ORDER_ID,mainIntent.getStringExtra(AvenuesParams.ORDER_ID)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.REDIRECT_URL,mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.CANCEL_URL,mainIntent.getStringExtra(AvenuesParams.CANCEL_URL)));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ENC_VAL,URLEncoder.encode(encVal)));

            String vPostParams = params.substring(0, params.length() - 1);
            Log.d("params :", vPostParams);

            try {
                String postData = URLEncoder.encode(vPostParams, "UTF-8");
                webview.postUrl(Constants.TRANS_URL, postData.getBytes());
            } catch (Exception e) {
                showToast("Exception occured while opening webview.");
            }
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
    }
}