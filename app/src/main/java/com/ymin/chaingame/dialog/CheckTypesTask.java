package com.ymin.chaingame.dialog;

import android.os.AsyncTask;

import com.ymin.chaingame.matching.MatchingClient;

public class CheckTypesTask extends AsyncTask<Void, Void, Void> {

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    ProgressDialog progressDialog;


    @Override
    protected Void doInBackground(Void... voids) {
        MatchingClient client = new MatchingClient();
        client.startClient();

        return null;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        super.onPostExecute(aVoid);
    }
}
