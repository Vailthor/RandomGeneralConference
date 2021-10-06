package com.personal.nathan.randomgeneralconferencetalk;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.personal.nathan.randomgeneralconferencetalk.R;

/**
 * Used to view the given talk in app with a WebView.
 */
public class TalkView extends Activity {

    final String TAG = "TALK_VIEW";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    /**
     * Open the talk with the given URL.
     */
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
        talkView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );


        talkView.loadUrl(URL);
        //getTalkHTML(URL);


    }

    @Override
    /**
     * Destroy the WebView, if this is not done audio that is playing will continue.
     */
    protected void onDestroy() {
        super.onDestroy();
        WebView talkView = findViewById(R.id.talkWindow);
        talkView.destroy();

    }
}
