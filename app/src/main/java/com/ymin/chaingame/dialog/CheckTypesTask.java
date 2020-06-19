package com.ymin.chaingame.dialog;

import android.os.AsyncTask;

public class CheckTypesTask extends AsyncTask<Void, Void, Void> {

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    ProgressDialog progressDialog;


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            for(int i=0;i<5;i++){
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
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
