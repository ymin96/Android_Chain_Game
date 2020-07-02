package com.ymin.chaingame.api;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class PapagoTranslate extends BaseTranslate{
    private static final String TAG = "Translate_Papago";

    public PapagoTranslate(){
        Map<String, String> requestHeaders = new HashMap<>();
        String clientId = "8rctGw89EicTuRcJaNsa";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "ZafON0T49p";//애플리케이션 클라이언트 시크릿값";
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        this.requestHeaders = requestHeaders;
        this.apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        this.postParams = "source=ko&target=en&text=";
        this.requestMethod = "POST";
    }

    @Override
    String parseResponse(String response) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(response);
            if(object.get("error") == null) {
                JSONObject message = (JSONObject)object.get("message");
                JSONObject result = (JSONObject)message.get("result");
                String translatedText = (String) result.get("translatedText");
                Log.d(TAG, "parseResponse: "+translatedText);
                return translatedText;
            }
        }catch (ParseException e){
            return "번역 오류가 발생했습니다.";
        }

        return "Error";
    }
}
