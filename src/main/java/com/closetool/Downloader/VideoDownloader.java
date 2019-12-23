package com.closetool.Downloader;

import com.closetool.utils.HttpClientUtils;
import com.closetool.utils.UrlUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class VideoDownloader extends SourceDownloader {


    public VideoDownloader(String des, HttpClient client,String url_get,String url,String filename) {
        this.filename = filename+"-Video.m4s";
        this.des = des;
        this.client = client;
        this.get = new HttpGet(url_get);
        this.get.setHeaders(HttpClientUtils.String2Headers("Host: "+ UrlUtils.getHost(url_get) +"\n" +
                "Connection: keep-alive\n" +
                "Origin: https://www.bilibili.com\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36\n" +
                "Accept: */*\n" +
                "Sec-Fetch-Site: cross-site\n" +
                "Sec-Fetch-Mode: cors\n" +
                "Referer: "+url+"\n" +
                "Accept-Encoding: identity\n" +
                "Accept-Language: zh-CN,zh;q=0.9\n" +
                "Range: bytes=0-"+len+"\n"));
    }
}
