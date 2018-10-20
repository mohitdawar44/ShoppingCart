package com.shopit.shopit.database;

import android.content.Context;
import android.os.AsyncTask;


import java.util.List;

/**
 *
 * Created by mohit on 7/1/18.
 *
 * Async task to access previous messages
 */

public class GetPersistableItemsAsyncTask extends AsyncTask<Void,Void,List<PersistableItem>> {

    private final Context context;

    private final DataAvailabilityListener dataAvailabilityListener;


    public GetPersistableItemsAsyncTask(Context context, DataAvailabilityListener dataAvailabilityListener){
        this.context = context;
        this.dataAvailabilityListener = dataAvailabilityListener;
    }

    @Override
    protected List<PersistableItem> doInBackground(Void... params) {
        return InstantiateDatabase.getDatabaseInstance
                (context.getApplicationContext()).itemsDao().
                getAllItems();
    }

    @Override
    protected void onPostExecute(List<PersistableItem> items) {
        super.onPostExecute(items);

        if(items != null){
            dataAvailabilityListener.onDataSetAvailable(items);
        }
    }
}
