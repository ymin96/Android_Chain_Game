package com.ymin.chaingame.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ymin.chaingame.R;
import com.ymin.chaingame.dialog.ProgressDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClick(View v){
        /*CheckTypesTask task = new CheckTypesTask();
        task.setProgressDialog(new ProgressDialog(MainActivity.this));
        task.execute();*/
        ProgressDialog dlg = new ProgressDialog(MainActivity.this);
        dlg.show();
    }
}