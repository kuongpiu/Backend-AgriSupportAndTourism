package com.example.agrisupportandtorism.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DescUtils {
    public static String DESCRIPTION_SPLITTER = ";";
    public static String KEY_VALUE_SPLITTER = ":";
    public static Map<String, String> convertStringToMap(String rawDesc){
        String[] descriptions = rawDesc.split(DESCRIPTION_SPLITTER);

        Map<String, String> descMap = new HashMap<>();

        for (int i = 0; i < descriptions.length; i++){
            String[] keyValueDescriptions = descriptions[i].split(KEY_VALUE_SPLITTER);

            if(keyValueDescriptions.length == 2){ // index of key = 0, index of value = 1
                descMap.put(keyValueDescriptions[0], keyValueDescriptions[1]);
            }
        }

        return descMap;
    }
    public static String convertMapToString(Map<String, String> descMap){
        StringBuilder stringBuilder = new StringBuilder();

        for(String key: descMap.keySet()){
            String value = descMap.get(key);
            if(value.trim().equals("")) continue;
            stringBuilder
                    .append(key)
                    .append(KEY_VALUE_SPLITTER)
                    .append(value)
                    .append(DESCRIPTION_SPLITTER);
        }

        return stringBuilder.toString();
    }
}
