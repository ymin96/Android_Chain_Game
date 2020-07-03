package com.ymin.chaingame.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ymin.chaingame.activity.MultipleGameActivity;
import com.ymin.chaingame.client.ActionCreator;
import com.ymin.chaingame.client.GameClient;
import com.ymin.chaingame.client.MatchingClient;

public class CheckTypesTask extends AsyncTask<Void, Void, String> {
    MatchingClient matchingClient = new MatchingClient(this);
    ProgressDialog progressDialog;
    ActionCreator actionCreator = new ActionCreator();
    Context context;


    public void setContext(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context, matchingClient);
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {

        String uuid = matchingClient.startClient();

        return uuid;
    }



    @Override
    protected void onPostExecute(String uuid) {
        progressDialog.dismiss();
        // 매칭 서버 연결 해제
        matchingClient.send(actionCreator.matchingConnectBreak());

        Intent intent = new Intent(context, MultipleGameActivity.class);
        intent.putExtra("uuid", uuid);
        context.startActivity(intent);
        super.onPostExecute(uuid);
    }
}
