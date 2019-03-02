package com.vailthor.randomgeneralconference;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TalkDao {
    @Query("select * from talk where id = :id")
    Talk getTalkByID(int id);

    @Insert
    void insert(Talk talk);

    @Delete
    void delete(Talk talk);

    @Update
    void update(Talk talk);

}
