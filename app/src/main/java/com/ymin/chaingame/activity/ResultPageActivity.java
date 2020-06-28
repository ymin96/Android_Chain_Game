package com.ymin.chaingame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

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
    }


}
