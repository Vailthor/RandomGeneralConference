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

    @Query("select * from talk where title = :title")
    Talk getTalkByTitle(String title);

    @Query("select * from talk where author = :author ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthor(String author);

    @Query("select * from talk where year = :year ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYear(int year);

    @Query("select * from talk where month = :month ORDER BY RANDOM() LIMIT 1")
    Talk getTalkbyMonth(int month);

    @Insert
    void insert(Talk talk);

    @Delete
    void delete(Talk talk);

    @Update
    void update(Talk talk);

}
