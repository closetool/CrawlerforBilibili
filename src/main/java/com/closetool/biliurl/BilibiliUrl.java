package com.closetool.biliurl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BilibiliUrl {

    public static final int baseUrl = 0;
    public static final int base_url = 1;
    public static final int backupUrl = 2;

    private Map<Integer, List<String>> audio_url = new HashMap<>(3);
    private Map<Integer, List<String>> video_url = new HashMap<>(3);

    public void addAudio_url(int which,String url){
        audio_url.computeIfAbsent(which, k -> new ArrayList<>());
        audio_url.get(which).add(url);
    }

    public void addVideo_url(int which,String url){
        video_url.computeIfAbsent(which,k -> new ArrayList<>());
        video_url.get(which).add(url);
    }

    public void parseUrl(String which,String urls){

    }
}
