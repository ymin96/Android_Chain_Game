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
import com.ymin.chaingame.api.GoogleTranslate;
import com.ymin.chaingame.api.KakaoTranslate;
import com.ymin.chaingame.api.PapagoTranslate;

import org.w3c.dom.Text;


public class TranslateActivity extends AppCompatActivity implements Button.OnClickListener{
    EditText editQuery;
    TextView google, papago, kakao;
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
                PapagoAsyncTask papagoAsyncTask = new PapagoAsyncTask();
                papagoAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,editQuery.getText().toString());
                KakaoAsyncTask kakaoAsyncTask = new KakaoAsyncTask();
                kakaoAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,editQuery.getText().toString());
                GoogleAsyncTask googleAsyncTask = new GoogleAsyncTask();
                googleAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, editQuery.getText().toString());
                break;
        }
    }

    class PapagoAsyncTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... strings) {
            PapagoTranslate papagoTranslate = new PapagoTranslate();
            String result = papagoTranslate.run(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            papago = (TextView)findViewById(R.id.papago_text);
            papago.setText(s);
        }
    }

    class KakaoAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            KakaoTranslate kakaoTranslate = new KakaoTranslate();
            String result = kakaoTranslate.run(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            kakao = (TextView)findViewById(R.id.kakao_text);
            kakao.setText(s);
        }
    }

    class GoogleAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            GoogleTranslate googleTranslate = new GoogleTranslate();
            String result = googleTranslate.run(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            google = (TextView) findViewById(R.id.google_text);
            google.setText(s);
        }
    }
}
