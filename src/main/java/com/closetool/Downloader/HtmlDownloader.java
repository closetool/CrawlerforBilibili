package com.closetool.Downloader;

import com.closetool.utils.HttpClientUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;



public class HtmlDownloader implements Downloader {
    private StringBuilder html;
    private HttpClient client;
    private HttpGet get;

    public HtmlDownloader(HttpClient client, String url) throws MalformedURLException {
        if (client != null)
            this.client = client;
        else
            throw new NullPointerException("Client is null");

        if (url == null || url.equals("")) throw new NullPointerException("url is empty");
        else get = new HttpGet(url);
        Header[] headers = HttpClientUtils.String2Headers("Host: www.bilibili.com\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36\n" +
                "Sec-Fetch-User: ?1\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n" +
                "Sec-Fetch-Site: same-site\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Referer: https://www.bilibili.com\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: zh-CN,zh;q=0.9");
        get.setHeaders(headers);
    }

    public HttpGet getGet() {
        return get;
    }

    public StringBuilder getHtml() {
        return html;
    }

    /**
     * 使用前一定要添加cookie
     * @param cookie cookie最好使用登录后的cookie
     */
    public void setCookie(String cookie) {
        get.addHeader("Cookie", cookie);
    }

    @Override
    public void download() {
        html = new StringBuilder();
        HttpResponse response = null;
        try {
            response = client.execute(get);
            InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());
            int length = 0;
            char[] chars = new char[50];
            while ((length = reader.read(chars)) != -1) {
                html.append(chars, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
