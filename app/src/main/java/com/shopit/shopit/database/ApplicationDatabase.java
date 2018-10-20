package com.shopit.shopit.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 *
 * Created by mohit on 6/1/18.
 */

@Database(entities = {PersistableItem.class}, version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {

    public abstract PersistableItemDao messageDao();

}
