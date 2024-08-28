package com.khajana.setting.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String convertDateToString(Date date, String datePattern) {

        try {

            if (date == null)
                return "";
            if (datePattern == null || datePattern.isEmpty())
                return "";

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern, Locale.US);

            return simpleDateFormat.format(date);

        } catch (Exception e) {

            return "";
        }

    }

    public static Date convertStringToDate(String strDate, String datePattern) {

        try {

            if (strDate == null || strDate.isEmpty())
                return null;
            if (datePattern == null || datePattern.isEmpty())
                return null;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern, Locale.US);

            return simpleDateFormat.parse(strDate);

        } catch (Exception e) {

            return null;
        }

    }

}
