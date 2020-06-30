package com.ymin.chaingame.client;

import org.json.simple.JSONObject;

public class ActionCreator {

    public String connectBreak(){
        JSONObject result = new JSONObject();
        result.put("type","CONNECT_BREAK");
        return result.toJSONString();
    }
}
