package com.closetool.utils;

import java.net.MalformedURLException;
import java.net.URL;

public final class UrlUtils {
    public static String parseHost(String url){
        String host = null;
        try {
            URL url_o = new URL(url);
            host = url_o.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if(host == null) throw new RuntimeException("解析Host失败");
        return host;
    }


    public static String parseP(String url){

        if(url.contains("bangumi")){
            String[] strings = url.split("/");
            return strings[strings.length - 1];
        }else{
            int i = 1;
            try{
                i = Integer.parseInt(url.split("\\?")[1].split("=")[1]);
            }catch (ArrayIndexOutOfBoundsException e){
            }
            return String.valueOf(i);
        }
    }
}
