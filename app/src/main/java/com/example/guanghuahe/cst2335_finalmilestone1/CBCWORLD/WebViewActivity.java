package com.example.guanghuahe.cst2335_finalmilestone1.CBCWORLD;
//import statements
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

public class WebViewActivity extends AppCompatActivity {

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

    protected void setup(){
        myWebView = (WebView) findViewById(R.id.webView);


        // display the whole webpage in the window
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);

        // enable zooming
        myWebView.getSettings().setBuiltInZoomControls(true);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // enable JavaScript

        myWebView.setWebViewClient(new WebViewClient()); // keep navigation in the app

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
    } //setup

    // use the device back button for browser history
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    public static Intent newIntent(Context context, String newsUrl) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(EXTRA_ID, newsUrl);
        return intent;

    }
}

