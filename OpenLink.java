package com.ashuguy.gulfnewsrss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class OpenLink extends AppCompatActivity {

    private String url;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_link);
        webView = (WebView) findViewById(R.id.webview);
        Bundle bundle = getIntent().getExtras();


        if (bundle == null)
        {

            url = null;

        }
        else
        {
            url = bundle.getString("URL");
            

        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);


    }
}
