package com.chengshi.shop.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

    /**
     * 时间戳转日期
     *
     * @param ms
     * @return
     */
    public static Date transForDate(Integer ms) throws ParseException {
        if (ms == null) {
            ms = 0;
        }
        long msl = (long) ms * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(msl);
        return sdf.parse(str);
    }

    public static String transForString(Integer ms) {
        String str = "";
        if (ms != null) {
            long msl = (long) ms * 1000;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = sdf.format(msl);
        }
        return str;
    }

    public static String transForString(Integer ms, String format) {
        String str = "";
        if (ms != null) {
            long msl = (long) ms * 1000;
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            str = sdf.format(msl);
        }
        return str;
    }

    public static String transForDateInChinese(Integer ms) {
        String str = "";
        if (ms != null) {
            long msl = (long) ms * 1000;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            str = sdf.format(msl);
        }
        return str;
    }

    public static String transForDateInChinese(Integer ms, String format) {
        String str = "";
        if (ms != null) {
            long msl = (long) ms * 1000;
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            str = sdf.format(msl);
        }
        return str;
    }

    /**
     * 日期转时间戳
     *
     * @param date
     * @return
     */
    public static Integer transForMilliSecond(Date date) {
        if (date == null)
            return null;
        return (int) (date.getTime() / 1000);
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
     * 日期字符串转时间戳
     *
     * @param dateStr
     * @return
     */
    public static Integer transForMilliSecond(String dateStr) throws ParseException {
        Date date = DateFormatUtil.formatDate(dateStr);
        return date == null ? null : DateFormatUtil.transForMilliSecond(date);
    }

    /**
     * 日期字符串转时间戳
     *
     * @param dateStr
     * @return
     */
    public static Integer transForMilliSecond(String dateStr, String format) {
        Date date = DateFormatUtil.formatDate(dateStr, format);
        return date == null ? null : DateFormatUtil.transForMilliSecond(date);
    }

    /**
     * 字符串转日期，格式为："yyyy-MM-dd HH:mm:ss"
     *
     * @param dateStr
     * @return
     */
    public static Date formatDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(dateStr);
    }

    /**
     * 字符串转日期，格式为："yyyy-MM-dd HH:mm:ss"
     *
     * @param dateStr
     * @return
     */
    public static Date formatDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date result = null;
        try {
            result = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
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
                str = sdf.format(msl);
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
    public static String transSecond(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
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
