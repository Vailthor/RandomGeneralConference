package com.vailthor.randomgeneralconference;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseInitializer {
    public static void populateAsync(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithData(db);
    }

    private static void populateWithData(AppDatabase db) {
        //read from csv file
        File file = new File("talks-sample.csv");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
                String[] tArr = text.split(",");
                tArr[0] = tArr[0].replace('+', ',');
                boolean report = false;
                if (tArr[4].equals('T'))
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
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithData(mDb);
            return null;
        }

    }
}
