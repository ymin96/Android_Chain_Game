package com.ymin.chaingame.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ymin.chaingame.R;
import com.ymin.chaingame.adapter.RecyclerActionViewAdapter;
import com.ymin.chaingame.etc.Action;
import com.ymin.chaingame.etc.Convert;
import com.ymin.chaingame.layout.RecyclerViewLayoutManager;

import java.util.ArrayList;
import java.util.Objects;

public class MultipleGameActivity extends AppCompatActivity implements Button.OnClickListener{
    RecyclerView mRecyclerView = null;
    RecyclerActionViewAdapter mAdapter = null;
    ArrayList<Action> actionList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_game);

        // 리사이클러뷰 세팅
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(
                new RecyclerViewLayoutManager(this,
                    this.getResources().getDisplayMetrics().widthPixels,
                    Convert.dpToPx(this, 335)
                )
        );
        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mRecyclerView.scrollToPosition(actionList.size() - 1);
            }
        }); // 화면이 Resize 되면 리사이클러뷰의 스크롤를 가장 아래로 바꿈

        // 리사이클러뷰에 어뎁터 객체 지정
        mAdapter = new RecyclerActionViewAdapter(actionList);
        mRecyclerView.setAdapter(mAdapter);

        addItem();
        mAdapter.notifyDataSetChanged();

        // 버튼 클릭 이벤트 등록
        ImageButton submit = (ImageButton) findViewById(R.id.action_submit);
        submit.setOnClickListener(this);
    }

    public void addItem() {
        Action action;

        for (int i = 0; i < 10; i++) {
            action = new Action();
            action.setPreFix("S");
            action.setContent("Content" + i);
            action.setPostFix("E");
            actionList.add(action);
        }

        action = new Action();
        action.setType(1);
        action.setPreFix("R");
        action.setContent("rest");
        action.setPostFix("T");
        action.setSubTitle("Rest");
        action.setSubstance("1.(어떤 것의) 나머지\n2.나머지 (사람들것들), 다른 사람들\n3.쉬다, 휴식을 취하다, 자다; (몸의 피로한 부분을 편하게) 쉬다");
        actionList.add(action);
    }

    @Override
    // 버튼 클릭 이벤트 리스너
    public void onClick(View view) {
        if (view.getId() == R.id.action_submit) {
            EditText editText = (EditText) findViewById(R.id.action_input);
            String content = editText.getText().toString();
            Action temp = new Action();
            temp.setType(1).setPreFix("T").setContent(content).setPostFix("T").setSubTitle("임시 데이터").setSubstance("임시 내용");
            actionList.remove(actionList.size() - 1);
            actionList.add(temp);
            mAdapter.notifyDataSetChanged();
        }
    }
}
