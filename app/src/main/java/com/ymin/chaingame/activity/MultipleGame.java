package com.ymin.chaingame.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ymin.chaingame.R;
import com.ymin.chaingame.adapter.RecyclerActionViewAdapter;
import com.ymin.chaingame.etc.Action;

import java.util.ArrayList;

public class MultipleGame extends AppCompatActivity {
    RecyclerView mRecyclerView = null;
    RecyclerActionViewAdapter mAdapter = null;
    ArrayList<Action> actions = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_game);

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager((this)));

        // 리사이클러뷰에 어뎁터 객체 지정
        mAdapter = new RecyclerActionViewAdapter(actions);
        mRecyclerView.setAdapter(mAdapter);

        addItem();
        mAdapter.notifyDataSetChanged();
    }

    public void addItem(){
        Action action;

        for(int i=0 ;i< 10; i++){
            action = new Action();
            action.setPreFix("S");
            action.setContent("Content"+i);
            action.setPostFix("E");
            actions.add(action);
        }
    }
}
