package com.ymin.chaingame.api;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GoogleTranslate extends BaseTranslate {
    private static final String TAG = "Translate_Google";

    public GoogleTranslate(){
        String apiKey = "AIzaSyDY-TZ625qhZ40Ge3nmJ_jc2NYBdwV8F7w";
        this.apiURL = "https://translation.googleapis.com/language/translate/v2";
        this.requestMethod = "POST";
        this.postParams = "source=ko&target=en&key="+apiKey+"&q=";
    }


    @Override
    String parseResponse(String response)  {
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(response);
            if(object.get("error") == null) {
                JSONObject data = (JSONObject)object.get("data");
                JSONArray translations = (JSONArray)data.get("translations");
                JSONObject translatedText = (JSONObject) translations.get(0);
                String result = (String)translatedText.get("translatedText");
                Log.d(TAG, "parseResponse: "+result);
                return result;
            }
        }catch (ParseException e){
            return "번역 오류가 발생했습니다.";
        }

        return "Error";
    }
}
