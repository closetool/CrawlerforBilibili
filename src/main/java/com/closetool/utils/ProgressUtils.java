package com.closetool.utils;

import java.util.Arrays;

public class ProgressUtils {
    public static long[] parseContentRange(String contentRange){
        String[] split = contentRange.split("/");
        long limit = Long.parseLong(split[1]);
        long current = Long.parseLong(split[0].split("-")[1]);
        return new long[]{limit,current};
    }

    public static void printProgress(long[] data){
        System.out.print("\r");
        int i = (int) (((double)data[1]/(double)data[0])*21);
        for (int j = 0; j < i; j++) {
            System.out.print('|');
        }
        for (int j = i; j < 20; j++) {
            System.out.print('-');
        }
        System.out.print("    "+(data[1]/1024/1024)+"/"+(data[0]/1024/1024)+"MB");
    }
}
