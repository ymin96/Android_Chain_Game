package com.ymin.chaingame.etc;

public class Action {

    // 아이템 타입을 구분하기 위한 type 변수
    private int type;

    public Action(){
        this.type = 0;  // 기본 축소 타입
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
}
