package com.shopit.shopit.database;


import java.util.List;

/**
 * Created by mohit on 7/1/18.
 * Interface to listen for data availability
 */

public interface DataAvailabilityListener {

    void onSingleResultAvailable(PersistableItem data);

    void onDataSetAvailable(List<PersistableItem> data);
}
