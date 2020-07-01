package com.ymin.chaingame.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ymin.chaingame.R;
import com.ymin.chaingame.api.KakaoTranslate;
import com.ymin.chaingame.api.PapagoTranslate;


public class TranslateActivity extends AppCompatActivity implements Button.OnClickListener{
    EditText editQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);


        ImageButton translate = (ImageButton) findViewById(R.id.translate);
        translate.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        editQuery = (EditText) findViewById (R.id.edit_text);
        switch (view.getId()){
            case R.id.translate:
                TextView papago = (TextView)findViewById(R.id.papago);
                PapagoAsyncTask papagoAsyncTask = new PapagoAsyncTask();
                papagoAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,editQuery.getText().toString());
                KakaoAsyncTask kakaoAsyncTask = new KakaoAsyncTask();
                kakaoAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,editQuery.getText().toString());
                break;
        }
    }

    class PapagoAsyncTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... strings) {
            PapagoTranslate papagoTranslate = new PapagoTranslate();
            papagoTranslate.run(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    class KakaoAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            KakaoTranslate kakaoTranslate = new KakaoTranslate();
            kakaoTranslate.run(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
