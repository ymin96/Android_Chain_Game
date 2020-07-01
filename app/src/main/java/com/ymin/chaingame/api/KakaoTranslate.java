package com.ymin.chaingame.api;

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

}
