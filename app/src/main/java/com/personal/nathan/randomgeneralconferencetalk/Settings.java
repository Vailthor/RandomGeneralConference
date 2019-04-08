package com.personal.nathan.randomgeneralconferencetalk;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.personal.nathan.randomgeneralconferencetalk.R;

/**
 * Settings page that allows for the settings to be changed.
 */
public class Settings extends AppCompatActivity {

    final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);


        //Load the saved settings
        Switch histSwitch = findViewById(R.id.historySwitch);
        histSwitch.setChecked(settings.getBoolean("history", true));
        Switch reportSwitch = findViewById(R.id.reportsSwitch);
        reportSwitch.setChecked(settings.getBoolean("report", true));
        Switch readSwitch = findViewById(R.id.readInApp);
        readSwitch.setChecked(settings.getBoolean("read", true));

        //Listeners for the different settings.
        histSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    settings.edit().putBoolean("history", true).apply();
                else
                    settings.edit().putBoolean("history", false).apply();

                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });
        reportSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    settings.edit().putBoolean("report", true).apply();
                else
                    settings.edit().putBoolean("report", false).apply();

                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });
        readSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    settings.edit().putBoolean("read", true).apply();
                else
                    settings.edit().putBoolean("read", false).apply();

                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });

    }
}
