package com.cayden.utils;

/**
 * Created by CaydenPrivate on 17/10/7.
 */
public class URLGeneratedUtil {


    public final static String JD_ITEM_URL = "https://item.jd.com/%s.html";

    public static String getJDItemUrl(Object o){
        return String.format(JD_ITEM_URL,o);
    }
}
