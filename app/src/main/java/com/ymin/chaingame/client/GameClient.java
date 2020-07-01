package com.ymin.chaingame.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ymin.chaingame.R;
import com.ymin.chaingame.activity.MultipleGameActivity;
import com.ymin.chaingame.etc.Action;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class GameClient implements Serializable {
    private static final String TAG = "GameClient";
    SocketChannel socketChannel;
    Activity activity = null;
    public CounterAsyncTask counterAsyncTask = null;

    public CounterAsyncTask getCounterAsyncTask() {
        return counterAsyncTask;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void startClient(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    socketChannel = SocketChannel.open();
                    socketChannel.configureBlocking(true);
                    socketChannel.connect(new InetSocketAddress("10.0.2.2", 5003));
                } catch (IOException e) {
                    if(socketChannel.isOpen()){stopClient();}
                }
                receive();
            }
        };
        thread.start();

    }

    public void stopClient() {
        try{
            if(socketChannel != null && socketChannel.isOpen()) {
                socketChannel.close();
            }
        }catch (IOException e){}
    }

    void receive(){
        while(true){
            try{
                ByteBuffer byteBuffer = ByteBuffer.allocate(1000);

                // 서버가 비정상적으로 종료했을 경우 IOException 발생
                int readByteCount = socketChannel.read(byteBuffer);

                // 서버가 정상적으로 Socket 의 close()를 호출했을 경우
                if(readByteCount == -1) {
                    throw new IOException();
                }

                byteBuffer.flip();
                Charset charset = Charset.forName("UTF-8");
                String data = charset.decode(byteBuffer).toString();
                Log.d(TAG, "receive: "+data);
                // 데이터를 받아 리듀서 함수에서 처리한다.
                JSONParser jsonParser = new JSONParser();
                JSONObject result = (JSONObject)jsonParser.parse(data);
                reducer(result);

            } catch (IOException | ParseException e) {
                stopClient();
            }
        }
    }

    public void send(final String data){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    Log.d(TAG, "send: "+data);
                    Charset charset = Charset.forName("UTF-8");
                    ByteBuffer byteBuffer = charset.encode(data);
                    socketChannel.write(byteBuffer);
                }catch (Exception e){
                    stopClient();
                }
            }
        };
        thread.start();
    }

    void reducer(JSONObject result){
        String type = (String)result.get("type");
        JSONObject payload = (JSONObject) result.get("payload");
        switch (type){
            // 준비 완료 신호
            case "READY_STATUS_FINISH": {
                boolean turn = (boolean) payload.get("turn");
                // 내 차례라면 EditText 를 선택할 수 있게 만들어준다.
                if (turn) {
                    this.activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            EditText editText = (EditText) activity.findViewById(R.id.action_input);
                            editText.setEnabled(true);
                            editText.setHint(null);
                        }
                    });
                }
                break;
            }
            // action 데이터와 turn 정보를 얻어 알맞게 화면을 전환시켜준다.
            case "RECEIVE_ACTION":{
                final boolean turn = (boolean) payload.get("turn");
                final Action action = Action.JsonToAction((JSONObject)payload.get("action"));
                if(counterAsyncTask != null && counterAsyncTask.runState)
                    counterAsyncTask.cancel(true);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 가장 뒤에 있는 NORMAL 타입 액션을 지워준다.
                        if (((MultipleGameActivity)activity).actionList.size() > 0)
                            ((MultipleGameActivity)activity).actionList.remove(((MultipleGameActivity)activity).actionList.size()-1);
                        // doInBackground 에서 전달받은 액션을 추가
                        ((MultipleGameActivity)activity).actionList.add(action);

                        // 검색 성공 시 실행할 작업
                        if(action.getResultType() == Action.SUCCESS){
                            Action temp = new Action().setType(Action.NORMAL).setPreFix(action.getPostFix());
                            ((MultipleGameActivity) activity).actionList.add(temp);

                            // 카운트 다운 쓰레드를 실행한다.
                            counterAsyncTask = new CounterAsyncTask();
                            counterAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                        // 검색 실패 시 실행할 작업
                        else{
                            // inputBar 을 사라지게 하고 Result Page 버튼을 보이게 한다.
                            LinearLayout inputBar = (LinearLayout) activity.findViewById(R.id.input_bar);
                            inputBar.setVisibility(View.GONE);
                            Button resultPage = (Button) activity.findViewById(R.id.result_page_button);
                            resultPage.setVisibility(View.VISIBLE);
                        }
                        // 화면 갱신
                        ((MultipleGameActivity)activity).mAdapter.notifyDataSetChanged();

                        // 자신의 차례라면
                        if(turn){
                            // EditText 를 다시 선택할 수 있게 만들어준다.
                            EditText editText = (EditText) activity.findViewById(R.id.action_input);
                            editText.setEnabled(true);
                            editText.setHint(null);
                        }


                        // 리사이클러뷰의 스크롤을 가장 아래로 내려준다.
                        ((MultipleGameActivity)activity).mRecyclerView.scrollToPosition(((MultipleGameActivity)activity).actionList.size() - 1);
                    }
                });
                break;
            }
        }
    }


    // 카운트 다운 체크하는 AsyncTask
    public class CounterAsyncTask extends AsyncTask<Void, Integer, Void> {
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
            int last = ((MultipleGameActivity)activity).actionList.size() - 1;
            Action temp = ((MultipleGameActivity)activity).actionList.get(last);
            temp.setContent(Integer.toString(values[0]));
            ((MultipleGameActivity)activity).actionList.set(last, temp);
            ((MultipleGameActivity)activity).mAdapter.notifyDataSetChanged();
        }

        // 카운트 다운이 다 끝나면 할 작업
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            runState = false;
            /* inputBar 을 사라지게 하고 Result Page 버튼을 보이게 한다.
            LinearLayout inputBar = (LinearLayout) activity.findViewById(R.id.input_bar);
            inputBar.setVisibility(View.GONE);
            Button resultPage = (Button) activity.findViewById(R.id.result_page_button);
            resultPage.setVisibility(View.VISIBLE);*/
        }
    }
}
