package com.ymin.chaingame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ymin.chaingame.R;
import com.ymin.chaingame.adapter.RecyclerResultViewAdapter;
import com.ymin.chaingame.etc.Action;
import com.ymin.chaingame.etc.Convert;
import com.ymin.chaingame.layout.RecyclerViewLayoutManager;

import java.util.ArrayList;

public class ResultPageActivity extends AppCompatActivity {

    RecyclerView mRecyclerView = null;
    RecyclerResultViewAdapter mAdapter = null;
    ArrayList<Action> actionList = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Intent 로부터 값 전달받음
        Intent intent = getIntent();
        actionList = (ArrayList<Action>) intent.getSerializableExtra("actionList");
        String rank = intent.getStringExtra("rank");

        // 랭킹 표시
        TextView rankTextView = (TextView)findViewById(R.id.rank);
        rankTextView.setText(rank);


        // 리사이클러뷰 세팅
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(
                new RecyclerViewLayoutManager(this,
                        this.getResources().getDisplayMetrics().widthPixels,
                        Convert.dpToPx(this, 300)
                )
        ); // 리사이클러뷰의 자시 아이템들을 수평 정렬 시킨다다

        // 리사이클러뷰에 어뎁터 객체 지정
        mAdapter = new RecyclerResultViewAdapter(actionList);
        mRecyclerView.setAdapter(mAdapter);

        // 메인 페이지 버튼 이벤트 리스너 등록
        Button mainPage = (Button) findViewById(R.id.main_page_button);
        mainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
