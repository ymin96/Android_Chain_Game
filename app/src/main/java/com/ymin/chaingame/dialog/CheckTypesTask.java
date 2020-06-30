package com.ymin.chaingame.dialog;

import android.content.Context;
import android.os.AsyncTask;

import com.ymin.chaingame.client.ActionCreator;
import com.ymin.chaingame.client.MatchingClient;

public class CheckTypesTask extends AsyncTask<Void, Void, Void> {
    MatchingClient client = new MatchingClient(this);
    ActionCreator actionCreator = new ActionCreator();
    MatchingClient matchingClient = new MatchingClient(this);
    ProgressDialog progressDialog;
    Context context;


    public void setContext(Context context){
        this.context = context;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        String uuid = client.startClient();

        return null;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context, matchingClient);
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        super.onPostExecute(aVoid);
    }

}
