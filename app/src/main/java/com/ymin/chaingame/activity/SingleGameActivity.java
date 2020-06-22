package com.ymin.chaingame.activity;

import android.content.Context;
import android.os.AsyncTask;
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
    ArrayList<Action> actionList = new ArrayList<>();

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

    // 버튼 클릭 이벤트
    @Override
    public void onClick(View view) {
        SearchTask searchTask = new SearchTask();
        if (view.getId() == R.id.action_submit) {
            searchTask.execute();
        }
    }

    // send 버튼 눌렀을 때 실행할 AsyncTask
    class SearchTask extends AsyncTask<Object,Void,Action>{
        String content;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Edittext 와 Button 을 비활성화 한다.
            EditText editText = (EditText) findViewById(R.id.action_input);
            content = editText.getText().toString();
            editText.setEnabled(false);
            editText.setText(null);
            editText.setHint("현재는 입력할 수 없습니다.");
        }

        @Override
        protected Action doInBackground(Object[] objects) {
            NaverScrawler scrawler = new NaverScrawler();
            Action action = null;
            int listSize = actionList.size();
            // 이전에 입력된 단어가 있을 때
            if(listSize > 0){
                Action lastAction = actionList.get(listSize - 1);
                // 이전 단어의 뒷글자와 입력한 단어의 앞글자가 같을 때
                if(compare(lastAction.getPreFix(), content.substring(0,1))){
                    // 데이터를 크롤링 하여 받아온다
                    action  = Action.parseJSON(scrawler.search(content));
                }
                // 이전 단어의 뒷글자와 입력한 단어의 앞글자가 댜를 때
                else{
                    // 실패한 데이터를 만들어 준다.
                    action = new Action()
                            .setContent(content)
                            .setType(Action.EXTEND)
                            .setResultType(Action.MATCH_FAIL)
                            .setSubTitle("매칭 실패")
                            .setSubstance("이전 단어의 뒷글자와 맞지 않습니다.")
                            .setPreFix(lastAction.getPreFix());
                }
            }
            // 처음 입력할 때
            else{
                action = Action.parseJSON(scrawler.search(content));
            }
            return action;
        }

        @Override
        protected void onPostExecute(Action action) {
            super.onPostExecute(action);
            // 가장 뒤에 있는 NORMAL 타입 액션을 지워준다.
            if (actionList.size() > 0)
                actionList.remove(actionList.size()-1);
            // doInBackground 에서 전달받은 액션을 추가
            actionList.add(action);

            // 검색 성공 시 실행할 작업
            if(action.getResultType() == Action.SUCCESS){
                Action temp = new Action().setType(Action.NORMAL).setPreFix(action.getPostFix());
                actionList.add(temp);
            }
            // 검색 실패 시 실행할 작업
            else{

            }
            // 화면 갱신
            mAdapter.notifyDataSetChanged();

            // EditText 를 다시 선택할 수 있게 만들어준다.
            EditText editText = (EditText) findViewById(R.id.action_input);
            editText.setEnabled(true);
            editText.setHint(null);

            // 리사이클러뷰의 스크롤을 가장 아래로 내려준다.
            mRecyclerView.scrollToPosition(actionList.size() - 1);
        }

        boolean compare(String a, String b){
            String pre = a.toUpperCase();
            String post = b.toUpperCase();
            if(pre.equals(post))
                return true;
            else
                return false;
        }
    }
}
