package com.example.guanghuahe.cst2335_finalmilestone1.CBCWORLD;
//source: https://developer.android.com/guide/webapps/webview
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.guanghuahe.cst2335_finalmilestone1.R;

public class CBCWebViewActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "ca.wlu.amalik.cbcnews";

    private WebView myWebView;
    private ProgressBar mProgressBar;
    private TextView titleTextView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setMax(100); // WebChromeClient reports in range 0-100
        titleTextView = (TextView) findViewById(R.id.titleTextView);

        url = getIntent().getStringExtra(EXTRA_ID);

        setup();
    }

    /**
     * setup displays the whole webpage in the window and enables zooming, javascript
     * and keeps navigation in the app
     */
    protected void setup(){
        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);


        myWebView.getSettings().setBuiltInZoomControls(true);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.setWebViewClient(new WebViewClient());

        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int progress) {
                if (progress == 100) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(progress);
                }
            }

            public void onReceivedTitle(WebView webView, String title) {
                titleTextView.setText(title);
            }
        });


        myWebView.loadUrl(url);
    }

    /**
     * onKeyDown uses the device back button to display browser history
     * checks the key event to see if there's history or not
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public static Intent newIntent(Context context, String newsUrl) {
        Intent intent = new Intent(context, CBCWebViewActivity.class);
        intent.putExtra(EXTRA_ID, newsUrl);
        return intent;

    }
}

