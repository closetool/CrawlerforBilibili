package com.closetool.Downloader;

import com.closetool.utils.HttpClientUtils;
import com.closetool.utils.ProgressUtils;
import com.closetool.utils.UrlUtils;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioDownloader extends SourceDownloader {
    /**
     *
     * @param des 文件存放地址
     * @param url_get 视频地址
     * @param url AV号地址
     */
    public AudioDownloader(String des, HttpClient client, String url_get, String url,String filename) {
        this.filename = filename+"-Audio.m4s";
        this.des = des;
        this.client = client;
        this.get = new HttpGet(url_get);
        this.get.setHeaders(HttpClientUtils.String2Headers("Host: "+ UrlUtils.parseHost(url_get) +"\n" +
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
