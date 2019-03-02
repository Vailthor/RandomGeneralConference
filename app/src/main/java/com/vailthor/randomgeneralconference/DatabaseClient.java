package com.vailthor.randomgeneralconference;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseClient {
    private Context ctx;
    private static DatabaseClient instance;

    private AppDatabase appDatabase;

    private DatabaseClient(Context ctx) {
        this.ctx = ctx;

        appDatabase = Room.databaseBuilder(ctx, AppDatabase.class, "Talks").build();
    }

    public static synchronized DatabaseClient getInstance(Context ctx) {
        if (instance == null) {
            instance = new DatabaseClient(ctx);
        }
        return instance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
