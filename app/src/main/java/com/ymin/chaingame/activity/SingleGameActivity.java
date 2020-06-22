package com.ymin.chaingame.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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
import com.ymin.chaingame.etc.Scrawler.NaverScrawler;
import com.ymin.chaingame.layout.RecyclerViewLayoutManager;

import java.util.ArrayList;

public class SingleGameActivity extends AppCompatActivity implements Button.OnClickListener{

    RecyclerView mRecyclerView = null;
    RecyclerActionViewAdapter mAdapter = null;
    static ArrayList<Action> actionList = new ArrayList<>();
    private static Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game);

        // 리사이클러뷰 세팅
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(
                new RecyclerViewLayoutManager(this,
                        this.getResources().getDisplayMetrics().widthPixels,
                        Convert.dpToPx(this, 335)
                )
        ); // 리사이클러뷰의 자식 아이템들을 수평 정렬 시킴
        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mRecyclerView.scrollToPosition(actionList.size() - 1);
            }
        }); // 화면이 Resize 되면 리사이클러뷰의 스크롤를 가장 아래로 바꿈

        // 리사이클러뷰에 어뎁터 객체 지정
        mAdapter = new RecyclerActionViewAdapter(actionList);
        mRecyclerView.setAdapter(mAdapter);

        // 버튼 클릭 이벤트 등록
        ImageButton submit = (ImageButton) findViewById(R.id.action_submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.action_submit) {
            EditText editText = (EditText) findViewById(R.id.action_input);
            String content = editText.getText().toString().toUpperCase();
            editText.setEnabled(false);
            editText.setText(null);
            editText.setHint("현재는 입력할 수 없습니다.");

            NaverScrawler scrawler = new NaverScrawler();
            Action temp = Action.parseJSON(scrawler.search(content));

            // 최초 입력시에 에러 방지
            if(actionList.size() > 0)
                actionList.remove(actionList.size() - 1);

            actionList.add(temp);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(actionList.size() - 1);
        }
    }

    class CompareThread implements Runnable{
        String content;

        public CompareThread(String content) {
            this.content = content;
        }

        @Override
        public void run() {
            int listSize = actionList.size();
            if(listSize > 0){
                Action lastAction = actionList.get(listSize - 1);
                // 이전 단어의 뒷글자와 입력한 단어의 앞글자 비교
                if(compare(lastAction.getPreFix(), content.substring(0,1))){
                    NaverScrawler scrawler = new NaverScrawler();
                    scrawler.search(content);
                }
                else{

                }
            }

        }

        boolean compare(String a, String b){
            if(a.equals(b))
                return true;
            else
                return false;
        }
    }
}
