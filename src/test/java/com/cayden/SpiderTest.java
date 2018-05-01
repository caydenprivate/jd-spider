package com.cayden;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderTest {


    public static void main(String[] args) {
        String regex = "(\\d+,\\d+,\\d+)";
        String url = "https://list.jd.com/list.html?cat=1713,9278&jth=i";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if(matcher.find()){
            System.out.println(matcher.group(1));
        }else{
            System.out.println("没有匹配");
        }


    }

}
