package com.ymin.chaingame.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ymin.chaingame.R;
import com.ymin.chaingame.adapter.RecyclerActionViewAdapter;
import com.ymin.chaingame.client.ActionCreator;
import com.ymin.chaingame.client.GameClient;
import com.ymin.chaingame.etc.Action;
import com.ymin.chaingame.etc.Convert;
import com.ymin.chaingame.etc.Scrawler.NaverScrawler;
import com.ymin.chaingame.layout.RecyclerViewLayoutManager;


import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MultipleGameActivity extends AppCompatActivity implements Button.OnClickListener{
    private static final String TAG = "MultipleGameActivity";
    public RecyclerView mRecyclerView = null;
    public RecyclerActionViewAdapter mAdapter = null;
    public ArrayList<Action> actionList = new ArrayList<>();
    GameClient gameClient = new GameClient();
    ActionCreator actionCreator = new ActionCreator();
    SearchTask searchTask = new SearchTask();
    GameClient.CounterAsyncTask prev;
    String uuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_game);

        // 인텐트로부터 데이터를 받아온다.
        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");
        gameClient.setActivity(this);
        gameClient.startClient();
        gameClient.send(actionCreator.connectRoom(uuid));

        // 리사이클러뷰 세팅
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(
                new RecyclerViewLayoutManager(this,
                    this.getResources().getDisplayMetrics().widthPixels,
                    Convert.dpToPx(this, 335)
                )
        );

        // 리사이클러뷰에 어뎁터 객체 지정
        mAdapter = new RecyclerActionViewAdapter(actionList);
        mRecyclerView.setAdapter(mAdapter);


        // 버튼 클릭 이벤트 등록
        ImageButton submit = (ImageButton) findViewById(R.id.action_submit);
        submit.setOnClickListener(this);
    }


    @Override
    // 버튼 클릭 이벤트 리스너
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_submit:
                // searchTask 실행
                searchTask = new SearchTask();
                searchTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
            case R.id.result_page_button:
                Intent intent = new Intent(getApplicationContext(), ResultPageActivity.class);
                intent.putExtra("actionList", actionList);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gameClient.stopClient();
        finish();
    }


    // send 버튼 눌렀을 때 실행할 AsyncTask
    public class SearchTask extends AsyncTask<Object,Void,Action>{
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
            // 서버에 action 을 전달해준다.
            JSONObject jsonAction = Action.toJsonObject(action);
            gameClient.send(actionCreator.sendAction(jsonAction, uuid));

        }

        boolean compare(String a, String b){
            String pre = a.toUpperCase();
            String post = b.toUpperCase();
            return pre.equals(post);
        }
    }


}
