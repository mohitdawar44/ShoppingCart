package com.shopit.shopit.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Async task to insert messages
 * Created by mohit on 7/1/18.
 */

public class InsertEntityAsyncTask extends AsyncTask<Void, Void, Long> {

    private PersistableItem entity;

    private ProgressDialog progressDialog;

    private EntityPersistenceListener entityPersistenceListener;

    private Context context;

    private PersistableItemDao persistableItemDao;

    private static long ALREADY_EXISTS = -100;

    public InsertEntityAsyncTask(final Context context, final PersistableItem entity,
                                 final EntityPersistenceListener entityPersistenceListener,
                                 final PersistableItemDao persistableItemDao) {
        this.entity = entity;
        this.context = context;
        this.entityPersistenceListener = entityPersistenceListener;
        this.persistableItemDao = persistableItemDao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Hold on please!");
        progressDialog.show();
    }

    @Override
    protected Long doInBackground(Void... params) {
        try {
            PersistableItem previousItem =
                    persistableItemDao.getItemByItemIdColorAndSize(entity.itemId,entity.itemColor,entity.itemSize);
            if(previousItem==null){
                return persistableItemDao.insertEntity(entity);
            }
        } catch (Exception exception) {
            Log.e("Inserting to cart",exception.getMessage());
                return null;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Long id) {
        super.onPostExecute(id);
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
            }
        }
        if (id != null) {
            entityPersistenceListener.onEntityPersisted();
        } else {
            entityPersistenceListener.onFailureOccured();
        }
    }
}
