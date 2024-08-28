package com.khajana.setting.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilForSp {
    public static String formatToCustomString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
