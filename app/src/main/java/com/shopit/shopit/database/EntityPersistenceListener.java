package com.shopit.shopit.database;

/**
 * Created by mohit on 8/1/18.
 * Interface that listens to commit
 */

public interface EntityPersistenceListener {

    void onEntityPersisted();

    void onFailureOccured();
}
