package com.ymin.chaingame.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ymin.chaingame.R;
import com.ymin.chaingame.dialog.ProgressDialog;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버튼에 클릭 이벤트 리스너 등록
        ImageButton multi, single, translate;
        multi = (ImageButton) findViewById(R.id.multiplay);
        single = (ImageButton) findViewById(R.id.singleplay);
        multi.setOnClickListener(this);
        single.setOnClickListener(this);
    }

    @Override
    // 버튼 클릭 이벤트 리스터 생성
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.multiplay:
                ProgressDialog dlg = new ProgressDialog(MainActivity.this);
                dlg.show();
                break;
            case R.id.singleplay:
                intent = new Intent(getApplicationContext(), SingleGameActivity.class);
                startActivity(intent);
                break;
            case R.id.translate:
                break;
        }
    }
}