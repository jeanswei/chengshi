package com.chengshi.shop.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author xuxinlong
 * @version 2017年09月19日
 */
public class JsonToHtmlUtil {

    /**
     * 输出json到HTML
     *
     * @param response
     * @param map
     */
    public static void outJson(HttpServletResponse response, HashMap<String, Object> map) {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        try {
            response.getWriter().println(hashMapToJson(map));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把数据源HashMap转换成json
     *
     * @param map
     */
    public static String hashMapToJson(HashMap map) {
        String string = "{";
        for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry e = (Map.Entry) it.next();
            string += "\"" + e.getKey() + "\":";
            string += "\"" + e.getValue() + "\",";
        }
        string = string.substring(0, string.lastIndexOf(","));
        string += "}";
        return string;
    }
}
