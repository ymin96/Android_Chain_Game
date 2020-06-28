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
import androidx.recyclerview.widget.RecyclerView;

import com.ymin.chaingame.R;
import com.ymin.chaingame.adapter.RecyclerActionViewAdapter;
import com.ymin.chaingame.etc.Action;
import com.ymin.chaingame.etc.Convert;
import com.ymin.chaingame.etc.Scrawler.NaverScrawler;
import com.ymin.chaingame.layout.RecyclerViewLayoutManager;

import java.util.ArrayList;

public class SingleGameActivity extends AppCompatActivity implements Button.OnClickListener{
    final static private String TAG = "SingleGameActivity";
    RecyclerView mRecyclerView = null;
    RecyclerActionViewAdapter mAdapter = null;
    ArrayList<Action> actionList = new ArrayList<>();
    SearchTask searchTask = new SearchTask();
    CounterAsyncTask prev;

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
        /*mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mRecyclerView.scrollToPosition(actionList.size() - 1);
            }
        }); // 화면이 Resize 되면 리사이클러뷰의 스크롤를 가장 아래로 바꿈 */

        // 리사이클러뷰에 어뎁터 객체 지정
        mAdapter = new RecyclerActionViewAdapter(actionList);
        mRecyclerView.setAdapter(mAdapter);

        // 버튼 클릭 이벤트 등록
        ImageButton submit = (ImageButton) findViewById(R.id.action_submit);
        Button resultPage = (Button) findViewById(R.id.result_page_button);
        submit.setOnClickListener(this);
        resultPage.setOnClickListener(this);
    }

    // 버튼 클릭 이벤트
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.action_submit:
                // searchTask를 실행하기 전에 이전 searchTask가 만든 카운터 스레드를 가져온다.
                prev = searchTask.getCounterAsyncTask();
                // searchTask 실행
                searchTask = new SearchTask();
                searchTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                // 이전 카운터 쓰레드가 끝나기 전에 버튼 클릭을 했다면 쓰레드를 종료시켜준다.
                if(prev != null && prev.runState)
                    prev.cancel(true);
                break;
            case R.id.result_page_button:
                Intent intent = new Intent(getApplicationContext(), ResultPageActivity.class);
                intent.putExtra("actionList", actionList);
                startActivity(intent);
                finish();
                break;
        }
    }

    // 뒤로가기 버튼 클릭 리스너
    @Override
    public void onBackPressed() {
        // 실행중인 스레드 종료
        if(searchTask.getCounterAsyncTask() != null)
            searchTask.getCounterAsyncTask().cancel(true);
        super.onBackPressed();
    }

    // send 버튼 눌렀을 때 실행할 AsyncTask
    class SearchTask extends AsyncTask<Object,Void,Action>{
        String content;
        CounterAsyncTask counterAsyncTask;

        public CounterAsyncTask getCounterAsyncTask() {
            return counterAsyncTask;
        }

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

                // 카운트 다운 쓰레드를 실행한다.
                counterAsyncTask = new CounterAsyncTask();
                counterAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
            // 검색 실패 시 실행할 작업
            else{
                // inputBar 을 사라지게 하고 Result Page 버튼을 보이게 한다.
                LinearLayout inputBar = (LinearLayout) findViewById(R.id.input_bar);
                inputBar.setVisibility(View.GONE);
                Button resultPage = (Button) findViewById(R.id.result_page_button);
                resultPage.setVisibility(View.VISIBLE);
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
            return pre.equals(post);
        }
    }

    // 카운트 다운 체크하는 AsyncTask
    class CounterAsyncTask extends AsyncTask<Void, Integer, Void>{
        public boolean runState = false;    // 현재 쓰레드가 실행중인지 체크해주는 변수

        @Override
        protected Void doInBackground(Void... voids) {
            int count = 10;
            runState = true;
            try{
                // 1초 간격으로 카운트
                while (count > 0){
                    if(isCancelled())
                        return null;
                    publishProgress(count);
                    Log.d(TAG, count+"초 남았습니다.");
                    Thread.sleep(1000);
                    count--;
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // 화면에 카운트 숫자를 갱신해준다.
            int last = actionList.size() - 1;
            Action temp = actionList.get(last);
            temp.setContent(Integer.toString(values[0]));
            actionList.set(last, temp);
            mAdapter.notifyDataSetChanged();
        }

        // 카운트 다운이 다 끝나면 할 작업
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            runState = false;
            // inputBar 을 사라지게 하고 Result Page 버튼을 보이게 한다.
            LinearLayout inputBar = (LinearLayout) findViewById(R.id.input_bar);
            inputBar.setVisibility(View.GONE);
            Button resultPage = (Button) findViewById(R.id.result_page_button);
            resultPage.setVisibility(View.VISIBLE);
        }
    }
}
