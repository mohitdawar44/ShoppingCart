package com.shopit.shopit.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 *
 * Created by mohit on 20/1/18.
 */
@Entity
public class PersistableItem {

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String categoryType;

    @ColumnInfo
    public String itemId;
}

