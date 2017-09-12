package com.chengshi.shop.util;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 枚举类
 * @author xuxinlong
 * @version 2017年07月11日
 */
public class EnumUtil extends EnumUtils {


    /**
     * 通用状态
     */
    public enum COMMONSTATUS {
        有效(1), 无效(2);
        private Integer value;

        COMMONSTATUS(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    public enum COUPONTYPE {
        全部商品(1), 指定商品(2);
        private Integer value;

        COUPONTYPE(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }


    /**
     * 根据数字找对应枚举类的释义
     *
     * @param enumName
     * @param param
     * @return
     */
    public static String getName(String enumName, Object param) {
        if (StringUtils.isBlank(enumName) || null == param) return null;
        Integer value = null;
        if (param instanceof Integer) {
            value = (Integer) param;
        } else if (param instanceof Byte) {
            value = Integer.valueOf(param.toString());
        } else if (param instanceof String) {
            value = Integer.valueOf(param.toString());
        }
        try {
            Class class1 = Class.forName(EnumUtil.class.getName() + "$" + enumName);
            for (Object obj : class1.getEnumConstants()) {
                if (class1.getMethod("getValue").invoke(obj).equals(value)) {
                    return class1.getMethod("name").invoke(obj).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }
}
