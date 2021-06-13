package com.fernandomoya.appproyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ViewActivity extends AppCompatActivity {
    WebView webView;
    String url = "http://www.pae.ec/derecho-animal/legislacion-vigente/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        webView = (WebView) findViewById(R.id.webView);
        final WebSettings ajustWebView = webView.getSettings();
        ajustWebView.setJavaScriptEnabled(true);
        ajustWebView.setLoadsImagesAutomatically(true);
        webView.loadUrl(url);
    }
}