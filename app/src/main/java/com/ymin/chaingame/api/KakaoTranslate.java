package com.ymin.chaingame.api;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class KakaoTranslate extends BaseTranslate {
    private static final String TAG = "Translate_Kakao";

    public KakaoTranslate(){
        Map<String, String> requestHeaders = new HashMap<>();
        String appKey = "KakaoAK 81d9ba0003608c4395a95639343e8c11";// 애플리케이션 네이티브 앱키
        requestHeaders.put("Authorization", appKey);
        this.requestHeaders = requestHeaders;
        this.apiURL = "https://kapi.kakao.com/v1/translation/translate";
        this.postParams = "src_lang=kr&target_lang=en&query=";
        this.requestMethod = "GET";
    }

    @Override
    String parseResponse(String response) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(response);
            if(object.get("error") == null) {
                String result = "";
                JSONArray translated_text = (JSONArray) object.get("translated_text");
                JSONArray temp = (JSONArray)translated_text.get(0);
                for(int i=0 ; i< temp.size(); i++){
                    result += (String)temp.get(i);
                }
                Log.d(TAG, "parseResponse: "+result);
                return result;
            }
        }catch (ParseException e){
            return "번역 오류가 발생했습니다.";
        }

        return "Error";
    }
}
