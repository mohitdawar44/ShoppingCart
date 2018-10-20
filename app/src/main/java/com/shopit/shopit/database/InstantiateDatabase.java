package com.shopit.shopit.database;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 *
 * Created by mohit on 6/1/18.
 */

public class InstantiateDatabase {

    private static ApplicationDatabase applicationDatabase;

    public static ApplicationDatabase getDatabaseInstance(Context context)
    {
        if(applicationDatabase==null){
            applicationDatabase = Room.databaseBuilder(context,ApplicationDatabase.class,"ApplicationDatabase").build();
        }

        return applicationDatabase;
    }
}
