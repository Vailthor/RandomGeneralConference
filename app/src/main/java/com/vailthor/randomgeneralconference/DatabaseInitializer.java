package com.vailthor.randomgeneralconference;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class DatabaseInitializer {
    private static final String TAG = "DBInitialize";
    public static void populateAsync(final AppDatabase db, Context context) {

        PopulateDbAsync task = new PopulateDbAsync(db, context);
        task.execute();
    }

    private static void populateWithData(AppDatabase db, Context ct) {
        //read from csv file
        AssetManager assetManager = ct.getAssets();
        BufferedReader reader = null;
        try {
            InputStream is = assetManager.open("talks.csv");
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String text = null;
            int count = 1;
            while ((text = reader.readLine()) != null) {
                if (count % 100 == 0)
                    Log.d(TAG, "Adding talk" + count);
                count++;
                String[] tArr = text.split(",");
                tArr[0] = tArr[0].replace('+', ',');
                boolean report = false;
                if (tArr[4].equals("T"))
                    report = true;
                Talk t = new Talk(tArr[0], tArr[1], Integer.parseInt(tArr[2]), Integer.parseInt(tArr[3]), report, tArr[5], tArr[6]);
                db.talkDao().insert(t);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }

        }
        Log.d(TAG, "Done Populating");
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private Context ct;

        PopulateDbAsync(AppDatabase db, Context context) {
            mDb = db;
            ct = context;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithData(mDb, ct);
            return null;
        }

    }
}
