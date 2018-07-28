/*
 *
 *  Proprietary and confidential. Property of Kellton Tech Solutions Ltd. Do not disclose or distribute.
 *  You must have written permission from Kellton Tech Solutions Ltd. to use this code.
 *
 */

package com.kelltontech.ui.activity;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import com.kelltontech.BuildConfig;
import com.kelltontech.R;
import com.kelltontech.application.BaseApplication;
import com.kelltontech.ui.IScreen;
import com.kelltontech.utils.KeypadUtils;


/**
 * This class is used as base-class for application-base-activity.
 */
public abstract class BaseActivity extends AppCompatActivity implements IScreen {

    private String LOG_TAG = getClass().getSimpleName();
    public static final String COMING_FROM_NOTIFICATION = "COMING_FROM_NOTIFICATION";
    public static final String NOTIFICATION_TYPE_BROADCAST = "NOTIFICATION_TYPE_BROADCAST";
    public static final String PUSH_MODEL = "PUSH_MODEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(COMING_FROM_NOTIFICATION) && bundle.containsKey(NOTIFICATION_TYPE_BROADCAST)
                && bundle.containsKey(PUSH_MODEL)) {

            Intent intent = new Intent("NOTIFICATION_CLICK_INTENT_FILTER");
            bundle.putString(NOTIFICATION_TYPE_BROADCAST, bundle.getString(NOTIFICATION_TYPE_BROADCAST));
            intent.putExtras(bundle);
            sendBroadcast(intent);
        }
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            Log.i(LOG_TAG, "onResume()");
        }

        Application application = this.getApplication();
        if (application instanceof BaseApplication) {
            BaseApplication baseApplication = (BaseApplication) application;
            if (baseApplication.isAppInBackground()) {
                onAppResumeFromBackground();
            }
            baseApplication.onActivityResumed();
        }
    }

    /**
     * This callback will be called after onResume if application is being
     * resumed from background. <br/>
     * <p/>
     * Subclasses can override this method to get this callback.
     */
    protected void onAppResumeFromBackground() {
        if (BuildConfig.DEBUG) {
            Log.i(LOG_TAG, "onAppResumeFromBackground()");
        }
    }

    /**
     * This method should be called to force app assume itself not in
     * background.
     */
    public final void setAppNotInBackground() {
        Application application = this.getApplication();
        if (application instanceof BaseApplication) {
            BaseApplication baseApplication = (BaseApplication) application;
            baseApplication.setAppInBackground(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (BuildConfig.DEBUG) {
            Log.i(LOG_TAG, "onPause()");
        }

        Application application = this.getApplication();
        if (application instanceof BaseApplication) {
            BaseApplication baseApplication = (BaseApplication) application;
            baseApplication.onActivityPaused();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (BuildConfig.DEBUG) {
            Log.i(LOG_TAG, "onNewIntent()");
        }
    }

    /**
     * Subclass should over-ride this method to update the UI with response,
     * this base class promises to call this method from UI thread.
     *
     * @param serviceResponse
     */
    public abstract void updateUi(final boolean status, final int action, final Object serviceResponse);

    // ////////////////////////////// show and hide ProgressDialog

//    private ProgressDialog mProgressDialog;
//
//    /**
//     * Shows a simple native progress dialog<br/>
//     * Subclass can override below two methods for custom dialogs- <br/>
//     * 1. showProgressDialog <br/>
//     * 2. removeProgressDialog
//     *
//     * @param bodyText
//     */
//    public void showProgressDialog(String bodyText) {
//        if (isFinishing()) {
//            return;
//        }
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(BaseActivity.this);
//            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            mProgressDialog.setCancelable(false);
//            mProgressDialog.setOnKeyListener(new Dialog.OnKeyListener() {
//                @Override
//                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                    if (keyCode == KeyEvent.KEYCODE_CAMERA || keyCode == KeyEvent.KEYCODE_SEARCH) {
//                        return true; //
//                    }
//                    return false;
//                }
//            });
//        }
//
//        mProgressDialog.setMessage(bodyText);
//
//        if (!mProgressDialog.isShowing()) {
//            mProgressDialog.show();
//        }
//    }
//
//    /**
//     * Removes the simple native progress dialog shown via showProgressDialog <br/>
//     * Subclass can override below two methods for custom dialogs- <br/>
//     * 1. showProgressDialog <br/>
//     * 2. removeProgressDialog
//     */
//    public void removeProgressDialog() {
//        try {
//            if (mProgressDialog != null && mProgressDialog.isShowing()) {
//                mProgressDialog.dismiss();
//            }
//        } catch (Exception e) {
//
//        }
//
//    }
//
//
//
//    public void setProgressDialog(ProgressDialog dialog) {
//        this.mProgressDialog = dialog;
//    }
    // ////////////////////////////// show and hide key-board


//    private PopupWindow mPpoPopupWindow;
//
//    private void popupProgressInit() {
//        mPpoPopupWindow= OtherUtill.showProgressDialog(this);
//    }
//
//    public void showProgressDialog(String message) {
//        if (isFinishing()) {
//            return;
//        }
//        if (mPpoPopupWindow == null) {
//           popupProgressInit();
//        }
//
//        if (!mPpoPopupWindow.isShowing()) {
//            mPpoPopupWindow.showAtLocation(getWindow().getDecorView().getRootView(),Gravity.CENTER,0,0);
//        }
//    }
//
//    /**
//     * Removes the simple native progress dialog shown via showProgressDialog <br/>
//     * Subclass can override below two methods for custom dialogs- <br/>
//     * 1. showProgressDialog <br/>
//     * 2. removeProgressDialog
//     */
//    public void removeProgressDialog() {
//        try {
//            if (mPpoPopupWindow != null && mPpoPopupWindow.isShowing()) {
//                mPpoPopupWindow.dismiss();
//            }
//        } catch (Exception e) {
//
//        }
//
//    }

//    @Override
//    public void onBackPressed() {
//        if(mPpoPopupWindow==null || !mPpoPopupWindow.isShowing()){
//            super.onBackPressed();
//        }
//    }

    private Dialog mDialog;
    private String mMessage = "";

    public void showProgressDialog(String message) {
        if (isFinishing()) {
            return;
        }
        if (mDialog == null || !mMessage.equals(message)) {
            mMessage = message;
            mDialog = new Dialog(this, android.R.style.Theme_Light);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.activity_web_view_test);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDialog.setCancelable(false);
            WebView webView = (WebView) mDialog.findViewById(R.id.webview);
            assert webView != null;
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            webView.getSettings().setJavaScriptEnabled(true);
//            webView.getSettings().setLoadWithOverviewMode(true);
//            webView.getSettings().setUseWideViewPort(true);
//            webView.getSettings().setSupportZoom(false);
            webView.setBackgroundColor(0);
            webView.setWebViewClient(new WebViewClient());
            webView.loadDataWithBaseURL("file:///android_asset/", "<!DOCTYPE html>\n" +
                    "<html >\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <title>Mahindra Mandala Spinner</title>\n" +
                    "  \n" +
                    "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css\">\n" +
                    "\n" +
                    "  \n" +
                    "      <style>\n" +
                    "      /* NOTE: The styles were added inline because Prefixfree needs access to your styles and they must be inlined if they are on local disk! */\n" +
                    "      @keyframes loader-anim {\n" +
                    "  0% {\n" +
                    "    transform: scale(1);\n" +
                    "  }\n" +
                    "  20% {\n" +
                    "    transform: scale(1.1);\n" +
                    "  }\n" +
                    "  30% {\n" +
                    "    transform: scale(1);\n" +
                    "  }\n" +
                    "  100% {\n" +
                    "    transform: scale(1);\n" +
                    "  }\n" +
                    "}\n" +
                    ".loader {\n" +
                    "  position: absolute;\n" +
                    "  width: 150px;\n" +
                    "  height: 150px;\n" +
                    "  left: 50%;\n" +
                    "  top: 50%;\n" +
                    "  transform: translate(-50%, -50%);\n" +
                    "}\n" +
                    ".loader > svg {\n" +
                    "  height: 100%;\n" +
                    "  position: absolute;\n" +
                    "  left: 50%;\n" +
                    "  top: 50%;\n" +
                    "  transform: translate(-50%, -50%);\n" +
                    "}\n" +
                    "\n" +
                    ".leaf {\n" +
                    "  position: absolute;\n" +
                    "  transform-origin: 80% 130%;\n" +
                    "}\n" +
                    "\n" +
                    ".leaf:nth-child(1) {\n" +
                    "  animation: loader-anim 1s infinite;\n" +
                    "  animation-delay: 0.125s;\n" +
                    "  animation-easing: cubic-bezier(0.64, 0.57, 0.67, 1.53);\n" +
                    "}\n" +
                    "\n" +
                    ".leaf:nth-child(2) {\n" +
                    "  animation: loader-anim 1s infinite;\n" +
                    "  animation-delay: 0.25s;\n" +
                    "  animation-easing: cubic-bezier(0.64, 0.57, 0.67, 1.53);\n" +
                    "}\n" +
                    "\n" +
                    ".leaf:nth-child(3) {\n" +
                    "  animation: loader-anim 1s infinite;\n" +
                    "  animation-delay: 0.375s;\n" +
                    "  animation-easing: cubic-bezier(0.64, 0.57, 0.67, 1.53);\n" +
                    "}\n" +
                    "\n" +
                    ".leaf:nth-child(4) {\n" +
                    "  animation: loader-anim 1s infinite;\n" +
                    "  animation-delay: 0.5s;\n" +
                    "  animation-easing: cubic-bezier(0.64, 0.57, 0.67, 1.53);\n" +
                    "}\n" +
                    "\n" +
                    "    </style>\n" +
                    "\n" +
                    "  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js\"></script>\n" +
                    "\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "  <div class=\"loader\">\n" +
                    "  <?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "  <svg\n" +
                    "     xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
                    "     xmlns:cc=\"http://creativecommons.org/ns#\"\n" +
                    "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
                    "     xmlns:svg=\"http://www.w3.org/2000/svg\"\n" +
                    "     xmlns=\"http://www.w3.org/2000/svg\"\n" +
                    "     version=\"1.1\"\n" +
                    "     id=\"svg2\"\n" +
                    "     viewBox=\"0 0 350 350\"\n" +
                    "     height=\"250\"\n" +
                    "     width=\"250\">\n" +
                    "    <defs\n" +
                    "       id=\"defs4\">\n" +
                    "      <clipPath\n" +
                    "         clipPathUnits=\"userSpaceOnUse\"\n" +
                    "         id=\"clipPath3350\">\n" +
                    "        <path\n" +
                    "           d=\"m 0,595.28 841.89,0 L 841.89,0 0,0 0,595.28 Z\"\n" +
                    "           id=\"path3352\" />\n" +
                    "      </clipPath>\n" +
                    "    </defs>\n" +
                    "    <metadata\n" +
                    "       id=\"metadata7\">\n" +
                    "      <rdf:RDF>\n" +
                    "        <cc:Work\n" +
                    "           rdf:about=\"\">\n" +
                    "          <dc:format>image/svg+xml</dc:format>\n" +
                    "          <dc:type\n" +
                    "             rdf:resource=\"http://purl.org/dc/dcmitype/StillImage\" />\n" +
                    "          <dc:title></dc:title>\n" +
                    "        </cc:Work>\n" +
                    "      </rdf:RDF>\n" +
                    "    </metadata>\n" +
                    "    <g\n" +
                    "       transform=\"translate(-105.34514,-298.13632)\"\n" +
                    "       id=\"layer1\">\n" +
                    "      <g\n" +
                    "         transform=\"translate(26.890338,-3.6675361)\"\n" +
                    "         id=\"g4139\">\n" +
                    "        <path\n" +
                    "           id=\"m_left\" class=\"leaf\"\n" +
                    "           style=\"fill:#f79f4b;fill-opacity:1;fill-rule:nonzero;stroke:none\"\n" +
                    "           d=\"m 242.87451,471.71456 c 2.175,-19.5375 -20.1275,-49.66 -41.0575,-57.37875 -9.0325,-3.33375 -20.38125,-4.9075 -27.8,3.13 -6.46125,6.98125 -3.0225,16.34875 0.89375,24.12375 3.30625,6.57375 0.85125,9.40375 -5.11625,11.88375 -6.02625,2.48625 -11.89875,5.54875 -17.44875,8.99625 -10.85125,6.7575 -19.885,17.20625 -16.13875,29.69125 15.98,53.2825 105.68125,-11.57125 106.6675,-20.44625\" />\n" +
                    "        <path\n" +
                    "           id=\"m_top\" class=\"leaf\"\n" +
                    "           style=\"fill:#34bae4;fill-opacity:1;fill-rule:nonzero;stroke:none\"\n" +
                    "           d=\"m 264.78552,382.13931 c -2.87875,-6.55875 -10.53125,-34.5225 -24.4375,-30.39875 -12.09875,3.5875 -20.9975,43.70375 12.84375,99.93375 7.29375,12.1175 16.7575,11.33125 24.33875,-0.5925 14.405,-22.6425 38.06375,-91.8775 23.925,-113.095 -9.25375,-13.89125 -30.635,33.80875 -36.67,44.1525\" />\n" +
                    "        <path\n" +
                    "           id=\"m_right\" class=\"leaf\"\n" +
                    "           style=\"fill:#ffd560;fill-opacity:1;fill-rule:nonzero;stroke:none\"\n" +
                    "           d=\"m 285.83164,458.92643 c -9.52125,6.255 -11.9375,14.60875 -7.73875,25.385 2.74125,7.04875 29.1775,65.93375 65.10125,38.60375 20.0675,-15.26125 -3.15,-31.87 -4.795,-32.9375 -5.55375,-3.59 -5.51875,-5.78875 0.7325,-9.19625 11.90625,-6.50625 49.61,-33.2425 23.29875,-47.49 -17.48375,-9.46125 -65.35625,18.2375 -76.59875,25.635\" />\n" +
                    "        <path\n" +
                    "           id=\"m_bottom\" class=\"leaf\"\n" +
                    "           style=\"fill:#b2d251;fill-opacity:1;fill-rule:nonzero;stroke:none\"\n" +
                    "           d=\"m 236.18264,550.53718 c -9.59125,11.4975 -34.79625,48.65375 -46.0375,31.3 -28.26125,-43.605 66.52375,-108.43125 73.87625,-97.035 28.29,41.165 11.93625,119.95625 -10.3225,131.38125 -27.13,13.91125 -19.6,-50.54 -17.51625,-65.64625\" />\n" +
                    "      </g>\n" +
                    "    </g>\n" +
                    "  </svg>\n" +
                    "\n" +
                    "</div>\n" +
                    "  \n" +
                    "    <script src=\"js/index.js\"></script>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>\n", "text/html", "utf-8", null);

        }


        try {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        } catch (Exception e) {

        }

        
    }

    private static class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    /**
     * Removes the simple native progress dialog shown via showProgressDialog <br/>
     * Subclass can override below two methods for custom dialogs- <br/>
     * 1. showProgressDialog <br/>
     * 2. removeProgressDialog
     */
    public void removeProgressDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (Exception e) {

        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        try {
            View v = getCurrentFocus();
            boolean ret = super.dispatchTouchEvent(event);

            if (v instanceof EditText) {
                View w = getCurrentFocus();
                int scrcoords[] = new int[2];
                w.getLocationOnScreen(scrcoords);
                float x = event.getRawX() + w.getLeft() - scrcoords[0];
                float y = event.getRawY() + w.getTop() - scrcoords[1];

                if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                    KeypadUtils.hideSoftKeypad(this);
                }
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

    }


}