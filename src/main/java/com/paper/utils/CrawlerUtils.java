package com.paper.utils;

public class CrawlerUtils {
    public static void sleepRandomMil(int low, int high){
        double mil = low + Math.random() * (high - low);
        try {
            Thread.sleep((int)mil);
        }catch (Exception ignored){

        }
    }
}
