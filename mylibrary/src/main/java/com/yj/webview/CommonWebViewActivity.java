package com.yj.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.sdk.YJManage;
import com.yj.ui.CustomProgressDialog;
import com.yj.ui.MResource;
import com.yj.util.Utils;


/**
 * 用于打开 H5 的 带 webview 的activity
 */
public class CommonWebViewActivity extends Activity {

    private View btnLeft;
    private TextView titleTxt;
    private LinearLayout webViewLayout;
    private CustomProgressDialog customProgressDialog;

    public static final String WEBVIEW_TITLE = "webview_title";
    public static final String WEBVIEW_URL = "webview_url";
    private static final String TAG = CommonWebViewActivity.class.getSimpleName();

    private HaiWebView mWebView;

    // 需要用 webview 打开的 url 地址
    private String mOriginalUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(MResource.getIdByName(this, Constants.LAYOUT,"common_webview_activity"));
        customProgressDialog = new CustomProgressDialog(this,"请稍后 ...");
        customProgressDialog.show();
        initUI();
        initWebViewParams();
        loadUrl();
    }

    @SuppressLint("JavascriptInterface")
    private void initUI() {
        btnLeft =  findViewById(MResource.getIdByName(this, Constants.ID,"btn_left"));
        titleTxt = (TextView) findViewById(MResource.getIdByName(this, Constants.ID,"txt_title"));
        webViewLayout = (LinearLayout) findViewById(MResource.getIdByName(this, Constants.ID,"webview_layout"));
//    customProgressDialog = new CustomProgressDialog(this);
//        LinearLayout webViewLayout = (LinearLayout) findViewById(R.id.webview_layout);
        mWebView = new HaiWebView(YJManage.getApplication());
        webViewLayout.addView(mWebView, -1, -1);
      //  mWebView.addJavascriptInterface(new HaiJsbridgeUtil(), "obj");
        mWebView.setWebViewClientListener(new HaiWebView.WebViewClientListener() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                customProgressDialog.dismiss();
            }


        });



        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    /**
     * 获取webview参数
     */
    private void initWebViewParams() {
        if (getIntent() == null || getIntent().getExtras() == null) {
            return;
        }

        Bundle bundle = getIntent().getExtras();
        try {
            mOriginalUrl = bundle.getString(WEBVIEW_URL);
            String title = bundle.getString(WEBVIEW_TITLE);

            if (TextUtils.isEmpty(title)) {
                title = "永聚互娱";
            }
            Utils.setBold(titleTxt);
            titleTxt.setText(title);
        } catch (Exception e) {
            e.printStackTrace();
//            LogUtils.e(TAG, "解析bundle数据出错！错误原因是:" + e);
        }
    }


    /**
     * 加载 h5 url
     */
    private void loadUrl() {
//    if (!mOriginalUrl.startsWith("http") && !mOriginalUrl.startsWith("https")) {
//      // 如果url不是http和https链接，则用系统打开
//      JumpModule.openAppSchama(CommonWebViewActivity.this, true, mOriginalUrl);
//    } else {
////      CookieSyncUtil.syncCookies(this, mOriginalUrl);
//      // url是http或https链接

        mWebView.loadUrl(mOriginalUrl);
//    mWebView.postUrl(mOriginalUrl,);
//    }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
            mWebView=null;
        }
    }
}
