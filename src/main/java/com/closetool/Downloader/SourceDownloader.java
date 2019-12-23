package com.closetool.Downloader;

import com.closetool.utils.ProgressUtils;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class SourceDownloader implements Downloader {
    protected String des;
    protected HttpClient client;
    protected HttpGet get;
    protected String filename;
    protected final static long len = 57 * 1024;


    @Override
    public void download() {
        File desFile = new File(des);
        if(!desFile.exists()) desFile.mkdirs();
        System.out.println("\nDownloading...\n");
        try {
            CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);
            long position = 0;
            get.setHeader("Range","bytes="+position +"-" + (position+len));
            response.close();
            long range = Long.parseLong(response.getHeaders("Content-Range")[0].getValue().split("/")[1]);
            int i = 0;
            FileOutputStream outputStream = new FileOutputStream(des+File.separator+filename);
            while(position < range){
                if(position + len >= range){
                    get.setHeader("Range","bytes="+position +"-" + (range - 1));
                }else{
                    get.setHeader("Range","bytes="+position +"-" + (position+len));
                }
                CloseableHttpResponse resp = (CloseableHttpResponse) client.execute(get);
                InputStream inputStream = resp.getEntity().getContent();
                byte[] bytes = new byte[1024];
                int length = 0;
                while((length = inputStream.read(bytes))!= -1){
                    outputStream.write(bytes,0,length);
                }
                position += len + 1;
                outputStream.flush();
                ProgressUtils.printProgress(
                        ProgressUtils.parseContentRange(
                                resp.getHeaders("Content-Range")[0]
                                        .getValue()));
                resp.close();
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
