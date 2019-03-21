package com.vailthor.randomgeneralconference;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class TalkView extends Activity {

    final String TAG = "TALK_VIEW";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_talk_view);

        Bundle extras = getIntent().getExtras();
        String URL = extras.getString("URL");
        WebView talkView = findViewById(R.id.talkWindow);
        talkView.setWebViewClient(new WebViewClient());
        talkView.setWebChromeClient(new WebChromeClient());
        talkView.getSettings().setJavaScriptEnabled(true);
        talkView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        Log.d(TAG, "onCreate: " + WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        talkView.loadUrl(URL);
        //getTalkHTML(URL);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebView talkView = findViewById(R.id.talkWindow);
        talkView.destroy();

    }
}
