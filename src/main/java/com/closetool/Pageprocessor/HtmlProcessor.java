package com.closetool.Pageprocessor;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlProcessor implements Processor {
    private StringBuilder html;
    private List<String> video_url = new ArrayList<>();
    private List<String> audio_url = new ArrayList<>();
    private String name;

    public List<String> getVideo_url() {
        return video_url;
    }

    public List<String> getAudio_url() {
        return audio_url;
    }

    public HtmlProcessor(StringBuilder html) {
        this.html = html;
    }

    public String getName() {
        return name;
    }

    @Override
    public void process() {
        Pattern pattern_title = Pattern.compile("<title(.*?)>(.*?)</title>");
        Matcher matcher_title = pattern_title.matcher(html);
        if(matcher_title.find()){
            name = matcher_title.group(2);
        }else{
            name = null;
        }

        Pattern pattern = Pattern.compile("window.__playinfo__=(.*?)}<");
        Matcher matcher = pattern.matcher(html);
        String jsonstr = null;
        while(matcher.find()){
            jsonstr = matcher.group(1)+"}";
        }
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseObject(jsonstr).get("data").toString()).get("dash").toString());
        JSONArray video = JSONArray.parseArray(jsonObject.get("video").toString());
        JSONArray audio = JSONArray.parseArray(jsonObject.get("audio").toString());
        for (int i = 0; i < video.size(); i++) {
            parseUrl(JSONObject.parseObject(video.get(i).toString()).get("base_url").toString(),0);
            parseUrl(JSONObject.parseObject(video.get(i).toString()).get("baseUrl").toString(),0);
            parseUrl(JSONObject.parseObject(video.get(i).toString()).getJSONArray("backup_url").toString(),0);
            parseUrl(JSONObject.parseObject(video.get(i).toString()).getJSONArray("backupUrl").toString(),0);
        }
        for (int i = 0; i < audio.size(); i++) {
            parseUrl(JSONObject.parseObject(audio.get(i).toString()).get("base_url").toString(), 1);
            parseUrl(JSONObject.parseObject(audio.get(i).toString()).get("baseUrl").toString(), 1);
            parseUrl(JSONObject.parseObject(audio.get(i).toString()).getJSONArray("backup_url").toString(), 1);
            parseUrl(JSONObject.parseObject(audio.get(i).toString()).getJSONArray("backupUrl").toString(), 1);
        }

    }

    private void parseUrl(String o,int sign){
        if(o.startsWith("[")){
            for (Object o1 : JSONArray.parseArray(o)) {
                if(sign == 1){
                    audio_url.add(o1.toString());
                }else{
                    video_url.add(o1.toString());
                }
            }
        }else{
            if(sign == 1){
                audio_url.add(o);
            }else{
                video_url.add(o);
            }
        }
    }


    private String handleFilename(String filename){
        filename = filename.replace(File.separator,"-");
        filename = filename.replace("\\","-");
        return filename.replace("/","-");
    }

}
