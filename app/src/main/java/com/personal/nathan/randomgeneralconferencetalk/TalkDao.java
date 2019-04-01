package com.personal.nathan.randomgeneralconferencetalk;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface TalkDao {
    //No checks
    @Query("select * from talk where id = :id")
    Talk getTalkByID(int id);

    @Query("select * from talk where author = :author and year = :year and month = :month and tags like :tag ORDER BY RANDOM() LIMIT 1")
    Talk getWithAllNoChecks(String author, int year, int month, String tag);

    @Query("select * from talk where author = :author ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthor(String author);

    @Query("select * from talk where author = :author and month = :month ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandMonth(String author, int month);

    @Query("select * from talk where author = :author and year = :year ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandYear(String author, int year);

    @Query("select * from talk where author = :author and year = :year and month = :month ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandDate(String author, int year, int month);

    @Query("select * from talk where author = :author and tags like :tag ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandTag(String author, String tag);

    @Query("select * from talk where author = :author and year = :year and tags like :tag ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorYearTag(String author, int year, String tag);

    @Query("select * from talk where author = :author and month = :month and tags like :tag ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorMonthTag(String author, int month, String tag);

    @Query("select * from talk where year = :year ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYear(int year);

    @Query("select * from talk where year = :year and month = :month ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByDate(int year, int month);

    @Query("select * from talk where year = :year and tags like :tag ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYearandTag(int year, String tag);

    @Query("select * from talk where year = :year and month = :month and tags like :tag ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYearMonthTag(int year, int month, String tag);

    @Query("select * from talk where month = :month ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByMonth(int month);

    @Query("select * from talk where month = :month and tags like :tag ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByMonthandTag(int month, String tag);

    @Query("select * from talk where tags like :tag ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByTag(String tag);


    //reportCheck
    @Query("select * from talk where report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getRandomTalkReport(Boolean report);

    @Query("select * from talk where author = :author and year = :year and month = :month and tags like :tag and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getWithAllReport(String author, int year, int month, String tag, Boolean report);

    @Query("select * from talk where author = :author and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthor(String author, Boolean report);

    @Query("select * from talk where author = :author and month = :month and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandMonth(String author, int month, Boolean report);

    @Query("select * from talk where author = :author and year = :year and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandYear(String author, int year, Boolean report);

    @Query("select * from talk where author = :author and year = :year and month = :month and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandDate(String author, int year, int month, Boolean report);

    @Query("select * from talk where author = :author and tags like :tag and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandTag(String author, String tag, Boolean report);

    @Query("select * from talk where author = :author and year = :year and tags like :tag and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorYearTag(String author, int year, String tag, Boolean report);

    @Query("select * from talk where author = :author and month = :month and tags like :tag and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorMonthTag(String author, int month, String tag, Boolean report);

    @Query("select * from talk where year = :year and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYear(int year, Boolean report);

    @Query("select * from talk where year = :year and month = :month and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByDate(int year, int month, Boolean report);

    @Query("select * from talk where year = :year and tags like :tag and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYearandTag(int year, String tag, Boolean report);

    @Query("select * from talk where year = :year and month = :month and tags like :tag and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYearMonthTag(int year, int month, String tag, Boolean report);

    @Query("select * from talk where month = :month and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByMonth(int month, Boolean report);

    @Query("select * from talk where month = :month and tags like :tag and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByMonthandTag(int month, String tag, Boolean report);

    @Query("select * from talk where tags like :tag and report = :report ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByTag(String tag, Boolean report);


    //historyCheck
    @Query("select * from talk where id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getRandomTalkHistory(int[] history);

    @Query("select * from talk where author = :author and year = :year and month = :month and tags like :tag and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getWithAllNoChecks(String author, int year, int month, String tag, int[] history);

    @Query("select * from talk where author = :author and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthor(String author, int[] history);

    @Query("select * from talk where author = :author and month = :month and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandMonth(String author, int month, int[] history);

    @Query("select * from talk where author = :author and year = :year and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandYear(String author, int year, int[] history);

    @Query("select * from talk where author = :author and year = :year and month = :month and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandDate(String author, int year, int month, int[] history);

    @Query("select * from talk where author = :author and tags like :tag and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandTag(String author, String tag, int[] history);

    @Query("select * from talk where author = :author and year = :year and tags like :tag and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorYearTag(String author, int year, String tag, int[] history);

    @Query("select * from talk where author = :author and month = :month and tags like :tag and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorMonthTag(String author, int month, String tag, int[] history);

    @Query("select * from talk where year = :year and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYear(int year, int[] history);

    @Query("select * from talk where year = :year and month = :month and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByDate(int year, int month, int[] history);

    @Query("select * from talk where year = :year and tags like :tag and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYearandTag(int year, String tag, int[] history);

    @Query("select * from talk where year = :year and month = :month and tags like :tag and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYearMonthTag(int year, int month, String tag, int[] history);

    @Query("select * from talk where month = :month and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByMonth(int month, int[] history);

    @Query("select * from talk where month = :month and tags like :tag and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByMonthandTag(int month, String tag, int[] history);

    @Query("select * from talk where tags like :tag and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByTag(String tag, int[] history);


    //report and history check
    @Query("select * from talk where report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getRandomTalk(Boolean report, int[] history);

    @Query("select * from talk where author = :author and year = :year and month = :month and tags like :tag and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getWithAllReport(String author, int year, int month, String tag, Boolean report, int[] history);

    @Query("select * from talk where author = :author and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthor(String author, Boolean report, int[] history);

    @Query("select * from talk where author = :author and month = :month and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandMonth(String author, int month, Boolean report, int[] history);

    @Query("select * from talk where author = :author and year = :year and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandYear(String author, int year, Boolean report, int[] history);

    @Query("select * from talk where author = :author and year = :year and month = :month and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandDate(String author, int year, int month, Boolean report, int[] history);

    @Query("select * from talk where author = :author and tags like :tag and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorandTag(String author, String tag, Boolean report, int[] history);

    @Query("select * from talk where author = :author and year = :year and tags like :tag and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorYearTag(String author, int year, String tag, Boolean report, int[] history);

    @Query("select * from talk where author = :author and month = :month and tags like :tag and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByAuthorMonthTag(String author, int month, String tag, Boolean report, int[] history);

    @Query("select * from talk where year = :year and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYear(int year, Boolean report, int[] history);

    @Query("select * from talk where year = :year and month = :month and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByDate(int year, int month, Boolean report, int[] history);

    @Query("select * from talk where year = :year and tags like :tag and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYearandTag(int year, String tag, Boolean report, int[] history);

    @Query("select * from talk where year = :year and month = :month and tags like :tag and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByYearMonthTag(int year, int month, String tag, Boolean report, int[] history);

    @Query("select * from talk where month = :month and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByMonth(int month, Boolean report, int[] history);

    @Query("select * from talk where month = :month and tags like :tag and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByMonthandTag(int month, String tag, Boolean report, int[] history);

    @Query("select * from talk where tags like :tag and report = :report and id not in (:history) ORDER BY RANDOM() LIMIT 1")
    Talk getTalkByTag(String tag, Boolean report, int[] history);

    @Query("select * from talk where title like '%'+:title+'%'")
    Talk getTalkByTitle(String title);

    @Query("DELETE FROM Talk")
    public void nukeTable();

    @Insert
    void insert(Talk talk);

    @Delete
    void delete(Talk talk);

    @Update
    void update(Talk talk);

}
