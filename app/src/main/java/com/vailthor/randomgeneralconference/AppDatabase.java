package com.vailthor.randomgeneralconference;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Talk.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TalkDao talkDao();
}
