package com.ymin.chaingame.activity;

import android.content.Intent;
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
import com.ymin.chaingame.api.BaseTranslate;
import com.ymin.chaingame.api.GoogleTranslate;
import com.ymin.chaingame.api.KakaoTranslate;
import com.ymin.chaingame.api.PapagoTranslate;

import org.w3c.dom.Text;


public class TranslateActivity extends AppCompatActivity implements Button.OnClickListener{
    EditText editQuery;
    TextView google, papago, kakao;
    ImageButton kr_to_en;
    ImageButton en_to_kr;
    PapagoTranslate papagoTranslate;
    KakaoTranslate kakaoTranslate;
    GoogleTranslate googleTranslate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        // 번역기 등록
        papagoTranslate = new PapagoTranslate(this);
        kakaoTranslate = new KakaoTranslate(this);
        googleTranslate = new GoogleTranslate(this);

        // 버튼 이벤트 리스너 등록
        ImageButton translate = (ImageButton) findViewById(R.id.translate);
        Button mainPage = (Button) findViewById(R.id.main_page_button);
        mainPage.setOnClickListener(this);
        kr_to_en = (ImageButton)findViewById(R.id.kr_to_en);
        en_to_kr = (ImageButton)findViewById(R.id.en_to_kr);
        translate.setOnClickListener(this);
        kr_to_en.setOnClickListener(this);
        en_to_kr.setOnClickListener(this);

        // 텍스트뷰 등록
        google = findViewById(R.id.google_text);
        papago = findViewById(R.id.papago_text);
        kakao = findViewById(R.id.kakao_text);

    }


    @Override
    public void onClick(View view) {
        editQuery = (EditText) findViewById (R.id.edit_text);
        String query = editQuery.getText().toString();
        switch (view.getId()){
            case R.id.translate: {
                TranslateAsyncTask translateAsyncTask;
                // 구글 번역 실행
                translateAsyncTask = new TranslateAsyncTask(googleTranslate, google);
                translateAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
                // 파파고 번역 실행
                translateAsyncTask = new TranslateAsyncTask(papagoTranslate, papago);
                translateAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
                // 카카오 번역 실행
                translateAsyncTask = new TranslateAsyncTask(kakaoTranslate, kakao);
                translateAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
                break;
            }
            case R.id.main_page_button:{
                finish();
                break;
            }
            case R.id.kr_to_en:{
                kr_to_en.setVisibility(View.GONE);
                en_to_kr.setVisibility(View.VISIBLE);
                googleTranslate.setEn_to_Ko();
                papagoTranslate.setEn_to_Ko();
                kakaoTranslate.setEn_to_Ko();
                break;
            }
            case R.id.en_to_kr:{
                en_to_kr.setVisibility(View.GONE);
                kr_to_en.setVisibility(View.VISIBLE);
                googleTranslate.setKo_To_En();
                papagoTranslate.setKo_To_En();
                kakaoTranslate.setKo_To_En();
                break;
            }
        }
    }

    class TranslateAsyncTask extends AsyncTask<String, Void, String>{
        BaseTranslate baseTranslate;
        TextView textView;

        public TranslateAsyncTask(BaseTranslate baseTranslate, TextView textView){
            this.baseTranslate = baseTranslate;
            this.textView = textView;
        }

        @Override
        protected String doInBackground(String... strings) {
            return baseTranslate.run(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }
    }
}
