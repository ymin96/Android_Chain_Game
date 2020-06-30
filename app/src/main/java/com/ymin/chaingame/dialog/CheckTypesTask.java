package com.ymin.chaingame.dialog;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ymin.chaingame.client.ActionCreator;
import com.ymin.chaingame.client.MatchingClient;

public class CheckTypesTask extends AsyncTask<Void, Void, String> {
    ActionCreator actionCreator = new ActionCreator();
    MatchingClient matchingClient = new MatchingClient(this);
    ProgressDialog progressDialog;
    Context context;


    public void setContext(Context context){
        this.context = context;
    }


    @Override
    protected String doInBackground(Void... voids) {

        String uuid = matchingClient.startClient();

        return uuid;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context, matchingClient);
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        super.onPostExecute(s);
    }
}
