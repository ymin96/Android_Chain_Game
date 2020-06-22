package com.ymin.chaingame.etc;


import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Action {
    // 아이템 타입을 구분하기 위한  변수
    public static final int NORMAL = 0;
    public static final int EXTEND = 1;
    private int type;

    // 결과 상태를 구분하기 위한 변수
    public static final int SUCCESS = 10;
    public static final int MATCH_FAIL = 11;
    public static final int TIME_OUT = 12;
    public static final int SEARCH_FAIL = 13;
    private int resultType;

    public Action() {
        this.type = NORMAL;  // 기본 축소 타입
    }

    private String preFix, postFix, content, subTitle, substance;

    public String getPreFix() {
        return preFix;
    }

    public Action setPreFix(String preFix) {
        this.preFix = preFix;
        return this;
    }

    public String getPostFix() {
        return postFix;
    }

    public Action setPostFix(String postFix) {
        this.postFix = postFix;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Action setContent(String content) {
        this.content = content;
        return this;
    }

    public int getType() {
        return type;
    }

    public Action setType(int type) {
        this.type = type;
        return this;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public Action setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public String getSubstance() {
        return substance;
    }

    public Action setSubstance(String substance) {
        this.substance = substance;
        return this;
    }

    public int getResultType() {
        return resultType;
    }

    public Action setResultType(int resultType) {
        this.resultType = resultType;
        return this;
    }

    public static Action parseJSON(String jsonData) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject object = (JSONObject) jsonParser.parse(jsonData);
            String prefix, postfix, content, subTitle, subStance = "";
            int resultType;

            content = (String) object.get("CONTENT");
            prefix =(String) object.get("PREFIX");
            postfix =(String) object.get("POSTFIX");
            subTitle = (String)object.get("SUB_TITLE");
            resultType = Integer.parseInt((String)object.get("RETURN_TYPE"));

            if (resultType == SUCCESS) {
                JSONArray jsonArray = (JSONArray) object.get("SUBSTANCE");
                for (int i = 1; i <= jsonArray.size(); i++) {
                    subStance += i + ". " + jsonArray.get(i) + "\n";
                }
            } else {
                subStance = (String) object.get("SUBSTANCE");
            }

            return new Action()
                    .setPreFix(prefix)
                    .setPostFix(postfix)
                    .setContent(content)
                    .setType(EXTEND)
                    .setSubTitle(subTitle)
                    .setSubstance(subStance)
                    .setResultType(resultType);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
