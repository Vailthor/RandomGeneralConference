package com.vailthor.randomgeneralconference;

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
    ArrayList<String> savedHistory;
    private static final String TAG = "History";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toDelete = new ArrayList<>();
        currentPosition = -1;
        Bundle extras = getIntent().getExtras();
         talksHistory = new ArrayList<>();
        if (extras != null) {
            talksHistory = extras.getStringArrayList("talksHistory");
            savedHistory = talksHistory;
            int negPost = talksHistory.indexOf("-1");
            if (negPost >= 0) {
                talksHistory.remove(negPost);
                firstNegative = 1;
            }
            else
                firstNegative = 0;
        }

        final int historySize = talksHistory.size();
        final ListView histList = findViewById(R.id.historyList);
        histList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                currentPosition = position;
                view.setSelected(true);
            }

        });
        if (historySize == 0 || (historySize == 1 && firstNegative == 1)) {
            talksHistory.add("(No Talks in History)");
            noTalks = true;
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                talksHistory);

        //histList.removeViewAt(0);
        histList.setAdapter(arrayAdapter);
        Button delHistory = findViewById(R.id.deleteHistory);
        Button delIndiv = findViewById(R.id.delSelected);

        delHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (toDelete.size() > 0)
                    toDelete.set(0, -1);
                else
                    toDelete.add(-1);
                //if (historySize > 0) {
                    talksHistory.clear();
                    arrayAdapter.notifyDataSetChanged();
                //}
            }
        });

        delIndiv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentPosition != -1) {
                    toDelete.add(savedHistory.indexOf(talksHistory.get(currentPosition)));
                    talksHistory.remove(currentPosition);
                    arrayAdapter.notifyDataSetChanged();
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
        Log.d(TAG, "onPause: ");
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
