package com.closetool.Spider;

import com.closetool.Downloader.AudioDownloader;
import com.closetool.Downloader.HtmlDownloader;
import com.closetool.Downloader.VideoDownloader;
import com.closetool.Pageprocessor.HtmlProcessor;
import com.closetool.utils.UrlUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.net.MalformedURLException;

public class Spider implements Crawl {
    private HttpClient client = HttpClients.createDefault();
    private String cookie;
    private String des;
    private String url;

    public Spider(String cookie, String des, String url) {
        this.cookie = cookie;
        this.des = des;
        this.url = url;
    }

    @Override
    public void run() throws MalformedURLException {
        HtmlDownloader downloader = new HtmlDownloader(client, url);
        downloader.setCookie(cookie);
        downloader.download();

        StringBuilder builder = downloader.getHtml();
        HtmlProcessor htmlProcessor = new HtmlProcessor(builder);
        htmlProcessor.process();
        des += File.separator + htmlProcessor.getName() + File.separator + UrlUtils.parseDir(url);
        new Thread(() -> {
            VideoDownloader videoDownloader = new VideoDownloader(des,
                    HttpClients.createDefault(), htmlProcessor.getVideo_url().get(0),
                    url, htmlProcessor.getName());
            videoDownloader.download();
        }).start();
        AudioDownloader audioDownloader = new AudioDownloader(des,
                HttpClients.createDefault(), htmlProcessor.getAudio_url().get(0),
                url, htmlProcessor.getName());
        audioDownloader.download();
    }
}
