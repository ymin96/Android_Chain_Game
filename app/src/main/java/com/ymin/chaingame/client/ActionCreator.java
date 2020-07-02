package com.ymin.chaingame.client;

import org.json.simple.JSONObject;

public class ActionCreator {

    public String connectRoom(String uuid){
        JSONObject result = new JSONObject();
        result.put("type", "CONNECT_ROOM");
        JSONObject payload = new JSONObject();
        payload.put("uuid", uuid);
        result.put("payload", payload);
        return result.toJSONString();
    }

    public String connectClose(String uuid){
        JSONObject result = new JSONObject();
        result.put("type","CONNECT_CLOSE");
        JSONObject payload = new JSONObject();
        payload.put("uuid", uuid);
        result.put("payload", payload);
        return result.toJSONString();
    }

    public String sendAction(JSONObject action, String uuid){
        JSONObject result = new JSONObject();
        result.put("type", "SEND_ACTION");
        JSONObject payload = new JSONObject();
        payload.put("action", action);
        payload.put("uuid", uuid);
        result.put("payload", payload);
        return result.toJSONString();
    }

}
