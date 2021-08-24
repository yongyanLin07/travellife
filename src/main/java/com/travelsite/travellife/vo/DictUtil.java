package com.travelsite.travellife.vo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class DictUtil {

    /**
     获取菜单的名
     */
    public static String value(String code) {
        Map<String, String> value = new HashMap<>();

        String dictValue = "1:目录,2:菜单,3:按钮";
        String[] outerSplit = dictValue.split(",");
        value = new LinkedHashMap<>();
        for (String osp : outerSplit) {
            String[] split = osp.split(":");
            if(split.length > 1){
                value.put(split[0], split[1]);
            }
        }
        return value.get(code);
    }


}
