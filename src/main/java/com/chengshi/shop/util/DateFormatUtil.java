package com.chengshi.shop.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转化工具类
 *
 * @author xuxinlong
 * @version 2017年07月10日
 */
public class DateFormatUtil {

    public static String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期转字符串 如:yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 日期字符串转date
     *
     * @param dateStr
     * @return
     */
    public static Date formatDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE_FORMAT);
        Date result = null;
        try {
            result = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static Integer currentTimeStamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 时间戳格式化输出
     *
     * @param ms     时间戳
     * @param format 格式化
     * @return
     */
    public static String transForDate(Integer ms, String format) {
        String str = "";
        if (ms != null) {
            long msl = (long) ms * 1000;
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if (!ms.equals(0)) {
                try {
                    str = sdf.format(msl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    /**
     * 将秒转换成时分秒
     *
     * @param second
     * @return
     */
    public static String transSecond(long second) {
        long h = 0;
        long d = 0;
        long s = 0;
        long temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
        if (h == 0) {
            if (d == 0) {
                return s + "秒";
            } else if (s == 0) {
                return d + "分钟";
            } else {
                return d + "分钟" + s + "秒";
            }
        } else if (d == 0) {
            if (s == 0) {
                return h + "小时";
            } else {
                return h + "小时" + s + "秒";
            }
        } else {
            return h + "小时" + d + "分钟" + s + "秒";
        }
    }
}
