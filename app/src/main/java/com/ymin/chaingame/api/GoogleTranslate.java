package com.ymin.chaingame.api;

import android.content.Context;
import android.util.Log;

import com.ymin.chaingame.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GoogleTranslate extends ParentTranslate implements BaseTranslate{
    private static final String TAG = "Translate_Google";
    String apiKey;
    public GoogleTranslate(Context context){
        apiKey = context.getString(R.string.google);
        this.apiURL = "https://translation.googleapis.com/language/translate/v2" ;
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

    @Override
    public void setKo_To_En() {
        this.postParams = "source=ko&target=en&key="+this.apiKey+"&q=";
    }

    @Override
    public void setEn_to_Ko() {
        this.postParams = "source=en&target=ko&key="+this.apiKey+"&q=";
    }
}
