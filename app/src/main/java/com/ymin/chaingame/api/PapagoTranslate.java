package com.ymin.chaingame.api;

import android.content.Context;
import android.util.Log;

import com.ymin.chaingame.R;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class PapagoTranslate extends ParentTranslate implements BaseTranslate{
    private static final String TAG = "Translate_Papago";

    public PapagoTranslate(Context context){
        Map<String, String> requestHeaders = new HashMap<>();
        String clientId = context.getString(R.string.papago_clientID);//애플리케이션 클라이언트 아이디값";
        String clientSecret = context.getString(R.string.papago_clientSecret);//애플리케이션 클라이언트 시크릿값";
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

    @Override
    public void setKo_To_En() {
        this.postParams = "source=ko&target=en&text=";
    }

    @Override
    public void setEn_to_Ko() {
        this.postParams = "source=en&target=ko&text=";
    }
}
