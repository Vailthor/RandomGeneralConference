package com.personal.nathan.randomgeneralconferencetalk;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Talk.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TalkDao talkDao();
}
