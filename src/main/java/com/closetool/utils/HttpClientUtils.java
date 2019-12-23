package com.closetool.utils;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class HttpClientUtils {

    //将从Fiddler中复制的header转换为对象
    public static Header[] String2Headers(String str){
        String[] headers_str = str.split("\n");
        ArrayList<Header> headers = new ArrayList<>();

        for (String s : headers_str) {
            String[] split = s.split(":", 2);
            String[] split1 = split[1].split(",");
            String value = "";
            for (int i = 0; i < split1.length; i++) {
                if (i != split1.length - 1) {
                    value += split1[i].trim() + ",";
                } else {
                    value += split1[i].trim();
                }
            }
            headers.add(new BasicHeader(split[0], value));
        }
        return headers.toArray(new Header[0]);
    }

    //转换为Map集合
    public static Map<String, List<String>> convertHeaders(Header[] headers){
        Map<String,List<String>> results = new HashMap<String, List<String>>();
        for (Header header : headers) {
            List<String> list = results.get(header.getName());
            if (list == null) {
                list = new ArrayList<String>();
                results.put(header.getName(), list);
            }
            list.add(header.getValue());
        }
        return results;
    }
}