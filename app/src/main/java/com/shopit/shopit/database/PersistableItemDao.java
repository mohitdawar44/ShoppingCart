package com.shopit.shopit.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 *
 * Created by mohit on 20/1/18.
 */

@Dao
public interface PersistableItemDao {


    @Insert( onConflict = OnConflictStrategy.FAIL)
    Long insertEntity(PersistableItem entity);

    @Update
    void updateEntity(PersistableItem entity);

    @Delete
    void deleteEntity(PersistableItem entity);

    @Query("delete from persistableItem")
    void deleteAllItems();

    @Query("Select * from persistableItem")
    List<PersistableItem> getAllItems();

    @Query("Select * from persistableItem WHERE itemId = :itemId "+
            "AND itemColor = :itemColor " +"AND itemSize = :itemSize")
    PersistableItem getItemByItemIdColorAndSize(String itemId,String itemColor,String itemSize);

   }
