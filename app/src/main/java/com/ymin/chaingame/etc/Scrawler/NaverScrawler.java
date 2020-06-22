package com.ymin.chaingame.etc.Scrawler;


import com.ymin.chaingame.etc.Action;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NaverScrawler {

    public String search(String word) {
        JSONObject result = new JSONObject();
        // 초기 데이터 설정
        result.put("CONTENT", word);
        result.put("PREFIX", word.substring(0, 1).toUpperCase());
        result.put("POSTFIX", word.substring(word.length() - 1).toUpperCase());

        // 제공받은 문자를 소문자로 변경
        word = word.toLowerCase();
        StringBuilder sub = new StringBuilder();

        try {
            Document doc = Jsoup.connect("http://endic.naver.com/search.nhn?query="+word).get();
            Element content = doc.getElementById("content");

            JSONArray substance = new JSONArray();
            String clip;

            // 네이버 사전 페이지에서 크롤링
            Element dl = content.getElementsByTag("dl").get(1);
            Element dd = dl.getElementsByTag("dd").get(0);

            clip= dd.getElementsByClass("fnt_k05").get(0).text();
            substance.add(clip);

            Elements fnt = dd.getElementsByClass("pad6").get(0).getElementsByClass("fnt_k22");
            for(Element element : fnt){
                if(element.tagName().equals("a")){
                    clip =element.text();
                    substance.add(clip);
                }
            }

            // 단어를 찾는데 성공하면 알맞은 데이터를 넣어 리턴해준다.
            result.put("RETURN_TYPE", Action.SUCCESS);
            result.put("SUB_TITLE", word);
            result.put("SUBSTANCE", substance);

        } catch (Exception e){  // 단어를 찾는데 실패
            result.put("RETURN_TYPE", Action.SEARCH_FAIL);
            result.put("SUB_TITLE", "실패");
            result.put("SUBSTANCE", "단어를 찾을 수 없습니다.");
            result.put("POSTFIX","");
            return result.toJSONString();
        }
        return result.toJSONString();
    }
}
