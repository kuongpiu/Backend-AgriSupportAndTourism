package com.example.agrisupportandtorism.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UrlUntil {
    public final static String URL_SPLITTER = ",";
    public static String convertUrlListToString(List<String> urls){
        if(urls != null){
            return String.join(URL_SPLITTER, urls);
        }
        return "";
    }
    public static List<String> convertStringToUrlList(String url){
        if(!url.isEmpty()){
            return Arrays.asList(url.split(URL_SPLITTER));
        }else{
            return new ArrayList<>();
        }
    }
}