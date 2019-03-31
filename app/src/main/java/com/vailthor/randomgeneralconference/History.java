package com.vailthor.randomgeneralconference;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;

public class History extends AppCompatActivity {

    int firstNegative;
    int currentPosition;
    boolean noTalks = false;
    ArrayList<Integer> toDelete;
    ArrayList<String> talksHistory;
    ArrayList<String> historyTitle;
    ArrayList<Integer> historyID;
    private static final String TAG = "History";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toDelete = new ArrayList<>();
        currentPosition = -1;
        Bundle extras = getIntent().getExtras();
        talksHistory = new ArrayList<>();
        historyID = new ArrayList<>();
        historyTitle = new ArrayList<>();

        if (extras != null) {


            talksHistory = extras.getStringArrayList("talksHistory");
            if (!talksHistory.isEmpty()) {
                for (String i : talksHistory) {
                    String[] hist = i.split("@");
                    historyTitle.add(hist[0]);
                    if (hist.length > 1) {
                        historyID.add(Integer.parseInt(hist[1]));
                    }
                    else
                        historyID.add(Integer.parseInt(hist[0]));
                }
                talksHistory = new ArrayList<>(historyTitle);
            }
            else {
                talksHistory.add("(No Talks in History)");
                noTalks = true;
            }

        }


        final ListView histList = findViewById(R.id.historyList);
        histList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                currentPosition = position;
                view.setSelected(true);
            }

        });
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                talksHistory);

        //histList.removeViewAt(0);
        histList.setAdapter(arrayAdapter);
        Button delHistory = findViewById(R.id.deleteHistory);
        Button delIndiv = findViewById(R.id.delSelected);
        Button openIndiv = findViewById(R.id.openSelected);

        delHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(History.this);

// 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.delete_history_message)
                        .setTitle(R.string.delete_history_title);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Yes button
                        if (toDelete.size() > 0)
                            toDelete.set(0, -1);
                        else
                            toDelete.add(-1);
                        talksHistory.clear();
                        arrayAdapter.notifyDataSetChanged();
                        //}
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        delIndiv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (currentPosition != -1) {


                        toDelete.add(historyID.get(historyTitle.indexOf(talksHistory.get(currentPosition))));
                        talksHistory.remove(currentPosition);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
                catch (Exception e) {
                    ;
                }
            }
        });

        openIndiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    Bundle extras = new Bundle();
                    int idToOpen = historyID.get(historyTitle.indexOf(talksHistory.get(currentPosition)));
                    extras.putInt("id", idToOpen);
                    if (!toDelete.isEmpty() && !noTalks) {
                        extras.putIntegerArrayList("toDelete", toDelete);
                        intent.putExtras(extras);
                        setResult(RESULT_OK, intent);
                    } else {
                        intent.putExtras(extras);
                        setResult(RESULT_CANCELED, intent);
                    }
                    finish();
                }
                catch (Exception e) {
                    ;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        Bundle extras = new Bundle();
        if (!toDelete.isEmpty() && !noTalks) {
            extras.putIntegerArrayList("toDelete", toDelete);
            intent.putExtras(extras);
            setResult(RESULT_OK, intent);
        }
        else
            setResult(RESULT_CANCELED, intent);
        finish();
    }



}
