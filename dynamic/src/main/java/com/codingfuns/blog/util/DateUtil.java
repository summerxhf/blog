package com.codingfuns.blog.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    public synchronized static String getCreateDateString() {
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(new Date());
    }

    public synchronized static String getUpdateDateString() {
        simpleDateFormat.applyPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        return simpleDateFormat.format(new Date());
    }

    public synchronized static Date getCreateDate(String createDateString) {
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm");
        try {
            return simpleDateFormat.parse(createDateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public synchronized static Date getUpdateDate(String updateDateString) {
        simpleDateFormat.applyPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        try {
            return simpleDateFormat.parse(updateDateString);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
