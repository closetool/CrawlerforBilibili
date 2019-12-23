package com.closetool.main;

import com.closetool.Pageprocessor.HtmlProcessor;
import com.closetool.Spider.Spider;
import sun.security.provider.ConfigFile;

import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Spider spider = new Spider("",
                "C:\\Users\\Administration\\Desktop", "https://www.bilibili.com/bangumi/play/ep251358");
        spider.run();
    }
}
