package com.ymin.chaingame.api;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;

public interface BaseTranslate {
    String run(String query);
    void setKo_To_En();
    void setEn_to_Ko();
}
