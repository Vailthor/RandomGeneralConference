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

// TODO: 3/20/2019 get back arrow on top bar working correctly

public class History extends AppCompatActivity {

    int firstNegative;
    int currentPosition;
    ArrayList<Integer> toDelete;
    ArrayList<String> talksHistory;
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
                    toDelete.set(0,-1);
                else
                    toDelete.add(-1);
                if (historySize - firstNegative > 0) {
                    talksHistory.clear();
                    arrayAdapter.notifyDataSetChanged();
                }

            }
        });

        delIndiv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentPosition != -1) {
                    toDelete.add(currentPosition + firstNegative);
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
        if (!toDelete.isEmpty()) {
            extras.putIntegerArrayList("toDelete", toDelete);
            intent.putExtras(extras);
            setResult(RESULT_OK, intent);
        }
        else
            setResult(RESULT_CANCELED, intent);
        finish();
    }



}
