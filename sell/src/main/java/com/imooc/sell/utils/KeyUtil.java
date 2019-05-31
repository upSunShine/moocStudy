package com.imooc.sell.utils;


import java.util.Random;

public class KeyUtil {


    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     */

    public static String getUniqueKey(){
        Random random = new Random();

        Integer s = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(s);
    }
}
