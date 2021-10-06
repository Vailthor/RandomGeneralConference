package com.personal.nathan.randomgeneralconferencetalk;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        readSwitch.setChecked(settings.getBoolean("read", false));
        Button refreshDB = findViewById(R.id.refresh);
        Button resetDB = findViewById(R.id.reset);

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
        /*
        refreshDB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Alert to tell the user what refresh does
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

                builder.setMessage(R.string.refresDBMessage)
                        .setTitle(R.string.refresDBTitle);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Yes button
                        settings.edit().putBoolean("first_time", true).apply();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        resetDB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Alert to tell the user what reset does
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

                builder.setMessage(R.string.resetDBMessage)
                        .setTitle(R.string.resetDBTitle);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Yes button

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        */
    }
}
