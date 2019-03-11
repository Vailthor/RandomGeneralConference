package com.vailthor.randomgeneralconference;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class TalkView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_view);

        Bundle extras = getIntent().getExtras();
        String URL = extras.getString("URL");
        WebView talkView = findViewById(R.id.talkWindow);
        talkView.loadUrl(URL);
    }
}
