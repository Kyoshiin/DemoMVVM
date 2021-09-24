package com.roy.mvvmexample;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room annotation which create all sqlite code during compilation
* for this object,{removes boiler plate code for SQLite}
 */

@Entity(tableName = "note_table")
public class Note {
    //room will auto generate the columns

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private int priority;

    //for room to recreate the object from db
    public Note(String title, String description, int priority) { //cuz id auto generate
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
