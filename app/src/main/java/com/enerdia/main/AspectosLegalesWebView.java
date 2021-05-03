package com.enerdia.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class AspectosLegalesWebView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspectos_legales_web_view);

        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("https://enerdia.weebly.com/aspectos-legales.html");

    }
}