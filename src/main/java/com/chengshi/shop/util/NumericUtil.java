package com.chengshi.shop.util;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

/**
 * 数字格式化工具类
 *
 * @author wr
 * @date 创建时间：2017年7月15日 *
 */
public class NumericUtil {

    /**
     * 获取唯一的12位数字
     *
     * @return
     */
    public static String getUUIDNum() {
        int hashCodeV = UUID.randomUUID().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        return (int) (Math.random() * 90 + 10) + String.format("%010d", hashCodeV);
    }

    /**
     * 数字格式化万以上单位
     *
     * @param s
     * @return
     */
    public static String formatBigNum(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        double num = Double.parseDouble(s);
        DecimalFormat df = new DecimalFormat("#.##");
        if (num >= 100000000) {
            int m = (int) (num / 100000000);
            double a = num - (double) m * 100000000;
            double i = a / 10000;
            df = new DecimalFormat("#");
            if (a == 0) {
                return m + "亿";
            } else {
                return m + "亿" + df.format(i) + "万";
            }
        } else if (num >= 10000) {
            double n = num / 10000;
            return df.format(n) + "万";
        } else {
            return df.format(num);
        }
    }

    /**
     * 获取随机6位数字
     *
     * @return
     */
    public static int getRandomSixNum() {
        int max = 999999;
        int min = 100000;
        return new Random().nextInt(max - min) + min;
    }

    /**
     * 格式化距离
     *
     * @param distance
     * @return
     */
    public static String formatDistance(double distance) {
        if (distance < 1000) {
            return distance + "m";
        } else {
            return distance / 1000 + "km";
        }
    }
}
