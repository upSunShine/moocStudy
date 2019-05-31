package com.ext.springboot.springproducer.util;

import java.util.Date;

/**
 * @Author: xting
 * @CreateDate: 2019/5/29 15:40
 *
 * 时间工具类
 */
public class DateUtils {
    public static Date addMinutes(Date orderTime, int orderTimeout) {
        Date afterDate = new Date(orderTime.getTime() + 60000*orderTimeout);
        return afterDate;
    }
}
