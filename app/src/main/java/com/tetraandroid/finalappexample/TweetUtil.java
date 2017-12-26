package com.tetraandroid.finalappexample;

/**
 * Created by MMT5544 on 26-12-2017.
 */

public class TweetUtil {

    public static final String RESULT = "result";
    public static final String HASH_TAG = "hashtag";
    public static String getMeHashTag(String str) {
        int end = str.length();
        int start = str.indexOf('#');
        for (int i = start; i < str.length(); i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                end = i - 1;
                break;
            }
        }
        return str.substring(start, end);
    }

}
