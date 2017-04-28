package com.intelygenz.esrarakici.newsreader.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by esrarakici on 27/04/2017.
 */

public class StringUtils {
    public static String removeTags(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        Pattern REMOVE_TAGS = Pattern.compile("<.+?>");
        Matcher m = REMOVE_TAGS.matcher(string);
        return m.replaceAll("").trim();
    }
}
