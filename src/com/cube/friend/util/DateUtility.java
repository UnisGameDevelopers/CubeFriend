package com.cube.friend.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtility {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm", Locale.KOREA);


    public static String getRemainingDate(Date d1, Date d2) {
        try {
            d1 = sdf.parse(sdf.format(d1));
            d2 = sdf.parse(sdf.format(d2));
            long diff = d1.getTime() - d2.getTime();

            long diffMinutes = Math.abs(diff / (60 * 1000) % 60);
            long diffHours = Math.abs(diff / (60 * 60 * 1000)) % 24;
            long diffDays = Math.abs(diff / (24 * 60 * 60 * 1000));

            return "§e" + diffDays + "일 " + diffHours + "시간 " + diffMinutes + "분";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
