
package com.shopit.shopit.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


/**
 * Created by mohit on 7/1/18.
 * <p>
 * Async task to clear previous messages
 */

public class DeleteEntityAsyncTask extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progressDialog;

    private Context context;

    private PersistableItemDao persistableItemDao;

    public DeleteEntityAsyncTask(final Context context,
                                 final PersistableItemDao persistableItemDao) {
        this.context = context;
        this.persistableItemDao = persistableItemDao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Clearing Cart");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        persistableItemDao.deleteAllItems();
        return null;
    }

    @Override
    protected void onPostExecute(Void voidResult) {
        super.onPostExecute(voidResult);
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
            }
        }
        Toast.makeText(context, "Cart cleared !", Toast.LENGTH_LONG).show();
    }
}
