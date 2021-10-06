package com.personal.nathan.randomgeneralconferencetalk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;

/**
 * Class to view and delete the history. Since main and history are two different activities the information must be passed back and forth using strings.
 */
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
    /**
     * Create activity by adding talks in the history to viewable list and starting listeners for the buttons.
     */
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

        //This allows the list to be selectable
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


        histList.setAdapter(arrayAdapter);
        Button delHistory = findViewById(R.id.deleteHistory);
        Button delIndiv = findViewById(R.id.delSelected);
        Button openIndiv = findViewById(R.id.openSelected);

        //Code for Delete Entire History Button
        delHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Alert to make sure the user actually wants to delete
                AlertDialog.Builder builder = new AlertDialog.Builder(History.this);

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

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        //Code for deleting one individual talk
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

        //Code for sending the talk selected back to the main activity.
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
    /**
     * Insure the back button in the app works the same as the android back button.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    /**
     * When back is pressed send the list of strings back to the main activity.
     */
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
