package com.personal.nathan.randomgeneralconferencetalk;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Talk implements Serializable{


    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "author")
    public String author;

    @ColumnInfo(name = "year")
    public int year;

    @ColumnInfo(name = "month")
    public int month;

    @ColumnInfo(name = "report")
    public boolean report;

    @ColumnInfo(name = "URL")
    public String URL;

    @ColumnInfo(name = "tags")
    public String tags;

    public Talk(String title, String author, int year, int month,
                boolean report, String URL, String tags)
    {
        this.title = title;
        this.author = author;
        this.year = year;
        this.month = month;
        this.report = report;
        this.URL = URL;
        this.tags = tags;
    }


    public int getId() {return id;}
    public void setID(int id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;}
    public int getMonth() {return month;}
    public void setMonth(int month) {this.month = month;}
    public boolean getReport() {return report;}
    public void setReport(boolean report) {this.report = report;}
    public String getURL() {return URL;}
    public void setURL(String URL) {this.URL = URL;}
    public String getTags() {return tags;}
    public void setTags(String tags) {this.tags = tags;}

}
